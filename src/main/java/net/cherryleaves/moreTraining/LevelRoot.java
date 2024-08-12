package net.cherryleaves.moreTraining;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import static net.cherryleaves.moreTraining.MoreTraining.CombatCount;
import static net.cherryleaves.moreTraining.MoreTraining.MiningCount;
import static net.cherryleaves.moreTraining.MoreTraining.FishingCount;

public class LevelRoot {
    public void MiningScoreLevelCount(FileConfiguration playerData, Player p){
        if (playerData.get("MiningLevel") == null){
            playerData.set("MiningLevel", 0);
        }
        int l = playerData.getInt("MiningLevel");
        for (int q = 1; q <= MiningCount.size(); q++) {
            if (l == MiningCount.size() - q){
                if (playerData.getInt("Mining") >= MiningCount.get(MiningCount.size() - q)) {
                    playerData.set("MiningLevel", l + 1);
                    p.sendMessage("マイニングスコアが" + ChatColor.AQUA + (MiningCount.get(MiningCount.size() - q)) + ChatColor.WHITE + "を達成しました！");
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                }
            }
        }
    }

    public void CombatScoreLevelCount(FileConfiguration playerData, Player p){
        if (playerData.get("CombatLevel") == null){
            playerData.set("CombatLevel", 0);
        }
        int l = playerData.getInt("CombatLevel");
        for (int q = 1; q <= CombatCount.size(); q++) {
            if (l == CombatCount.size() - q){
                if (playerData.getInt("Combat") >= CombatCount.get(CombatCount.size() - q)) {
                    playerData.set("CombatLevel", l + 1);
                    p.sendMessage("コンバットスコアが" + ChatColor.RED + (CombatCount.get(CombatCount.size() - q)) + ChatColor.WHITE + "を達成しました！");
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                }
            }
        }
    }

    public void FishingScoreLevelCount(FileConfiguration playerData, Player p){
        if (playerData.get("FishingLevel") == null){
            playerData.set("FishingLevel", 0);
        }
        int l = playerData.getInt("FishingLevel");
        for (int q = 1; q <= FishingCount.size(); q++) {
            if (l == FishingCount.size() - q){
                if (playerData.getInt("Fishing") >= FishingCount.get(FishingCount.size() - q)) {
                    playerData.set("FishingLevel", l + 1);
                    p.sendMessage("フィッシングスコアが" + ChatColor.GREEN + (FishingCount.get(FishingCount.size() - q)) + ChatColor.WHITE + "を達成しました！");
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                }
            }
        }
    }
}
