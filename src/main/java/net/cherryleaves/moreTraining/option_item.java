package net.cherryleaves.moreTraining;

import org.bukkit.*;
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

    String SoundMenuName_on = "サウンド " + ChatColor.GREEN + "on";
    String SoundMenuName_off = "サウンド " + ChatColor.RED + "off";
    String TextMenuName_on = "チャット " + ChatColor.GREEN + "on";
    String TextMenuName_off = "チャット " + ChatColor.RED + "off";

    @EventHandler
    public void InventoryClick(InventoryClickEvent e){
        @Nullable ItemStack i = e.getCurrentItem();
        Player p = (Player) e.getWhoClicked();
        if (i == null) {
            return;
        }
        if(Objects.requireNonNull(i).getType().equals(Material.NETHER_STAR) && i.getItemMeta().getDisplayName().equals("メニュー") && !p.getGameMode().equals(GameMode.CREATIVE)){
            e.setCancelled(true);
        }
        else if(e.getView().getTitle().equals(ChatColor.DARK_AQUA + "ショップ")){
            e.setCancelled(true);
        }
        else if(e.getView().getTitle().equals(ChatColor.DARK_AQUA + "メニュー <詳しくは各アイテムクリック>")) {
            e.setCancelled(true);
            if(i.getType().equals(Material.JUKEBOX)) {
                p.playSound(p.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                if(p.getScoreboardTags().contains("Tag_SoundMenuName_on")){
                    p.removeScoreboardTag("Tag_SoundMenuName_on");
                    p.addScoreboardTag("Tag_SoundMenuName_off");
                    menuGUI(p, p);
                }
                else if(p.getScoreboardTags().contains("Tag_SoundMenuName_off")){
                    p.removeScoreboardTag("Tag_SoundMenuName_off");
                    p.addScoreboardTag("Tag_SoundMenuName_on");
                    menuGUI(p, p);
                }
                else{
                    p.addScoreboardTag("Tag_SoundMenuName_on");
                }
            }
            if(i.getType().equals(Material.MAP)){
                p.playSound(p.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1.0f, 1.0f);
                if(p.getScoreboardTags().contains("Tag_TextMenuName_on")) {
                    p.removeScoreboardTag("Tag_TextMenuName_on");
                    p.addScoreboardTag("Tag_TextMenuName_off");
                    menuGUI(p, p);
                }
                else if(p.getScoreboardTags().contains("Tag_TextMenuName_off")) {
                    p.removeScoreboardTag("Tag_TextMenuName_off");
                    p.addScoreboardTag("Tag_TextMenuName_on");
                    menuGUI(p, p);
                }
                else{
                    p.addScoreboardTag("Tag_TextMenuName_on");
                }
            }
            else if (i.getType().equals(Material.OAK_SIGN)){
                shopGUI(p, p);
            }
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
            menuGUI(p, p);
        }
    }

    public static int file_out(UUID playerUUID, String path){
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

    public void menuGUI(Player p, Player p2) {
        Inventory menu1 = Bukkit.createInventory(null, 45, ChatColor.DARK_AQUA + "メニュー <詳しくは各アイテムクリック>");
        menu1.clear();
        p.openInventory(menu1);
        menu1.setItem(11, setItemDisplayName(Material.STONE_SWORD, "コンバットスコア", new ArrayList<>(List.of("現在のポイントは→" + file_out(p2.getUniqueId(), "Combat")))));
        menu1.setItem(12, setItemDisplayName(Material.GRAY_STAINED_GLASS_PANE, " ", new ArrayList<>(List.of(" "))));
        menu1.setItem(13, setItemDisplayName(Material.STONE_PICKAXE, "マイニングスコア", new ArrayList<>(List.of("現在のポイントは→" + file_out(p2.getUniqueId(), "Mining")))));
        menu1.setItem(14, setItemDisplayName(Material.GRAY_STAINED_GLASS_PANE, " ", new ArrayList<>(List.of(" "))));
        menu1.setItem(15, setItemDisplayName(Material.FISHING_ROD, "フィッシングスコア", new ArrayList<>(List.of("現在のポイントは→" + file_out(p2.getUniqueId(), "Fishing")))));
        menu1.setItem(31, setItemDisplayName(Material.OAK_SIGN, "ショップ", new ArrayList<>(List.of("実装はかなり先だろう..."))));
        if (p2.getScoreboardTags().contains("Tag_SoundMenuName_on")) {
            menu1.setItem(30, setItemDisplayName(Material.JUKEBOX, SoundMenuName_on, new ArrayList<>(List.of("スコアが加算された時にサウンドを鳴らす"))));
        }
        else if(p2.getScoreboardTags().contains("Tag_SoundMenuName_off")){
            menu1.setItem(30, setItemDisplayName(Material.JUKEBOX, SoundMenuName_off, new ArrayList<>(List.of("スコアが加算された時にサウンドを鳴らす"))));
        }
        if(p2.getScoreboardTags().contains("Tag_TextMenuName_on")){
            menu1.setItem(32, setItemDisplayName(Material.MAP, TextMenuName_on, new ArrayList<>(List.of("スコアが加算された時にチャットを送信する"))));
        }
        else if(p2.getScoreboardTags().contains("Tag_TextMenuName_off")){
            menu1.setItem(32, setItemDisplayName(Material.MAP, TextMenuName_off, new ArrayList<>(List.of("スコアが加算された時にチャットを送信する"))));
        }
    }
    public void shopGUI(Player p, Player p2) {
        Inventory menu2 = Bukkit.createInventory(null, 45, ChatColor.DARK_AQUA + "ショップ");
        menu2.clear();
        p.openInventory(menu2);
        menu2.setItem(0, setItemDisplayName(Material.WHITE_CONCRETE, "Nothing Effect", new ArrayList<>(List.of(ChatColor.GREEN + "解放済み"))));
    }
}