package RailGun;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.util.BlockIterator;

import Data.PlayerData;
import Timer.EndGameTimer;
import Util.ActionBarAPI;
import de.BukkitFabo.QuakeCraft.Main;
import de.BukkitFabo.QuakeCraft.QuakeInfo;

public class RailGun implements QuakeInfo {

	public static HashMap<Player, Player> kill = new HashMap<Player, Player>();
	public static HashMap<Player, Integer> killsteak = new HashMap<Player, Integer>();
	static int i = 0;
	
//    private static Method world_getHandle = null;
//    private static Method nms_world_broadcastEntityEffect = null;
//    private static Method firework_getHandle = null;
	
	private static ScoreboardManager manager = Bukkit.getScoreboardManager();
	private static Scoreboard board = manager.getNewScoreboard();
	private static Objective objective = board.registerNewObjective("§3Kills:", "dummy");
	
	public static void shootRailGun(Player p) {
		
		Location loc = p.getEyeLocation();
		BlockIterator blocksToAdd = new BlockIterator(loc, 0D, 100);
		Location blockToAdd = null;
		
		while (blocksToAdd.hasNext()) {
			blockToAdd = blocksToAdd.next().getLocation();
			for(Player all : Bukkit.getOnlinePlayers()) {
				if(all == p) {
					continue;
				}
				if(all.getLocation().distance(blockToAdd) <= 2.0) {
					if(!kill.containsKey(all) && !Main.spectator.contains(all)) {
						p.playSound(p.getLocation(), Sound.ANVIL_LAND, 15, 1);
						kill.put(all, p);
						PlayerData data = PlayerData.playerdata.get(all);
						data.setDeaths(data.getDeaths() +1);
						
//						Firework fw = (Firework) all.getWorld().spawn(all.getLocation(), Firework.class);
//						Object nms_world = null;
//						Object nms_firework = null;
//						
//						if(world_getHandle == null) {
//							world_getHandle = getMethod(all.getWorld().getClass(), "getHandle");
//							firework_getHandle = getMethod(fw.getWorld().getClass(), "getHandle");
//						}
//						
//						try {
//							nms_world = world_getHandle.invoke(all.getWorld(), (Object[]) null);
//							nms_firework = firework_getHandle.invoke(fw, (Object[]) null);
//						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//							e.printStackTrace();
//						}
//						
//						if(nms_world_broadcastEntityEffect == null) {
//							nms_world_broadcastEntityEffect = getMethod(nms_world.getClass(), "broadcastEntityEffect");
//						}
//						
//						FireworkMeta meta = fw.getFireworkMeta();
//						meta.clearEffects();
//						meta.setPower(1);
//						meta.addEffect(FireworkEffect.builder().flicker(true).withColor(Color.AQUA, Color.LIME, Color.RED, Color.YELLOW, Color.BLUE).with(Type.BALL).trail(true).build());
//						fw.setFireworkMeta(meta);
//						
//						try {
//							nms_world_broadcastEntityEffect.invoke(nms_world, new Object[] {nms_firework, (byte) 17});
//						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//							e.printStackTrace();
//						}
//						fw.remove();
						
						
						if(data.getHighestkillstreak() < all.getLevel()) {
							data.setHighestkillstreak(all.getLevel());
						}
						
						data = PlayerData.playerdata.get(p);
						data.setKills(data.getKills() +1);
						data.setPoints(data.getPoints() +10);
						data.setMoney(data.getMoney() +1);
						
						if(killsteak.containsKey(all)) {
							killsteak.put(all, 0);
						}
						
						Main.stats.put(p, Main.stats.get(p) +1);
						killsteak.put(p, killsteak.get(p) +1);
						
						if(killsteak.get(p) == 3) {
							Bukkit.broadcastMessage("§c§l" + p.getDisplayName() + " hat einen");
							Bukkit.broadcastMessage("§c§l3er Killstreak!");
							Bukkit.broadcastMessage("§e§lSTOPPT IHN!");
							data.setMoney(data.getMoney() +3);
						} else if(killsteak.get(p) == 5) {
							Bukkit.broadcastMessage("§c§l" + p.getDisplayName() + " hat einen");
							Bukkit.broadcastMessage("§c§l5er Killstreak!");
							Bukkit.broadcastMessage("§e§lHALTET IHN AUF!");
							data.setMoney(data.getMoney() +5);
						} else if(killsteak.get(p) == 10) {
							Bukkit.broadcastMessage("§c§l" + p.getDisplayName() + " hat einen");
							Bukkit.broadcastMessage("§c§l5er Killstreak!");
							Bukkit.broadcastMessage("§e§lER DREHT DURCH!");
							data.setMoney(data.getMoney() +10);
						} else if(killsteak.get(p) == 20) {
							Bukkit.broadcastMessage("§c§l" + p.getDisplayName() + " hat einen");
							Bukkit.broadcastMessage("§c§l5er Killstreak!");
							Bukkit.broadcastMessage("§e§lER IST UNAUFHALTBAR!");
							data.setMoney(data.getMoney() +20);
						}
						
						setScoreboard();
						
						if(Main.stats.get(p) >= 25) {
							all.getInventory().clear();
							data.setWins(data.getWins() +1);
							data.setMoney(data.getMoney() +15);
							Bukkit.broadcastMessage(prefix + "§3Der Spieler §6" + p.getDisplayName() + " §3hat das Spiel gewonnen.");
							startfirework(p);
							EndGameTimer.startEndGameTimer();
						}
						
						all.setHealth(0.0D);
						p.setLevel(p.getLevel() +1);
						ActionBarAPI.sendActionBar(all, "§6" + p.getDisplayName() + " §c► §6" + all.getDisplayName());
						break;
					}
				}
			}
			if (blockToAdd.getBlock().getType() != Material.AIR) {
				break;
			}
			
			p.playSound(p.getLocation(), Sound.FIREWORK_LAUNCH, 3, 1);
			
			PlayerData data = PlayerData.playerdata.get(p);
			p.getWorld().playEffect(blockToAdd, Effect.valueOf(data.getRailGunEffect()), 1);
			p.getWorld().playEffect(blockToAdd, Effect.valueOf(data.getRailGunEffect()), 1);
			p.getWorld().playEffect(blockToAdd, Effect.valueOf(data.getRailGunEffect()), 1);
			
		}
		
		
	}
	
    /*private static Method getMethod(Class<?> cl, String method) {
        for(Method m : cl.getMethods()) {
                if(m.getName().equals(method)) {
                        return m;
                }
        }
        return null;
    }*/
	
	
	@SuppressWarnings("deprecation")
	private static void setScoreboard() {
		for(Player all : Bukkit.getOnlinePlayers()) {
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			objective.setDisplayName("§3Kills:");
			Score score = objective.getScore(all);
			score.setScore(Main.stats.get(all));
			all.setScoreboard(board);
		}
	}
	
	private static void startfirework(final Player p) {
		new BukkitRunnable() {
			@Override
			public void run() {
				if(i != 10) {
					i++;
					spawnFirework(p.getEyeLocation());
				} else cancel();
			}
		}.runTaskTimer(Main.getPlugin(), 0L, 20L);
	}
	
	
	
	public static void spawnFirework(Location loc) {
		
		Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta meta = fw.getFireworkMeta();
		
		Random rn = new Random();
		
		int rt = rn.nextInt(4) +1;
		Type type = Type.BALL;
		if(rt == 1) type = Type.BALL;
		if(rt == 2) type = Type.BALL_LARGE;
        if (rt == 3) type = Type.BURST;
        if (rt == 4) type = Type.CREEPER;
        if (rt == 5) type = Type.STAR;
        
        
        int r1i = rn.nextInt(17) + 1;
        int r2i = rn.nextInt(17) + 1;
        Color c1 = getColor(r1i);
        Color c2 = getColor(r2i);
        
        FireworkEffect effect = FireworkEffect.builder().flicker(rn.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(rn.nextBoolean()).build();
        
        meta.addEffect(effect);
        
        int rp = rn.nextInt(2) +1;
        meta.setPower(rp);
        
        fw.setFireworkMeta(meta);
	}
	
	
	private static Color getColor(int i) {
		Color c = null;
		if(i==1){
		c=Color.AQUA;
		}
		if(i==2){
		c=Color.BLACK;
		}
		if(i==3){
		c=Color.BLUE;
		}
		if(i==4){
		c=Color.FUCHSIA;
		}
		if(i==5){
		c=Color.GRAY;
		}
		if(i==6){
		c=Color.GREEN;
		}
		if(i==7){
		c=Color.LIME;
		}
		if(i==8){
		c=Color.MAROON;
		}
		if(i==9){
		c=Color.NAVY;
		}
		if(i==10){
		c=Color.OLIVE;
		}
		if(i==11){
		c=Color.ORANGE;
		}
		if(i==12){
		c=Color.PURPLE;
		}
		if(i==13){
		c=Color.RED;
		}
		if(i==14){
		c=Color.SILVER;
		}
		if(i==15){
		c=Color.TEAL;
		}
		if(i==16){
		c=Color.WHITE;
		}
		if(i==17){
		c=Color.YELLOW;
		}
		 
		return c;
		}

}
