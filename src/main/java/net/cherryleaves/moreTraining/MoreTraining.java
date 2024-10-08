package net.cherryleaves.moreTraining;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class MoreTraining extends JavaPlugin implements Listener{

    public static ArrayList<Integer> MiningCount = new ArrayList<>();
    public static ArrayList<Integer> CombatCount = new ArrayList<>();
    public static ArrayList<Integer> FishingCount = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new option_item(), this);
        list_in();
        new BlockList().list_in();
        super.onEnable();
    }

    public void list_in() {
        MiningCount.addAll(Arrays.asList(100, 500, 1000, 2000, 5000, 10000, 15000, 20000, 50000, 100000));
        CombatCount.addAll(Arrays.asList(100, 500, 1000, 2000, 5000, 10000, 15000, 20000, 50000, 100000));
        FishingCount.addAll(Arrays.asList(10, 50, 100, 200, 500, 1000, 1500, 2000, 5000, 10000, 15000, 20000, 50000, 100000));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        super.onDisable();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        UUID playerUUID = p.getUniqueId();
        p.getInventory().setItem(8, option_item.item("メニュー", Material.NETHER_STAR));
        File playerFile = new File("PD/" + playerUUID + ".yml");
        if (!playerFile.exists()) {
            try {
                playerFile.createNewFile();
                FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);
                playerData.set("name", p.getName());
                playerData.save(playerFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!p.getScoreboardTags().contains("Tag_TextMenuName_off") || p.getScoreboardTags().contains("Tag_TextMenuName_on")) {
            p.addScoreboardTag("Tag_TextMenuName_on");
        }
        if (!p.getScoreboardTags().contains("Tag_SoundMenuName_off") || p.getScoreboardTags().contains("Tag_SoundMenuName_on")) {
            p.addScoreboardTag("Tag_SoundMenuName_on");
        }
    }

    @EventHandler
    public void BlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        UUID pu = p.getUniqueId();
        Material b = e.getBlock().getType();
        Material mh = p.getInventory().getItemInMainHand().getType();
        int ToolTier = new BlockList().Point(mh);
        FilePath(p, pu,"Mining",ToolTier);
        if(p.getScoreboardTags().contains("Tag_SoundMenuName_on")){
            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 2.0f);
        }
        if(p.getScoreboardTags().contains("Tag_TextMenuName_on")){
            p.sendMessage("スコア" + ChatColor.AQUA + "+" + ToolTier + ChatColor.GRAY + "(合計" + option_item.file_out(p.getUniqueId(), "Mining") + ")");
        }
    }

    @EventHandler
    public void Attack(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player p){
            double damage = e.getDamage();
            UUID pu = p.getUniqueId();
            FilePath(p, pu,"Combat", (int) damage);
            if(p.getScoreboardTags().contains("Tag_SoundMenuName_on")){
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 2.0f);
            }
            if(p.getScoreboardTags().contains("Tag_TextMenuName_on")){
                p.sendMessage("スコア" + ChatColor.RED + "+" + (int) damage + ChatColor.GRAY + "(合計" + option_item.file_out(p.getUniqueId(), "Combat") + ")");
            }
        }
    }

    @EventHandler
    public void Fishing(PlayerFishEvent e) {
        // 釣りが成功したとき
        if (e.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            Player p = e.getPlayer();
            UUID pu = p.getUniqueId();
            FilePath(p, pu,"Fishing", 1);
            if(p.getScoreboardTags().contains("Tag_SoundMenuName_on")){
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 2.0f);
            }
            if(p.getScoreboardTags().contains("Tag_TextMenuName_on")){
                p.sendMessage("スコア" + ChatColor.GREEN + "+" + 1 + ChatColor.GRAY + "(合計" + option_item.file_out(p.getUniqueId(), "Fishing") + ")");
            }
        }
    }


    public void FilePath(Player p, UUID u, String path, int number/*, List<Arrays> li*/){
        File pf = new File("PD/" + u + ".yml");
        try {
            FileConfiguration playerData = YamlConfiguration.loadConfiguration(pf);
            int BBI = playerData.getInt(path);
            playerData.set(path, BBI + number);
            if (path.equals("Mining")) {
                new LevelRoot().MiningScoreLevelCount(playerData, p);
            }
            if (path.equals("Combat")) {
                new LevelRoot().CombatScoreLevelCount(playerData, p);
            }
            if (path.equals("Fishing")) {
                new LevelRoot().FishingScoreLevelCount(playerData, p);
            }
            playerData.save(pf);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
