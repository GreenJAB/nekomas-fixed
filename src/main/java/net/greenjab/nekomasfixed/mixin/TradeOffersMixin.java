package net.greenjab.nekomasfixed.mixin;

import net.greenjab.nekomasfixed.registry.registries.ItemRegistry;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.village.TradeOffers.PROFESSION_TO_LEVELED_TRADE;

@Mixin(TradeOffers.class)
public class TradeOffersMixin {

    @Inject(method = "<clinit>", at = @At(value = "TAIL"))
    private static void modifyVillagers(CallbackInfo ci) {
        PROFESSION_TO_LEVELED_TRADE.get(VillagerProfession.FISHERMAN).replace(3, new TradeOffers.Factory[]{
                new TradeOffers.BuyItemFactory(ItemRegistry.PEARL, 3, 16, 20),
                new TradeOffers.SellEnchantedToolFactory(Items.FISHING_ROD, 3, 3, 10, 0.2F)
            });
        }
}
