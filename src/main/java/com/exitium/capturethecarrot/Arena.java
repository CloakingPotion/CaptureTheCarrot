package com.exitium.capturethecarrot;

import com.exitium.capturethecarrot.util.PlayerUUIDUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

public class Arena {

  private String id;
  private ArenaState state;
  private ArrayList<Location> spawns;
  private ArrayList<UUID> players;

  protected Arena(String id) {

    this.id = id;
    this.state = ArenaState.WAITING;

    this.spawns = new ArrayList<>();

    Set<String> keys = getKeys();

    if (keys != null) {
      for (String spawnID : getKeys()) {
        spawns.add(SettingsManager.getArenas().get(id + ".spawns." + spawnID));
      }
    }

    this.players = new ArrayList<>();
  }

  private Set<String> getKeys() {
    ConfigurationSection conf = SettingsManager.getArenas().get(id + ".spawns");

    if (conf == null) {
      return null;
    }

    Set<String> keys = conf.getKeys(false);

    if (keys == null) {
      return null;
    }

    return keys;
  }

  public String getID() {
    return id;
  }

  public int getMaxPlayers() {
    return spawns.size();
  }

  public ArenaState getState() {
    return state;
  }

  protected void setState(ArenaState state) {
    this.state = state;
  }

  public UUID[] getPlayers() {
    return players.toArray(new UUID[players.size()]);
  }

  public boolean hasPlayer(Player p) {
    return players.contains(p);
  }

  public void addPlayer(Player p) {
    if (players.size() + 1 > spawns.size()) {
      p.sendMessage(ChatColor.RED + "This arena is full.");
      return;
    }

    players.add(p.getUniqueId());
    p.teleport(spawns.get(players.size() - 1));

    ItemStack kitSelector = new ItemStack(Material.COMPASS);
    ItemMeta meta = kitSelector.getItemMeta();
    meta.setDisplayName(ChatColor.GOLD + "Kit Selector");
    meta.setLore(Arrays.asList("Right click this", "to choose", "your kit."));
    kitSelector.setItemMeta(meta);
    p.getInventory().addItem(kitSelector);

    p.sendMessage(ChatColor.GREEN + "You have joined arean " + id + ".");

    if (players.size() >= spawns.size() && state == ArenaState.WAITING) {
      this.state = ArenaState.COUNTDOWN;
      new Countdown(this, 30, 30, 20, 10, 5, 4, 3, 2, 1).runTaskTimer(Main.getPlugin(), 0, 1000);
    }
  }

  public void removePlayer(Player p) {
    players.remove(p.getUniqueId());
    p.teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
    if (players.size() <= 1) {
      if (players.size() == 1) {
        Player winner = PlayerUUIDUtil.getPlayer(players.get(0));

        if (winner == null) {
          Bukkit.getServer().getLogger().severe("A Null player has won a game on arena: " + id);
        } else {

          Bukkit.getServer().broadcastMessage(winner.getName() + " has won arena " + id + "!");
          players.remove(0);

          winner.teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
        }

      } else {
        Bukkit.getServer().broadcastMessage("Arena " + id + "!");
      }

      players.clear();
      state = ArenaState.WAITING;
    }
  }

  public void addSpawn(Location loc) {
    spawns.add(loc);
  }

  public void sendBroadcastToPlayers(String... message) {
    players.forEach(
        (playerUUID) -> {
          Player p = PlayerUUIDUtil.getPlayer(playerUUID);

          if (p == null) {
            return;
          }

          for (int i = 0; i < message.length; i++) {
            p.sendMessage(message[i]);
          }
        });
  }

  public enum ArenaState {
    WAITING,
    COUNTDOWN,
    STARTED
  }
}
