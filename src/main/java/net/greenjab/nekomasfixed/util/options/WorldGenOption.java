package net.greenjab.nekomasfixed.util.options;

public class WorldGenOption<T> extends Option<T> {


    public WorldGenOption(String name, T defaultValue) {
        super(name, defaultValue, true);
    }
}
