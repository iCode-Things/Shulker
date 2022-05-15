package me.dan.shulker.listener;

import me.dan.shulker.ShulkerPlugin;
import me.dan.shulker.configuration.Messages;
import me.dan.shulker.manager.Shulker;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!e.getBlock().getType().name().endsWith("SHULKER_BOX")) {
            return;
        }

        List<Shulker> shulkerList = ShulkerPlugin.getInstance().getShulkerManager().get(e.getPlayer().getUniqueId());

        if (shulkerList == null) {
            e.getPlayer().teleport(e.getPlayer().getWorld().getSpawnLocation());
            return;
        }

        for (Shulker shulker : shulkerList) {
            if (!shulker.getLocation().equals(e.getBlock().getLocation())) {
                continue;
            }

            for (ItemStack itemStack : e.getBlock().getDrops()) {
                e.getPlayer().getInventory().addItem(itemStack);
            }

            e.setCancelled(true);

            e.getBlock().setType(Material.AIR);

            ShulkerPlugin.getInstance().getShulkerManager().removeSingle(shulker);
            return;
        }

        Messages.SHULKER_NOT_OWNED.send(e.getPlayer());
    }

}
