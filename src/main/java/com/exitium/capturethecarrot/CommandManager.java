package com.exitium.capturethecarrot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.exitium.capturethecarrot.cmds.AddLobbySign;
import com.exitium.capturethecarrot.cmds.AddSpawn;
import com.exitium.capturethecarrot.cmds.CreateArena;
import com.exitium.capturethecarrot.cmds.Join;
import com.exitium.capturethecarrot.cmds.Leave;
import com.exitium.capturethecarrot.cmds.RemoveArena;

public class CommandManager implements CommandExecutor{

	private ArrayList<GameCommand> cmds = new ArrayList<GameCommand>();
	
	protected CommandManager() {
//		cmds = new ArrayList<>();
		cmds.add(new Join());
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
					p.sendMessage(ChatColor.GOLD + "/ctc (" + StringUtils.join(info.aliases(), " ").trim() + ") " + info.usage() + " - " + info.description());
				}
				
				return true;
			}
			
			GameCommand wanted = null;
			
			for(GameCommand gcmd : cmds) {
				CommandInfo info = gcmd.getClass().getAnnotation(CommandInfo.class);
				for (String alias : info.aliases()) {
					if (alias.equals(args[0])) {
						wanted = gcmd;
						break;
					}
				}
			}
			
			if (wanted == null) {
				p.sendMessage(ChatColor.DARK_RED + "Could not find command,");
				return true;
			}
			
			List<String> newArgs = Arrays.asList(args);
			if (newArgs.size() == 1)
				newArgs.removeAll(Arrays.asList(newArgs.get(0)));
			args = newArgs.toArray(new String[newArgs.size()]);
			
			wanted.onCommand(p, args);
		}
		return false;
		
	}
}