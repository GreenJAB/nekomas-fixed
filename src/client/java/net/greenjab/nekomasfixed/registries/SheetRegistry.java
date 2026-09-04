package net.greenjab.nekomasfixed.registries;

import net.minecraft.client.renderer.SpriteMapper;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;

public class SheetRegistry {
    public static final Identifier TERRACOTTA_DECORATED_POT_SHEET = Identifier.fromNamespaceAndPath("nekomasfixed", "textures/atlas/terracotta_decorated_pot.png");
    public static final Identifier POT_FACE_ITEMS_SHEET = Identifier.fromNamespaceAndPath("nekomasfixed", "textures/atlas/custom_pot.png");

//    public static final Identifier TERRACOTTA_DECORATED_POT_DEFINITION = Identifier.fromNamespaceAndPath("nekomasfixed", "terracotta_decorated_pot");
//    public static final Identifier POT_FACE_ITEMS_DEFINITION = Identifier.fromNamespaceAndPath("nekomasfixed", "pot");

    public static final SpriteMapper TERRACOTTA_DECORATED_POT_MAPPER = new SpriteMapper(TERRACOTTA_DECORATED_POT_SHEET, "entity/terracotta_decorated_pot");
//    public static final SpriteMapper POT_FACE_ITEMS_MAPPER = new SpriteMapper(POT_FACE_ITEMS_SHEET, "entity/pot");

    public static final SpriteId TERRACOTTA_DECORATED_POT_BASE = TERRACOTTA_DECORATED_POT_MAPPER.defaultNamespaceApply("terracotta_decorated_pot_base");
//    public static final SpriteId TERRACOTTA_DECORATED_POT_SIDE = TERRACOTTA_DECORATED_POT_MAPPER.defaultNamespaceApply("terracotta_decorated_pot_side");

    public static void registerSheets(){
    }
}