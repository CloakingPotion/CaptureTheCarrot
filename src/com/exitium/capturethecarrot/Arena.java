package com.exitium.capturethecarrot;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Arena {

	public enum ArenaState {
		WAITING, COUNTDOWN, STARTED
	}
	
	private String id;
	private ArenaState state;
	
	private ArrayList<Location> spawns;
	private ArrayList<Player> players;
	
	protected Arena(String id) {
		this.id = id;
		this.state = ArenaState.WAITING;
		
		this.spawns = new ArrayList<Location>();
		for(String spawnID : SettingsManager.getArenas().<ConfigurationSection>get(id + ".spawns").getKeys(false)) {
			spawns.add(SettingsManager.getArenas().<Location>get(id + ".spawns." + spawnID));
			
		}
		
		this.players = new ArrayList<Player>();
	}
	
	public String getID() {
		return id;
	}
	
	public int getMaxPlayers() {
		return spawns.size();
	}
	
	public ArenaState getState() {
		return state;
	}
	
	protected void setState(ArenaState state) {
		this.state = state;
	}
	
	public Player[] getPlayers() {
		return players.toArray(new Player[players.size()]);
	}
	
	public boolean hasPlayer(Player p) {
		return players.contains(p);
	}
	
	public void addPlayer(Player p) {
		if (players.size() + 1 > spawns.size()) {
			p.sendMessage(ChatColor.RED + "This arena is full.");
			return;
		}
		
		players.add(p);
		p.teleport(spawns.get(players.size()-1));
		
		ItemStack kitSelector = new ItemStack(Material.COMPASS);
		ItemMeta meta = kitSelector.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Kit Selector");
		meta.setLore(Arrays.asList("Right click this", "to choose", "your kit."));
		kitSelector.setItemMeta(meta);
		p.getInventory().addItem(kitSelector);
		
		p.sendMessage(ChatColor.GREEN + "You have joined arean " + id + ".");
		
		if (players.size() >= spawns.size() && state == ArenaState.WAITING) {
			this.state = ArenaState.COUNTDOWN;
			new Countdown(this, 30, 30, 20, 10, 5, 4, 3, 2, 1).runTaskTimer(Main.getPlugin(), 0, 1000);
		}
	}
	
	public void removePlayer(Player p) {
		players.remove(p);
		p.teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
		if (players.size() <= 1) {
			if (players.size()  == 1) {
				Bukkit.getServer().broadcastMessage(players.get(0).getName() + " has won arena " + id + "!");
				players.remove(0);
				players.get(0).teleport(Bukkit.getServer().getWorlds().get(0).getSpawnLocation());
			}else{
				Bukkit.getServer().broadcastMessage("Arena " + id + "!");
			}
			
			players.clear();
			state = ArenaState.WAITING;
		}
	}
	
	public void addSpawn(Location loc) {
		spawns.add(loc);
	}
	
}