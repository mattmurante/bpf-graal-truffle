// CheckStyle: start generated
package com.oracle.truffle.bpf.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.bpf.nodes.MemInstructionNode;
import com.oracle.truffle.bpf.nodes.util.EBPFOpcodes;
import com.oracle.truffle.bpf.nodes.util.MemLambda;

@GeneratedBy(MemInstructionNode.class)
public final class MemInstructionNodeGen extends MemInstructionNode {

    private final byte opcode;
    private final byte srcReg;
    private final byte destReg;
    private final short offset;
    private final int imm;
    @CompilationFinal private int state_;

    private MemInstructionNodeGen(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
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
        if ((state & 0b1) != 0 /* is-active doSTXDW() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_STXDW);
            return doSTXDW();
        }
        if ((state & 0b10) != 0 /* is-active doSTXW() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_STXW);
            return doSTXW();
        }
        if ((state & 0b100) != 0 /* is-active doSTXH() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_STXH);
            return doSTXH();
        }
        if ((state & 0b1000) != 0 /* is-active doSTXB() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_STXB);
            return doSTXB();
        }
        if ((state & 0b10000) != 0 /* is-active doSTDW() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_STDW);
            return doSTDW();
        }
        if ((state & 0b100000) != 0 /* is-active doSTW() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_STW);
            return doSTW();
        }
        if ((state & 0b1000000) != 0 /* is-active doSTH() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_STH);
            return doSTH();
        }
        if ((state & 0b10000000) != 0 /* is-active doSTB() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_STB);
            return doSTB();
        }
        if ((state & 0b100000000) != 0 /* is-active doLDXDW() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_LDXDW);
            return doLDXDW();
        }
        if ((state & 0b1000000000) != 0 /* is-active doLDXW() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_LDXW);
            return doLDXW();
        }
        if ((state & 0b10000000000) != 0 /* is-active doLDXH() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_LDXH);
            return doLDXH();
        }
        if ((state & 0b100000000000) != 0 /* is-active doLDXB() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_LDXB);
            return doLDXB();
        }
        if ((state & 0b1000000000000) != 0 /* is-active doLDDW() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_LDDW);
            return doLDDW();
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize();
    }

    private MemLambda executeAndSpecialize() {
        int state = state_;
        if ((opcode == EBPFOpcodes.EBPF_OP_STXDW)) {
            this.state_ = state = state | 0b1 /* add-active doSTXDW() */;
            return doSTXDW();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_STXW)) {
            this.state_ = state = state | 0b10 /* add-active doSTXW() */;
            return doSTXW();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_STXH)) {
            this.state_ = state = state | 0b100 /* add-active doSTXH() */;
            return doSTXH();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_STXB)) {
            this.state_ = state = state | 0b1000 /* add-active doSTXB() */;
            return doSTXB();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_STDW)) {
            this.state_ = state = state | 0b10000 /* add-active doSTDW() */;
            return doSTDW();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_STW)) {
            this.state_ = state = state | 0b100000 /* add-active doSTW() */;
            return doSTW();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_STH)) {
            this.state_ = state = state | 0b1000000 /* add-active doSTH() */;
            return doSTH();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_STB)) {
            this.state_ = state = state | 0b10000000 /* add-active doSTB() */;
            return doSTB();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_LDXDW)) {
            this.state_ = state = state | 0b100000000 /* add-active doLDXDW() */;
            return doLDXDW();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_LDXW)) {
            this.state_ = state = state | 0b1000000000 /* add-active doLDXW() */;
            return doLDXW();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_LDXH)) {
            this.state_ = state = state | 0b10000000000 /* add-active doLDXH() */;
            return doLDXH();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_LDXB)) {
            this.state_ = state = state | 0b100000000000 /* add-active doLDXB() */;
            return doLDXB();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_LDDW)) {
            this.state_ = state = state | 0b1000000000000 /* add-active doLDDW() */;
            return doLDDW();
        }
        throw new UnsupportedSpecializationException(this, new Node[] {});
    }

    @Override
    public NodeCost getCost() {
        int state = state_;
        if (state == 0b0) {
            return NodeCost.UNINITIALIZED;
        } else if ((state & (state - 1)) == 0 /* is-single-active  */) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    public static MemInstructionNode create(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
        return new MemInstructionNodeGen(opcode, srcReg, destReg, offset, imm);
    }

}
