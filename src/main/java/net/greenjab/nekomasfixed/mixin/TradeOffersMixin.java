package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.entity.npc.villager.VillagerTrades;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.world.entity.npc.villager.VillagerTrades.TRADES;

@Mixin(VillagerTrades.class)
public class TradeOffersMixin {

    @Inject(method = "<clinit>", at = @At(value = "TAIL"))
    private static void modifyVillagers(CallbackInfo ci) {
        TRADES.get(VillagerProfession.FISHERMAN).replace(3, new VillagerTrades.ItemListing[]{
                new VillagerTrades.EmeraldForItems(ItemRegistry.PEARL, 3, 16, 20),
                new VillagerTrades.EnchantedItemForEmeralds(Items.FISHING_ROD, 3, 3, 10, 0.2F)
            });
        }
}
