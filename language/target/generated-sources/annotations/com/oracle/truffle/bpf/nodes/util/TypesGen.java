// CheckStyle: start generated
package com.oracle.truffle.bpf.nodes.util;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.bpf.nodes.util.JumpLambda;
import com.oracle.truffle.bpf.nodes.util.MemLambda;
import com.oracle.truffle.bpf.nodes.util.RegLambda;
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

    public static boolean isJumpLambda(Object value) {
        return value instanceof JumpLambda;
    }

    public static JumpLambda asJumpLambda(Object value) {
        assert value instanceof JumpLambda : "TypesGen.asJumpLambda: JumpLambda expected";
        return (JumpLambda) value;
    }

    public static JumpLambda expectJumpLambda(Object value) throws UnexpectedResultException {
        if (value instanceof JumpLambda) {
            return (JumpLambda) value;
        }
        throw new UnexpectedResultException(value);
    }

    public static boolean isRegLambda(Object value) {
        return value instanceof RegLambda;
    }

    public static RegLambda asRegLambda(Object value) {
        assert value instanceof RegLambda : "TypesGen.asRegLambda: RegLambda expected";
        return (RegLambda) value;
    }

    public static RegLambda expectRegLambda(Object value) throws UnexpectedResultException {
        if (value instanceof RegLambda) {
            return (RegLambda) value;
        }
        throw new UnexpectedResultException(value);
    }

    public static boolean isMemLambda(Object value) {
        return value instanceof MemLambda;
    }

    public static MemLambda asMemLambda(Object value) {
        assert value instanceof MemLambda : "TypesGen.asMemLambda: MemLambda expected";
        return (MemLambda) value;
    }

    public static MemLambda expectMemLambda(Object value) throws UnexpectedResultException {
        if (value instanceof MemLambda) {
            return (MemLambda) value;
        }
        throw new UnexpectedResultException(value);
    }

}
