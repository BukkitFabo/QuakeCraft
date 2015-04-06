package Timer;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R2.CraftServer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import Data.PlayerData;
import Util.ItemManager;
import de.BukkitFabo.QuakeCraft.FileManager;
import de.BukkitFabo.QuakeCraft.Main;
import de.BukkitFabo.QuakeCraft.QuakeInfo;

public class LobbyTimer implements QuakeInfo {

	public static int time = 60, TaskID;
	public static boolean isRunning;
	private static int spawn = 1;
	
	private static ScoreboardManager manager = Bukkit.getScoreboardManager();
	private static Scoreboard board = manager.getNewScoreboard();
	private static Objective objective = board.registerNewObjective("§3Kills:", "dummy");
	
	public static void startLobbyTimer() {
		if(isRunning) {
			return;
		}
		
		isRunning = true;
		
		TaskID = new BukkitRunnable() {
			
			@Override
			public void run() {
				time--;
				
				if(time == 0) {
					cancelLobbyTimer();
					Main.waiting = false;
					Main.ingame = true;
					Main.warmup = true;
					
					WarmUpTimer.startWarmUpTimer();
					((CraftServer)Bukkit.getServer()).getServer().setMotd("§cWarmup:§6" + Main.Map);
					for(Player all : Bukkit.getOnlinePlayers()) {
						Main.alive.add(all);
						all.getInventory().clear();
						all.playSound(all.getLocation(), Sound.LEVEL_UP, 15, 1);
						all.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3));
						all.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1));
						
						PlayerData data = PlayerData.playerdata.get(all);
						data.setRounds(data.getRounds() +1);
						ItemStack railgun = new ItemManager(new ItemStack(data.getRailGunMaterial().getType())).modify().setDisplayName("§6RailGun").addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 1).hideFlags().build();
						all.getInventory().setItem(0, railgun);
						all.getInventory().setHelmet(data.getHat());
						
						List<String> spawn = FileManager.location.getStringList("Location.Spawns");
						String[] info = spawn.get(LobbyTimer.spawn).split(",");
						World world = Bukkit.getWorld(info[0]);
						double x = Double.valueOf(info[1]);
						double y = Double.valueOf(info[2]);
						double z = Double.valueOf(info[3]);
						float yaw = Float.valueOf(info[4]);
						float pitch = Float.valueOf(info[5]);
						
						objective.setDisplaySlot(DisplaySlot.SIDEBAR);
						objective.setDisplayName("§3Kills:");
						@SuppressWarnings("deprecation")
						Score score = objective.getScore(all);
						score.setScore(Main.stats.get(all));
						all.setScoreboard(board);
						
						all.teleport(new Location(world, x, y, z, yaw, pitch));
						
						LobbyTimer.spawn++;
					}
				} else if(time == 30 || time == 10 || time == 5 || time == 3 || time == 2) {
					Bukkit.broadcastMessage(prefix + "§3Das Spiel startet in §6" + time + " §3Sekunden.");
				} else if(time == 1) {
					Bukkit.broadcastMessage(prefix + "§3Das Spiel startet in §6" + time + " §3Sekunde.");
				}
				
				for(Player all : Bukkit.getOnlinePlayers()) {
					all.setLevel(time);
					all.setExp((float) time/60);
				}
				
			}
		}.runTaskTimer(Main.getPlugin(), 0L, 20L).getTaskId();
		
	}
	
	
	public static void cancelLobbyTimer() {
		if(isRunning) {
			isRunning = false;
			Bukkit.getScheduler().cancelTask(TaskID);
			for(Player all : Bukkit.getOnlinePlayers()) {
				all.setExp(0);
				all.setLevel(0);
			}
		}
	}
	
}
