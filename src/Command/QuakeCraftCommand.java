package Command;

import java.io.IOException;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import de.BukkitFabo.QuakeCraft.FileManager;
import de.BukkitFabo.QuakeCraft.QuakeInfo;

public class QuakeCraftCommand implements CommandExecutor, QuakeInfo {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("Du musst ein Spieler sein.");
			return true;
		}
		Player p = (Player)sender;
		if(!p.hasPermission("Server.Admin")) {
			return true;
		}
		String location = p.getWorld().getName() + "," + p.getLocation().getX() + "," + p.getLocation().getY() + "," + p.getLocation().getZ() + "," + p.getLocation().getYaw() + "," + p.getLocation().getPitch();
		FileConfiguration cfg = FileManager.location;
		
		if(args.length == 1) {
			
			if(args[0].equalsIgnoreCase("help")) {
				printHelpLine(p);
				return true;
			} else if(args[0].equalsIgnoreCase("setlobby")) {
				cfg.set("Location.Lobby", location);
				try {
					cfg.save(FileManager.locfile);
					p.sendMessage(prefix + "§3Du hast die §6Lobby §3gesetzt.");
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			} else if(args[0].equalsIgnoreCase("setspectator")) {
				cfg.set("Location.Spectator", location);
				try {
					cfg.save(FileManager.locfile);
					p.sendMessage(prefix + "§3Du hast den §6SpectatorSpawn §3gesetzt.");
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			} else printHelpLine(p);
			
		} else if(args.length == 2) {
			
			if(args[0].equalsIgnoreCase("setspawn") && args[1].equalsIgnoreCase("next")) {
				List<String> locs = cfg.getStringList("Location.Spawns");
				locs.add(location);
				cfg.set("Location.Spawns", locs);
				try {
					cfg.save(FileManager.locfile);
					p.sendMessage(prefix + "§3Du hast den §6" + locs.size() + " §3Spawn gesetzt.");
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			} else printHelpLine(p);
			
		} else {
			
		}
			
		
		return false;
	}
	
	public void printHelpLine(Player p) {
		p.sendMessage(prefix + "§c/quakecraft <setlobby>");
		p.sendMessage(prefix + "§c/quakecraft <setspectator>");
		p.sendMessage(prefix + "§c/quakecraft <setspawn> <next>");
		p.sendMessage(prefix + "§c/quakecraft <help>");
	}

}
