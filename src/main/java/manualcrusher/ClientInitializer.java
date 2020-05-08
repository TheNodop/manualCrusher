package manualcrusher;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;

import static manualcrusher.Initializer.CRUSHER_ENTITY;

public class ClientInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.INSTANCE.register(CRUSHER_ENTITY, CrusherRenderer::new);

    }
}
