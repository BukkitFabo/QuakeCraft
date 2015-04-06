package Listener;

import net.minecraft.server.v1_8_R2.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R2.PacketPlayInClientCommand.EnumClientCommand;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import de.BukkitFabo.QuakeCraft.Main;

public class PlayerDeathListener implements Listener {
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		final Player p = (Player)e.getEntity();
		e.setDeathMessage(null);
				
		e.getDrops().clear();
		p.getInventory().clear();
		p.setLevel(0);
		p.setExp(0);
		
		Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getPlugin(), new Runnable(){
			@Override
			public void run() {
				try {
					PacketPlayInClientCommand packet = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
					((CraftPlayer)p).getHandle().playerConnection.a(packet);            
				} catch (Exception e1) {
					e1.printStackTrace();
				}         
			}   
		}, 10L);
		
	}

}
