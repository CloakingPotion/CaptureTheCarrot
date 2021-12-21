package com.exitium.capturethecarrot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.exitium.capturethecarrot.cmds.*;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor{

	private ArrayList<GameCommand> cmds = new ArrayList<GameCommand>();
	
	protected CommandManager() {
//		cmds = new ArrayList<>();
		cmds.add(new Join());
		cmds.add(new ListArenas());
		cmds.add(new Leave());
		cmds.add(new CreateArena());
		cmds.add(new RemoveArena());
//		cmds.add(new AddChest());
		cmds.add(new AddSpawn());
		cmds.add(new AddLobbySign());
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLable, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can use CaptureTheCarrot.");
			return true;
		}
		
		Player p = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("ctc")) {
			if(args.length == 0) {
				for(GameCommand gcmd : cmds) {
					CommandInfo info = gcmd.getClass().getAnnotation(CommandInfo.class);
					String toSend = ChatColor.GOLD + "/ctc (" + StringUtils.join(info.aliases(), " ").trim() + ")";

					if (info.usage().equals("")) {
						toSend += " - " + info.description();
					} else {
						toSend += " " + info.usage() + " - " + info.description();
					}

					p.sendMessage(toSend);
				}
				
				return true;
			}

			GameCommand wanted = getGameCommand(args[0]);
			
			if (wanted == null) {
				p.sendMessage(ChatColor.DARK_RED + "Could not find command,");
				return true;
			}

			String[] newArgs = args;
			if (newArgs.length >= 1)
				newArgs = Arrays.copyOfRange(args, 1, args.length);
			
			wanted.onCommand(p, newArgs);
		}
		return false;
		
	}

	private GameCommand getGameCommand(String command) {
		GameCommand wanted = null;

		for(GameCommand gcmd : cmds) {
			CommandInfo info = gcmd.getClass().getAnnotation(CommandInfo.class);
			for (String alias : info.aliases()) {
				if (alias.equalsIgnoreCase(command)) {
					return gcmd;
				}
			}
		}
		return null;
	}
}