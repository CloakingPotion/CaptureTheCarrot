package com.exitium.capturethecarrot;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ArenaManager {

  private static ArenaManager instance = new ArenaManager();
  private static ArrayList<Arena> arenas = new ArrayList<Arena>();

  private ArenaManager() {}

  public static ArenaManager getInstance() {
    return instance;
  }

  public void setup() {
    if (SettingsManager.getArenas().getKeys(false) == null) {
      logNoArenas();
      return;
    }

    if (arenas.isEmpty() == false) arenas.clear();

    boolean some = false;
    for (String arenaID : SettingsManager.getArenas().getKeys(false)) {
      Arena arena = new Arena(arenaID);
      arenas.add(arena);
      logArena(arena);

      if (!some) {
        some = true;
      }
    }

    if (!some) {
      logNoArenas();
    }
  }

  private void logNoArenas() {
    Bukkit.getLogger().info("No arenas found. Initializing without any arenas");
  }

  private void logArena(Arena arena) {
    if (arena == null) {
      return;
    }
    if (arena.getID() == null) {
      Bukkit.getLogger().info(("Loading Arena"));
      return;
    }
    Bukkit.getLogger().info(("Loading Arena " + arena.getID()));
  }

  public Arena getArena(String id) {
    if (arenas == null) {
      return null;
    }

    for (Arena arena : arenas) {
      if (arena.getID() == null) {}

      if (arena.getID().equals(id)) {
        return arena;
      }
    }
    return null;
  }

  public Arena getArena(Player p) {
    for (Arena arena : arenas) {
      if (arena.hasPlayer(p)) {
        return arena;
      }
    }
    return null;
  }
}
