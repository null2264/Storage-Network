package com.null2264.storagenetwork.config;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.conversion.ObjectConverter;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.io.WritingException;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.electronwill.nightconfig.toml.TomlWriter;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.nio.file.Path;

import static com.null2264.storagenetwork.ZiroStorageNetwork.LOGGER;
import static com.null2264.storagenetwork.ZiroStorageNetwork.MOD_ID;

public class ConfigHelper
{
    private static final Path configPath = FabricLoader.getInstance().getConfigDir();
    private static final File configFile = new File(configPath + File.separator + MOD_ID + ".toml");
    private static ConfigData CONFIG;

    public static void loadAndSaveDefault() {
        ConfigHelper.loadOrDefault();
        if (!configFile.exists()) {
            ConfigHelper.save();
        }
    }

    public static void loadOrDefault() {
        try {
            ConfigHelper.load();
            LOGGER.info("Config file successfully loaded");
        } catch (Exception e) {
            LOGGER.error("There was an error while (re)loading the config file!", e);
            CONFIG = new ConfigData();
            LOGGER.warn("Falling back to default config...");
        }
    }

    public static void load() {
        FileConfig toml = FileConfig.of(configFile);
        toml.load();
        CONFIG = new ObjectConverter().toObject(toml, ConfigData::new);
        toml.close();
    }

    public static void save() {
        try {
            LOGGER.info("Trying to create config file...");
            Config toml = new ObjectConverter().toConfig(CONFIG, Config::inMemory);
            TomlWriter writer = new TomlWriter();
            writer.write(toml, configFile, WritingMode.REPLACE);
        } catch (WritingException e) {
            LOGGER.error("There was an error while creating the config file!", e);
        }
    }

    public static ConfigData get() {
        return CONFIG;
    }
}