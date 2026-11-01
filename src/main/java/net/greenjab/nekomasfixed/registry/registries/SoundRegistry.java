package net.greenjab.nekomasfixed.registry.registries;

import net.greenjab.nekomasfixed.NekomasFixed;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundRegistry {
    public static final SoundEvent PHANTOM_MEMBRANE_USE = registerSoundEvent("item.phantom_membrane.use");
    public static final SoundEvent ENTITY_CLEAR_ITEM_FRAME_ADD_ITEM = registerSoundEvent("entity.clear_item_frame.add_item");
    public static final SoundEvent ENTITY_CLEAR_ITEM_FRAME_BREAK = registerSoundEvent("entity.clear_item_frame.break");
    public static final SoundEvent ENTITY_CLEAR_ITEM_FRAME_PLACE = registerSoundEvent("entity.clear_item_frame.place");
    public static final SoundEvent ENTITY_CLEAR_ITEM_FRAME_REMOVE_ITEM = registerSoundEvent("entity.clear_item_frame.remove_item");
    public static final SoundEvent ENTITY_CLEAR_ITEM_FRAME_ROTATE_ITEM = registerSoundEvent("entity.clear_item_frame.rotate_item");
    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = NekomasFixed.id(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void register() {
        //Ectoluminescence.LOGGER.info("Registering sounds for " + Ectoluminescence.MOD_ID);
    }
}
