// CheckStyle: start generated
package com.oracle.truffle.bpf.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.bpf.nodes.ALUInstructionNode;
import com.oracle.truffle.bpf.nodes.util.EBPFOpcodes;
import com.oracle.truffle.bpf.nodes.util.RegLambda;

@GeneratedBy(ALUInstructionNode.class)
public final class ALUInstructionNodeGen extends ALUInstructionNode {

    private final byte opcode;
    private final byte srcReg;
    private final byte destReg;
    private final short offset;
    private final int imm;
    @CompilationFinal private int state_;

    private ALUInstructionNodeGen(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
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
        if ((state & 0b1) != 0 /* is-active doLE() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_LE);
            return doLE();
        }
        if ((state & 0b10) != 0 /* is-active doBE() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_BE);
            return doBE();
        }
        if ((state & 0b100) != 0 /* is-active doMOV_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_MOV_IMM);
            return doMOV_IMM();
        }
        if ((state & 0b1000) != 0 /* is-active doMOV_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_MOV_REG);
            return doMOV_REG();
        }
        if ((state & 0b10000) != 0 /* is-active doLSH_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_LSH_IMM);
            return doLSH_IMM();
        }
        if ((state & 0b100000) != 0 /* is-active doLSH_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_LSH_REG);
            return doLSH_REG();
        }
        if ((state & 0b1000000) != 0 /* is-active doRSH_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_RSH_IMM);
            return doRSH_IMM();
        }
        if ((state & 0b10000000) != 0 /* is-active doRSH_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_RSH_REG);
            return doRSH_REG();
        }
        if ((state & 0b100000000) != 0 /* is-active doARSH_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_ARSH_IMM);
            return doARSH_IMM();
        }
        if ((state & 0b1000000000) != 0 /* is-active doARSH_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_ARSH_REG);
            return doARSH_REG();
        }
        if ((state & 0b10000000000) != 0 /* is-active doMUL_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_MUL_REG);
            return doMUL_REG();
        }
        if ((state & 0b100000000000) != 0 /* is-active doMUL_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_MUL_IMM);
            return doMUL_IMM();
        }
        if ((state & 0b1000000000000) != 0 /* is-active doNEG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_NEG);
            return doNEG();
        }
        if ((state & 0b10000000000000) != 0 /* is-active doOR_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_OR_IMM);
            return doOR_IMM();
        }
        if ((state & 0b100000000000000) != 0 /* is-active doOR_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_OR_REG);
            return doOR_REG();
        }
        if ((state & 0b1000000000000000) != 0 /* is-active doAND_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_AND_IMM);
            return doAND_IMM();
        }
        if ((state & 0x10000) != 0 /* is-active doAND_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_AND_REG);
            return doAND_REG();
        }
        if ((state & 0x20000) != 0 /* is-active doXOR_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_XOR_IMM);
            return doXOR_IMM();
        }
        if ((state & 0x40000) != 0 /* is-active doXOR_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_XOR_REG);
            return doXOR_REG();
        }
        if ((state & 0x80000) != 0 /* is-active doMOD_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_MOD_IMM);
            return doMOD_IMM();
        }
        if ((state & 0x100000) != 0 /* is-active doMOD_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_MOD_REG);
            return doMOD_REG();
        }
        if ((state & 0x200000) != 0 /* is-active doADD_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_ADD_IMM);
            return doADD_IMM();
        }
        if ((state & 0x400000) != 0 /* is-active doADD_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_ADD_REG);
            return doADD_REG();
        }
        if ((state & 0x800000) != 0 /* is-active doSUB_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_SUB_IMM);
            return doSUB_IMM();
        }
        if ((state & 0x1000000) != 0 /* is-active doSUB_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_SUB_REG);
            return doSUB_REG();
        }
        if ((state & 0x2000000) != 0 /* is-active doDIV_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_DIV_REG);
            return doDIV_REG();
        }
        if ((state & 0x4000000) != 0 /* is-active doDIV_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_DIV_IMM);
            return doDIV_IMM();
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize();
    }

    private RegLambda executeAndSpecialize() {
        int state = state_;
        if ((opcode == EBPFOpcodes.EBPF_OP_LE)) {
            this.state_ = state = state | 0b1 /* add-active doLE() */;
            return doLE();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_BE)) {
            this.state_ = state = state | 0b10 /* add-active doBE() */;
            return doBE();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_MOV_IMM)) {
            this.state_ = state = state | 0b100 /* add-active doMOV_IMM() */;
            return doMOV_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_MOV_REG)) {
            this.state_ = state = state | 0b1000 /* add-active doMOV_REG() */;
            return doMOV_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_LSH_IMM)) {
            this.state_ = state = state | 0b10000 /* add-active doLSH_IMM() */;
            return doLSH_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_LSH_REG)) {
            this.state_ = state = state | 0b100000 /* add-active doLSH_REG() */;
            return doLSH_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_RSH_IMM)) {
            this.state_ = state = state | 0b1000000 /* add-active doRSH_IMM() */;
            return doRSH_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_RSH_REG)) {
            this.state_ = state = state | 0b10000000 /* add-active doRSH_REG() */;
            return doRSH_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_ARSH_IMM)) {
            this.state_ = state = state | 0b100000000 /* add-active doARSH_IMM() */;
            return doARSH_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_ARSH_REG)) {
            this.state_ = state = state | 0b1000000000 /* add-active doARSH_REG() */;
            return doARSH_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_MUL_REG)) {
            this.state_ = state = state | 0b10000000000 /* add-active doMUL_REG() */;
            return doMUL_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_MUL_IMM)) {
            this.state_ = state = state | 0b100000000000 /* add-active doMUL_IMM() */;
            return doMUL_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_NEG)) {
            this.state_ = state = state | 0b1000000000000 /* add-active doNEG() */;
            return doNEG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_OR_IMM)) {
            this.state_ = state = state | 0b10000000000000 /* add-active doOR_IMM() */;
            return doOR_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_OR_REG)) {
            this.state_ = state = state | 0b100000000000000 /* add-active doOR_REG() */;
            return doOR_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_AND_IMM)) {
            this.state_ = state = state | 0b1000000000000000 /* add-active doAND_IMM() */;
            return doAND_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_AND_REG)) {
            this.state_ = state = state | 0x10000 /* add-active doAND_REG() */;
            return doAND_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_XOR_IMM)) {
            this.state_ = state = state | 0x20000 /* add-active doXOR_IMM() */;
            return doXOR_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_XOR_REG)) {
            this.state_ = state = state | 0x40000 /* add-active doXOR_REG() */;
            return doXOR_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_MOD_IMM)) {
            this.state_ = state = state | 0x80000 /* add-active doMOD_IMM() */;
            return doMOD_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_MOD_REG)) {
            this.state_ = state = state | 0x100000 /* add-active doMOD_REG() */;
            return doMOD_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_ADD_IMM)) {
            this.state_ = state = state | 0x200000 /* add-active doADD_IMM() */;
            return doADD_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_ADD_REG)) {
            this.state_ = state = state | 0x400000 /* add-active doADD_REG() */;
            return doADD_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_SUB_IMM)) {
            this.state_ = state = state | 0x800000 /* add-active doSUB_IMM() */;
            return doSUB_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_SUB_REG)) {
            this.state_ = state = state | 0x1000000 /* add-active doSUB_REG() */;
            return doSUB_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_DIV_REG)) {
            this.state_ = state = state | 0x2000000 /* add-active doDIV_REG() */;
            return doDIV_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_DIV_IMM)) {
            this.state_ = state = state | 0x4000000 /* add-active doDIV_IMM() */;
            return doDIV_IMM();
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

    public static ALUInstructionNode create(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
        return new ALUInstructionNodeGen(opcode, srcReg, destReg, offset, imm);
    }

}
