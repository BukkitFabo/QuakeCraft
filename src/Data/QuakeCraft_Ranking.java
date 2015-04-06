package Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.BukkitFabo.QuakeCraft.Main;

public class QuakeCraft_Ranking {

	private static HashMap<Integer, UUID> rank = new HashMap<Integer, UUID>();
	public static HashMap<String, Integer> playerrang = new HashMap<String, Integer>();
	
	public static void refresh() {
		rank.clear();
		
		ResultSet rs = Main.sql.query("SELECT Player FROM QuakeCraft ORDER BY Points");
		
		int i = 0;
		
		try {
			while(rs.next()) {
				i++;
				rank.put(i, UUID.fromString(rs.getString("Player")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Loaded " + rank.size() + " Ranks!");
		}
	}
	
	public static Integer getRank(Player p) {
		int rang = 0;
		
		for(Integer i : rank.keySet()) {
			if(rank.get(i).equals(p.getUniqueId())) {
				rang = QuakeCraft_Ranking.rank.size() +1 -i;
			}
		}
		
		return rang;
	}
	
}
