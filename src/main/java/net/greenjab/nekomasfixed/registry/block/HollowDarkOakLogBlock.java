package net.greenjab.nekomasfixed.registry.block;

import net.greenjab.nekomasfixed.registry.block.entity.HollowDarkOakLogBlockEntity;
import net.greenjab.nekomasfixed.registry.item.SoulfireTridentItem;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;

public class HollowDarkOakLogBlock extends AbstractHollowLogBlock{
    private HollowDarkOakLogBlockEntity blockEntity;
    public HollowDarkOakLogBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUseWithItem(ItemStack stack, BlockState state, World world,
                                         BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        System.out.println("clicked!!");
        if (!world.isClient() && stack.isOf(Items.SHEARS)) {
            System.out.println("entered IFELSE");
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof HollowDarkOakLogBlockEntity logBE) {
                System.out.println("Stored: " +
                        net.minecraft.registry.Registries.BLOCK.getId(logBE.getStoredBlock().getBlock()));

                if (!logBE.getStoredBlock().isAir()) {
                    player.dropStack(
                            (ServerWorld) world,
                            logBE.getStoredBlock().getPickStack(world, pos, true)
                    );
                }
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        this.blockEntity = new HollowDarkOakLogBlockEntity(pos, state);
        return this.blockEntity;
    }
}
