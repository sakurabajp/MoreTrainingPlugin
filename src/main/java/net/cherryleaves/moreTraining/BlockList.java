package net.cherryleaves.moreTraining;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockList {
    public static ArrayList<Material> BlockLevel1 = new ArrayList<>();
    public static ArrayList<Material> BlockLevel2 = new ArrayList<>();
    public static ArrayList<Material> BlockLevel3 = new ArrayList<>();
    public static ArrayList<Material> BlockLevel4 = new ArrayList<>();
    public static ArrayList<Material> BlockLevel5 = new ArrayList<>();

    public void list_in() {
        BlockLevel1.addAll(Arrays.asList(null, Material.WOODEN_PICKAXE, Material.WOODEN_SHOVEL, Material.STONE_SHOVEL, Material.WOODEN_AXE, Material.STONE_SHOVEL));
        BlockLevel2.addAll(Arrays.asList(Material.STONE_PICKAXE, Material.IRON_SHOVEL, Material.IRON_AXE));
        BlockLevel3.addAll(Arrays.asList(Material.IRON_PICKAXE, Material.DIAMOND_SHOVEL, Material.NETHERITE_SHOVEL, Material.DIAMOND_AXE, Material.NETHERITE_AXE));
        BlockLevel4.addAll(Arrays.asList(Material.DIAMOND_PICKAXE, Material.GOLDEN_SHOVEL, Material.GOLDEN_AXE));
        BlockLevel5.addAll(Arrays.asList(Material.NETHERITE_PICKAXE, Material.GOLDEN_PICKAXE));
    }

    public int Point(Material mh){
        int i = 1;
        if(BlockLevel1.contains(mh)){
            i = 2;
        }
        else if(BlockLevel2.contains(mh)){
            i = 4;
        }
        else if(BlockLevel3.contains(mh)){
            i = 6;
        }
        else if(BlockLevel4.contains(mh)){
            i = 8;
        }
        else if(BlockLevel5.contains(mh)){
            i = 10;
        }
        return i;
    }
}
