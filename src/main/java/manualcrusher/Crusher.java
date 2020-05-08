package manualcrusher;

import it.unimi.dsi.fastutil.Hash;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.Container;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class Crusher extends Block implements BlockEntityProvider {

    public ServerPlayNetworkHandler networkHandler;

    public Crusher(Block.Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new CrusherEntity();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hitResult) {
        if(!world.isClient) {
            CrusherEntity crusherEntity = (CrusherEntity) world.getBlockEntity(pos);
            if (crusherEntity.isInvEmpty()) {
                ItemStack stackInHand = player.getStackInHand(hand);
                Item itemInHand = stackInHand.getItem();
                if (itemInHand == Items.IRON_ORE || itemInHand == Items.GOLD_ORE) {
                    player.playSound(SoundEvents.BLOCK_STONE_BREAK, 1.0F, 1.0F);
                    crusherEntity.setInvStack(0, new ItemStack(itemInHand));
                    stackInHand.decrement(1);
                    crusherEntity.sync();
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance) {
        if (!world.isClient && entity instanceof LivingEntity
            && (entity instanceof PlayerEntity || entity.getWidth() * entity.getWidth() * entity.getHeight() > 0.512F)) {
            CrusherEntity crusherEntity = (CrusherEntity) world.getBlockEntity(pos);
            crusherEntity.updateCrushingState(world, pos, 1);
            if (crusherEntity.CRUSHING_STATE == 0) {
                crusherEntity.clear();
            }
            crusherEntity.sync();
        }
        super.onLandedUpon(world, pos, entity, distance);
    }


    @Override
    public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CrusherEntity) {
                ItemScatterer.spawn(world, (BlockPos)pos, (Inventory)((CrusherEntity)blockEntity));
                // update comparators
                world.updateHorizontalAdjacent(pos, this);
            }
            super.onBlockRemoved(state, world, pos, newState, moved);
        }
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return Container.calculateComparatorOutput(world.getBlockEntity(pos));
    }
}
