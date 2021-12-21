package com.exitium.capturethecarrot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class Main extends JavaPlugin {

  /** Blue-Red teams Designated by wool */
  public Main() {
    super();
  }

  protected Main(
      JavaPluginLoader loader, PluginDescriptionFile descriptionFile, File dataFolder, File file) {
    super(loader, descriptionFile, dataFolder, file);
  }

  public static Plugin getPlugin() {
    return Bukkit.getServer().getPluginManager().getPlugin("CaptureTheCarrot");
  }

  @Override
  public void onEnable() {
    ArenaManager.getInstance().setup(); // Does nothing if no arenas are loaded

    PluginManager pm = Bukkit.getServer().getPluginManager();

    CommandManager commandManager = new CommandManager();
    this.getCommand("ctc").setExecutor(commandManager);
  }

  @Override
  public void onDisable() {
    PluginManager pm = Bukkit.getServer().getPluginManager();
  }
}
