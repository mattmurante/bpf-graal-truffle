package com.oracle.truffle.bpf.nodes.util;

import com.oracle.truffle.api.dsl.TypeSystem;

//Defines a typesystem for the Truffle API

@TypeSystem({boolean.class, int.class, long[].class, Memory.class})

public class Types {
}