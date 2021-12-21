package com.exitium.capturethecarrot;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Set;

public class SettingsManager {

  private static final SettingsManager configuration = new SettingsManager("config"),
      arenas = new SettingsManager("arenas"),
      signs = new SettingsManager("signs");
  private File file;
  private FileConfiguration config;

  private SettingsManager(String fileName) {
    if (!Main.getPlugin().getDataFolder().exists()) {
      Main.getPlugin().getDataFolder().mkdir();
    }

    file = new File(Main.getPlugin().getDataFolder(), fileName + ".yml");

    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    config = YamlConfiguration.loadConfiguration(file);

    if (config.getKeys(true).size() == 0) {
      // This allows me to know that there is nothing in the file
      // And I can add null checks to config methods to avoid
      // unnecessary code.
      config = null;
    }
  }

  public static SettingsManager getConfig() {
    return configuration;
  }

  public static SettingsManager getArenas() {
    return arenas;
  }

  public static SettingsManager getSigns() {
    return signs;
  }

  @SuppressWarnings("unchecked")
  public <T> T get(String path) {
    if (config == null) {
      return null;
    }

    return (T) config.get(path);
  }

  public boolean hasConfirugationSection(String path) {
    if (config == null) {
      return false;
    }

    return config.isConfigurationSection(path);
  }

  public Set<String> getKeys(boolean deep) {
    if (config == null) {
      return null;
    }

    return config.getKeys(deep);
  }

  public void set(String path, Object value) {
    if (config == null) {
      return;
    }

    config.set(path, value);
    save();
  }

  public boolean containsStringPath(String path) {
    if (config == null) {
      return false;
    }

    return config.contains(path);
  }

  public ConfigurationSection createSection(String path) {
    if (config == null) {
      config = YamlConfiguration.loadConfiguration(file);
    }

    ConfigurationSection section = config.createSection(path);
    save();
    return section;
  }

  private void save() {
    if (config == null) {
      return;
    }

    try {
      config.save(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
