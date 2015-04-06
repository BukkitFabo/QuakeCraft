package Listener;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import Data.PlayerData;
import RailGun.RailGun;
import Timer.LobbyTimer;
import Util.ItemManager;
import de.BukkitFabo.QuakeCraft.FileManager;
import de.BukkitFabo.QuakeCraft.Main;
import de.BukkitFabo.QuakeCraft.QuakeInfo;

public class PlayerJoinListener implements Listener, QuakeInfo {
	
	String[] lobbylore = {"§7Klicken um zurück", "§7in die Lobby zu gelangen."};
	ItemStack lobby = new ItemManager(new ItemStack(Material.GLOWSTONE_DUST)).modify().setDisplayName("§6Lobby").setLore(lobbylore).hideFlags().build();
	String[] statslore = {"§7Klicken um deine", "§7Statistiken zu sehen."};
	ItemStack stats = new ItemManager(new ItemStack(Material.EMERALD)).modify().setLore(statslore).setDisplayName("§6Statistiken").addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 1).hideFlags().build();
	String[] teleporterlore = {"§7Klicken um deine", "§7Statistiken zu sehen."};
	ItemStack teleporter = new ItemManager(new ItemStack(Material.COMPASS)).modify().setLore(teleporterlore).setDisplayName("§6Teleporter").hideFlags().build();
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		e.setJoinMessage(null);
		
		Main.stats.put(p, 0);
		RailGun.killsteak.put(p, 0);
		
		if(Main.sql.getCoins(p.getUniqueId()) == null) {
			Main.sql.queryUpdate("INSERT INTO `Player_Money` (Player, Coins) VALUES ('" + p.getUniqueId().toString() + "', 0)");
		}
		
		if(Main.sql.getRounds(p.getUniqueId()) == null) {
			Main.sql.queryUpdate("INSERT INTO `QuakeCraft` (Player, Kills, Deaths, Rounds, Wins, Points, HighestKillstreak, PlayerInfo) VALUES ('" + p.getUniqueId().toString() + "', 0, 0, 0, 0, 0, 0, 'SPELL%AIR%WOOD_HOE')");
		}
		
		p.removePotionEffect(PotionEffectType.SPEED);
		p.removePotionEffect(PotionEffectType.JUMP);
		p.removePotionEffect(PotionEffectType.INVISIBILITY);
		
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.setHealth(20.0);
		p.setFoodLevel(20);
		p.setExp(0);
		p.setLevel(0);
		p.setFireTicks(0);
		
		ScoreboardManager manager = Bukkit.getScoreboardManager();	
		Scoreboard main = manager.getMainScoreboard();
		Team Spectator = main.getTeam("Spectator");
		if(Spectator == null){
			Spectator = main.registerNewTeam("Spectator");
		}
		Spectator.setPrefix("§7");
		
		new Thread(new Runnable() {	
			@Override
			public void run() {
				PlayerData data = new PlayerData(p.getUniqueId(), 
						Main.sql.getKills(p.getUniqueId()),
						Main.sql.getDeaths(p.getUniqueId()),
						Main.sql.getRounds(p.getUniqueId()),
						Main.sql.getWins(p.getUniqueId()),
						Main.sql.getPoints(p.getUniqueId()),
						Main.sql.getHighestKillstreak(p.getUniqueId()),
						Main.sql.getPlayerInfo(p.getUniqueId()), 
						Main.sql.getCoins(p.getUniqueId()));
				data.convertPlayerInfosIntoElements();
				PlayerData.playerdata.put(p, data);
			}
		}).start();
	
		if(Main.spectator.contains(p)) {
			Main.spectator.remove(p);
		}
		
		if(Main.waiting) {
			if(Spectator.hasPlayer(p)) Spectator.removePlayer(p);
			
			Bukkit.broadcastMessage(prefix + "§6" + p.getDisplayName() + " §3hat das Spiel betreten.");
			
			if(Bukkit.getOnlinePlayers().size() >= 2) {
				LobbyTimer.startLobbyTimer();
			}
			
			String[] loc = FileManager.location.getString("Location.Lobby").split(",");
			World w = Bukkit.getWorld(loc[0]);
			double x = Double.valueOf(loc[1]);
			double y = Double.valueOf(loc[2]);
			double z = Double.valueOf(loc[3]);
			float yaw = Float.valueOf(loc[4]);
			float pitch = Float.valueOf(loc[5]);
			
			w.setSpawnLocation((int)x, (int)y, (int)z);
			p.teleport(new Location(w, x, y, z, yaw, pitch));
			
			p.getWorld().playEffect(p.getLocation(), Effect.LAVA_POP, 1);
			
			p.getInventory().setItem(7, stats);
			p.getInventory().setItem(8, lobby);
			
		} else {
			Main.spectator.add(p);
			
			
			Spectator.addPlayer(p);
			p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
			String[] loc = FileManager.location.getString("Location.Spectator").split(",");
			World w = Bukkit.getWorld(loc[0]);
			double x = Double.valueOf(loc[1]);
			double y = Double.valueOf(loc[2]);
			double z = Double.valueOf(loc[3]);
			float yaw = Float.valueOf(loc[4]);
			float pitch = Float.valueOf(loc[5]);
			
			w.setSpawnLocation((int)x, (int)y, (int)z);
			p.teleport(new Location(w, x, y, z, yaw, pitch));
			
			p.setAllowFlight(true);
			p.setFlying(true);
			
			p.getInventory().setItem(0, teleporter);
			p.getInventory().setItem(7, stats);
			p.getInventory().setItem(8, lobby);
		}
		
	}

}
