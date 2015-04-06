package de.BukkitFabo.QuakeCraft;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {

	public static File locfile = new File(Main.getPlugin().getDataFolder(), "Locations.yml");
	public static FileConfiguration location = YamlConfiguration.loadConfiguration(locfile);
	
	public FileManager() { }
	
	public void loadFiles() {
		File locfile = new File(Main.getPlugin().getDataFolder(), "Locations.yml");
		FileConfiguration location = YamlConfiguration.loadConfiguration(locfile);
		try {
			location.save(locfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
