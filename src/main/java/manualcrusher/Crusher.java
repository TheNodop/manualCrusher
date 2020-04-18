package manualcrusher;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.List;

public class Crusher extends Block implements BlockEntityProvider {

    public Crusher(Block.Settings settings)
    {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new CrusherEntity();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hitResult) {
        CrusherEntity crusherEntity = (CrusherEntity)world.getBlockEntity(pos);
        if(!world.isClient && crusherEntity.isEmpty()){
            ItemStack stackInHand = player.getStackInHand(hand);
            Item itemInHand = stackInHand.getItem();
            if(itemInHand == Items.IRON_ORE | itemInHand == Items.GOLD_ORE){
                crusherEntity.setContent(new ItemStack(itemInHand));
                stackInHand.decrement(1);
                return ActionResult.SUCCESS;
            }
        }
        return super.onUse(state, world, pos, player, hand, hitResult);
    }

    public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance) {
        if (!world.isClient && world.random.nextFloat() < distance - 0.5F && entity instanceof LivingEntity && (entity instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) && entity.getWidth() * entity.getWidth() * entity.getHeight() > 0.512F) {
            CrusherEntity crusherEntity = (CrusherEntity)world.getBlockEntity(pos);
            crusherEntity.updateCrushingState(1);
        }

        super.onLandedUpon(world, pos, entity, distance);

    }

}
