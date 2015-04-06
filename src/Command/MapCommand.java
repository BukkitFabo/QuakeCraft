package Command;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.BukkitFabo.QuakeCraft.FileManager;

public class MapCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			return true;
		}
		
		if(args.length == 1) {
			String mapName = args[0];
			FileManager.location.set("MapName", mapName);
			sender.sendMessage("Der Mapname lautet nun: " + mapName);
			try {
				FileManager.location.save(FileManager.locfile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}

}
