// CheckStyle: start generated
package com.oracle.truffle.bpf.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.bpf.nodes.CommitStateNode;
import com.oracle.truffle.bpf.nodes.InstructionNode;
import com.oracle.truffle.bpf.nodes.util.JumpLambda;
import com.oracle.truffle.bpf.nodes.util.MemLambda;
import com.oracle.truffle.bpf.nodes.util.RegLambda;
import java.util.concurrent.locks.Lock;

@GeneratedBy(CommitStateNode.class)
public final class CommitStateNodeGen extends CommitStateNode {

    @Child private InstructionNode inst_;
    @CompilationFinal private int state_;
    @CompilationFinal private int exclude_;

    private CommitStateNodeGen(FrameSlot pcSlot, FrameSlot regsSlot, FrameSlot memSlot, InstructionNode inst) {
        super(pcSlot, regsSlot, memSlot);
        this.inst_ = inst;
    }

    @Override
    public Object executeGeneric(VirtualFrame frameValue) {
        int state = state_;
        if ((state & 0b111) == 0 /* only-active commitBool(VirtualFrame, boolean) */ && state != 0  /* is-not commitJump(VirtualFrame, JumpLambda) && commitRegs(VirtualFrame, RegLambda) && commitMem(VirtualFrame, MemLambda) && commitBool(VirtualFrame, boolean) */) {
            return executeGeneric_boolean0(frameValue, state);
        } else {
            return executeGeneric_generic1(frameValue, state);
        }
    }

    private Object executeGeneric_boolean0(VirtualFrame frameValue, int state) {
        boolean instValue_;
        try {
            instValue_ = this.inst_.executeBoolean(frameValue);
        } catch (UnexpectedResultException ex) {
            return executeAndSpecialize(frameValue, ex.getResult());
        }
        assert (state & 0b1000) != 0 /* is-active commitBool(VirtualFrame, boolean) */;
        try {
            return commitBool(frameValue, instValue_);
        } catch (FrameSlotTypeException ex) {
            // implicit transferToInterpreterAndInvalidate()
            Lock lock = getLock();
            lock.lock();
            try {
                this.exclude_ = this.exclude_ | 0b1000 /* add-excluded commitBool(VirtualFrame, boolean) */;
                this.state_ = this.state_ & 0xfffffff7 /* remove-active commitBool(VirtualFrame, boolean) */;
            } finally {
                lock.unlock();
            }
            return executeAndSpecialize(frameValue, instValue_);
        }
    }

    private Object executeGeneric_generic1(VirtualFrame frameValue, int state) {
        Object instValue_ = this.inst_.executeGeneric(frameValue);
        if ((state & 0b1) != 0 /* is-active commitJump(VirtualFrame, JumpLambda) */ && instValue_ instanceof JumpLambda) {
            JumpLambda instValue__ = (JumpLambda) instValue_;
            try {
                return commitJump(frameValue, instValue__);
            } catch (FrameSlotTypeException ex) {
                // implicit transferToInterpreterAndInvalidate()
                Lock lock = getLock();
                lock.lock();
                try {
                    this.exclude_ = this.exclude_ | 0b1 /* add-excluded commitJump(VirtualFrame, JumpLambda) */;
                    this.state_ = this.state_ & 0xfffffffe /* remove-active commitJump(VirtualFrame, JumpLambda) */;
                } finally {
                    lock.unlock();
                }
                return executeAndSpecialize(frameValue, instValue__);
            }
        }
        if ((state & 0b10) != 0 /* is-active commitRegs(VirtualFrame, RegLambda) */ && instValue_ instanceof RegLambda) {
            RegLambda instValue__ = (RegLambda) instValue_;
            try {
                return commitRegs(frameValue, instValue__);
            } catch (FrameSlotTypeException ex) {
                // implicit transferToInterpreterAndInvalidate()
                Lock lock = getLock();
                lock.lock();
                try {
                    this.exclude_ = this.exclude_ | 0b10 /* add-excluded commitRegs(VirtualFrame, RegLambda) */;
                    this.state_ = this.state_ & 0xfffffffd /* remove-active commitRegs(VirtualFrame, RegLambda) */;
                } finally {
                    lock.unlock();
                }
                return executeAndSpecialize(frameValue, instValue__);
            }
        }
        if ((state & 0b100) != 0 /* is-active commitMem(VirtualFrame, MemLambda) */ && instValue_ instanceof MemLambda) {
            MemLambda instValue__ = (MemLambda) instValue_;
            try {
                return commitMem(frameValue, instValue__);
            } catch (FrameSlotTypeException ex) {
                // implicit transferToInterpreterAndInvalidate()
                Lock lock = getLock();
                lock.lock();
                try {
                    this.exclude_ = this.exclude_ | 0b100 /* add-excluded commitMem(VirtualFrame, MemLambda) */;
                    this.state_ = this.state_ & 0xfffffffb /* remove-active commitMem(VirtualFrame, MemLambda) */;
                } finally {
                    lock.unlock();
                }
                return executeAndSpecialize(frameValue, instValue__);
            }
        }
        if ((state & 0b1000) != 0 /* is-active commitBool(VirtualFrame, boolean) */ && instValue_ instanceof Boolean) {
            boolean instValue__ = (boolean) instValue_;
            try {
                return commitBool(frameValue, instValue__);
            } catch (FrameSlotTypeException ex) {
                // implicit transferToInterpreterAndInvalidate()
                Lock lock = getLock();
                lock.lock();
                try {
                    this.exclude_ = this.exclude_ | 0b1000 /* add-excluded commitBool(VirtualFrame, boolean) */;
                    this.state_ = this.state_ & 0xfffffff7 /* remove-active commitBool(VirtualFrame, boolean) */;
                } finally {
                    lock.unlock();
                }
                return executeAndSpecialize(frameValue, instValue__);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, instValue_);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frameValue) {
        int state = state_;
        if ((state & 0b111) == 0 /* only-active commitBool(VirtualFrame, boolean) */ && state != 0  /* is-not commitJump(VirtualFrame, JumpLambda) && commitRegs(VirtualFrame, RegLambda) && commitMem(VirtualFrame, MemLambda) && commitBool(VirtualFrame, boolean) */) {
            return executeBoolean_boolean2(frameValue, state);
        } else {
            return executeBoolean_generic3(frameValue, state);
        }
    }

    private boolean executeBoolean_boolean2(VirtualFrame frameValue, int state) {
        boolean instValue_;
        try {
            instValue_ = this.inst_.executeBoolean(frameValue);
        } catch (UnexpectedResultException ex) {
            return executeAndSpecialize(frameValue, ex.getResult());
        }
        assert (state & 0b1000) != 0 /* is-active commitBool(VirtualFrame, boolean) */;
        try {
            return commitBool(frameValue, instValue_);
        } catch (FrameSlotTypeException ex) {
            // implicit transferToInterpreterAndInvalidate()
            Lock lock = getLock();
            lock.lock();
            try {
                this.exclude_ = this.exclude_ | 0b1000 /* add-excluded commitBool(VirtualFrame, boolean) */;
                this.state_ = this.state_ & 0xfffffff7 /* remove-active commitBool(VirtualFrame, boolean) */;
            } finally {
                lock.unlock();
            }
            return executeAndSpecialize(frameValue, instValue_);
        }
    }

    private boolean executeBoolean_generic3(VirtualFrame frameValue, int state) {
        Object instValue_ = this.inst_.executeGeneric(frameValue);
        if ((state & 0b1) != 0 /* is-active commitJump(VirtualFrame, JumpLambda) */ && instValue_ instanceof JumpLambda) {
            JumpLambda instValue__ = (JumpLambda) instValue_;
            try {
                return commitJump(frameValue, instValue__);
            } catch (FrameSlotTypeException ex) {
                // implicit transferToInterpreterAndInvalidate()
                Lock lock = getLock();
                lock.lock();
                try {
                    this.exclude_ = this.exclude_ | 0b1 /* add-excluded commitJump(VirtualFrame, JumpLambda) */;
                    this.state_ = this.state_ & 0xfffffffe /* remove-active commitJump(VirtualFrame, JumpLambda) */;
                } finally {
                    lock.unlock();
                }
                return executeAndSpecialize(frameValue, instValue__);
            }
        }
        if ((state & 0b10) != 0 /* is-active commitRegs(VirtualFrame, RegLambda) */ && instValue_ instanceof RegLambda) {
            RegLambda instValue__ = (RegLambda) instValue_;
            try {
                return commitRegs(frameValue, instValue__);
            } catch (FrameSlotTypeException ex) {
                // implicit transferToInterpreterAndInvalidate()
                Lock lock = getLock();
                lock.lock();
                try {
                    this.exclude_ = this.exclude_ | 0b10 /* add-excluded commitRegs(VirtualFrame, RegLambda) */;
                    this.state_ = this.state_ & 0xfffffffd /* remove-active commitRegs(VirtualFrame, RegLambda) */;
                } finally {
                    lock.unlock();
                }
                return executeAndSpecialize(frameValue, instValue__);
            }
        }
        if ((state & 0b100) != 0 /* is-active commitMem(VirtualFrame, MemLambda) */ && instValue_ instanceof MemLambda) {
            MemLambda instValue__ = (MemLambda) instValue_;
            try {
                return commitMem(frameValue, instValue__);
            } catch (FrameSlotTypeException ex) {
                // implicit transferToInterpreterAndInvalidate()
                Lock lock = getLock();
                lock.lock();
                try {
                    this.exclude_ = this.exclude_ | 0b100 /* add-excluded commitMem(VirtualFrame, MemLambda) */;
                    this.state_ = this.state_ & 0xfffffffb /* remove-active commitMem(VirtualFrame, MemLambda) */;
                } finally {
                    lock.unlock();
                }
                return executeAndSpecialize(frameValue, instValue__);
            }
        }
        if ((state & 0b1000) != 0 /* is-active commitBool(VirtualFrame, boolean) */ && instValue_ instanceof Boolean) {
            boolean instValue__ = (boolean) instValue_;
            try {
                return commitBool(frameValue, instValue__);
            } catch (FrameSlotTypeException ex) {
                // implicit transferToInterpreterAndInvalidate()
                Lock lock = getLock();
                lock.lock();
                try {
                    this.exclude_ = this.exclude_ | 0b1000 /* add-excluded commitBool(VirtualFrame, boolean) */;
                    this.state_ = this.state_ & 0xfffffff7 /* remove-active commitBool(VirtualFrame, boolean) */;
                } finally {
                    lock.unlock();
                }
                return executeAndSpecialize(frameValue, instValue__);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return executeAndSpecialize(frameValue, instValue_);
    }

    private boolean executeAndSpecialize(VirtualFrame frameValue, Object instValue) {
        Lock lock = getLock();
        boolean hasLock = true;
        lock.lock();
        int state = state_;
        int exclude = exclude_;
        try {
            if (((exclude & 0b1)) == 0 /* is-not-excluded commitJump(VirtualFrame, JumpLambda) */ && instValue instanceof JumpLambda) {
                JumpLambda instValue_ = (JumpLambda) instValue;
                this.state_ = state = state | 0b1 /* add-active commitJump(VirtualFrame, JumpLambda) */;
                try {
                    lock.unlock();
                    hasLock = false;
                    return commitJump(frameValue, instValue_);
                } catch (FrameSlotTypeException ex) {
                    // implicit transferToInterpreterAndInvalidate()
                    lock.lock();
                    try {
                        this.exclude_ = this.exclude_ | 0b1 /* add-excluded commitJump(VirtualFrame, JumpLambda) */;
                        this.state_ = this.state_ & 0xfffffffe /* remove-active commitJump(VirtualFrame, JumpLambda) */;
                    } finally {
                        lock.unlock();
                    }
                    return executeAndSpecialize(frameValue, instValue_);
                }
            }
            if (((exclude & 0b10)) == 0 /* is-not-excluded commitRegs(VirtualFrame, RegLambda) */ && instValue instanceof RegLambda) {
                RegLambda instValue_ = (RegLambda) instValue;
                this.state_ = state = state | 0b10 /* add-active commitRegs(VirtualFrame, RegLambda) */;
                try {
                    lock.unlock();
                    hasLock = false;
                    return commitRegs(frameValue, instValue_);
                } catch (FrameSlotTypeException ex) {
                    // implicit transferToInterpreterAndInvalidate()
                    lock.lock();
                    try {
                        this.exclude_ = this.exclude_ | 0b10 /* add-excluded commitRegs(VirtualFrame, RegLambda) */;
                        this.state_ = this.state_ & 0xfffffffd /* remove-active commitRegs(VirtualFrame, RegLambda) */;
                    } finally {
                        lock.unlock();
                    }
                    return executeAndSpecialize(frameValue, instValue_);
                }
            }
            if (((exclude & 0b100)) == 0 /* is-not-excluded commitMem(VirtualFrame, MemLambda) */ && instValue instanceof MemLambda) {
                MemLambda instValue_ = (MemLambda) instValue;
                this.state_ = state = state | 0b100 /* add-active commitMem(VirtualFrame, MemLambda) */;
                try {
                    lock.unlock();
                    hasLock = false;
                    return commitMem(frameValue, instValue_);
                } catch (FrameSlotTypeException ex) {
                    // implicit transferToInterpreterAndInvalidate()
                    lock.lock();
                    try {
                        this.exclude_ = this.exclude_ | 0b100 /* add-excluded commitMem(VirtualFrame, MemLambda) */;
                        this.state_ = this.state_ & 0xfffffffb /* remove-active commitMem(VirtualFrame, MemLambda) */;
                    } finally {
                        lock.unlock();
                    }
                    return executeAndSpecialize(frameValue, instValue_);
                }
            }
            if (((exclude & 0b1000)) == 0 /* is-not-excluded commitBool(VirtualFrame, boolean) */ && instValue instanceof Boolean) {
                boolean instValue_ = (boolean) instValue;
                this.state_ = state = state | 0b1000 /* add-active commitBool(VirtualFrame, boolean) */;
                try {
                    lock.unlock();
                    hasLock = false;
                    return commitBool(frameValue, instValue_);
                } catch (FrameSlotTypeException ex) {
                    // implicit transferToInterpreterAndInvalidate()
                    lock.lock();
                    try {
                        this.exclude_ = this.exclude_ | 0b1000 /* add-excluded commitBool(VirtualFrame, boolean) */;
                        this.state_ = this.state_ & 0xfffffff7 /* remove-active commitBool(VirtualFrame, boolean) */;
                    } finally {
                        lock.unlock();
                    }
                    return executeAndSpecialize(frameValue, instValue_);
                }
            }
            throw new UnsupportedSpecializationException(this, new Node[] {this.inst_}, instValue);
        } finally {
            if (hasLock) {
                lock.unlock();
            }
        }
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

    public static CommitStateNode create(FrameSlot pcSlot, FrameSlot regsSlot, FrameSlot memSlot, InstructionNode inst) {
        return new CommitStateNodeGen(pcSlot, regsSlot, memSlot, inst);
    }

}
