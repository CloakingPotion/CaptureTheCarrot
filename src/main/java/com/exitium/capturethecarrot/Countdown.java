package com.exitium.capturethecarrot;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.exitium.capturethecarrot.Arena.ArenaState;

public class Countdown extends BukkitRunnable{

	private Arena arena;
	private int i;
	private ArrayList<Integer> countingNums;
	
	public Countdown(Arena arena, int start, int... cNums) {
		this.i = start;
		countingNums = new ArrayList<Integer>();
		
		for (int c : cNums) {
			countingNums.add(c);
		}
	}
	
	@Override
	public void run() {
		
		if (i == 0) {
			for (Player p : arena.getPlayers()) {
				p.sendMessage(ChatColor.GOLD + "The game has begun");
			}
			
			arena.setState(ArenaState.STARTED); // If you want to generalize this class, you'd probably want a Runnable.
			
			cancel();
			return;
		}
		
		
		
		if (countingNums.contains(i)) {
			for (Player p : arena.getPlayers()) {
				p.sendMessage(ChatColor.GOLD + "The game will begin in " + i + "seconds");
			}
		}
		
		i--;
	}
}