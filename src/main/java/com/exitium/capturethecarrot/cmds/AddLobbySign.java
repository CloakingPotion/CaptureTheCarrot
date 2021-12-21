package com.exitium.capturethecarrot.cmds;

import com.exitium.capturethecarrot.*;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

@CommandInfo(
    aliases = {"addlobbysign", "addsign", "als"},
    usage = "<arenaName>",
    description = "Add a lobby sign.")
public class AddLobbySign extends GameCommand {

  @SuppressWarnings("deprecation")
  @Override
  public void onCommand(Player p, String[] args) {
    if (args.length == 0) {
      p.sendMessage(
          ChatColor.RED + "You must specify the arena to which you want to bind this sign.");
      return;
    }

    Arena arena = ArenaManager.getInstance().getArena(args[0]);

    if (arena == null) {
      p.sendMessage(ChatColor.RED + "An arena with that name does not exist.");
      return;
    }

    Block target = p.getTargetBlock(null, 10);

    if (target == null) {
      p.sendMessage(ChatColor.DARK_RED + "You are not looking at a block.");
      return;
    }

    if (!(target.getState() instanceof Sign)) {
      p.sendMessage(ChatColor.DARK_RED + "You are not looking at a sign.");
      return;
    }

    SettingsManager.getSigns()

        .set(
            String.valueOf(SettingsManager.getArenas().getKeys(false).size()),
            new LobbySign(target.getLocation(), arena));

    // New sign would not be in SignManager.
  }
}
