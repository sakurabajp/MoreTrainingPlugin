package net.cherryleaves.moreTraining;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class option_item implements Listener {
    public static ItemStack item(String name, Material item) {
        ItemStack option = new ItemStack(item);
        ItemMeta option_meta = option.getItemMeta();
        option_meta.setDisplayName(name);
        option.setItemMeta(option_meta);
        return option;
    }
}
