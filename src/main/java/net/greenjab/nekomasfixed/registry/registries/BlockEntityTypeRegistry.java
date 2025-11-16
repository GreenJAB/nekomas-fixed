package net.greenjab.nekomasfixed.registry.registries;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registry.block.entity.ClamBlockEntity;
import net.greenjab.nekomasfixed.registry.block.entity.NautilusBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;


public class BlockEntityTypeRegistry {

    public static final BlockEntityType<ClamBlockEntity> CLAM_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, NekomasFixed.id("clam"),
            FabricBlockEntityTypeBuilder.create(ClamBlockEntity::new, BlockRegistry.CLAM, BlockRegistry.CLAM_BLUE, BlockRegistry.CLAM_PINK, BlockRegistry.CLAM_PURPLE).build());

    public static final BlockEntityType<NautilusBlockEntity> NAUTILUS_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, NekomasFixed.id("nautilus"),
            FabricBlockEntityTypeBuilder.create(NautilusBlockEntity::new, BlockRegistry.NAUTILUS_BLOCK).build());

    public static void registerBlockEntityType() {
        System.out.println("register BlockEntityType");
    }

}
