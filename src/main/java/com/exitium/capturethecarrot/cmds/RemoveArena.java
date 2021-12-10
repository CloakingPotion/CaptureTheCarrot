package com.exitium.capturethecarrot.cmds;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.exitium.capturethecarrot.ArenaManager;
import com.exitium.capturethecarrot.CommandInfo;
import com.exitium.capturethecarrot.GameCommand;
import com.exitium.capturethecarrot.SettingsManager;

@CommandInfo(description = "Remove an arena.", usage = "<name>", aliases = { "removearena", "ra"})
public class RemoveArena extends GameCommand{
	
	public void onCommand(Player p, String[] args) {
		if (args.length == 0) {
			p.sendMessage(ChatColor.RED + "You must specify the name of the arena.");
			return;
		}
		
		String name = args[0];
		if(ArenaManager.getInstance().getArena(name) == null) {
			p.sendMessage(ChatColor.RED + "An arena with that name does not exist.");
			return;
		}
		
		SettingsManager.getArenas().set(name, null);
		
		p.sendMessage(ChatColor.GREEN + "Removed arena " + name + ". This will be applied on reload");
	}
}