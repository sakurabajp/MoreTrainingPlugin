package net.cherryleaves.moreTraining;

import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public final class MoreTraining extends JavaPlugin implements Listener {

    public ArrayList<Integer> MiningCount = new ArrayList<Integer>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        super.onEnable();
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new option_item(), this);
        list_in();
    }

    public void list_in(){
        MiningCount.addAll(Arrays.asList(100, 500, 1000, 2000, 5000, 10000, 15000, 20000, 50000, 100000));
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
    }

    @EventHandler
    public void BlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        UUID pu = p.getUniqueId();
        Material b = e.getBlock().getType();
        FilePath(p, pu,"Mining",1);
    }

    @EventHandler
    public void Attack(PrePlayerAttackEntityEvent e) {
        Player p = e.getPlayer();
        UUID pu = p.getUniqueId();
        FilePath(p, pu,"Combat",1);
    }


    public void FilePath(Player p, UUID u, String path, int number/*, List<Arrays> li*/){
        File pf = new File("PD/" + u + ".yml");
        try {
            FileConfiguration playerData = YamlConfiguration.loadConfiguration(pf);
            int BBI = playerData.getInt(path);
            playerData.set(path, BBI + number);
            if (MiningCount.contains(BBI + number) && path.equals("Mining")) {
                p.sendMessage("マイニングスコアが" + ChatColor.AQUA + (BBI + number) + ChatColor.WHITE + "を達成しました！");
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            }
            playerData.save(pf);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
