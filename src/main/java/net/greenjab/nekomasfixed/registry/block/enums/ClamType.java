package net.greenjab.nekomasfixed.registry.block.enums;
import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

public enum ClamType implements StringRepresentable {
	REGULAR("regular"),
	BLUE("blue"),
	PINK("pink"),
	PURPLE("purple");

	private final String name;

	public static final Codec<ClamType> CODEC = StringRepresentable.fromEnum(ClamType::values);

	ClamType(final String name) {
		this.name = name;
	}

	@Override
	public @NonNull String getSerializedName() {
		return this.name;
	}

}