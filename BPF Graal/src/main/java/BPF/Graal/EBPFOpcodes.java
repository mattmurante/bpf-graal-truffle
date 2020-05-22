package BPF.Graal;

//All opcode constants taken from bpf-rpython

public final class EBPFOpcodes {
	  public static final byte EBPF_CLS_LD = 0x00;
	  public static final byte EBPF_CLS_LDX = 0x01;
	  public static final byte EBPF_CLS_ST = 0x02;
	  public static final byte EBPF_CLS_STX = 0x03;
	  public static final byte EBPF_CLS_ALU = 0x04;
	  public static final byte EBPF_CLS_JMP = 0x05;
	  public static final byte EBPF_CLS_ALU64 = 0x07;
	
	  public static final byte EBPF_SRC_IMM = 0x00;
	  public static final byte EBPF_SRC_REG = 0x08;
	
	  public static final byte EBPF_SIZE_W = 0x00;
	  public static final byte EBPF_SIZE_H = 0x08;
	  public static final byte EBPF_SIZE_B = 0x10;
	  public static final byte EBPF_SIZE_DW = 0x18;
	
	  public static final byte EBPF_MODE_IMM = 0x00;
	  public static final byte EBPF_MODE_MEM = 0x60;
	
	  public static final byte EBPF_OP_ADD_IMM  = (EBPF_CLS_ALU|EBPF_SRC_IMM|0x00);
	  public static final byte EBPF_OP_ADD_REG  = (EBPF_CLS_ALU|EBPF_SRC_REG|0x00);
	  public static final byte EBPF_OP_SUB_IMM  = (EBPF_CLS_ALU|EBPF_SRC_IMM|0x10);
	  public static final byte EBPF_OP_SUB_REG  = (EBPF_CLS_ALU|EBPF_SRC_REG|0x10);
	  public static final byte EBPF_OP_MUL_IMM  = (EBPF_CLS_ALU|EBPF_SRC_IMM|0x20);
	  public static final byte EBPF_OP_MUL_REG  = (EBPF_CLS_ALU|EBPF_SRC_REG|0x20);
	  public static final byte EBPF_OP_DIV_IMM  = (EBPF_CLS_ALU|EBPF_SRC_IMM|0x30);
	  public static final byte EBPF_OP_DIV_REG  = (EBPF_CLS_ALU|EBPF_SRC_REG|0x30);
	  public static final byte EBPF_OP_OR_IMM   = (EBPF_CLS_ALU|EBPF_SRC_IMM|0x40);
	  public static final byte EBPF_OP_OR_REG   = (EBPF_CLS_ALU|EBPF_SRC_REG|0x40);
	  public static final byte EBPF_OP_AND_IMM  = (EBPF_CLS_ALU|EBPF_SRC_IMM|0x50);
	  public static final byte EBPF_OP_AND_REG  = (EBPF_CLS_ALU|EBPF_SRC_REG|0x50);
	  public static final byte EBPF_OP_LSH_IMM  = (EBPF_CLS_ALU|EBPF_SRC_IMM|0x60);
	  public static final byte EBPF_OP_LSH_REG  = (EBPF_CLS_ALU|EBPF_SRC_REG|0x60);
	  public static final byte EBPF_OP_RSH_IMM  = (EBPF_CLS_ALU|EBPF_SRC_IMM|0x70);
	  public static final byte EBPF_OP_RSH_REG  = (EBPF_CLS_ALU|EBPF_SRC_REG|0x70);
	  public static final byte EBPF_OP_NEG      = (byte) (EBPF_CLS_ALU|0x80);
	  public static final byte EBPF_OP_MOD_IMM  = (byte) (EBPF_CLS_ALU|EBPF_SRC_IMM|0x90);
	  public static final byte EBPF_OP_MOD_REG  = (byte) (EBPF_CLS_ALU|EBPF_SRC_REG|0x90);
	  public static final byte EBPF_OP_XOR_IMM  = (byte) (EBPF_CLS_ALU|EBPF_SRC_IMM|0xa0);
	  public static final byte EBPF_OP_XOR_REG  = (byte) (EBPF_CLS_ALU|EBPF_SRC_REG|0xa0);
	  public static final byte EBPF_OP_MOV_IMM  = (byte) (EBPF_CLS_ALU|EBPF_SRC_IMM|0xb0);
	  public static final byte EBPF_OP_MOV_REG  = (byte) (EBPF_CLS_ALU|EBPF_SRC_REG|0xb0);
	  public static final byte EBPF_OP_ARSH_IMM = (byte) (EBPF_CLS_ALU|EBPF_SRC_IMM|0xc0);
	  public static final byte EBPF_OP_ARSH_REG = (byte) (EBPF_CLS_ALU|EBPF_SRC_REG|0xc0);
	  public static final byte EBPF_OP_LE       = (byte) (EBPF_CLS_ALU|EBPF_SRC_IMM|0xd0);
	  public static final byte EBPF_OP_BE       = (byte) (EBPF_CLS_ALU|EBPF_SRC_REG|0xd0);
	
	  public static final byte EBPF_OP_ADD64_IMM  = (EBPF_CLS_ALU64|EBPF_SRC_IMM|0x00);
	  public static final byte EBPF_OP_ADD64_REG  = (EBPF_CLS_ALU64|EBPF_SRC_REG|0x00);
	  public static final byte EBPF_OP_SUB64_IMM  = (EBPF_CLS_ALU64|EBPF_SRC_IMM|0x10);
	  public static final byte EBPF_OP_SUB64_REG  = (EBPF_CLS_ALU64|EBPF_SRC_REG|0x10);
	  public static final byte EBPF_OP_MUL64_IMM  = (EBPF_CLS_ALU64|EBPF_SRC_IMM|0x20);
	  public static final byte EBPF_OP_MUL64_REG  = (EBPF_CLS_ALU64|EBPF_SRC_REG|0x20);
	  public static final byte EBPF_OP_DIV64_IMM  = (EBPF_CLS_ALU64|EBPF_SRC_IMM|0x30);
	  public static final byte EBPF_OP_DIV64_REG  = (EBPF_CLS_ALU64|EBPF_SRC_REG|0x30);
	  public static final byte EBPF_OP_OR64_IMM   = (EBPF_CLS_ALU64|EBPF_SRC_IMM|0x40);
	  public static final byte EBPF_OP_OR64_REG   = (EBPF_CLS_ALU64|EBPF_SRC_REG|0x40);
	  public static final byte EBPF_OP_AND64_IMM  = (EBPF_CLS_ALU64|EBPF_SRC_IMM|0x50);
	  public static final byte EBPF_OP_AND64_REG  = (EBPF_CLS_ALU64|EBPF_SRC_REG|0x50);
	  public static final byte EBPF_OP_LSH64_IMM  = (EBPF_CLS_ALU64|EBPF_SRC_IMM|0x60);
	  public static final byte EBPF_OP_LSH64_REG  = (EBPF_CLS_ALU64|EBPF_SRC_REG|0x60);
	  public static final byte EBPF_OP_RSH64_IMM  = (EBPF_CLS_ALU64|EBPF_SRC_IMM|0x70);
	  public static final byte EBPF_OP_RSH64_REG  = (EBPF_CLS_ALU64|EBPF_SRC_REG|0x70);
	  public static final byte EBPF_OP_NEG64      = (byte) (EBPF_CLS_ALU64|0x80);
	  public static final byte EBPF_OP_MOD64_IMM  = (byte) (EBPF_CLS_ALU64|EBPF_SRC_IMM|0x90);
	  public static final byte EBPF_OP_MOD64_REG  = (byte) (EBPF_CLS_ALU64|EBPF_SRC_REG|0x90);
	  public static final byte EBPF_OP_XOR64_IMM  = (byte) (EBPF_CLS_ALU64|EBPF_SRC_IMM|0xa0);
	  public static final byte EBPF_OP_XOR64_REG  = (byte) (EBPF_CLS_ALU64|EBPF_SRC_REG|0xa0);
	  public static final byte EBPF_OP_MOV64_IMM  = (byte) (EBPF_CLS_ALU64|EBPF_SRC_IMM|0xb0);
	  public static final byte EBPF_OP_MOV64_REG  = (byte) (EBPF_CLS_ALU64|EBPF_SRC_REG|0xb0);
	  public static final byte EBPF_OP_ARSH64_IMM = (byte) (EBPF_CLS_ALU64|EBPF_SRC_IMM|0xc0);
	  public static final byte EBPF_OP_ARSH64_REG = (byte) (EBPF_CLS_ALU64|EBPF_SRC_REG|0xc0);
	
	  public static final byte EBPF_OP_LDXW  = (EBPF_CLS_LDX|EBPF_MODE_MEM|EBPF_SIZE_W);
	  public static final byte EBPF_OP_LDXH  = (EBPF_CLS_LDX|EBPF_MODE_MEM|EBPF_SIZE_H);
	  public static final byte EBPF_OP_LDXB  = (EBPF_CLS_LDX|EBPF_MODE_MEM|EBPF_SIZE_B);
	  public static final byte EBPF_OP_LDXDW = (EBPF_CLS_LDX|EBPF_MODE_MEM|EBPF_SIZE_DW);
	  public static final byte EBPF_OP_STW   = (EBPF_CLS_ST|EBPF_MODE_MEM|EBPF_SIZE_W);
	  public static final byte EBPF_OP_STH   = (EBPF_CLS_ST|EBPF_MODE_MEM|EBPF_SIZE_H);
	  public static final byte EBPF_OP_STB   = (EBPF_CLS_ST|EBPF_MODE_MEM|EBPF_SIZE_B);
	  public static final byte EBPF_OP_STDW  = (EBPF_CLS_ST|EBPF_MODE_MEM|EBPF_SIZE_DW);
	  public static final byte EBPF_OP_STXW  = (EBPF_CLS_STX|EBPF_MODE_MEM|EBPF_SIZE_W);
	  public static final byte EBPF_OP_STXH  = (EBPF_CLS_STX|EBPF_MODE_MEM|EBPF_SIZE_H);
	  public static final byte EBPF_OP_STXB  = (EBPF_CLS_STX|EBPF_MODE_MEM|EBPF_SIZE_B);
	  public static final byte EBPF_OP_STXDW = (EBPF_CLS_STX|EBPF_MODE_MEM|EBPF_SIZE_DW);
	  public static final byte EBPF_OP_LDDW  = (EBPF_CLS_LD|EBPF_MODE_IMM|EBPF_SIZE_DW);
	
	  public static final byte EBPF_OP_JA       = (EBPF_CLS_JMP|0x00);
	  public static final byte EBPF_OP_JEQ_IMM  = (EBPF_CLS_JMP|EBPF_SRC_IMM|0x10);
	  public static final byte EBPF_OP_JEQ_REG  = (EBPF_CLS_JMP|EBPF_SRC_REG|0x10);
	  public static final byte EBPF_OP_JGT_IMM  = (EBPF_CLS_JMP|EBPF_SRC_IMM|0x20);
	  public static final byte EBPF_OP_JGT_REG  = (EBPF_CLS_JMP|EBPF_SRC_REG|0x20);
	  public static final byte EBPF_OP_JGE_IMM  = (EBPF_CLS_JMP|EBPF_SRC_IMM|0x30);
	  public static final byte EBPF_OP_JGE_REG  = (EBPF_CLS_JMP|EBPF_SRC_REG|0x30);
	  public static final byte EBPF_OP_JSET_REG = (EBPF_CLS_JMP|EBPF_SRC_REG|0x40);
	  public static final byte EBPF_OP_JSET_IMM = (EBPF_CLS_JMP|EBPF_SRC_IMM|0x40);
	  public static final byte EBPF_OP_JNE_IMM  = (EBPF_CLS_JMP|EBPF_SRC_IMM|0x50);
	  public static final byte EBPF_OP_JNE_REG  = (EBPF_CLS_JMP|EBPF_SRC_REG|0x50);
	  public static final byte EBPF_OP_JSGT_IMM = (EBPF_CLS_JMP|EBPF_SRC_IMM|0x60);
	  public static final byte EBPF_OP_JSGT_REG = (EBPF_CLS_JMP|EBPF_SRC_REG|0x60);
	  public static final byte EBPF_OP_JSGE_IMM = (EBPF_CLS_JMP|EBPF_SRC_IMM|0x70);
	  public static final byte EBPF_OP_JSGE_REG = (EBPF_CLS_JMP|EBPF_SRC_REG|0x70);
//	  public static final byte EBPF_OP_CALL     = (byte) (EBPF_CLS_JMP|0x80);
	  public static final byte EBPF_OP_EXIT     = (byte) (EBPF_CLS_JMP|0x90);
	  public static final byte EBPF_OP_JLT_IMM  = (byte) (EBPF_CLS_JMP|EBPF_SRC_IMM|0xa0);
	  public static final byte EBPF_OP_JLT_REG  = (byte) (EBPF_CLS_JMP|EBPF_SRC_REG|0xa0);
	  public static final byte EBPF_OP_JLE_IMM  = (byte) (EBPF_CLS_JMP|EBPF_SRC_IMM|0xb0);
	  public static final byte EBPF_OP_JLE_REG  = (byte) (EBPF_CLS_JMP|EBPF_SRC_REG|0xb0);
	  public static final byte EBPF_OP_JSLT_IMM = (byte) (EBPF_CLS_JMP|EBPF_SRC_IMM|0xc0);
	  public static final byte EBPF_OP_JSLT_REG = (byte) (EBPF_CLS_JMP|EBPF_SRC_REG|0xc0);
	  public static final byte EBPF_OP_JSLE_IMM = (byte) (EBPF_CLS_JMP|EBPF_SRC_IMM|0xd0);
	  public static final byte EBPF_OP_JSLE_REG = (byte) (EBPF_CLS_JMP|EBPF_SRC_REG|0xd0);
	
	  public static final byte EBPF_CLS_MASK = 0x07;
	  public static final byte EBPF_ALU_OP_MASK = (byte) 0xf0;
}