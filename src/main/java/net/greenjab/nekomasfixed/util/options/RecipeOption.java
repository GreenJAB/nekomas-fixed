package net.greenjab.nekomasfixed.util.options;

import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class RecipeOption extends Option<Boolean> {
    public static final List<RecipeOption> ALL_RECIPES = new ArrayList<>();

    private final Predicate<String> pathMatcher;

    public RecipeOption(String name, Predicate<String> pathMatcher, boolean defaultValue) {
        super(name, defaultValue);
        this.pathMatcher = pathMatcher;
        ALL_RECIPES.add(this);
    }

    public boolean controls(Identifier id) {
        return id.getNamespace().equals("nekomasfixed") && pathMatcher.test(id.getPath());
    }
}