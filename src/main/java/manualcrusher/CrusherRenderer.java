package manualcrusher;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.datafixer.fix.EntityItemFrameDirectionFix;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;

public class CrusherRenderer extends BlockEntityRenderer<CrusherEntity> {

    public CrusherRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(CrusherEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        Inventory inventory = (Inventory) blockEntity.getWorld().getBlockEntity(blockEntity.getPos());
        ItemStack stack = inventory.getInvStack(0);

        matrices.push();
        matrices.translate(0.5, 0.95, 0.5);
        if(!stack.isEmpty()) {
            // Move the item

            // Lighting (would be black)
            int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());

            //MinecraftClient.getInstance().getEntityRenderManager().render(item,0,0,0,0,0,matrices, vertexConsumers, lightAbove);
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers);
        }
        matrices.pop();
    }
}
