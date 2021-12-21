package com.exitium.capturethecarrot.cmds;

import com.exitium.capturethecarrot.*;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

@CommandInfo(
    aliases = {"addspawn", "as"},
    usage = "<arenaName>",
    description = "Add a spawn to an arena.")
public class AddSpawn extends GameCommand {

  @Override
  public void onCommand(Player p, String[] args) {
    if (args.length == 0) {
      p.sendMessage(ChatColor.RED + "You must specify the arena to which the spawn will be added.");
      return;
    }

    Arena a = ArenaManager.getInstance().getArena(args[0]);

    if (a == null) {
      p.sendMessage(ChatColor.RED + "An arena with that name does not exist.");
      return;
    }

    a.addSpawn(p.getLocation());

    String spawnsPath = a.getID() + ".spawns";
    SettingsManager arenas = SettingsManager.getArenas();

    ConfigurationSection spawns = arenas.<ConfigurationSection>get(spawnsPath);

    if (!SettingsManager.getArenas().hasConfirugationSection(spawnsPath)) {
      arenas.set(spawnsPath + ".0", p.getLocation());
    } else {

      arenas.set(
          spawnsPath + "." + arenas.<ConfigurationSection>get(spawnsPath).getKeys(false).size(),
          p.getLocation());
    }
    p.sendMessage(ChatColor.GREEN + "You have successfully added a spawn to " + a.getID() + "!");
  }
}
