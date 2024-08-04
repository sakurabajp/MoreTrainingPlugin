package net.cherryleaves.moreTraining;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class option_item implements Listener {
    public static ItemStack item(String name, Material item) {
        ItemStack option = new ItemStack(item);
        ItemMeta option_meta = option.getItemMeta();
        option_meta.setDisplayName(name);
        option.setItemMeta(option_meta);
        return option;
    }

    Inventory menu1 = Bukkit.createInventory(null, 45, ChatColor.DARK_AQUA + "メニュー <詳しく知りたい場合は各アイテムをクリック> ");

    @EventHandler
    public void InventoryClick(InventoryClickEvent e){
        @Nullable ItemStack i = e.getCurrentItem();
        if (i == null) {
            return;
        }
        if(Objects.requireNonNull(i).getType().equals(Material.NETHER_STAR) && i.getItemMeta().getDisplayName().equals("メニュー")){
            e.setCancelled(true);
        }
        else if(e.getInventory().equals(menu1)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void DropNetherStar(PlayerDropItemEvent e){
        @Nullable ItemStack i = e.getItemDrop().getItemStack();
        if(i.getType().equals(Material.NETHER_STAR) && i.getItemMeta().getDisplayName().equals("メニュー")){
            e.setCancelled(true);
        }
    }

    public ItemStack setItemDisplayName(Material material, String name, List<String> name2) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta).setDisplayName(name);
        meta.setLore(name2);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void Interact(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack i = e.getItem();
        if (i == null) {
            return;
        }
        if (Objects.requireNonNull(i).getType().equals(Material.NETHER_STAR) && i.getItemMeta().getDisplayName().equals("メニュー")) {
            menu1.setItem(11, setItemDisplayName(Material.STONE_SWORD, "コンバットスコア", new ArrayList<>(List.of("現在のポイントは→" + file_out(p.getUniqueId(), "Combat")))));
            menu1.setItem(12, setItemDisplayName(Material.STONE_PICKAXE, "マイニングスコア", new ArrayList<>(List.of())));
            menu1.setItem(13, setItemDisplayName(Material.FISHING_ROD, "フィッシングスコア", new ArrayList<>(List.of())));
            menu1.setItem(14, setItemDisplayName(Material.GOLDEN_BOOTS, "ウォーキングスコア", new ArrayList<>(List.of())));
            menu1.setItem(15, setItemDisplayName(Material.BREWING_STAND, "アルケミスティングスコア", new ArrayList<>(List.of(""))));
            menu1.setItem(30, setItemDisplayName(Material.JUKEBOX, "サウンド", new ArrayList<>(List.of("スコアが加算された時にサウンドを鳴らす"))));
            menu1.setItem(32, setItemDisplayName(Material.MAP, "テキストチャット", new ArrayList<>(List.of("スコアが加算された時にチャットを送信する"))));
            p.openInventory(menu1);
        }
    }

    public int file_out(UUID playerUUID, String path){
        File playerFile = new File("PD/" + playerUUID + ".yml");
        int playerD = 0;
        try {
            FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);
            playerD = playerData.getInt(path);
            playerData.save(playerFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return playerD;
    }
}
