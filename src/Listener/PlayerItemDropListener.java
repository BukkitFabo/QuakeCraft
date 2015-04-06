package Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerItemDropListener implements Listener {
	
	@EventHandler
	public void onPlayerItemDrop(PlayerDropItemEvent e) {
		e.setCancelled(true);
	}

}
