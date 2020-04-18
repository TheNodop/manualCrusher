package manualcrusher;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;

import static manualcrusher.Crusher.*;
import static manualcrusher.Initializer.GOLD_CHUNK;
import static manualcrusher.Initializer.IRON_CHUNK;

public class CrusherEntity extends BlockEntity {

    private int crushing_state = 0;
    private ItemStack content = ItemStack.EMPTY;

    public CrusherEntity() {
        super(Initializer.CRUSHER_ENTITY);
    }

    public boolean isEmpty(){
        return content == ItemStack.EMPTY;
    }

    public void setContent(ItemStack stack) {
        content = stack;
    }

    public ItemStack getContent() {
        return content;
    }

    public void updateCrushingState(int amount) {
        if (!isEmpty()) {
            crushing_state += amount;
            if (crushing_state >= 8) {
                crushing_state = 0;
                content = ItemStack.EMPTY;
                dropStack(getWorld(),pos, getProduct(content));
            }
            markDirty();
        }
    }

    private ItemStack getProduct(ItemStack content) {
        if (content.isItemEqual(new ItemStack(Items.IRON_ORE))){
            return new ItemStack(IRON_CHUNK,2);
        } else if (content.isItemEqual(new ItemStack(Items.GOLD_ORE))){
            return new ItemStack(GOLD_CHUNK,2);
        } else {
            return ItemStack.EMPTY;
        }
    }

    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);

        // Save current state to the tag
        tag.putInt("crushing_state", crushing_state);
        tag.putInt("content_type", contentToInt(content.getItem()));

        return tag;
    }

    private int contentToInt(Item content) {
        int returnValue = 0;
        if (content == Items.IRON_ORE) {
            returnValue = 1;
        } else if (content == Items.GOLD_ORE) {
            returnValue =  2;
        }
        return returnValue;
    }

    private ItemStack intToContent(int content) {
        ItemStack returnValue = ItemStack.EMPTY;
        if (content == 1) {
            returnValue = new ItemStack((Items.IRON_ORE));
        } else if (content == 2) {
            returnValue = new ItemStack((Items.GOLD_ORE));
        }
        return returnValue;
    }

    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        crushing_state = tag.getInt("crushing_state");
        content = intToContent(tag.getInt("content_type"));
    }
}
