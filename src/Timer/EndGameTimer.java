package Timer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.BukkitFabo.QuakeCraft.Main;
import de.BukkitFabo.QuakeCraft.QuakeInfo;

public class EndGameTimer implements QuakeInfo {

	public static void startEndGameTimer() {
		Main.grace = true;
		for(Player all : Bukkit.getOnlinePlayers()) {
			all.getInventory().clear();
			all.getInventory().setArmorContents(null);
			all.setExp(0);
			all.setLevel(0);
		}
		setPlayerDataToMySQL();
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				sendAllToLobby();
			}
		}, 20*20L);
		
	}
	
	private static void setPlayerDataToMySQL() {
		Main.sql.syncronisize();
	}
	
	private static void sendAllToLobby() {
		Bukkit.broadcastMessage(prefix + "§4§lRestart...");
		for(final Player all : Bukkit.getOnlinePlayers()) {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);
				try{
					out.writeUTF("Connect");
					out.writeUTF("lobby");
				}catch(IOException ex){
					ex.printStackTrace();
				}
					
			all.sendPluginMessage(Main.getPlugin(), "BungeeCord", b.toByteArray());
		}
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				Bukkit.shutdown();
			}
		}, 20L);
	}
	
}
