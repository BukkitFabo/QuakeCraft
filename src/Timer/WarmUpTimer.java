package Timer;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R2.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.BukkitFabo.QuakeCraft.Main;

public class WarmUpTimer {

	private static int time = 10;
	
	public static void startWarmUpTimer() {
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				time--;
				
				if(time == 0) {
					((CraftServer)Bukkit.getServer()).getServer().setMotd("§cIngame:§6" + Main.Map);
					cancel();
					Main.warmup = false;
					Main.grace = false;
					for(Player all : Bukkit.getOnlinePlayers()) {
						all.playSound(all.getLocation(), Sound.AMBIENCE_THUNDER, 3, 1);
						all.getWorld().playEffect(all.getLocation(), Effect.LAVA_POP, 15);
					}
				}
				
				if(time <= 5 && time != 0) {
					for(Player all : Bukkit.getOnlinePlayers()) {
						all.playSound(all.getLocation(), Sound.ITEM_PICKUP, 3, 1);
					}
				}
				
				for(Player all : Bukkit.getOnlinePlayers()) {
					all.setLevel(time);
					all.setExp((float) time/10);
				}
			}
		}.runTaskTimer(Main.getPlugin(), 0L, 20L).getTaskId();
		
	}
	
}
