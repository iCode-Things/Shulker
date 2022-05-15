package me.dan.shulker.listener;

import me.dan.shulker.ShulkerPlugin;
import me.dan.shulker.configuration.Messages;
import me.dan.shulker.manager.Shulker;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        Block block = e.getClickedBlock();

        assert block != null;
        if (!block.getType().name().endsWith("SHULKER_BOX")) {
            return;
        }

        List<Shulker> shulkerList = ShulkerPlugin.getInstance().getShulkerManager().get(e.getPlayer().getUniqueId());

        if (shulkerList == null || shulkerList.isEmpty()) {
            Messages.SHULKER_NOT_OWNED.send(e.getPlayer());
            e.getPlayer().teleport(e.getPlayer().getWorld().getSpawnLocation());
            return;
        }

        for (Shulker shulker : shulkerList) {
            if (shulker.getLocation().equals(block.getLocation())) {
                return;
            }
        }
        Messages.SHULKER_NOT_OWNED.send(e.getPlayer());
        e.getPlayer().teleport(e.getPlayer().getWorld().getSpawnLocation());
    }

}
