package net.greenjab.nekomasfixed.render.item.model;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.greenjab.nekomasfixed.NekomasFixed;
import net.greenjab.nekomasfixed.registries.EntityModelLayerRegistry;
import net.greenjab.nekomasfixed.registries.TextureRegistry;
import net.greenjab.nekomasfixed.render.block.entity.model.ClamBlockModel;
import net.minecraft.client.data.Model;
import net.minecraft.client.data.TextureKey;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.item.model.special.SimpleSpecialModelRenderer;
import net.minecraft.client.render.item.model.special.SpecialModelRenderer;
import net.minecraft.client.texture.SpriteHolder;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class ClamModelRenderer {//implements SimpleSpecialModelRenderer {


   /* public static final Identifier NORMAL_ID = NekomasFixed.id("normal");
    public static final Identifier BLUE_ID = NekomasFixed.id("blue");
    public static final Identifier PINK_ID = NekomasFixed.id("pink");
    public static final Identifier PURPLE_ID = NekomasFixed.id("purple");
    
    private final SpriteHolder spriteHolder;
    private final ClamBlockModel model;
    private final SpriteIdentifier textureId;
    private final float openness;

    public ClamModelRenderer(SpriteHolder spriteHolder, ClamBlockModel model, SpriteIdentifier textureId, float openness) {
        this.spriteHolder = spriteHolder;
        this.model = model;
        this.textureId = textureId;
        this.openness = openness;
    }

    @Override
    public void render(ItemDisplayContext displayContext, MatrixStack matrices, OrderedRenderCommandQueue queue, int light, int overlay, boolean glint, int i) {
        System.out.println("clam render");
        queue.submitModel(
                this.model,
                this.openness,
                matrices,
                this.textureId.getRenderLayer(RenderLayer::getEntitySolid),
                light,
                overlay,
                -1,
                this.spriteHolder.getSprite(this.textureId),
                i,
                null
        );
    }

    @Override
    public void collectVertices(Set<Vector3f> vertices) {
        MatrixStack matrixStack = new MatrixStack();
        this.model.setAngles(this.openness);
        this.model.getRootPart().collectVertices(matrixStack, vertices);
    }

    @Environment(EnvType.CLIENT)
    public record Unbaked(Identifier texture, float openness) implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<ClamModelRenderer.Unbaked> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Identifier.CODEC.fieldOf("texture").forGetter(ClamModelRenderer.Unbaked::texture),
                                Codec.FLOAT.optionalFieldOf("openness", 0.0F).forGetter(ClamModelRenderer.Unbaked::openness)
                        )
                        .apply(instance, ClamModelRenderer.Unbaked::new)
        );

        public Unbaked(Identifier texture) {
            this(texture, 0.0F);
            System.out.println("Unbaked");
        }

        @Override
        public MapCodec<ClamModelRenderer.Unbaked> getCodec() {
            System.out.println("CODEC");
            return CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(SpecialModelRenderer.BakeContext context) {
            System.out.println("Baked");
            ClamBlockModel clamBlockModel = new ClamBlockModel(context.entityModelSet().getModelPart(EntityModelLayerRegistry.CLAM));
            SpriteIdentifier spriteIdentifier = TexturedRenderLayers.CHEST_SPRITE_MAPPER.map(this.texture);
            return new ClamModelRenderer(context.spriteHolder(), clamBlockModel, spriteIdentifier, this.openness);
        }
    }*/
}
