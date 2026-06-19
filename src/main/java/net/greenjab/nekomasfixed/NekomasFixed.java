package net.greenjab.nekomasfixed;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.greenjab.nekomasfixed.network.SyncHandler;
import net.greenjab.nekomasfixed.registry.block.cauldron.CauldronBehaviour;
import net.greenjab.nekomasfixed.registry.block.entity.SoupCauldronBlockEntity;
import net.greenjab.nekomasfixed.registry.entity.Moobloom.MoobloomEntity;
import net.greenjab.nekomasfixed.registry.entity.SuspiciousSpider.SuspiciousSpiderEntity;
import net.greenjab.nekomasfixed.registry.entity.Termite.TermiteEntity;
import net.greenjab.nekomasfixed.registry.registries.*;
import net.greenjab.nekomasfixed.util.CakeRegistry;
import net.greenjab.nekomasfixed.util.ModTreeDecorators;
import net.greenjab.nekomasfixed.util.ModTrunkPlacers;
import net.greenjab.nekomasfixed.world.ModWorldGeneration;
import net.minecraft.block.*;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
		CustomTrackedDataHandlerRegistry.init();
		EntityTypeRegistry.registerEntityType();
		ModTrunkPlacers.register();
		ModTreeDecorators.register();
		ModWorldGeneration.generateModWorldGen();
		OtherRegistry.registerOther();
		RecipeRegistry.registerRecipes();
		SyncHandler.init();
		CakeRegistry.init();
		CauldronBehaviour.register();
		ScreenHandlerRegistry.registerScreenHandlers();

		System.out.println(Blocks.RED_BED.getTranslationKey());

		UseBlockCallback.EVENT.register((PlayerEntity player, World world, Hand hand, BlockHitResult hit) -> {
			BlockPos pos = hit.getBlockPos();
			BlockState state = world.getBlockState(pos);

			if (!world.isClient() &&
					!player.getMainHandStack().isOf(Items.AIR) &&
					(world.getBlockState(pos.down()).isIn(BlockTags.FIRE) || world.getBlockState(pos.down()).isIn(BlockTags.CAMPFIRES)) &&
					state.isOf(Blocks.WATER_CAULDRON)
					&& (player.getMainHandStack().isIn(ItemTags.MEAT) || player.getMainHandStack().isOf(Items.POTION))) {

				world.setBlockState(pos, BlockRegistry.SOUP_CAULDRON.getDefaultState());
				if (world.getBlockEntity(pos) instanceof SoupCauldronBlockEntity soup ) {
					soup.addInput(player.getMainHandStack().copyWithCount(1));
				}

				player.getMainHandStack().decrementUnlessCreative(1, player);
				return ActionResult.SUCCESS;
			}
			if(!world.isClient() && world.getBlockEntity(pos) instanceof SoupCauldronBlockEntity blockEntity){
				ItemStack stack = player.getMainHandStack();
				if(player.getMainHandStack().isIn(ItemTags.MEAT) || player.getMainHandStack().isOf(Items.POTION)){
					blockEntity.addInput(stack.copyWithCount(1));
					player.getMainHandStack().decrementUnlessCreative(1, player);
					return ActionResult.SUCCESS;
				}
				else if(!blockEntity.canBePicked() && stack.isOf(Items.STICK) && (world.getBlockState(pos.down()).isIn(BlockTags.FIRE) || world.getBlockState(pos.down()).isIn(BlockTags.CAMPFIRES))){
					//i am putting the condition here too -> if the player breaks the fire block... the soup cauldron can still be filled with ingredients but for mixing he should a fire block beneath the soup cauldron
					blockEntity.setHasStirred(true);
					return ActionResult.SUCCESS;
				}
				else if(stack.isOf(Items.BOWL) && blockEntity.hasStirred){
					blockEntity.setHasStirred(false);
					ItemStack soup = new ItemStack(ItemRegistry.SPECIAL_STEW);
					List<ItemStack> copiedInputs = blockEntity.getInputs().stream().map(ItemStack::copy).toList();
					soup.set(OtherRegistry.SOUP_INGREDIENTS, copiedInputs);
					player.setStackInHand(Hand.MAIN_HAND, soup);
					world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
					return ActionResult.SUCCESS;
				}
			}
			return ActionResult.PASS;
		});

        LOGGER.info("TERMITE = {}", EntityTypeRegistry.TERMITE);
		FabricDefaultAttributeRegistry.register(EntityTypeRegistry.TERMITE, TermiteEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(EntityTypeRegistry.MOOBLOOM, MoobloomEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(EntityTypeRegistry.SUS_SPIDER, SuspiciousSpiderEntity.createPoisenousSpiderAttributes());
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