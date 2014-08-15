package com.exitium.capturethecarrot;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class CarrotManager {

	private CarrotManager() { }
	
	private static final CarrotManager instance = new CarrotManager();
	
	public static final CarrotManager getInstance() {
		return instance;
	}
	
	HashMap<Carrot, Player> carrots = new HashMap<Carrot, Player>();
	
	public ArrayList<Carrot> getCarrots(Player p) {
		ArrayList<Carrot> playerCarrots = new ArrayList<Carrot>();
		for (Carrot c : carrots.keySet()) {
			if(c.getPlayer().getName().equals(p.getName())) {
				playerCarrots.add(c);
			}
		}
		
		return playerCarrots;
	}
	
	public Carrot getCarrotAtLocation(Location loc) {
		for (Carrot c : carrots.keySet()) {
			if(c.getLocation() == loc) {
				Block block = loc.getWorld().getBlockAt(loc);
				if (!(block instanceof Carrot)) {
					// TODO: remove carrot
					return null;
				}
				
				Carrot carrot = (Carrot) block;
				
				return carrot;
			}
		}
		
		return null;
	}
	
	public Carrot getCarrot(Player p) {
		for (Carrot c : carrots.keySet()) {
			if (carrots.get(c).equals(p)) {
				return c;
			}
		}
		return null;
	}
	
	public Player getPlayer(Carrot c) {
		for (Player p : carrots.values()) {
			if (carrots.containsKey(c)) {
				if (carrots.get(c).equals(p)) {
					return p;
				}
			}
		}
		
		return null;
	}
	
	public void removeCarrot(Carrot c) {
		if (carrots.containsKey(c)) {
			carrots.remove(c);
		}
	}
	
	public void putCarrot(Carrot c, Player p) {
			carrots.put(c, p);
	}
	
	public boolean containsCarrot(Carrot c) {
		if(carrots.containsKey(c)) {
			return true;
		}
		
		return false;
	}
}