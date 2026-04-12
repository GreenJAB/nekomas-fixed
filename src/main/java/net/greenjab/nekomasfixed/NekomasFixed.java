package net.greenjab.nekomasfixed;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.greenjab.nekomasfixed.mixin.accessor.FlowerPotBlockAccessor;
import net.greenjab.nekomasfixed.network.SyncHandler;
import net.greenjab.nekomasfixed.registry.block.AbstractHollowLogBlock;
import net.greenjab.nekomasfixed.registry.block.cauldron.CauldronBehaviour;
import net.greenjab.nekomasfixed.registry.block.entity.AbstractHollowLogBlockEntity;
import net.greenjab.nekomasfixed.registry.entity.Termite.TermiteEntity;
import net.greenjab.nekomasfixed.registry.registries.*;
import net.greenjab.nekomasfixed.util.ModTreeDecorators;
import net.greenjab.nekomasfixed.util.ModTrunkPlacers;
import net.greenjab.nekomasfixed.world.ModWorldGeneration;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.datafixer.fix.ChunkPalettedStorageFix;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.greenjab.nekomasfixed.registry.block.AbstractHollowLogBlock.HAS_WATER;
import static net.greenjab.nekomasfixed.registry.block.AbstractHollowLogBlock.LIGHT_LEVEL;
import static net.minecraft.block.PillarBlock.AXIS;

public class NekomasFixed implements ModInitializer {
	public static final String MOD_NAME = "Nekomas' Fixed Minecraft";
	public static final String NAMESPACE = "nekomasfixed";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAMESPACE);

	@Override
	public void onInitialize() {
		BlockEntityTypeRegistry.registerBlockEntityType();
		BlockRegistry.registerBlocks();
		ItemRegistry.registerItems();
		ItemGroupRegistry.registerItemGroup();
		EntityTypeRegistry.registerEntityType();
		ModTrunkPlacers.register();
		ModTreeDecorators.register();
		ModWorldGeneration.generateModWorldGen();
		OtherRegistry.registerOther();
		RecipeRegistry.registerRecipes();
		SyncHandler.init();
		CauldronBehaviour.register();
		ScreenHandlerRegistry.registerScreenHandlers();

		UseBlockCallback.EVENT.register((PlayerEntity player, World world, Hand hand, BlockHitResult hit) -> {
			BlockPos pos = hit.getBlockPos();
			BlockState state = world.getBlockState(pos);

			if (state.getBlock() instanceof AbstractHollowLogBlock) {

				if (!world.isClient()) {
					BlockEntity be = world.getBlockEntity(pos);

                    if (be instanceof AbstractHollowLogBlockEntity logBE) {
						if(player.getMainHandStack().isOf(Items.SHEARS) && !world.isClient()){
							player.dropStack((ServerWorld) world, logBE.getStoredBlock().getPickStack(world, pos.north(), true));
							logBE.setStoredBlock(Blocks.AIR.getDefaultState());
							world.updateListeners(pos, state,state, 3);
						}

						if(player.getMainHandStack().getItem() instanceof BlockItem blockItem){
							if(blockItem.getBlock().getDefaultState().isIn(BlockTags.FLOWERS) && logBE.getStoredBlock().isIn(BlockTags.FLOWER_POTS)){
								Block plant = blockItem.getBlock();
								Block potted = FlowerPotBlockAccessor.getContentToPotted().get(plant);
								if (potted != null) {
									logBE.setStoredBlock(potted.getDefaultState());
								}
							}
							if(logBE.getStoredBlock().isAir()){
								logBE.setStoredBlock(blockItem.getBlock().getDefaultState());
								player.getMainHandStack().decrementUnlessCreative(1, player);
								world.updateListeners(pos, state, state, 3);
							}
							if(blockItem.getBlock().getDefaultState().getLuminance()>0){
								world.setBlockState(pos, state.with(LIGHT_LEVEL, blockItem.getBlock().getDefaultState().getLuminance()));
							}

						}
						else if(player.getMainHandStack().isOf(Items.WATER_BUCKET)){
							if (state.get(PillarBlock.AXIS).isVertical() && !world.getBlockState(pos.down()).isAir()){
								player.setStackInHand(Hand.MAIN_HAND, Items.BUCKET.getDefaultStack());
								world.setBlockState(pos, state.with(HAS_WATER, true), Block.NOTIFY_ALL);
								player.dropStack((ServerWorld) world, logBE.getStoredBlock().getPickStack(world, pos, true));
							}
						}
						else if(player.getMainHandStack().isOf(Items.BUCKET) && logBE.getCachedState().get(HAS_WATER)){
							player.setStackInHand(Hand.MAIN_HAND, Items.WATER_BUCKET.getDefaultStack());
							world.setBlockState(pos, state.with(HAS_WATER, false), Block.NOTIFY_ALL);
						}
						logBE.markDirty();
					}
				}
				return ActionResult.SUCCESS;
			}
			return ActionResult.PASS;
		});

        LOGGER.info("TERMITE = {}", EntityTypeRegistry.TERMITE);
		FabricDefaultAttributeRegistry.register(EntityTypeRegistry.TERMITE, TermiteEntity.createAttributes());
	}


	public static Identifier id(String path) {
		return Identifier.of(NAMESPACE, path);
	}

	public static int enchantLevel(ItemStack stack, String name) {
		int level = 0;
		ItemEnchantmentsComponent itemEnchantmentsComponent = stack.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);
		for (RegistryEntry<Enchantment> e : stack.getEnchantments().getEnchantments()) {
			if (e.getIdAsString().toLowerCase().contains(name.toLowerCase())) {
				level += itemEnchantmentsComponent.getLevel(e);
			}
		}
		return level;
	}
}