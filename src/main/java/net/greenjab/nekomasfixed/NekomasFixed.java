package net.greenjab.nekomasfixed;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.greenjab.nekomasfixed.network.SyncHandler;
import net.greenjab.nekomasfixed.registry.block.HollowOakLogBlock;
import net.greenjab.nekomasfixed.registry.block.cauldron.CauldronBehaviour;
import net.greenjab.nekomasfixed.registry.block.entity.HollowOakLogBlockEntity;
import net.greenjab.nekomasfixed.registry.entity.Termite.TermiteEntity;
import net.greenjab.nekomasfixed.registry.registries.*;
import net.greenjab.nekomasfixed.util.ModTreeDecorators;
import net.greenjab.nekomasfixed.util.ModTrunkPlacers;
import net.greenjab.nekomasfixed.world.ModWorldGeneration;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

			if (state.getBlock() instanceof HollowOakLogBlock) {

				if (!world.isClient()) {
					BlockEntity be = world.getBlockEntity(pos);

					if (be instanceof HollowOakLogBlockEntity logBE) {
						if(player.getMainHandStack().getItem() instanceof BlockItem blockItem){
							logBE.setStoredBlock(blockItem.getBlock().getDefaultState());
							System.out.println(logBE.getStoredBlock());
							world.updateListeners(pos, state, state, 3);
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