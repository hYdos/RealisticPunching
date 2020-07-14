package io.github.hydos.realisticpunching.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    @Final
    private EntityRenderDispatcher renderManager;

    @Inject(method = "renderArmHoldingItem", at = @At("HEAD"), cancellable = true)
    public void test(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float equipProgress, float swingProgress, Arm arm, CallbackInfo ci) {
        ci.cancel();
        matrices.push();
        renderRightArm(matrices, vertexConsumers, light, equipProgress, swingProgress, arm);
        matrices.pop();
        matrices.push();
        renderLeftArm(matrices, vertexConsumers, light, equipProgress, swingProgress, arm);
        matrices.pop();
    }

    private void renderLeftArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float equipProgress, float swingProgress, Arm arm) {
        float g = MathHelper.sqrt(swingProgress);
        float h = -0.3F * MathHelper.sin(g * 3.1415927F) + 0.05f;
        float i = 0.4F * MathHelper.sin(g * 6.2831855F);
        float j = -0.4F * MathHelper.sin(swingProgress * 3.1415927F);
        matrices.translate((double) (-1.0F * (h + 0.64000005F)), (double) (i + -0.6F + equipProgress * -0.6F), j + -0.71999997F);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-1.0F * 45.0F));
        float k = MathHelper.sin(swingProgress * swingProgress * 3.1415927F);
        float l = MathHelper.sin(g * 3.1415927F);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-1.0F * l * 70.0F));
        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-1.0F * k * -20.0F));
        AbstractClientPlayerEntity abstractClientPlayerEntity = this.client.player;
        this.client.getTextureManager().bindTexture(abstractClientPlayerEntity.getSkinTexture());
        matrices.translate(-1.0F * -1.0F, 3.5999999046325684D, 3.5D);
        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-1.0F * 125.0F));
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(200.0F));
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-1.0F * -135.0F));
        matrices.translate(-1.0F * 5.6F, 0.0D, 0.0D);
        PlayerEntityRenderer playerEntityRenderer = (PlayerEntityRenderer) this.renderManager.getRenderer(abstractClientPlayerEntity);
        playerEntityRenderer.renderLeftArm(matrices, vertexConsumers, light, abstractClientPlayerEntity);

    }

    private void renderRightArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float equipProgress, float swingProgress, Arm arm) {
        float f = 1.0F;
        float g = MathHelper.sqrt(swingProgress);
        float h = -0.3F * MathHelper.sin(g * 3.1415927F) + 0.05f;
        float i = 0.4F * MathHelper.sin(g * 6.2831855F);
        float j = -0.4F * MathHelper.sin(swingProgress * 3.1415927F);
        matrices.translate((double) (f * (h + 0.64000005F)), (double) (i + -0.6F + equipProgress * -0.6F), (double) (j + -0.71999997F));
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(f * 45.0F));
        float k = MathHelper.sin(swingProgress * swingProgress * 3.1415927F);
        float l = MathHelper.sin(g * 3.1415927F);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(f * l * 70.0F));
        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(f * k * -20.0F));
        AbstractClientPlayerEntity abstractClientPlayerEntity = this.client.player;
        this.client.getTextureManager().bindTexture(abstractClientPlayerEntity.getSkinTexture());
        matrices.translate((double) (f * -1.0F), 3.5999999046325684D, 3.5D);
        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(f * 120.0F));
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(200.0F));
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(f * -135.0F));
        matrices.translate((double) (f * 5.6F), 0.0D, 0.0D);
        PlayerEntityRenderer playerEntityRenderer = (PlayerEntityRenderer) this.renderManager.getRenderer(abstractClientPlayerEntity);
        playerEntityRenderer.renderRightArm(matrices, vertexConsumers, light, abstractClientPlayerEntity);

    }


}
