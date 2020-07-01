// CheckStyle: start generated
package com.oracle.truffle.bpf.nodes.util;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.bpf.nodes.util.Memory;
import com.oracle.truffle.bpf.nodes.util.Types;

@GeneratedBy(Types.class)
public final class TypesGen extends Types {

    @Deprecated public static final TypesGen TYPES = new TypesGen();

    protected TypesGen() {
    }

    public static boolean isBoolean(Object value) {
        return value instanceof Boolean;
    }

    public static boolean asBoolean(Object value) {
        assert value instanceof Boolean : "TypesGen.asBoolean: boolean expected";
        return (boolean) value;
    }

    public static boolean expectBoolean(Object value) throws UnexpectedResultException {
        if (value instanceof Boolean) {
            return (boolean) value;
        }
        throw new UnexpectedResultException(value);
    }

    public static boolean isInteger(Object value) {
        return value instanceof Integer;
    }

    public static int asInteger(Object value) {
        assert value instanceof Integer : "TypesGen.asInteger: int expected";
        return (int) value;
    }

    public static int expectInteger(Object value) throws UnexpectedResultException {
        if (value instanceof Integer) {
            return (int) value;
        }
        throw new UnexpectedResultException(value);
    }

    public static boolean isLongArray(Object value) {
        return value instanceof long[];
    }

    public static long[] asLongArray(Object value) {
        assert value instanceof long[] : "TypesGen.asLongArray: long[] expected";
        return (long[]) value;
    }

    public static long[] expectLongArray(Object value) throws UnexpectedResultException {
        if (value instanceof long[]) {
            return (long[]) value;
        }
        throw new UnexpectedResultException(value);
    }

    public static boolean isMemory(Object value) {
        return value instanceof Memory;
    }

    public static Memory asMemory(Object value) {
        assert value instanceof Memory : "TypesGen.asMemory: Memory expected";
        return (Memory) value;
    }

    public static Memory expectMemory(Object value) throws UnexpectedResultException {
        if (value instanceof Memory) {
            return (Memory) value;
        }
        throw new UnexpectedResultException(value);
    }

}
