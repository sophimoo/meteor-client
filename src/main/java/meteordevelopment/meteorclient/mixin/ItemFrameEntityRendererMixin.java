/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.mixin;

import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.render.NoRender;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.entity.ItemFrameEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemFrameEntityRenderer.class)
public class ItemFrameEntityRendererMixin {
    // Modify the BlockState variable to AIR when hiding item frames
    // This is loaded before renderBlockAsEntity is called
    @ModifyVariable(
        method = "render(Lnet/minecraft/client/render/entity/state/ItemFrameEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V",
        at = @At(value = "STORE"),
        ordinal = 0
    )
    private BlockState modifyFrameBlockState(BlockState state) {
        if (Modules.get().get(NoRender.class).noItemFrames()) {
            return Blocks.AIR.getDefaultState();
        }
        return state;
    }
}
