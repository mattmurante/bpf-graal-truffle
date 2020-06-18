// CheckStyle: start generated
package com.oracle.truffle.bpf.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.bpf.nodes.DoubleWordHelperNode;
import com.oracle.truffle.bpf.nodes.util.EBPFOpcodes;
import com.oracle.truffle.bpf.nodes.util.RegLambda;

@GeneratedBy(DoubleWordHelperNode.class)
public final class DoubleWordHelperNodeGen extends DoubleWordHelperNode {

    private final byte opcode;
    private final byte srcReg;
    private final byte destReg;
    private final short offset;
    private final int imm;
    @CompilationFinal private int state_;

    private DoubleWordHelperNodeGen(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
        this.opcode = opcode;
        this.srcReg = srcReg;
        this.destReg = destReg;
        this.offset = offset;
        this.imm = imm;
    }

    @Override
    public byte getOpcode() {
        return this.opcode;
    }

    @Override
    public byte getSrcReg() {
        return this.srcReg;
    }

    @Override
    public byte getDestReg() {
        return this.destReg;
    }

    @Override
    public short getOffset() {
        return this.offset;
    }

    @Override
    public int getImm() {
        return this.imm;
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state = state_;
        if (state != 0 /* is-active doLDDW() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_LDDW);
            return doLDDW();
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize();
    }

    private RegLambda executeAndSpecialize() {
        int state = state_;
        if ((opcode == EBPFOpcodes.EBPF_OP_LDDW)) {
            this.state_ = state = state | 0b1 /* add-active doLDDW() */;
            return doLDDW();
        }
        throw new UnsupportedSpecializationException(this, new Node[] {});
    }

    @Override
    public NodeCost getCost() {
        int state = state_;
        if (state == 0b0) {
            return NodeCost.UNINITIALIZED;
        } else {
            return NodeCost.MONOMORPHIC;
        }
    }

    public static DoubleWordHelperNode create(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
        return new DoubleWordHelperNodeGen(opcode, srcReg, destReg, offset, imm);
    }

}
