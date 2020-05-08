package manualcrusher;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Optional;

public class Initializer implements ModInitializer {

	public static final String MOD_ID = "manualcrusher";

	public static final Item IRON_CHUNK = new Item(new Item.Settings().group(ItemGroup.MATERIALS));
	public static final Item GOLD_CHUNK = new Item(new Item.Settings().group(ItemGroup.MATERIALS));

	public static final Crusher MANUAL_CRUSHER = new Crusher(BlockWithEntity.Settings.of(Material.METAL));
	public static BlockEntityType<CrusherEntity> CRUSHER_ENTITY;


	public static final RecipeType<CrushingRecipe> CRUSHING = new RecipeType<CrushingRecipe>() {
		@Override
		public <C extends Inventory> Optional<CrushingRecipe> get(Recipe<C> recipe, World world, C inventory) {
			return Optional.empty();
		}
	};


	@Override
	public void onInitialize() {
		System.out.println("Loading manualCrusher");


		Registry.register(Registry.RECIPE_TYPE, new Identifier(MOD_ID, "crushing"), CRUSHING);

		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "iron_chunk"), IRON_CHUNK);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "gold_chunk"), GOLD_CHUNK);

		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "crusher"), MANUAL_CRUSHER);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "crusher"), new BlockItem(MANUAL_CRUSHER,
				new Item.Settings().group(ItemGroup.REDSTONE)));

		CRUSHER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "manualcrusher:crusher", BlockEntityType.Builder.create(CrusherEntity::new, MANUAL_CRUSHER).build(null));


		System.out.println("Finished loading manualCrusher");
	}


}
