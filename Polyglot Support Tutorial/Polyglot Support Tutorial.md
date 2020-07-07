# Making a Truffle Language that can Host Guest Languages From Within A Program

A noteable feature of Oracle’s GraalVM and Truffle combination is the ability for languages built using the Truffle API to interact in a polyglot context almost seamlessly. Each of Oracle’s Truffle languages have built-in support for this functionality, with one language acting as a host that can then execute code from a guest language. For example, `graalpython` can be run with the command-line option `--polyglot`, which allows access to any Truffle languages that is installed into GraalVM, and then programs for any guest language can be executed within the python script with a builtin command called `eval`. However, the ways in which this can be implemented within a Truffle language are not well documented or very clear from Oracle’s documentation or from the source code of their Truffle languages. The following is an overview on how to easily achieve polyglot capabilities with your own Truffle language, and clear up any pitfalls one could run into during the process.

## How do I change the way my Truffle language is built to allow polyglot?

The typical process for building a Truffle language is as follows:
1. Develop a parser to implement the syntax of the language
2. Implement the semantics by defining each of the Truffle nodes required by the language
3. Create a `node factory` object that follows along with the parser and generates a corresponding Truffle node for each language construct, ultimately forming Truffle trees for each function call

Once you have completed these three steps (in whatever order makes the most sense for you), you have built a language that essentially gets Java JIT compilation for free. It is worth noting that the Truffle DSL can do a lot of the work for you by providing annotations that can help reduce boilerplate code as well as signal to the compiler to provide various optimizations, but the details are better left to a tutorial dedicated to creating Truffle languages in general.

In order for your language to run guest language code, both the syntax and semantics must be implemented. That means you will have to modify your parser to support an interlanguage operation, and also have a specific Truffle node that carries out this action. This can be achieved in different ways, but to illustrate the way I found easiest and most straightforward, I modified Oracle's test language `Simple language` to run `(e)BPF` programs using my Truffle implementation of (e)BPF. My revision of Simple language can be found in the same directory as this tutorial, and if you were to look through my version and compare it to the original, you would not find many differences between the two. This is because Simple language's parser already provided a builtin keyword for evaluating guest programs, `eval`, as well as a Truffle node to define how it operates. Since the syntax and semantics for polyglot were already in place, pretty much all I needed to do was make my (e)BPF implementation accessible to the Simple language `Context` to get Simple language to run (e)BPF programs.

## What is a context and how do I make a guest language "visible" to my host language?

When defining a Truffle language, the API expects you to register your language using the `@TruffleLanguage.Registration` annotation, which entails setting properties for your language such as a `Language name, ID, default mime type`, and others. This is to make it easier for your language to utilize the Truffle API and better support language interoperability, which in turn makes implementing your language easier as well. Assuming you've already completed the three main steps for making a Truffle language, after you have also filled in the language information required by the Truffle API, running a program for your language is very simple. You first create a `Source` object, which is provided by GraalVM's `polyglot` package, which also has a `Context` object that you must instantiate in order to execute the source. Registering your language is very important to this process, because the Source and Context `Builders` are specific to the `Language IDs` that you provide them. Thus, if you want to make a Source object for Simple language (Language ID = `"sl"`), you would write:

`Source source = Source.newBuilder("sl", new InputStreamReader(System.in)).build();`

if the source code for the Simple language program was coming from standard input. This ties the source object to your Truffle implementation of your language, which can then be executed with the context object:

`Context context = Context.newBuilder("sl").build();`
`Value result = context.eval(source);`

For clarification, `Value` is another class provided by the polyglot package in GraalVM that stores the generic result of a given Truffle language's source execution, and `context.eval()` should not be confused with the `eval` method defined in Simple language to provide guest language support. There are additional options you can add when building a Source or a Context, but the key idea here is that they both are designed for storing and executing source code of a generic Truffle language, and are not specific to any language in particular. This makes it simple to run any other Truffle language from within another language, by simply providing the Language ID to the Source and Context objects. For instance, if I wanted to run my (e)BPF Truffle Language (Language ID = `"bpf"`) that is given a program `code` from within SL, all I would need to put would be:

`Source source = Source.newBuilder("bpf", code).build();`
`Context context = Context.newBuilder("bpf").build();`
`return context.eval(source);`

and this is almost exactly how it is implemented in Simple language's builtin `eval` node. The generation of Source is the same, the main difference is in the execution of the source, which is performed by the language-specific context (which is attached to your Truffle language during creation of your language class, and not the same as the polyglot context referenced above) via its `parse()` method. The language context requests that the Truffle language `environment` make a `parsePublic()` call, which essentially tells the polyglot context to create a Truffle `callTarget` for the language id specified. This callTarget can be incorporated into the Simple language Truffle tree since callTarget is a Truffle API construct and is not language specific, and can be executed with its `call()` method. So what we originally had above becomes:

`Source source = Source.newBuilder("bpf", code).build();`
`return context.parse(source) //Given context is the language-specific SLContext passed to this function`

and this is actually how the builtin `eval` node is implemented in the original Simple language implementation. The major difference here is that a new Source object was created for `"bpf"` source code, but we did not make a new context, because we want to run the (e)BPF program within the existing context. But to run (e)BPF code, the language ID for my (e)BPF implementation must be provided to the polyglot context, and when the original polyglot context was created, we only permitted use of Simple language. To amend this, we can either add `"bpf"` as a permitted language:

`Context context = Context.newBuilder("sl", "bpf").build();`

or just leave the arguments empty to allow all languages to be used:

`Context context = Context.newBuilder().build();`

The latter is what graalpython switches its context to when provided with the `--polyglot` command-line option.

As a side note, I still had to change the builtin `eval` Truffle node a bit more to support my (e)BPF Truffle language, since my language was defined to interpret a byte Source instead of a character Source. This was easily achieved by creating a condition for when the language passed to `eval` has id "bpf"; for the language (e)BPF, `eval` will read bytes from a specified file and create a byte Source by building the Source object as follows:

`Source source = Source.newBuilder("bpf", ByteSequence.create(bytes)).build() //Where bytes is the byte array read from the source file`

So now Simple language can parse the `eval` keyword, the `node factory` will follow along with the parser and create an `eval` Truffle node when needed, and if the language `"bpf"` is specified this node will execute bytes from the desired source file which is possible because the polyglot context supports `"bpf"`. Are we done?

## Installing a language into GraalVM

We are not done yet. We can give Simple language's polyglot context the language ID `"bpf"`, but this has no meaning because `"bpf"` was registered with the (e)BPF Truffle language in another project. Now we need to provide GraalVM with all of our languages so that they can interact with each other. Building the (e)BPF and Simple languages generates component jar files, which can be installed into GraalVM so that GraalVM has access to these languages when they are needed during runtime. To install a component, ensure the `JAVA_HOME` environment variable is pointing to the home directory in GraalVM and run:

`gu -L install {path-to-component.jar}`

After (e)BPF and simple language are installed into GraalVM, executing a Simple language program with an embedded (e)BPF program should be simple:

`bpf_result = eval("bpf", {path-to-program.bpf}); //This is Simple language code`

Test.sl is provided in the directory that contains this tutorial to showcase this functionality.

## Native Image support

Polyglot support within a program is also possible with `native-image`. The native image generator creates an executable binary that can run independent of GraalVM, but it is still necessary to install your component jars into GraalVM even if your target is a native image. This is because when building a native image that uses polyglot, in order to provide access to other languages, you must give `--language:your-language-id` as a command-line argument. So if the guest language component is not installed into GraalVM, the language id you want to provide to the native image generator will not be recognized. As an example, after I installed both (e)BPF and Simple language into GraalVM, I built the native image for Simple language with support for running (e)BPF programs by running the following:

`"$JAVA_HOME"/bin/native-image \`
`--macro:truffle --no-fallback --initialize-at-build-time --verbose --language:bpf --no-server` `--report-unsupported-elements-at-runtime \`
`-cp ../language/target/simplelanguage.jar:../launcher/target/sl-launcher.jar \`
`com.oracle.truffle.sl.launcher.SLMain \`
`slnative`

## Extra info

If you would like to examine the source code for my version of Simple language, I recommend looking at `com.oracle.truffle.sl.builtins.SLEvalBuiltin.java` in the simplelanguage language project and the main class `com.oracle.truffle.sl.launcher.SLMain.java` in the simplelanguage launcher project. If you want additional resources, I would read the documentation at https://www.graalvm.org/docs/, as well as examine the graalpython implementation at https://github.com/graalvm/graalpython.