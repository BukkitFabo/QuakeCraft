package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.mysql.jdbc.PreparedStatement;

import de.BukkitFabo.QuakeCraft.Main;

public class MySQL {

	private String host = "";
	private String database = "";
	private String user = "";
	private String password = "";
	
	public MySQL(String host, String database, String user, String password) {
		this.host = host;
		this.database = database;
		this.user = user;
		this.password = password;
	}
	
	public Connection con;
	public Connection openConnection() throws Exception {
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.database + "?user=" + this.user + "&password=" + this.password + "&?autoreconnect=true");
		this.con = con;
		return con;
			
	}
	
	public Connection getConnection(){
		
		return con;
		
	}
	
	public boolean hasConnection(){
		
		try{
			return (con != null) || con.isValid(1);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
       
	public void queryUpdate(String query){
		
		Connection conn = con;
		PreparedStatement st = null;
		try{
			st = (PreparedStatement)conn.prepareStatement(query);
			st.executeUpdate();
		}catch (SQLException e){
			System.err.println("Failed to send update " + query + ".");
		}finally{
			closeRessources(null, st);
		}
		
	}
	
	public static void closeRessources(ResultSet rs, PreparedStatement st) {
		
		if(rs != null){
			try{
				rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		if(st != null){
			try{
				st.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
	}
	
	public ResultSet query(String qry) {
		ResultSet rs = null;
		try {
			Statement st = con.createStatement();
			rs = st.executeQuery(qry);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public void closeConnection(){
		try{
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			con = null;
		}
	}
	
	public void startReconectTimer(int min){
		
		new BukkitRunnable() {
			
			@Override
			public void run() {

				try {
					openConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}.runTaskTimerAsynchronously(Main.getPlugin(), 20*60*min, 20*60*min);
		
	}
	
	public void syncronisize() {
		
		for(final Player p : PlayerData.playerdata.keySet()) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					final PlayerData data = PlayerData.playerdata.get(p);
					setKills(p.getUniqueId(), data.getKills());
					setDeaths(p.getUniqueId(), data.getDeaths());
					setRounds(p.getUniqueId(), data.getRounds());
					setWins(p.getUniqueId(), data.getWins());
					setPoints(p.getUniqueId(), data.getPoints());
					setHighestKillstreak(p.getUniqueId(), data.getHighestkillstreak());
					setPlayerInfo(p.getUniqueId(), data.getPlayerInfo());
					setCoins(p.getUniqueId(), data.getMoney());
				}
			}).start();
		}	
	}
	
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	public Integer getCoins(UUID uuid){
		
		try{
			java.sql.PreparedStatement stmt = con.prepareStatement("SELECT Coins FROM `Player_Money` WHERE Player = ?");
			stmt.setString(1, uuid.toString());
			ResultSet rs = stmt.executeQuery();
			if(!rs.next())
				return null;
			int Coins = rs.getInt("Coins");
			rs.close();
			stmt.close();
			return Coins;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
		
	}
	
	public Integer setCoins(UUID uuid, int Coins){
		
		try{
			java .sql.PreparedStatement stmt = con.prepareStatement("UPDATE `Player_Money` SET Coins = ? WHERE Player = ?");
			stmt.setInt(1, Coins);
			stmt.setString(2, uuid.toString());
			stmt.executeUpdate();
			stmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return Coins;
	}
	
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	
	public Integer getKills(UUID uuid){
		
		try{
			java.sql.PreparedStatement stmt = con.prepareStatement("SELECT Kills FROM `QuakeCraft` WHERE Player = ?");
			stmt.setString(1, uuid.toString());
			ResultSet rs = stmt.executeQuery();
			if(!rs.next())
				return null;
			int Kills = rs.getInt("Kills");
			rs.close();
			stmt.close();
			return Kills;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
		
	}
	public Integer setKills(UUID uuid, int Kills){
		
		try{
			java .sql.PreparedStatement stmt = con.prepareStatement("UPDATE `QuakeCraft` SET Kills = ? WHERE Player = ?");
			stmt.setInt(1, Kills);
			stmt.setString(2, uuid.toString());
			stmt.executeUpdate();
			stmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return Kills;
	}
	
	public Integer getDeaths(UUID uuid){
		
		try{
			java.sql.PreparedStatement stmt = con.prepareStatement("SELECT Deaths FROM `QuakeCraft` WHERE Player = ?");
			stmt.setString(1, uuid.toString());
			ResultSet rs = stmt.executeQuery();
			if(!rs.next())
				return null;
			int Deaths = rs.getInt("Deaths");
			rs.close();
			stmt.close();
			return Deaths;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
		
	}
	public Integer setDeaths(UUID uuid, int Deaths){
		
		try{
			java .sql.PreparedStatement stmt = con.prepareStatement("UPDATE `QuakeCraft` SET Deaths = ? WHERE Player = ?");
			stmt.setInt(1, Deaths);
			stmt.setString(2, uuid.toString());
			stmt.executeUpdate();
			stmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return Deaths;
	}
	
	public Integer getRounds(UUID uuid){
		
		try{
			java.sql.PreparedStatement stmt = con.prepareStatement("SELECT Rounds FROM `QuakeCraft` WHERE Player = ?");
			stmt.setString(1, uuid.toString());
			ResultSet rs = stmt.executeQuery();
			if(!rs.next())
				return null;
			int Rounds = rs.getInt("Rounds");
			rs.close();
			stmt.close();
			return Rounds;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
		
	}
	public Integer setRounds(UUID uuid, int Rounds){
		
		try{
			java .sql.PreparedStatement stmt = con.prepareStatement("UPDATE `QuakeCraft` SET Rounds = ? WHERE Player = ?");
			stmt.setInt(1, Rounds);
			stmt.setString(2, uuid.toString());
			stmt.executeUpdate();
			stmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return Rounds;
	}
	
	public Integer getWins(UUID uuid){
		
		try{
			java.sql.PreparedStatement stmt = con.prepareStatement("SELECT Wins FROM `QuakeCraft` WHERE Player = ?");
			stmt.setString(1, uuid.toString());
			ResultSet rs = stmt.executeQuery();
			if(!rs.next())
				return null;
			int Wins = rs.getInt("Wins");
			rs.close();
			stmt.close();
			return Wins;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
		
	}
	public Integer setWins(UUID uuid, int Wins){
		
		try{
			java .sql.PreparedStatement stmt = con.prepareStatement("UPDATE `QuakeCraft` SET Wins = ? WHERE Player = ?");
			stmt.setInt(1, Wins);
			stmt.setString(2, uuid.toString());
			stmt.executeUpdate();
			stmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return Wins;
	}
	
	public Integer getPoints(UUID uuid){
		
		try{
			java.sql.PreparedStatement stmt = con.prepareStatement("SELECT Points FROM `QuakeCraft` WHERE Player = ?");
			stmt.setString(1, uuid.toString());
			ResultSet rs = stmt.executeQuery();
			if(!rs.next())
				return null;
			int Points = rs.getInt("Points");
			rs.close();
			stmt.close();
			return Points;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
		
	}
	public Integer setPoints(UUID uuid, int Points){
		
		try{
			java .sql.PreparedStatement stmt = con.prepareStatement("UPDATE `QuakeCraft` SET Points = ? WHERE Player = ?");
			stmt.setInt(1, Points);
			stmt.setString(2, uuid.toString());
			stmt.executeUpdate();
			stmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return Points;
	}
	
	public Integer getHighestKillstreak(UUID uuid){
		
		try{
			java.sql.PreparedStatement stmt = con.prepareStatement("SELECT HighestKillstreak FROM `QuakeCraft` WHERE Player = ?");
			stmt.setString(1, uuid.toString());
			ResultSet rs = stmt.executeQuery();
			if(!rs.next())
				return null;
			int HighestKillstreak = rs.getInt("HighestKillstreak");
			rs.close();
			stmt.close();
			return HighestKillstreak;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
		
	}
	public Integer setHighestKillstreak(UUID uuid, int HighestKillstreak){
		
		try{
			java .sql.PreparedStatement stmt = con.prepareStatement("UPDATE `QuakeCraft` SET HighestKillstreak = ? WHERE Player = ?");
			stmt.setInt(1, HighestKillstreak);
			stmt.setString(2, uuid.toString());
			stmt.executeUpdate();
			stmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return HighestKillstreak;
	}
	
	public String getPlayerInfo(UUID uuid){
		
		try{
			java.sql.PreparedStatement stmt = con.prepareStatement("SELECT PlayerInfo FROM `QuakeCraft` WHERE Player = ?");
			stmt.setString(1, uuid.toString());
			ResultSet rs = stmt.executeQuery();
			if(!rs.next())
				return null;
			String PlayerInfo = rs.getString("PlayerInfo");
			rs.close();
			stmt.close();
			return PlayerInfo;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
		
	}
	public String setPlayerInfo(UUID uuid, String PlayerInfo){
		
		try{
			java .sql.PreparedStatement stmt = con.prepareStatement("UPDATE `QuakeCraft` SET PlayerInfo = ? WHERE Player = ?");
			stmt.setString(1, PlayerInfo);
			stmt.setString(2, uuid.toString());
			stmt.executeUpdate();
			stmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return PlayerInfo;
	}
}
