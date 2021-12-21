package com.exitium.capturethecarrot.cmds;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.exitium.capturethecarrot.Arena;
import com.exitium.capturethecarrot.ArenaManager;
import com.exitium.capturethecarrot.CommandInfo;
import com.exitium.capturethecarrot.GameCommand;

@CommandInfo(aliases = {"join", "j"}, usage = "<arenaName>", description =  "Join a game")
public class Join extends GameCommand{

	public void onCommand(Player p, String[] args) {
		if (ArenaManager.getInstance().getArena(p) != null) {
			p.sendMessage(ChatColor.RED + "You are already in a game.");
			return;
		}
		
		if (args.length == 0) {
			p.sendMessage(ChatColor.DARK_RED + "You must specify the arena to join.");
			return;
		}
				
		Arena a = ArenaManager.getInstance().getArena(args[0]);
	
		if (a == null) {
			p.sendMessage(ChatColor.RED + "An arena by that name does not exsist");
			return;
		}
		
		a.addPlayer(p);
	}
}