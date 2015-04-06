package Command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.BukkitFabo.QuakeCraft.Main;
import de.BukkitFabo.QuakeCraft.QuakeInfo;

import Timer.LobbyTimer;

public class StartCommand implements CommandExecutor, QuakeInfo {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("Du musst ein Spieler sein.");
			return true;
		}
		Player p = (Player)sender;
		
		if(LobbyTimer.time <= 10 || !Main.waiting || !(Bukkit.getOnlinePlayers().size() > 1)) {
			p.sendMessage(prefix + "§cDu kannst das Spiel jetzt nicht starten.");
			return true;
		} else {
			LobbyTimer.startLobbyTimer();
			LobbyTimer.time = 11;
			p.sendMessage(prefix + "§3Du hast das Spiel gestartet.");
		}
		
		
		return false;
	}

}
