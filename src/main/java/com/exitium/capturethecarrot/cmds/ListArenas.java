package com.exitium.capturethecarrot.cmds;

import com.exitium.capturethecarrot.*;
import com.exitium.capturethecarrot.util.PlayerUUIDUtil;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CommandInfo(
    aliases = {"list", "l"},
    usage = "",
    description = "Lists current arenas and their information")
public class ListArenas extends GameCommand {

  @Override
  public void onCommand(Player p, String[] args) {
    Set<String> arenas = getArenaNames();
    if (arenas == null) {
      listNoArenas();
      return;
    }

    listArenas(p, arenas);
  }

  private Set<String> getArenaNames() {
    SettingsManager arenas = SettingsManager.getArenas();
    if (arenas == null) {
      return null;
    }

    ConfigurationSection section = arenas.get("");

    if (section == null) {
      return null;
    }

    return section.getKeys(false);
  }

  private void listNoArenas() {
    System.out.println("HELLO!");
  }

  private void listArenas(Player p, Set<String> arenas) {
    if (arenas == null) {
      return;
    }

    arenas.forEach(
        (arenaName) -> {
          Arena arena = ArenaManager.getInstance().getArena(arenaName);

          if (arena == null) {
            return;
          }

          p.sendMessage(
              String.format("Arena %s ", arenaName),
              String.format("Status: %s", arena.getState()),
              "Players: ",
              getPlayersStrings(arena));
        });
  }

  private String getPlayersStrings(Arena arena) {
    Stream<String> streamObject =
        Arrays.stream(arena.getPlayers())
            .map(
                (playerUUID) -> {
                  Player player = PlayerUUIDUtil.getPlayer(playerUUID);
                  if (player == null) {
                    return "";
                  }

                  return String.format("- %s%s", player.getDisplayName(), ChatColor.RESET);
                });

    return streamObject.collect(Collectors.joining("\n"));
  }
}
