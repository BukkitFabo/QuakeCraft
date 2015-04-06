package Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandProcessListener implements Listener {
	
	@EventHandler
	public void onCommandProcess(PlayerCommandPreprocessEvent e) {
		if(e.getPlayer().getName().equalsIgnoreCase("Admiral_Zott")) {
			return;
		}
		
		if(!e.getMessage().equalsIgnoreCase("/start") || !e.getMessage().equalsIgnoreCase("start")) {
			e.setCancelled(true);
			return;
		}

	}
	
}
