package Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

import de.BukkitFabo.QuakeCraft.Main;

public class GuiChangeListener implements Listener {
	
	@EventHandler
	public void onGuiChange(PlayerItemHeldEvent e) {
		if(Main.ingame || Main.warmup) {
			e.getPlayer().getInventory().setHeldItemSlot(0);
		}
	}

}
