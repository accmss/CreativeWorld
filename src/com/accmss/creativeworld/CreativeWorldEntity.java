package com.accmss.creativeworld;


import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;


public class CreativeWorldEntity implements Listener  {


public CreativeWorldEntity(CreativeWorld plugin) 
{
		
}
	

	  
@EventHandler (priority = EventPriority.NORMAL)
public void onCreatureSpawn(CreatureSpawnEvent event)
{

	World world = event.getEntity().getWorld();
		if (world.getName().equalsIgnoreCase(CreativeWorldConfig.CreativeName)) 
		{
		event.setCancelled(true);
		}

	
}


}
