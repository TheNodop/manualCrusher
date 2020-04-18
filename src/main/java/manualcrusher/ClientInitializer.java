package manualcrusher;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;

import static manualcrusher.Initializer.CRUSHER_ENTITY;

public class ClientInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Client Side Stuff
        BlockEntityRendererRegistry.INSTANCE.register(CRUSHER_ENTITY, CrusherRenderer::new);
    }
}
