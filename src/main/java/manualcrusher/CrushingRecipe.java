package manualcrusher;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.commons.lang3.Validate;

public class CrushingRecipe implements Recipe<Inventory> {

    private Identifier id;
    private ItemStack input;
    private ItemStack output;
    private final String type;

    public CrushingRecipe(Identifier id, String type, ItemStack input, ItemStack output) {
        this.id = id;
        this.type = type;
        this.input = input;
        this.output = output;
    }

    public void serialize(JsonObject jsonObject){
        jsonObject.addProperty("input", this.input.getName().toString());
        jsonObject.addProperty("output", this.output.getName().toString());
        jsonObject.addProperty("amount", this.output.getCount());
    }

    public void deserialize(JsonObject jsonObject){
        Validate.isTrue(this.input.isEmpty());
        this.input = deserializeItemStack(JsonHelper.getString(jsonObject, "input"));
        this.output = deserializeItemStack(JsonHelper.getString(jsonObject, "output"), JsonHelper.getInt(jsonObject, "amount", 1));
    }

    public ItemStack deserializeItemStack(String name) { return deserializeItemStack(name, 1); }

    public ItemStack deserializeItemStack(String name, int amount) {
        Item item = (Item)Registry.ITEM.get(new Identifier(name));
        return new ItemStack(item, amount);
    }

    public ItemStack getIngredient() { return this.input; }

    @Override
    public ItemStack getOutput() { return this.output; }

    @Override
    public Identifier getId() { return this.id; }

    @Override
    public RecipeSerializer<?> getSerializer() { return null; }

    @Override
    public RecipeType<?> getType() { return Initializer.CRUSHING; }

    @Override
    public boolean matches(Inventory inv, World worldIn) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ItemStack craft(Inventory inv) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean fits(int width, int height) {
        throw new UnsupportedOperationException();
    }
}
