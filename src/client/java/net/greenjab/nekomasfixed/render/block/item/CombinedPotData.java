package net.greenjab.nekomasfixed.render.block.item;

import net.greenjab.nekomasfixed.registry.other.PotEngravingDecoration;
import net.greenjab.nekomasfixed.registry.other.PotFaceDecoration;
import net.minecraft.world.level.block.entity.PotDecorations;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public record CombinedPotData(
         Optional<PotDecorations> vanillaDecorations,
         Optional<PotEngravingDecoration> engraving,
         Optional<PotFaceDecoration> face
) {}
