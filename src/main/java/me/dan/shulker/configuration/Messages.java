package me.dan.shulker.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.dan.pluginapi.configuration.Configuration;
import me.dan.pluginapi.message.Message;

import java.util.List;

@AllArgsConstructor
@Getter
public enum Messages implements Configuration, Message {

    PREFIX("prefix", "&8[&3Shulker&8] &b"),
    ALREADY_IN_REGION("already-in-region", "{prefix}There is already a shulker in this region!"),
    SHULKER_NOT_OWNED("shulker-not-owned", "{prefix}You do not own this shulker!"),
    ;

    private final String path;
    @Setter
    private Object value;

    @Override
    public List<String> getStringList() {
        return Configuration.super.getStringList();
    }

    @Override
    public String getPrefix() {
        return PREFIX.getString();
    }

    @Override
    public String getString() {
        return Configuration.super.getString();
    }
}
