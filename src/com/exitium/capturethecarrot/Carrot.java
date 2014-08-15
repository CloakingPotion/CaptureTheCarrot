package com.exitium.capturethecarrot;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Carrot {

	Player player;
	Location location;
	
	public Carrot(Location loc) {
		this.location = loc;
	}
	
	public void setPlayer(Player p) {
		this.player = p;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Location getLocation() {
		return location;
	}
}
