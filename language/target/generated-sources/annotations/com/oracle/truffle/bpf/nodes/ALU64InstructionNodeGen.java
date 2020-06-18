// CheckStyle: start generated
package com.oracle.truffle.bpf.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.bpf.nodes.ALU64InstructionNode;
import com.oracle.truffle.bpf.nodes.util.EBPFOpcodes;
import com.oracle.truffle.bpf.nodes.util.RegLambda;

@GeneratedBy(ALU64InstructionNode.class)
public final class ALU64InstructionNodeGen extends ALU64InstructionNode {

    private final byte opcode;
    private final byte srcReg;
    private final byte destReg;
    private final short offset;
    private final int imm;
    @CompilationFinal private long state_;

    private ALU64InstructionNodeGen(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
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
        long state = state_;
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
        if ((state & 0x10000L) != 0 /* is-active doAND_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_AND_REG);
            return doAND_REG();
        }
        if ((state & 0x20000L) != 0 /* is-active doXOR_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_XOR_IMM);
            return doXOR_IMM();
        }
        if ((state & 0x40000L) != 0 /* is-active doXOR_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_XOR_REG);
            return doXOR_REG();
        }
        if ((state & 0x80000L) != 0 /* is-active doMOD_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_MOD_IMM);
            return doMOD_IMM();
        }
        if ((state & 0x100000L) != 0 /* is-active doMOD_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_MOD_REG);
            return doMOD_REG();
        }
        if ((state & 0x200000L) != 0 /* is-active doADD_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_ADD_IMM);
            return doADD_IMM();
        }
        if ((state & 0x400000L) != 0 /* is-active doADD_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_ADD_REG);
            return doADD_REG();
        }
        if ((state & 0x800000L) != 0 /* is-active doSUB_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_SUB_IMM);
            return doSUB_IMM();
        }
        if ((state & 0x1000000L) != 0 /* is-active doSUB_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_SUB_REG);
            return doSUB_REG();
        }
        if ((state & 0x2000000L) != 0 /* is-active doDIV_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_DIV_REG);
            return doDIV_REG();
        }
        if ((state & 0x4000000L) != 0 /* is-active doDIV_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_DIV_IMM);
            return doDIV_IMM();
        }
        if ((state & 0x8000000L) != 0 /* is-active doMOV64_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_MOV64_IMM);
            return doMOV64_IMM();
        }
        if ((state & 0x10000000L) != 0 /* is-active doMOV64_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_MOV64_REG);
            return doMOV64_REG();
        }
        if ((state & 0x20000000L) != 0 /* is-active doLSH64_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_LSH64_IMM);
            return doLSH64_IMM();
        }
        if ((state & 0x40000000L) != 0 /* is-active doLSH64_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_LSH64_REG);
            return doLSH64_REG();
        }
        if ((state & 0x80000000L) != 0 /* is-active doRSH64_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_RSH64_IMM);
            return doRSH64_IMM();
        }
        if ((state & 0x100000000L) != 0 /* is-active doRSH64_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_RSH64_REG);
            return doRSH64_REG();
        }
        if ((state & 0x200000000L) != 0 /* is-active doARSH64_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_ARSH64_IMM);
            return doARSH64_IMM();
        }
        if ((state & 0x400000000L) != 0 /* is-active doARSH64_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_ARSH64_REG);
            return doARSH64_REG();
        }
        if ((state & 0x800000000L) != 0 /* is-active doMUL64_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_MUL64_REG);
            return doMUL64_REG();
        }
        if ((state & 0x1000000000L) != 0 /* is-active doMUL64_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_MUL64_IMM);
            return doMUL64_IMM();
        }
        if ((state & 0x2000000000L) != 0 /* is-active doNEG64() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_NEG64);
            return doNEG64();
        }
        if ((state & 0x4000000000L) != 0 /* is-active doOR64_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_OR64_IMM);
            return doOR64_IMM();
        }
        if ((state & 0x8000000000L) != 0 /* is-active doOR64_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_OR64_REG);
            return doOR64_REG();
        }
        if ((state & 0x10000000000L) != 0 /* is-active doAND64_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_AND64_IMM);
            return doAND64_IMM();
        }
        if ((state & 0x20000000000L) != 0 /* is-active doAND64_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_AND64_REG);
            return doAND64_REG();
        }
        if ((state & 0x40000000000L) != 0 /* is-active doXOR64_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_XOR64_IMM);
            return doXOR64_IMM();
        }
        if ((state & 0x80000000000L) != 0 /* is-active doXOR64_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_XOR64_REG);
            return doXOR64_REG();
        }
        if ((state & 0x100000000000L) != 0 /* is-active doMOD64_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_MOD64_IMM);
            return doMOD64_IMM();
        }
        if ((state & 0x200000000000L) != 0 /* is-active doMOD64_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_MOD64_REG);
            return doMOD64_REG();
        }
        if ((state & 0x400000000000L) != 0 /* is-active doADD64_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_ADD64_IMM);
            return doADD64_IMM();
        }
        if ((state & 0x800000000000L) != 0 /* is-active doADD64_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_ADD64_REG);
            return doADD64_REG();
        }
        if ((state & 0x1000000000000L) != 0 /* is-active doSUB64_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_SUB64_IMM);
            return doSUB64_IMM();
        }
        if ((state & 0x2000000000000L) != 0 /* is-active doSUB64_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_SUB64_REG);
            return doSUB64_REG();
        }
        if ((state & 0x4000000000000L) != 0 /* is-active doDIV64_REG() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_DIV64_REG);
            return doDIV64_REG();
        }
        if ((state & 0x8000000000000L) != 0 /* is-active doDIV64_IMM() */) {
            assert (opcode == EBPFOpcodes.EBPF_OP_DIV64_IMM);
            return doDIV64_IMM();
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize();
    }

    private RegLambda executeAndSpecialize() {
        long state = state_;
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
            this.state_ = state = state | 0x10000L /* add-active doAND_REG() */;
            return doAND_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_XOR_IMM)) {
            this.state_ = state = state | 0x20000L /* add-active doXOR_IMM() */;
            return doXOR_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_XOR_REG)) {
            this.state_ = state = state | 0x40000L /* add-active doXOR_REG() */;
            return doXOR_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_MOD_IMM)) {
            this.state_ = state = state | 0x80000L /* add-active doMOD_IMM() */;
            return doMOD_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_MOD_REG)) {
            this.state_ = state = state | 0x100000L /* add-active doMOD_REG() */;
            return doMOD_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_ADD_IMM)) {
            this.state_ = state = state | 0x200000L /* add-active doADD_IMM() */;
            return doADD_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_ADD_REG)) {
            this.state_ = state = state | 0x400000L /* add-active doADD_REG() */;
            return doADD_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_SUB_IMM)) {
            this.state_ = state = state | 0x800000L /* add-active doSUB_IMM() */;
            return doSUB_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_SUB_REG)) {
            this.state_ = state = state | 0x1000000L /* add-active doSUB_REG() */;
            return doSUB_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_DIV_REG)) {
            this.state_ = state = state | 0x2000000L /* add-active doDIV_REG() */;
            return doDIV_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_DIV_IMM)) {
            this.state_ = state = state | 0x4000000L /* add-active doDIV_IMM() */;
            return doDIV_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_MOV64_IMM)) {
            this.state_ = state = state | 0x8000000L /* add-active doMOV64_IMM() */;
            return doMOV64_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_MOV64_REG)) {
            this.state_ = state = state | 0x10000000L /* add-active doMOV64_REG() */;
            return doMOV64_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_LSH64_IMM)) {
            this.state_ = state = state | 0x20000000L /* add-active doLSH64_IMM() */;
            return doLSH64_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_LSH64_REG)) {
            this.state_ = state = state | 0x40000000L /* add-active doLSH64_REG() */;
            return doLSH64_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_RSH64_IMM)) {
            this.state_ = state = state | 0x80000000L /* add-active doRSH64_IMM() */;
            return doRSH64_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_RSH64_REG)) {
            this.state_ = state = state | 0x100000000L /* add-active doRSH64_REG() */;
            return doRSH64_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_ARSH64_IMM)) {
            this.state_ = state = state | 0x200000000L /* add-active doARSH64_IMM() */;
            return doARSH64_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_ARSH64_REG)) {
            this.state_ = state = state | 0x400000000L /* add-active doARSH64_REG() */;
            return doARSH64_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_MUL64_REG)) {
            this.state_ = state = state | 0x800000000L /* add-active doMUL64_REG() */;
            return doMUL64_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_MUL64_IMM)) {
            this.state_ = state = state | 0x1000000000L /* add-active doMUL64_IMM() */;
            return doMUL64_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_NEG64)) {
            this.state_ = state = state | 0x2000000000L /* add-active doNEG64() */;
            return doNEG64();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_OR64_IMM)) {
            this.state_ = state = state | 0x4000000000L /* add-active doOR64_IMM() */;
            return doOR64_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_OR64_REG)) {
            this.state_ = state = state | 0x8000000000L /* add-active doOR64_REG() */;
            return doOR64_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_AND64_IMM)) {
            this.state_ = state = state | 0x10000000000L /* add-active doAND64_IMM() */;
            return doAND64_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_AND64_REG)) {
            this.state_ = state = state | 0x20000000000L /* add-active doAND64_REG() */;
            return doAND64_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_XOR64_IMM)) {
            this.state_ = state = state | 0x40000000000L /* add-active doXOR64_IMM() */;
            return doXOR64_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_XOR64_REG)) {
            this.state_ = state = state | 0x80000000000L /* add-active doXOR64_REG() */;
            return doXOR64_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_MOD64_IMM)) {
            this.state_ = state = state | 0x100000000000L /* add-active doMOD64_IMM() */;
            return doMOD64_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_MOD64_REG)) {
            this.state_ = state = state | 0x200000000000L /* add-active doMOD64_REG() */;
            return doMOD64_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_ADD64_IMM)) {
            this.state_ = state = state | 0x400000000000L /* add-active doADD64_IMM() */;
            return doADD64_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_ADD64_REG)) {
            this.state_ = state = state | 0x800000000000L /* add-active doADD64_REG() */;
            return doADD64_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_SUB64_IMM)) {
            this.state_ = state = state | 0x1000000000000L /* add-active doSUB64_IMM() */;
            return doSUB64_IMM();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_SUB64_REG)) {
            this.state_ = state = state | 0x2000000000000L /* add-active doSUB64_REG() */;
            return doSUB64_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_DIV64_REG)) {
            this.state_ = state = state | 0x4000000000000L /* add-active doDIV64_REG() */;
            return doDIV64_REG();
        }
        if ((opcode == EBPFOpcodes.EBPF_OP_DIV64_IMM)) {
            this.state_ = state = state | 0x8000000000000L /* add-active doDIV64_IMM() */;
            return doDIV64_IMM();
        }
        throw new UnsupportedSpecializationException(this, new Node[] {});
    }

    @Override
    public NodeCost getCost() {
        long state = state_;
        if (state == 0b0) {
            return NodeCost.UNINITIALIZED;
        } else if ((state & (state - 1)) == 0 /* is-single-active  */) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    public static ALU64InstructionNode create(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
        return new ALU64InstructionNodeGen(opcode, srcReg, destReg, offset, imm);
    }

}
