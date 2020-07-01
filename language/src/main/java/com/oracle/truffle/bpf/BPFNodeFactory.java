package com.oracle.truffle.bpf;

import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.bpf.nodes.ExpressionNode;
import com.oracle.truffle.bpf.nodes.InstructionNode;
import com.oracle.truffle.bpf.nodes.InstructionNodeGen;
import com.oracle.truffle.bpf.nodes.alu.*;
import com.oracle.truffle.bpf.nodes.alu64.*;
import com.oracle.truffle.bpf.nodes.jump.*;
import com.oracle.truffle.bpf.nodes.mem.*;
import com.oracle.truffle.bpf.nodes.other.BENodeGen;
import com.oracle.truffle.bpf.nodes.other.EXITNodeGen;
import com.oracle.truffle.bpf.nodes.other.LENodeGen;
import com.oracle.truffle.bpf.nodes.util.EBPFOpcodes;
import com.oracle.truffle.bpf.nodes.util.NotYetImplemented;
import com.oracle.truffle.bpf.nodes.util.ReadMemoryNodeGen;
import com.oracle.truffle.bpf.nodes.util.ReadPCNodeGen;
import com.oracle.truffle.bpf.nodes.util.ReadRegsNodeGen;

//Class to generate all the Truffle nodes for a bpf program during parsing
public class BPFNodeFactory {
	
	private final FrameSlot pcSlot;
	private final FrameSlot regsSlot;
	private final FrameSlot memSlot;
	
	public BPFNodeFactory(FrameSlot pcSlot, FrameSlot regsSlot, FrameSlot memSlot) {
		this.pcSlot = pcSlot;
		this.regsSlot = regsSlot;
		this.memSlot = memSlot;
	}
	
	//Makes jump instruction nodes
	public InstructionNode jumpInst(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
		ExpressionNode expr;
		switch(opcode) {
			case EBPFOpcodes.EBPF_OP_JA:
				expr = JANodeGen.create(ReadPCNodeGen.create(pcSlot), ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JEQ_IMM:
				expr = JEQ_IMMNodeGen.create(ReadPCNodeGen.create(pcSlot), ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JEQ_REG:
				expr = JEQ_REGNodeGen.create(ReadPCNodeGen.create(pcSlot), ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JGE_IMM:
				expr = JGE_IMMNodeGen.create(ReadPCNodeGen.create(pcSlot), ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JGE_REG:
				expr = JGE_REGNodeGen.create(ReadPCNodeGen.create(pcSlot), ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JGT_IMM:
				expr = JGT_IMMNodeGen.create(ReadPCNodeGen.create(pcSlot), ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JGT_REG:
				expr = JGT_REGNodeGen.create(ReadPCNodeGen.create(pcSlot), ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JLE_IMM:
				expr = JLE_IMMNodeGen.create(ReadPCNodeGen.create(pcSlot), ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JLE_REG:
				expr = JLE_REGNodeGen.create(ReadPCNodeGen.create(pcSlot), ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JLT_IMM:
				expr = JLT_IMMNodeGen.create(ReadPCNodeGen.create(pcSlot), ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JLT_REG:
				expr = JLT_REGNodeGen.create(ReadPCNodeGen.create(pcSlot), ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JNE_IMM:
				expr = JNE_IMMNodeGen.create(ReadPCNodeGen.create(pcSlot), ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JNE_REG:
				expr = JNE_REGNodeGen.create(ReadPCNodeGen.create(pcSlot), ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JSET_IMM:
				expr = JSET_IMMNodeGen.create(ReadPCNodeGen.create(pcSlot), ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JSET_REG:
				expr = JSET_REGNodeGen.create(ReadPCNodeGen.create(pcSlot), ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JSGE_IMM:
				expr = JSGE_IMMNodeGen.create(ReadPCNodeGen.create(pcSlot), ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JSGE_REG:
				expr = JSGE_REGNodeGen.create(ReadPCNodeGen.create(pcSlot), ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JSGT_IMM:
				expr = JSGT_IMMNodeGen.create(ReadPCNodeGen.create(pcSlot), ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JSGT_REG:
				expr = JSGT_REGNodeGen.create(ReadPCNodeGen.create(pcSlot), ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JSLE_IMM:
				expr = JSLE_IMMNodeGen.create(ReadPCNodeGen.create(pcSlot), ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JSLE_REG:
				expr = JSLE_REGNodeGen.create(ReadPCNodeGen.create(pcSlot), ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JSLT_IMM:
				expr = JSLT_IMMNodeGen.create(ReadPCNodeGen.create(pcSlot), ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_JSLT_REG:
				expr = JSLT_REGNodeGen.create(ReadPCNodeGen.create(pcSlot), ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_EXIT:
				expr = EXITNodeGen.create(opcode, srcReg, destReg, offset, imm);
				break;
			default:
				throw new NotYetImplemented();
		}
		return InstructionNodeGen.create(expr, pcSlot, regsSlot, memSlot);
	}
	
	//Makes alu instruction nodes
	public InstructionNode aluInst(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
		ExpressionNode expr;
		switch (opcode) {
			case EBPFOpcodes.EBPF_OP_ADD_IMM:
				expr = ADD_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_ADD_REG:
				expr = ADD_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_AND_IMM:
				expr = AND_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_AND_REG:
				expr = AND_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_ARSH_IMM:
				expr = ARSH_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_ARSH_REG:
				expr = ARSH_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_DIV_IMM:
				expr = DIV_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_DIV_REG:
				expr = DIV_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_LSH_IMM:
				expr = LSH_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_LSH_REG:
				expr = LSH_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MOD_IMM:
				expr = MOD_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MOD_REG:
				expr = MOD_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MOV_IMM:
				expr = MOV_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MOV_REG:
				expr = MOV_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MUL_IMM:
				expr = MUL_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MUL_REG:
				expr = MUL_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_NEG:
				expr = NEGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_OR_IMM:
				expr = OR_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_OR_REG:
				expr = OR_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_RSH_IMM:
				expr = RSH_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_RSH_REG:
				expr = RSH_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_SUB_IMM:
				expr = SUB_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_SUB_REG:
				expr = SUB_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_XOR_IMM:
				expr = XOR_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_XOR_REG:
				expr = XOR_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_LE:
				expr = LENodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_BE:
				expr = BENodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			default:
				throw new NotYetImplemented();
		}
		return InstructionNodeGen.create(expr, pcSlot, regsSlot, memSlot);
	}
	
	//Makes alu64 instruction nodes
	public InstructionNode alu64Inst(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
		ExpressionNode expr;
		switch (opcode) {
			case EBPFOpcodes.EBPF_OP_ADD64_IMM:
				expr = ADD64_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_ADD64_REG:
				expr = ADD64_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_AND64_IMM:
				expr = AND64_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_AND64_REG:
				expr = AND64_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_ARSH64_IMM:
				expr = ARSH64_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_ARSH64_REG:
				expr = ARSH64_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_DIV64_IMM:
				expr = DIV64_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_DIV64_REG:
				expr = DIV64_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_LSH64_IMM:
				expr = LSH64_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_LSH64_REG:
				expr = LSH64_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MOD64_IMM:
				expr = MOD64_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MOD64_REG:
				expr = MOD64_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MOV64_IMM:
				expr = MOV64_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MOV64_REG:
				expr = MOV64_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MUL64_IMM:
				expr = MUL64_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_MUL64_REG:
				expr = MUL64_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_NEG64:
				expr = NEG64NodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_OR64_IMM:
				expr = OR64_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_OR64_REG:
				expr = OR64_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_RSH64_IMM:
				expr = RSH64_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_RSH64_REG:
				expr = RSH64_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_SUB64_IMM:
				expr = SUB64_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_SUB64_REG:
				expr = SUB64_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_XOR64_IMM:
				expr = XOR64_IMMNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_XOR64_REG:
				expr = XOR64_REGNodeGen.create(ReadRegsNodeGen.create(regsSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			default:
				throw new NotYetImplemented();
		}
		return InstructionNodeGen.create(expr, pcSlot, regsSlot, memSlot);
	}
	
	//Makes memory instruction nodes
	public InstructionNode memInst(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
		ExpressionNode expr;
		switch (opcode) {
			case EBPFOpcodes.EBPF_OP_LDDW:
				expr = LDDW1NodeGen.create(ReadRegsNodeGen.create(regsSlot), ReadMemoryNodeGen.create(memSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_LDXB:
				expr = LDXBNodeGen.create(ReadRegsNodeGen.create(regsSlot), ReadMemoryNodeGen.create(memSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_LDXDW:
				expr = LDXDWNodeGen.create(ReadRegsNodeGen.create(regsSlot), ReadMemoryNodeGen.create(memSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_LDXH:
				expr = LDXHNodeGen.create(ReadRegsNodeGen.create(regsSlot), ReadMemoryNodeGen.create(memSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_LDXW:
				expr = LDXWNodeGen.create(ReadRegsNodeGen.create(regsSlot), ReadMemoryNodeGen.create(memSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_STB:
				expr = STBNodeGen.create(ReadRegsNodeGen.create(regsSlot), ReadMemoryNodeGen.create(memSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_STDW:
				expr = STDWNodeGen.create(ReadRegsNodeGen.create(regsSlot), ReadMemoryNodeGen.create(memSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_STH:
				expr = STHNodeGen.create(ReadRegsNodeGen.create(regsSlot), ReadMemoryNodeGen.create(memSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_STW:
				expr = STWNodeGen.create(ReadRegsNodeGen.create(regsSlot), ReadMemoryNodeGen.create(memSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_STXB:
				expr = STXBNodeGen.create(ReadRegsNodeGen.create(regsSlot), ReadMemoryNodeGen.create(memSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_STXDW:
				expr = STXDWNodeGen.create(ReadRegsNodeGen.create(regsSlot), ReadMemoryNodeGen.create(memSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_STXH:
				expr = STXHNodeGen.create(ReadRegsNodeGen.create(regsSlot), ReadMemoryNodeGen.create(memSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			case EBPFOpcodes.EBPF_OP_STXW:
				expr = STXWNodeGen.create(ReadRegsNodeGen.create(regsSlot), ReadMemoryNodeGen.create(memSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			default:
				throw new NotYetImplemented();
		}
		return InstructionNodeGen.create(expr, pcSlot, regsSlot, memSlot);
	}
	
	//Makes second node for lldw instruction
	public InstructionNode lddwInst2(byte opcode, byte srcReg, byte destReg, short offset, int imm) {
		ExpressionNode expr;
		switch (opcode) {
			case EBPFOpcodes.EBPF_OP_LDDW:
				expr = LDDW2NodeGen.create(ReadRegsNodeGen.create(regsSlot), ReadMemoryNodeGen.create(memSlot),
						opcode, srcReg, destReg, offset, imm);
				break;
			default:
				throw new NotYetImplemented();
		}
		return InstructionNodeGen.create(expr, pcSlot, regsSlot, memSlot);
	}
	
}
