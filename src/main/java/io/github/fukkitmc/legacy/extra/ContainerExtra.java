package io.github.fukkitmc.legacy.extra;

import net.minecraft.server.Container;
import org.bukkit.inventory.InventoryView;

public interface ContainerExtra {

    default InventoryView getBukkitView() {
        throw new RuntimeException("getBukkitView not implemented for class " + getClass().getName());
    }

    default void transferTo(Container other, org.bukkit.craftbukkit.entity.CraftHumanEntity player){
        throw new RuntimeException("transferTo not implemented for class " + getClass().getName());
    }

}
