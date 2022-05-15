package me.dan.shulker.manager;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.Getter;
import me.dan.pluginapi.file.gson.GsonUtil;
import me.dan.pluginapi.manager.Manager;
import me.dan.shulker.ShulkerPlugin;
import org.bukkit.Location;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class ShulkerManager extends Manager<UUID, List<Shulker>> {

    private File folder;

    @Override
    public void init() {
        this.folder = new File(ShulkerPlugin.getInstance().getDataFolder(), "shulkers");
        if (!this.folder.exists()) {
            this.folder.mkdirs();
        }

        if (this.folder.listFiles() == null) {
            return;
        }

        for (File file : folder.listFiles()) {
            Shulker shulker = GsonUtil.read(folder, file.getName(), Shulker.class);
            List<Shulker> shulkerList = get(shulker.getUuid());
            if (shulkerList == null) {
                shulkerList = new ArrayList<>();
            }
            shulkerList.add(shulker);
            insert(shulker.getUuid(), shulkerList);
        }
    }

    public void insertSingle(Shulker shulker) {
        List<Shulker> shulkerList = get(shulker.getUuid());
        if (shulkerList == null) {
            shulkerList = new ArrayList<>();
        }
        shulkerList.add(shulker);
        insert(shulker.getUuid(), shulkerList);
    }

    public void removeSingle(Shulker shulker) {
        List<Shulker> shulkerList = get(shulker.getUuid());
        shulker.delete();
        if (shulkerList == null) {
            return;
        }
        shulkerList.remove(shulker);
        if (shulkerList.isEmpty()) {
            remove(shulker.getUuid());
            return;
        }
        insert(shulker.getUuid(), shulkerList);
    }

    public Shulker getShulkerInRegion(ProtectedRegion region) {
        for (List<Shulker> shulkerList : getAll()) {
            for (Shulker shulker : shulkerList) {
                if (isInRegion(region, shulker.getLocation())) {
                    return shulker;
                }
            }
        }
        return null;
    }

    private boolean isInRegion(ProtectedRegion region, Location location) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        int minX = region.getMinimumPoint().getX();
        int minY = region.getMinimumPoint().getY();
        int minZ = region.getMinimumPoint().getZ();
        int maxX = region.getMaximumPoint().getX();
        int maxY = region.getMaximumPoint().getY();
        int maxZ = region.getMaximumPoint().getZ();
        return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
    }


}
