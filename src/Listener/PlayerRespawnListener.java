package Listener;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Data.PlayerData;
import RailGun.RailGun;
import Util.ItemManager;
import de.BukkitFabo.QuakeCraft.FileManager;
import de.BukkitFabo.QuakeCraft.Main;

public class PlayerRespawnListener implements Listener {

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		final Player p = e.getPlayer();
		
		if(RailGun.kill.containsKey(p)) {
			RailGun.kill.remove(p);
			
			e.setRespawnLocation(getRandomSpawn());
			PlayerData data = PlayerData.playerdata.get(p);
			if(!Main.grace) {
				ItemStack railgun = new ItemManager(new ItemStack(data.getRailGunMaterial().getType())).modify().setDisplayName("§6RailGun").addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 1).hideFlags().build();
				p.getInventory().setItem(0, railgun);
				p.getInventory().setHelmet(data.getHat());
			}
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				
				@Override
				public void run() {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3));
					p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1));					
				}
			}, 20L);

			
		}
		
	}
	
	
	public Location getRandomSpawn() {
		List<String> spawn = FileManager.location.getStringList("Location.Spawns");
		String[] info = spawn.get(new Random().nextInt(spawn.size())).split(",");
		World world = Bukkit.getWorld(info[0]);
		double x = Double.valueOf(info[1]);
		double y = Double.valueOf(info[2]);
		double z = Double.valueOf(info[3]);
		float yaw = Float.valueOf(info[4]);
		float pitch = Float.valueOf(info[5]);
		
		return new Location(world, x, y, z, yaw, pitch);
	}
	
}
