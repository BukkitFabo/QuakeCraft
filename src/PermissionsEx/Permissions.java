package PermissionsEx;

import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;


public class Permissions {
	
	public static boolean isAdmin(Player p){
		PermissionUser user = PermissionsEx.getUser(p);
		try {
			return (user.inGroup("Admin"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean isModerator(Player p){
		PermissionUser user = PermissionsEx.getUser(p);
		try {
			return (user.inGroup("Moderator"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean isArchitekt(Player p){
		PermissionUser user = PermissionsEx.getUser(p);
		
		try {
			return (user.inGroup("Architekt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public static boolean isYouTuber(Player p){
		PermissionUser user = PermissionsEx.getUser(p);
		try {
			return (user.inGroup("YouTuber"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean isVIP(Player p){
		PermissionUser user = PermissionsEx.getUser(p);
		try {
			return (user.inGroup("VIP"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void addRank(Player p, int secounds, String group) {
		PermissionUser user = PermissionsEx.getUser(p);
		user.addGroup(group, "", secounds);
		
		
	}
	
}
