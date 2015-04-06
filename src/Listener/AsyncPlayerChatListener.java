package Listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.BukkitFabo.QuakeCraft.Main;

import PermissionsEx.Permissions;

public class AsyncPlayerChatListener implements Listener {
	
	@EventHandler
	public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		e.setCancelled(true);
		
		if(Main.ingame) {
			if(Main.spectator.contains(p)) {
				for(Player spec : Main.spectator) {
					
					if(p.hasPermission("Server.Team")) {
						e.setMessage(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
					}
					if(Permissions.isAdmin(p)) {
						
						
						spec.sendMessage("§4[§4§l†§r§4] " + "§4[Admin] " + p.getName() + "§8: §f" + e.getMessage());
						return;
						
					} else if(Permissions.isModerator(p)) {
						
						spec.sendMessage("§4[§4§l†§r§4] " + "§2[Moderator] " + p.getName() + "§8: §f" + e.getMessage());
						return;
						
					} else if(Permissions.isArchitekt(p)) {
						
						spec.sendMessage("§4[§4§l†§r§4] " + "§a[Architekt] " + p.getName() + "§8: §f" + e.getMessage());
						return;
						
					} else if(Permissions.isYouTuber(p)) {
						
						spec.sendMessage("§4[§4§l†§r§4] " + "§5[YouTuber] " + p.getName() + "§8: §7" + e.getMessage());
						return;
						
					} else if(Permissions.isVIP(p)) {
						
						spec.sendMessage("§4[§4§l†§r§4] " + "§6[VIP] " + p.getName() + "§8: §7" + e.getMessage());
						return;
						
					} else {
						
						spec.sendMessage("§4[§4§l†§r§4] " + "§9" + p.getName() + "§8: §7" + e.getMessage());
						return;
						
					}
				}
				
			} else {
				
				if(p.hasPermission("Server.Team")) {
					e.setMessage(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
				}
				
				if(Permissions.isAdmin(p)) {
					
					Bukkit.broadcastMessage("§4[Admin] " + p.getName() + "§8: §f" + e.getMessage());
					return;
					
				} else if(Permissions.isModerator(p)) {
					
					Bukkit.broadcastMessage("§2[Moderator] " + p.getName() + "§8: §f" + e.getMessage());
					return;
					
				} else if(Permissions.isArchitekt(p)) {
					
					Bukkit.broadcastMessage("§a[Architekt] " + p.getName() + "§8: §f" + e.getMessage());
					return;
					
				} else if(Permissions.isYouTuber(p)) {
					
					Bukkit.broadcastMessage("§5[YouTuber] " + p.getName() + "§8: §7" + e.getMessage());
					return;
					
				} else if(Permissions.isVIP(p)) {
					
					Bukkit.broadcastMessage("§6[VIP] " + p.getName() + "§8: §7" + e.getMessage());
					return;
					
				} else {
					
					Bukkit.broadcastMessage("§9" + p.getName() + "§8: §7" + e.getMessage());
					return;
					
				}
			}
		} else {
			if(p.hasPermission("Server.Team")) {
				e.setMessage(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
			}
			
			if(Permissions.isAdmin(p)) {
				
				Bukkit.broadcastMessage("§4[Admin] " + p.getName() + "§8: §f" + e.getMessage());
				return;
				
			} else if(Permissions.isModerator(p)) {
				
				Bukkit.broadcastMessage("§2[Moderator] " + p.getName() + "§8: §f" + e.getMessage());
				return;
				
			} else if(Permissions.isArchitekt(p)) {
				
				Bukkit.broadcastMessage("§a[Architekt] " + p.getName() + "§8: §f" + e.getMessage());
				return;
				
			} else if(Permissions.isYouTuber(p)) {
				
				Bukkit.broadcastMessage("§5[YouTuber] " + p.getName() + "§8: §7" + e.getMessage());
				return;
				
			} else if(Permissions.isVIP(p)) {
				
				Bukkit.broadcastMessage("§6[VIP] " + p.getName() + "§8: §7" + e.getMessage());
				return;
				
			} else {
				
				Bukkit.broadcastMessage("§9" + p.getName() + "§8: §7" + e.getMessage());
				return;
				
			}
		}
	}

}
