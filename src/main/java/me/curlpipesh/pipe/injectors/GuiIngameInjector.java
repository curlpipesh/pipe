package me.curlpipesh.pipe.injectors;

import me.curlpipesh.bytecodetools.inject.Inject;
import me.curlpipesh.bytecodetools.inject.Injector;
import me.curlpipesh.pipe.util.Constants;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

import java.util.Iterator;
import java.util.List;

import static me.curlpipesh.bytecodetools.util.AccessHelper.isPublic;
import static me.curlpipesh.bytecodetools.util.AccessHelper.isVoid;
import static org.objectweb.asm.Opcodes.*;

/**
 * Adds the {@link me.curlpipesh.pipe.event.Render2D} event firing.
 *
 * @author c
 * @since 4/30/15
 */
@Inject(Constants.GUIINGAME)
public class GuiIngameInjector extends Injector {
    @Override
    @SuppressWarnings("unchecked")
    protected void inject(ClassReader cr, ClassNode cn) {
        ((List<MethodNode>) cn.methods).stream()
                .filter(m -> m.name.equals("a") && m.desc.equals("(F)V") && isVoid(m.desc) && isPublic(m.access))
                .forEach(m -> {
                    InsnList list = new InsnList();
                    list.add(new FieldInsnNode(GETSTATIC, "me/curlpipesh/pipe/event/Render2D", "instance", "Lme/curlpipesh/pipe/event/Render2D;"));
                    list.add(new MethodInsnNode(INVOKESTATIC, "pw/aria/event/EventManager", "push", "(Ljava/lang/Object;)Ljava/lang/Object;", false));
                    Iterator<AbstractInsnNode> i = m.instructions.iterator();
                    AbstractInsnNode node = null;
                    while(i.hasNext()) {
                        AbstractInsnNode n = i.next();
                        if(n.getOpcode() == RETURN) {
                            node = n;
                            break;
                        }
                    }
                    if(node == null) {
                        throw new IllegalStateException("RETURN insn node was null?!");
                    }
                    m.instructions.insertBefore(node, list);
                });
    }
}
