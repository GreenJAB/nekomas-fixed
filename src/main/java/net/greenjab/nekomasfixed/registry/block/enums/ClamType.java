package net.greenjab.nekomasfixed.registry.block.enums;
import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

public enum ClamType implements StringIdentifiable {
	REGULAR("regular"),
	BLUE("blue"),
	PINK("pink"),
	PURPLE("purple");

	private final String name;

	public static final Codec<ClamType> CODEC = StringIdentifiable.createCodec(ClamType::values);

	ClamType(final String name) {
		this.name = name;
	}

	@Override
	public String asString() {
		return this.name;
	}

}