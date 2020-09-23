// CheckStyle: start generated
package com.oracle.truffle.bpf;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleFile.FileTypeDetector;
import com.oracle.truffle.api.TruffleLanguage.ContextPolicy;
import com.oracle.truffle.api.TruffleLanguage.Provider;
import com.oracle.truffle.api.TruffleLanguage.Registration;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.bpf.BPFLanguage;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@GeneratedBy(BPFLanguage.class)
@Registration(byteMimeTypes = {"application/x-bpf"}, contextPolicy = ContextPolicy.SHARED, defaultMimeType = "application/x-bpf", id = "bpf", name = "BPF")
public final class BPFLanguageProvider implements Provider {

    @Override
    public String getLanguageClassName() {
        return "com.oracle.truffle.bpf.BPFLanguage";
    }

    @Override
    public TruffleLanguage<?> create() {
        return new BPFLanguage();
    }

    @Override
    public List<FileTypeDetector> createFileTypeDetectors() {
        return Collections.emptyList();
    }

    @Override
    public Collection<String> getServicesClassNames() {
        return Collections.emptySet();
    }

}
