package Listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import Data.PlayerData;
import Timer.EndGameTimer;
import Timer.LobbyTimer;

import de.BukkitFabo.QuakeCraft.Main;
import de.BukkitFabo.QuakeCraft.QuakeInfo;

public class PlayerQuitListener implements Listener, QuakeInfo {

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage(null);
		
		if(Main.waiting) {
			if(Bukkit.getOnlinePlayers().size() -1 < 2) {
				LobbyTimer.cancelLobbyTimer();
			}
		}
		
		if(Main.alive.contains(p)) {
			Main.alive.remove(p);
			
			if(Main.grace) {
				return;
			}
			Bukkit.broadcastMessage(prefix + "§6" + p.getDisplayName() + " §3hat das Spiel verlassen.");
			if(Main.alive.size() <= 1) {
				EndGameTimer.startEndGameTimer();
				for(Player alive : Main.alive) {
					PlayerData data = PlayerData.playerdata.get(alive);
					data.setWins(data.getWins() +1);
					data.setMoney(data.getMoney() +15);
					Bukkit.broadcastMessage(prefix + "§3Der Spieler §6" + alive.getDisplayName() + " §3hat das Spiel gewonnen, da alle Anderen gelefted sind.");
				}
			}
		} else if(Main.spectator.contains(p)) {
			Main.spectator.remove(p);
		}
	}
	
}
