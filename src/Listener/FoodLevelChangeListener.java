package Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {
	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		
		e.setCancelled(true);
		if(e.getFoodLevel() != 20) {
			e.setFoodLevel(20);
		}
		
	}

}
