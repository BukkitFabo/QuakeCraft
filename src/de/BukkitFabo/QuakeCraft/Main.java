package de.BukkitFabo.QuakeCraft;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R2.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import Command.MapCommand;
import Command.QuakeCraftCommand;
import Command.StartCommand;
import Data.MySQL;
import Data.QuakeCraft_Ranking;
import Listener.AsyncPlayerChatListener;
import Listener.BlockBreakListener;
import Listener.BlockPlaceListener;
import Listener.EntityDamageListener;
import Listener.EntitySpawnListener;
import Listener.FoodLevelChangeListener;
import Listener.GuiChangeListener;
import Listener.InventoryClickListener;
import Listener.PlayerCommandProcessListener;
import Listener.PlayerDeathListener;
import Listener.PlayerInteractListener;
import Listener.PlayerItemDropListener;
import Listener.PlayerJoinListener;
import Listener.PlayerMoveListener;
import Listener.PlayerQuitListener;
import Listener.PlayerRespawnListener;
import Listener.PlayerToggleSneakListener;
import Listener.WeatherChangeListener;

public class Main extends JavaPlugin {
	
	public static Plugin plugin;
	public static Plugin getPlugin() {
		return plugin;
	}
	
	public static String Map;
	public static HashMap<Player, Integer> stats = new HashMap<Player, Integer>();
	
	public static MySQL sql;
	public static boolean waiting = true, ingame = false, warmup = false, grace = true;
	public static ArrayList<Player> alive = new ArrayList<Player>();
	public static ArrayList<Player> spectator= new ArrayList<Player>();
	
	@Override
	public void onEnable() {
		plugin = this;
		new FileManager().loadFiles();
		
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerRespawnListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
		Bukkit.getPluginManager().registerEvents(new FoodLevelChangeListener(), this);
		Bukkit.getPluginManager().registerEvents(new WeatherChangeListener(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
		Bukkit.getPluginManager().registerEvents(new EntitySpawnListener(), this);
		Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
		Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerItemDropListener(), this);
		Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), this);
		Bukkit.getPluginManager().registerEvents(new GuiChangeListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerToggleSneakListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerCommandProcessListener(), this);
		Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
		
		getCommand("QuakeCraft").setExecutor(new QuakeCraftCommand());
		getCommand("Start").setExecutor(new StartCommand());
		getCommand("setmap").setExecutor(new MapCommand());
		
		Map = FileManager.location.getString("MapName");
		((CraftServer)Bukkit.getServer()).getServer().setMotd("§aWaiting:§6" + Map);
		
		sql = new MySQL("109.230.252.108", "MinecraftServer", "MinecraftServer", "***************");
		try {
			sql.openConnection();
			sql.queryUpdate("CREATE TABLE IF NOT EXISTS QuakeCraft(Player VARCHAR(50), Kills INT, Deaths INT, Rounds INT, Wins INT, Points INT, HighestKillstreak INT, PlayerInfo VARCHAR(1000))");
			sql.queryUpdate("CREATE TABLE IF NOT EXISTS Player_Money(Player VARCHAR(50), Coins INT");
			sql.startReconectTimer(30);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				QuakeCraft_Ranking.refresh();
			}
		}).start();
		
	}

	@Override
	public void onDisable() {
		
	}
}
