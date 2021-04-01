package com.null2264.storagenetwork.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

import java.util.ArrayList;
import java.util.List;

import static com.null2264.storagenetwork.ZiroStorageNetwork.MODID;

@me.shedaniel.autoconfig.annotation.Config(name=MODID)
public class Config implements ConfigData
{
    public boolean logSpam = true;
    public boolean enableAutoSearchFocus = true;
    public boolean reloadNetworkWhenUnloadChunk = false;

    public int exchangeBufferSize = 134217727;
    public int remoteMaxRange = -1;
    public int autoRefreshTicks = 20;

    List<String> NotallowedBlocks = new ArrayList<String>();

    public static void init() {
        AutoConfig.register(Config.class, Toml4jConfigSerializer::new);
    }

    public static Config get() {
        return AutoConfig.getConfigHolder(Config.class).getConfig();
    }

    public static void save() {
        AutoConfig.getConfigHolder(Config.class).save();
    }
}