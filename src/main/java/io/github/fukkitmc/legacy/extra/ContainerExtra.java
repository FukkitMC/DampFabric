package io.github.fukkitmc.legacy.extra;

import org.bukkit.inventory.InventoryView;

public interface ContainerExtra {

    default InventoryView getBukkitView() {
        throw new RuntimeException("getBukkitView not implemented for class " + getClass().getName());
    }

}
