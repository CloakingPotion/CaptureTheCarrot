package com.exitium.capturethecarrot.cmds;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.exitium.capturethecarrot.Arena;
import com.exitium.capturethecarrot.ArenaManager;
import com.exitium.capturethecarrot.CommandInfo;
import com.exitium.capturethecarrot.GameCommand;
import com.exitium.capturethecarrot.SettingsManager;

@CommandInfo(description = "Add a spawn to an arena.", usage = "<arenaName>", aliases = { "addspawn", "as"})
public class AddSpawn extends GameCommand{

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
		
		SettingsManager.getArenas().set(a.getID() + ".spawns." + SettingsManager.getArenas().<ConfigurationSection>get(a.getID() + ".spawns").getKeys(false).size(), p.getLocation());
	}
}