// CheckStyle: start generated
package com.oracle.truffle.bpf.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.bpf.nodes.JumpInstructionNode;
import com.oracle.truffle.bpf.nodes.util.EBPFOpcodes;

@GeneratedBy(JumpInstructionNode.class)
public final class JumpInstructionNodeGen extends JumpInstructionNode {

    private final byte opcode;
    private final byte srcReg;
    private final byte destReg;
    private final short offset;
    private final int imm;
    @CompilationFinal private int state_;

    private JumpInstructionNodeGen(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
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
        if ((state & 0b1) != 0 /* is-active doJEQ_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_JEQ_IMM);
            return doJEQ_IMM();
        }
        if ((state & 0b10) != 0 /* is-active doJNE_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_JNE_IMM);
            return doJNE_IMM();
        }
        if ((state & 0b100) != 0 /* is-active doJNE_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_JNE_REG);
            return doJNE_REG();
        }
        if ((state & 0b1000) != 0 /* is-active doJEQ_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_JEQ_REG);
            return doJEQ_REG();
        }
        if ((state & 0b10000) != 0 /* is-active doJGE_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_JGE_IMM);
            return doJGE_IMM();
        }
        if ((state & 0b100000) != 0 /* is-active doJGE_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_JGE_REG);
            return doJGE_REG();
        }
        if ((state & 0b1000000) != 0 /* is-active doJGT_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_JGT_REG);
            return doJGT_REG();
        }
        if ((state & 0b10000000) != 0 /* is-active doJGT_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_JGT_IMM);
            return doJGT_IMM();
        }
        if ((state & 0b100000000) != 0 /* is-active doJSGT_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_JSGT_REG);
            return doJSGT_REG();
        }
        if ((state & 0b1000000000) != 0 /* is-active doJSGT_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_JSGT_IMM);
            return doJSGT_IMM();
        }
        if ((state & 0b10000000000) != 0 /* is-active doJSGE_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_JSGE_IMM);
            return doJSGE_IMM();
        }
        if ((state & 0b100000000000) != 0 /* is-active doJSGE_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_JSGE_REG);
            return doJSGE_REG();
        }
        if ((state & 0b1000000000000) != 0 /* is-active doJLT_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_JLT_IMM);
            return doJLT_IMM();
        }
        if ((state & 0b10000000000000) != 0 /* is-active doJLT_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_JLT_REG);
            return doJLT_REG();
        }
        if ((state & 0b100000000000000) != 0 /* is-active doJLE_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_JLE_IMM);
            return doJLE_IMM();
        }
        if ((state & 0b1000000000000000) != 0 /* is-active doJLE_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_JLE_REG);
            return doJLE_REG();
        }
        if ((state & 0x10000) != 0 /* is-active doJSLT_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_JSLT_IMM);
            return doJSLT_IMM();
        }
        if ((state & 0x20000) != 0 /* is-active doJSLT_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_JSLT_REG);
            return doJSLT_REG();
        }
        if ((state & 0x40000) != 0 /* is-active doJSLE_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_JSLE_IMM);
            return doJSLE_IMM();
        }
        if ((state & 0x80000) != 0 /* is-active doJSLE_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_JSLE_REG);
            return doJSLE_REG();
        }
        if ((state & 0x100000) != 0 /* is-active doJSET_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_JSET_REG);
            return doJSET_REG();
        }
        if ((state & 0x200000) != 0 /* is-active doJSET_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_JSET_IMM);
            return doJSET_IMM();
        }
        if ((state & 0x400000) != 0 /* is-active doJA() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_JA);
            return doJA();
        }
        if ((state & 0x800000) != 0 /* is-active doEXIT() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_EXIT);
            return doEXIT();
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize();
    }

    @Override
    public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
        int state = state_;
        if ((state & 0x800000) != 0 /* is-active doEXIT() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_EXIT);
            return doEXIT();
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return expectBoolean(executeAndSpecialize());
    }

    private Object executeAndSpecialize() {
        int state = state_;
        if ((opcode == EBPFOpcodes.EBPF_OP_JEQ_IMM)) {
            this.state_ = state = state | 0b1 /* add-active doJEQ_IMM() */;
            return doJEQ_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_JNE_IMM)) {
            this.state_ = state = state | 0b10 /* add-active doJNE_IMM() */;
            return doJNE_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_JNE_REG)) {
            this.state_ = state = state | 0b100 /* add-active doJNE_REG() */;
            return doJNE_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_JEQ_REG)) {
            this.state_ = state = state | 0b1000 /* add-active doJEQ_REG() */;
            return doJEQ_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_JGE_IMM)) {
            this.state_ = state = state | 0b10000 /* add-active doJGE_IMM() */;
            return doJGE_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_JGE_REG)) {
            this.state_ = state = state | 0b100000 /* add-active doJGE_REG() */;
            return doJGE_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_JGT_REG)) {
            this.state_ = state = state | 0b1000000 /* add-active doJGT_REG() */;
            return doJGT_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_JGT_IMM)) {
            this.state_ = state = state | 0b10000000 /* add-active doJGT_IMM() */;
            return doJGT_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_JSGT_REG)) {
            this.state_ = state = state | 0b100000000 /* add-active doJSGT_REG() */;
            return doJSGT_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_JSGT_IMM)) {
            this.state_ = state = state | 0b1000000000 /* add-active doJSGT_IMM() */;
            return doJSGT_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_JSGE_IMM)) {
            this.state_ = state = state | 0b10000000000 /* add-active doJSGE_IMM() */;
            return doJSGE_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_JSGE_REG)) {
            this.state_ = state = state | 0b100000000000 /* add-active doJSGE_REG() */;
            return doJSGE_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_JLT_IMM)) {
            this.state_ = state = state | 0b1000000000000 /* add-active doJLT_IMM() */;
            return doJLT_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_JLT_REG)) {
            this.state_ = state = state | 0b10000000000000 /* add-active doJLT_REG() */;
            return doJLT_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_JLE_IMM)) {
            this.state_ = state = state | 0b100000000000000 /* add-active doJLE_IMM() */;
            return doJLE_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_JLE_REG)) {
            this.state_ = state = state | 0b1000000000000000 /* add-active doJLE_REG() */;
            return doJLE_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_JSLT_IMM)) {
            this.state_ = state = state | 0x10000 /* add-active doJSLT_IMM() */;
            return doJSLT_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_JSLT_REG)) {
            this.state_ = state = state | 0x20000 /* add-active doJSLT_REG() */;
            return doJSLT_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_JSLE_IMM)) {
            this.state_ = state = state | 0x40000 /* add-active doJSLE_IMM() */;
            return doJSLE_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_JSLE_REG)) {
            this.state_ = state = state | 0x80000 /* add-active doJSLE_REG() */;
            return doJSLE_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_JSET_REG)) {
            this.state_ = state = state | 0x100000 /* add-active doJSET_REG() */;
            return doJSET_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_JSET_IMM)) {
            this.state_ = state = state | 0x200000 /* add-active doJSET_IMM() */;
            return doJSET_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_JA)) {
            this.state_ = state = state | 0x400000 /* add-active doJA() */;
            return doJA();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_EXIT)) {
            this.state_ = state = state | 0x800000 /* add-active doEXIT() */;
            return doEXIT();
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

    private static boolean expectBoolean(Object value) throws UnexpectedResultException {
        if (value instanceof Boolean) {
            return (boolean) value;
        }
        throw new UnexpectedResultException(value);
    }

    public static JumpInstructionNode create(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
        return new JumpInstructionNodeGen(opcode, srcReg, destReg, offset, imm);
    }

}
