package manualcrusher;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Initializer implements ModInitializer {

	public static final Item IRON_CHUNK = new Item(new Item.Settings().group(ItemGroup.MATERIALS));
	public static final Item GOLD_CHUNK = new Item(new Item.Settings().group(ItemGroup.MATERIALS));

	public static final Crusher MANUAL_CRUSHER = new Crusher(Block.Settings.of(Material.METAL));

	public static BlockEntityType<CrusherEntity> CRUSHER_ENTITY;

	@Override
	public void onInitialize() {
		System.out.println("Loading manualCrusher");

		Registry.register(Registry.ITEM, new Identifier("manualcrusher", "iron_chunk"), IRON_CHUNK);
		Registry.register(Registry.ITEM, new Identifier("manualcrusher", "gold_chunk"), GOLD_CHUNK);

		Registry.register(Registry.BLOCK, new Identifier("manualcrusher", "crusher"), MANUAL_CRUSHER);
		Registry.register(Registry.ITEM, new Identifier("manualcrusher", "crusher"), new BlockItem(MANUAL_CRUSHER,
				new Item.Settings().group(ItemGroup.REDSTONE)));

		CRUSHER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "manualcrusher:crusher", BlockEntityType.Builder.create(CrusherEntity::new, MANUAL_CRUSHER).build(null));

		System.out.println("Finished loading manualCrusher");
	}


}
