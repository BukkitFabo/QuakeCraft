package Listener;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import Data.PlayerData;
import Data.QuakeCraft_Ranking;
import RailGun.RailGun;
import Util.ItemManager;
import de.BukkitFabo.QuakeCraft.Main;
import de.BukkitFabo.QuakeCraft.QuakeInfo;

public class PlayerInteractListener implements Listener, QuakeInfo {

	public static HashMap<Player, Player> kill = new HashMap<Player, Player>();
	private HashMap<Player, Integer> hasshoot = new HashMap<Player, Integer>();
	
	private ArrayList<Player> click = new ArrayList<Player>();
	
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		e.setCancelled(true);
		
		
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Material item = p.getItemInHand().getType();
			
			if(item == Material.GLOWSTONE_DUST) {
				ByteArrayOutputStream b = new ByteArrayOutputStream();
				DataOutputStream out = new DataOutputStream(b);
					try{
						out.writeUTF("Connect");
						out.writeUTF("lobby");
					}catch(IOException ex){
						ex.printStackTrace();
					}
							
				p.sendPluginMessage(Main.getPlugin(), "BungeeCord", b.toByteArray());
				return;
			} else if(item == Material.EMERALD) {
				p.openInventory(getQuakeCraftStatsInventory(p));
				return;
			}
			
			if(item == Material.STICK) {
				for(Player all : Bukkit.getOnlinePlayers()) {
					if(all != p) {
						all.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20*5, 1));
					}
				}
				return;
			} 
			
			if(!(item == Material.WOOD_HOE || item == Material.STONE_HOE || item == Material.GOLD_HOE || item == Material.IRON_HOE || item == Material.DIAMOND_HOE)) {
				return;
			}
			
			if(!Main.grace) {
				if(!hasshoot.containsKey(p)) {
					hasshoot.put(p, new BukkitRunnable() {
						int time = 40;
						@Override
						public void run() {
							time--;
							p.setExp((float) time/40);
							if(time == 0) {
								cancel();
								hasshoot.remove(p);
							}
						}
					}.runTaskTimer(Main.getPlugin(), 0L, 1L).getTaskId());
					
				} else return;
			
				RailGun.shootRailGun(p);
			}
			
		} else if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			Material item = p.getItemInHand().getType();
			if(!(item == Material.WOOD_HOE || item == Material.STONE_HOE || item == Material.GOLD_HOE || item == Material.IRON_HOE || item == Material.DIAMOND_HOE)) {
				return;
			}
			
			if(!Main.grace) {
				if(click.contains(p)) {
					return;
				}
				p.setVelocity(p.getEyeLocation().getDirection().multiply(3.0).setY(0.7));
				p.getWorld().playEffect(p.getLocation(), Effect.LAVA_POP, 3);
				click.add(p);
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
					@Override
					public void run() {
						click.remove(p);
					}
				}, 20L*5L);
			}
		}
	}
	
	public Inventory getQuakeCraftStatsInventory(Player p) {
		Inventory inv = Bukkit.createInventory(null, 18, "§aQuake Stats von " + p.getName() + ":");
		PlayerData data = PlayerData.playerdata.get(p);
		
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		
		double wahrscheinlichkeit = (double) data.getWins() / data.getRounds() * 100;
		double killsdurchtode = (double) data.getKills() / data.getDeaths();
		String gewinnwahrscheinlichkeit = nf.format(wahrscheinlichkeit);
		String killsdeaths = nf.format(killsdurchtode);
		
		ItemStack kills = ItemManager.newItemStack(Material.IRON_SWORD, 1, (short) 0, "§6Kills: §5" + data.getKills(), "§7Höchster Killstreak: §8" + data.getHighestkillstreak());
		ItemStack deaths = ItemManager.newItemStack(Material.SKULL_ITEM, 1, (short) 0, "§6Deaths: §5" + data.getDeaths(), null);
		ItemStack wins = ItemManager.newItemStack(Material.DIAMOND, 1, (short) 0, "§6Wins: §5" + data.getWins(), null);
		ItemStack rounds = ItemManager.newItemStack(Material.EMERALD, 1, (short) 0, "§6Runden: §5" + data.getRounds(), null);
		ItemStack kd = ItemManager.newItemStack(Material.BARRIER, 1, (short) 0, "§6Kills/Tode: §5" + killsdeaths, null);
		ItemStack wl = ItemManager.newItemStack(Material.BARRIER, 1, (short) 0, "§6Gewinnwahrscheinlichkeit: §5" + gewinnwahrscheinlichkeit + "%", null);
		ItemStack rang = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
		SkullMeta rangm = (SkullMeta) rang.getItemMeta();
		rangm.setOwner(p.getName());
		rangm.setDisplayName("§6Rang: §5" + QuakeCraft_Ranking.getRank(p));
		rang.setItemMeta(rangm);
		ItemStack points = ItemManager.newItemStack(Material.EXP_BOTTLE, 1, (short) 0, "§6Punkte: §5" + data.getPoints(), null);
		ItemStack clan = ItemManager.newItemStack(Material.SKULL_ITEM, 1, (short) 3, "§6Clan: §5Coming soon", "§7Klicken um Statistiken an zu zeigen.");
		
		inv.setItem(0, kills);
		inv.setItem(1, deaths);
		inv.setItem(3, kd);
		inv.setItem(5, wl);
		inv.setItem(7, wins);
		inv.setItem(8, rounds);
		inv.setItem(11, rang);
		inv.setItem(13, points);
		inv.setItem(15, clan);
		
		return inv;
	}
	
}
