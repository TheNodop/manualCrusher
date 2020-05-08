package manualcrusher;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static manualcrusher.Crusher.*;
import static manualcrusher.Initializer.GOLD_CHUNK;
import static manualcrusher.Initializer.IRON_CHUNK;

public class CrusherEntity extends BlockEntity implements CrusherInventory, BlockEntityClientSerializable {

    public DefaultedList<ItemStack> inventory;
    private static final int INVENTORY_SIZE = 1;
    public static int CRUSHING_STATE = 0;

    public CrusherEntity() {
        super(Initializer.CRUSHER_ENTITY);
        this.inventory = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);
    }

    public void updateCrushingState(World world, BlockPos pos, int amount) {
        if (!this.isInvEmpty() && !world.isClient()) {
            CRUSHING_STATE += amount;
            if (CRUSHING_STATE >= 4) {
                dropStack(world, pos, getProduct());
                CRUSHING_STATE = 0;
            }
            markDirty();
        }
    }

    private ItemStack getProduct() {
        if (getContainedItemType() == Items.IRON_ORE){
            return new ItemStack(IRON_CHUNK,2);
        } else if (getContainedItemType() == Items.GOLD_ORE) {
            return new ItemStack(GOLD_CHUNK,2);
        } else {
            return ItemStack.EMPTY;
        }
    }

    public DefaultedList<ItemStack> getItems(){
        return inventory;
    }

    public Item getContainedItemType(){
        return this.getItems().get(0).getItem();
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        CRUSHING_STATE = tag.getInt("crushing_state");
        Inventories.fromTag(tag, this.inventory);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putInt("crushing_state", CRUSHING_STATE);
        Inventories.toTag(tag, this.inventory);
        return super.toTag(tag);
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        super.fromTag(tag);
        CRUSHING_STATE = tag.getInt("crushing_state");
        Inventories.fromTag(tag, this.inventory);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        tag.putInt("crushing_state", CRUSHING_STATE);
        Inventories.toTag(tag, this.inventory);
        return super.toTag(tag);
    }
}
