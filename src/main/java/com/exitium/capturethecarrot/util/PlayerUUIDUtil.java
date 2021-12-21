package com.exitium.capturethecarrot.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerUUIDUtil {

  public static Player getPlayer(UUID uuid) {
    if (uuid == null) {
      return null;
    }

    Player player = Bukkit.getPlayer(uuid);

    return player;
  }
}
