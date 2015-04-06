package Listener;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.BukkitFabo.QuakeCraft.Main;

public class PlayerToggleSneakListener implements Listener {
	
	private ArrayList<Player> sneak = new ArrayList<Player>();
	
	@EventHandler
	public void PlayerToggleSneak(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		
		if(!Main.grace) {
			if(!sneak.contains(p)) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 5));
				sneak.add(p);
			} else {
				p.removePotionEffect(PotionEffectType.SLOW);
				sneak.remove(p);
			}
		}
	}

}
