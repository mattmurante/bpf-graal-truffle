package com.oracle.truffle.bpf;

import com.oracle.truffle.bpf.nodes.InstructionNode;
import com.oracle.truffle.bpf.nodes.alu.*;
import com.oracle.truffle.bpf.nodes.alu64.*;
import com.oracle.truffle.bpf.nodes.jump.*;
import com.oracle.truffle.bpf.nodes.mem.*;
import com.oracle.truffle.bpf.nodes.other.BENodeGen;
import com.oracle.truffle.bpf.nodes.other.EXITNodeGen;
import com.oracle.truffle.bpf.nodes.other.LENodeGen;
import com.oracle.truffle.bpf.nodes.util.EBPFOpcodes;
import com.oracle.truffle.bpf.nodes.util.NotYetImplemented;

//Class to generate all the Truffle nodes for a bpf program during parsing
public class BPFNodeFactory {
	
	//Makes jump instruction nodes
	public InstructionNode jumpInst(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
		InstructionNode inst;
		switch(opcode) {
			case EBPFOpcodes.EBPF_OP_JA:
				inst = JANodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JEQ_IMM:
				inst = JEQ_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JEQ_REG:
				inst = JEQ_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JGE_IMM:
				inst = JGE_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JGE_REG:
				inst = JGE_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JGT_IMM:
				inst = JGT_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JGT_REG:
				inst = JGT_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JLE_IMM:
				inst = JLE_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JLE_REG:
				inst = JLE_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JLT_IMM:
				inst = JLT_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JLT_REG:
				inst = JLT_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JNE_IMM:
				inst = JNE_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JNE_REG:
				inst = JNE_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JSET_IMM:
				inst = JSET_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JSET_REG:
				inst = JSET_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JSGE_IMM:
				inst = JSGE_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JSGE_REG:
				inst = JSGE_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JSGT_IMM:
				inst = JSGT_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JSGT_REG:
				inst = JSGT_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JSLE_IMM:
				inst = JSLE_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JSLE_REG:
				inst = JSLE_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JSLT_IMM:
				inst = JSLT_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JSLT_REG:
				inst = JSLT_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_EXIT:
				inst = EXITNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			default:
				throw new NotYetImplemented();
		}
		return inst;
	}
	
	//Makes alu instruction nodes
	public InstructionNode aluInst(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
		InstructionNode inst;
		switch (opcode) {
			case EBPFOpcodes.EBPF_OP_ADD_IMM:
				inst = ADD_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_ADD_REG:
				inst = ADD_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_AND_IMM:
				inst = AND_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_AND_REG:
				inst = AND_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_ARSH_IMM:
				inst = ARSH_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_ARSH_REG:
				inst = ARSH_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_DIV_IMM:
				inst = DIV_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_DIV_REG:
				inst = DIV_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_LSH_IMM:
				inst = LSH_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_LSH_REG:
				inst = LSH_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MOD_IMM:
				inst = MOD_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MOD_REG:
				inst = MOD_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MOV_IMM:
				inst = MOV_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MOV_REG:
				inst = MOV_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MUL_IMM:
				inst = MUL_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MUL_REG:
				inst = MUL_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_NEG:
				inst = NEGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_OR_IMM:
				inst = OR_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_OR_REG:
				inst = OR_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_RSH_IMM:
				inst = RSH_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_RSH_REG:
				inst = RSH_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_SUB_IMM:
				inst = SUB_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_SUB_REG:
				inst = SUB_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_XOR_IMM:
				inst = XOR_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_XOR_REG:
				inst = XOR_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_LE:
				inst = LENodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_BE:
				inst = BENodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			default:
				throw new NotYetImplemented();
		}
		return inst;
	}
	
	//Makes alu64 instruction nodes
	public InstructionNode alu64Inst(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
		InstructionNode inst;
		switch (opcode) {
			case EBPFOpcodes.EBPF_OP_ADD64_IMM:
				inst = ADD64_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_ADD64_REG:
				inst = ADD64_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_AND64_IMM:
				inst = AND64_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_AND64_REG:
				inst = AND64_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_ARSH64_IMM:
				inst = ARSH64_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_ARSH64_REG:
				inst = ARSH64_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_DIV64_IMM:
				inst = DIV64_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_DIV64_REG:
				inst = DIV64_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_LSH64_IMM:
				inst = LSH64_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_LSH64_REG:
				inst = LSH64_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MOD64_IMM:
				inst = MOD64_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MOD64_REG:
				inst = MOD64_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MOV64_IMM:
				inst = MOV64_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MOV64_REG:
				inst = MOV64_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MUL64_IMM:
				inst = MUL64_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MUL64_REG:
				inst = MUL64_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_NEG64:
				inst = NEG64NodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_OR64_IMM:
				inst = OR64_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_OR64_REG:
				inst = OR64_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_RSH64_IMM:
				inst = RSH64_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_RSH64_REG:
				inst = RSH64_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_SUB64_IMM:
				inst = SUB64_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_SUB64_REG:
				inst = SUB64_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_XOR64_IMM:
				inst = XOR64_IMMNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_XOR64_REG:
				inst = XOR64_REGNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			default:
				throw new NotYetImplemented();
		}
		return inst;
	}
	
	//Makes memory instruction nodes
	public InstructionNode memInst(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
		InstructionNode inst;
		switch (opcode) {
			case EBPFOpcodes.EBPF_OP_LDDW:
				inst = LDDW1NodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_LDXB:
				inst = LDXBNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_LDXDW:
				inst = LDXDWNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_LDXH:
				inst = LDXHNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_LDXW:
				inst = LDXWNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_STB:
				inst = STBNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_STDW:
				inst = STDWNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_STH:
				inst = STHNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_STW:
				inst = STWNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_STXB:
				inst = STXBNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_STXDW:
				inst = STXDWNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_STXH:
				inst = STXHNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_STXW:
				inst = STXWNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			default:
				throw new NotYetImplemented();
		}
		return inst;
	}
	
	//Makes second node for lldw instruction
	public InstructionNode lddwInst2(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
		InstructionNode inst;
		switch (opcode) {
			case EBPFOpcodes.EBPF_OP_LDDW:
				inst = LDDW2NodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			default:
				throw new NotYetImplemented();
		}
		return inst;
	}
	
}
