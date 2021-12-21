package com.exitium.capturethecarrot.cmds;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.exitium.capturethecarrot.Arena;
import com.exitium.capturethecarrot.ArenaManager;
import com.exitium.capturethecarrot.CommandInfo;
import com.exitium.capturethecarrot.GameCommand;

@CommandInfo(aliases = {"leave", "l"}, usage = "", description = "Leave a game")
public class Leave extends GameCommand{

	public void onCommand(Player p, String[] args) {
		Arena a = ArenaManager.getInstance().getArena(p);
		
		if (ArenaManager.getInstance().getArena(p) == null) {
			p.sendMessage(ChatColor.RED + "You are not in a game.");
			return;
		}
		
		a.removePlayer(p);
	}
}