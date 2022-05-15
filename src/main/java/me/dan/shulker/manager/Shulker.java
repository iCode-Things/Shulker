package me.dan.shulker.manager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.dan.pluginapi.file.gson.GsonUtil;
import me.dan.shulker.ShulkerPlugin;
import org.bukkit.Location;

import java.io.File;
import java.util.UUID;

@Getter
public class Shulker {

    private final UUID uuid;

    private final Location location;

    public Shulker(UUID uuid, Location location) {
        this.uuid = uuid;
        this.location = location;
        save();
    }

    public void save() {
        GsonUtil.save(ShulkerPlugin.getInstance().getShulkerManager().getFolder(), location.hashCode() + "", this);
    }

    public void delete() {
        File file = new File(ShulkerPlugin.getInstance().getShulkerManager().getFolder(), location.hashCode() + ".json");
        file.delete();
    }


}
