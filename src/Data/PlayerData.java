package Data;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerData {

	public static HashMap<Player, PlayerData> playerdata = new HashMap<Player, PlayerData>();
	
	private UUID uuid;
	private int kills;
	private int deaths;
	private int rounds;
	private int wins;
	private int points;
	private int highestkillstreak;
	private String playerInfo;
	
	private int money;
	
//---------------PLAYERINFO---------------
	private String railGunEffect;
	private ItemStack hat;
	private ItemStack railGunMaterial;
//----------------------------------------
	
	public PlayerData(UUID uuid, int kills, int deaths, int rounds, int wins, int points, int highestkillstreak, String playerInfo, int money) {
		this.uuid = uuid;
		this.kills = kills;
		this.deaths = deaths;
		this.rounds = rounds;
		this.wins = wins;
		this.points = points;
		this.playerInfo = playerInfo;
		
		this.money = money;
	}
	
	
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
	public int getKills() {
		return kills;
	}
	public void setKills(int kills) {
		this.kills = kills;
	}
	
	public int getDeaths() {
		return deaths;
	}
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	
	public int getRounds() {
		return rounds;
	}
	public void setRounds(int rounds) {
		this.rounds = rounds;
	}
	
	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
	public int getHighestkillstreak() {
		return highestkillstreak;
	}
	public void setHighestkillstreak(int highestkillstreak) {
		this.highestkillstreak = highestkillstreak;
	}
	
	public String getPlayerInfo() {
		return this.playerInfo;
	}
	public void setPlayerInfo(String playerInfo) {
		this.playerInfo = playerInfo;
	}
	
	public String getRailGunEffect() {
		return railGunEffect;
	}
	public void setRailGunEffect(String railGunEffect) {
		this.railGunEffect = railGunEffect;
	}
	
	public ItemStack getHat() {
		return hat;
	}
	public void setHat(ItemStack hat) {
		this.hat = hat;
	}
	
	public ItemStack getRailGunMaterial() {
		return railGunMaterial;
	}
	public void setRailGunMaterial(ItemStack railGunMaterial) {
		this.railGunMaterial = railGunMaterial;
	}
	
	public void convertPlayerInfosIntoElements() {
		String[] playerinfos = playerInfo.split("%");
		railGunEffect = playerinfos[0];
		hat = new ItemStack(Material.valueOf(playerinfos[1]));
		railGunMaterial = new ItemStack(Material.valueOf(playerinfos[2]));
	}
	
	public void convertPlayerInfosIntoString() {
		String playerinfos = 
				railGunEffect.toString() + "%" + hat.getType().toString() + railGunMaterial.getType().toString();
		setPlayerInfo(playerinfos);
	}
	
	
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	
}
