#!/usr/bin/env python3
#
# Pure-Python BPF Loader for ELF files
#
# Based on IOVisor's ubpf_loader.c
#
# Author: Sreepathi Pai

from elftools.elf.elffile import ELFFile
from elftools.elf.enums import ENUM_E_MACHINE
from elftools.elf.constants import SH_FLAGS
from elftools.elf.relocation import RelocationSection

MAX_SECTIONS = 32
EM_BPF = 247

def load_elf(elfstream):
    elf = ELFFile(elfstream)

    e_ident = elf.header['e_ident']
    if e_ident['EI_CLASS'] != 'ELFCLASS64':
        raise ValueError(f"Invalid ELF class '{e_ident['EI_CLASS']}', must be ELFCLASS64")

    if e_ident['EI_DATA'] != 'ELFDATA2LSB':
        raise ValueError(f"Invalid data ordering '{e_ident['EI_DATA']}', must be ELFDATA2LSB")

    if e_ident['EI_VERSION'] != 'EV_CURRENT':
        raise ValueError(f"Invalid version '{e_ident['EI_VERSION']}', must be EV_CURRENT")

    if e_ident['EI_OSABI'] not in ('ELFOSABI_NONE', 'ELFOSABI_SYSV'):
        raise ValueError(f"Invalid OS ABI '{e_ident['EI_OSABI']}', must be ELFOSABI_NONE or ELFOSABI_SYSV")

    if elf.header['e_type'] != 'ET_REL':
        raise ValueError(f"ELF is not relocatable ('{elf.header['e_type']}', must be ET_REL)")

    if elf.header['e_machine'] not in (ENUM_E_MACHINE['EM_NONE'], EM_BPF):
        raise ValueError(f"Invalid machine '{elf.header['e_machine']}', must be EM_NONE or EM_BPF")

    if elf.header['e_shnum'] > MAX_SECTIONS:
        raise ValueError(f"Too many sections {elf.header['e_shnum']}, must be {MAX_SECTIONS} or fewer")

    text_section = None
    for s in elf.iter_sections():
        if s['sh_type'] == 'SHT_PROGBITS' and s['sh_flags'] == (SH_FLAGS.SHF_ALLOC | SH_FLAGS.SHF_EXECINSTR):
            text_section = s
            break
    else:
        raise ValueError("No text section found")

    for s in elf.iter_sections():
        if not isinstance(s, RelocationSection): continue
        raise NotImplementedError
        # TODO: relocations

    return text_section.data()

if __name__ == "__main__":
    import argparse
    p = argparse.ArgumentParser(description="Load a BPF object, and extract a raw binary")
    p.add_argument("elffile", help="ELF file containing BPF object")
    p.add_argument("output", help="Output file for raw binary")

    args = p.parse_args()

    with open(args.elffile, "rb") as f:
        bpf = load_elf(f)
        with open(args.output, "wb") as f:
            f.write(bpf)
