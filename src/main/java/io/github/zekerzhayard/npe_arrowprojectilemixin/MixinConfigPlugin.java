package io.github.zekerzhayard.npe_arrowprojectilemixin;

import java.util.List;
import java.util.Set;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class MixinConfigPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        MappingResolver resolver = FabricLoader.getInstance().getMappingResolver();
        for (MethodNode mn : targetClass.methods) {
            if (mn.name.startsWith("handler$") && mn.name.endsWith("$interceptOnHit") && mn.desc.equals("(L" + resolver.mapClassName("intermediary", "net.minecraft.class_3966").replace('.', '/') + ";Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V")) {
                for (AbstractInsnNode node : mn.instructions.toArray()) {
                    if (node.getOpcode() == Opcodes.INVOKEVIRTUAL) {
                        MethodInsnNode min = (MethodInsnNode) node;
                        if (min.owner.equals(resolver.mapClassName("intermediary", "net.minecraft.class_1297").replace('.', '/')) && min.name.equals(resolver.mapMethodName("intermediary", "net.minecraft.class_1297", "method_5877", "()Ljava/lang/Iterable;")) && min.desc.equals("()Ljava/lang/Iterable;")) {
                            LabelNode labelNode = new LabelNode();
                            mn.instructions.insertBefore(node, new VarInsnNode(Opcodes.ASTORE, 9));
                            mn.instructions.insertBefore(node, new VarInsnNode(Opcodes.ALOAD, 9));
                            mn.instructions.insertBefore(node, new JumpInsnNode(Opcodes.IFNONNULL, labelNode));
                            mn.instructions.insertBefore(node, new InsnNode(Opcodes.RETURN));
                            mn.instructions.insertBefore(node, labelNode);
                            mn.instructions.insertBefore(node, new VarInsnNode(Opcodes.ALOAD, 9));
                        }
                    }
                }
            }
        }
    }
}
