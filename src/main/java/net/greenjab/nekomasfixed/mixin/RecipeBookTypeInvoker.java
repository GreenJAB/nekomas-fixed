package net.greenjab.nekomasfixed.mixin;

import net.minecraft.recipe.book.RecipeBookType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RecipeBookType.class)
public interface RecipeBookTypeInvoker {
    @Invoker("<init>")
    static RecipeBookType invokeInit(String internalName, int internalId) {
        throw new AssertionError();
    }

    @Invoker("values")
    static RecipeBookType[] invokeValues() {
        throw new AssertionError();
    }
}