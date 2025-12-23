package net.greenjab.nekomasfixed.registry.block.enums;
import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

public enum NautilusBlockType implements StringIdentifiable {
	REGULAR("regular"),
	ZOMBIE("zombie"),
	CORAL("coral");

	private final String name;

	public static final Codec<NautilusBlockType> CODEC = StringIdentifiable.createCodec(NautilusBlockType::values);

	NautilusBlockType(final String name) {
		this.name = name;
	}

	@Override
	public String asString() {
		return this.name;
	}

}