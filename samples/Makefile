all: primes.bpf primes_1000.bpf primes_10000.bpf euler49.bpf

# make for bpf
%.o: %.c
	clang -c -target bpf -O2 $<

# make executable
%: %.c
	gcc -O2 -fsanitize=address -DDEBUG $< -o $@

%.bpf: %.o
	python3 loader.py $< $@
