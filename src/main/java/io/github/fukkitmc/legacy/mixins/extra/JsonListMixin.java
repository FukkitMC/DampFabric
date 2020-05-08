package io.github.fukkitmc.legacy.mixins.extra;

import io.github.fukkitmc.legacy.extra.JsonListExtra;
import net.minecraft.server.JsonList;
import net.minecraft.server.JsonListEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.Map;

@Mixin(JsonList.class)
public class JsonListMixin<V> implements JsonListExtra {


    @Shadow public Map<String, V> d;

    @Override
    public Collection<JsonListEntry> getValues() {
        return (Collection<JsonListEntry>) this.d.values();
    }
}
