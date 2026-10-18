package net.greenjab.nekomasfixed.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Option<T> {
    public static final List<Option<?>> ALL = new ArrayList<>();

    public final String name;
    public final T defaultValue;
    private T value;

    public final boolean lockedInWorld;

    public Option(String name, T defaultValue) {
        this(name, defaultValue, false);
    }

    public Option(String name, T defaultValue, boolean lockedInWorld) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.lockedInWorld = lockedInWorld;
        ALL.add(this);
    }

    public T get(){
        return value == null ? defaultValue : value;
    }
    public void set(T v){
        value = v;
    }
    public void reset(){
        set(defaultValue);
    }

    @SuppressWarnings("unchecked")
    public void load(JsonObject json, Gson gson) {
        if (!json.has(name)) { reset(); return; }
        try {
            set((T) gson.fromJson(json.get(name), defaultValue.getClass()));
        } catch (Exception e) {
            reset();
        }
    }

    public void save(JsonObject json, Gson gson) {
        json.add(name, gson.toJsonTree(get()));
    }
}