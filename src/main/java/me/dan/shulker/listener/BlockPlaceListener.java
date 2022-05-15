package me.dan.shulker.listener;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.dan.shulker.ShulkerPlugin;
import me.dan.shulker.configuration.Messages;
import me.dan.shulker.manager.Shulker;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (!e.getBlock().getType().name().endsWith("SHULKER_BOX")) {
            return;
        }
        Location location = e.getBlock().getLocation();
        ApplicableRegionSet set = WorldGuard.getInstance().getPlatform().getRegionContainer().get(new BukkitWorld(location.getWorld())).getApplicableRegions(BlockVector3.at(location.getX(), location.getY(), location.getZ()));
        for (ProtectedRegion region : set.getRegions()) {
            Shulker shulker = ShulkerPlugin.getInstance().getShulkerManager().getShulkerInRegion(region);
            if (shulker != null) {
                e.setCancelled(true);
                Messages.ALREADY_IN_REGION.send(e.getPlayer());
                return;
            }
        }

        Shulker shulker = new Shulker(e.getPlayer().getUniqueId(), location);

        ShulkerPlugin.getInstance().getShulkerManager().insertSingle(shulker);

    }

}
