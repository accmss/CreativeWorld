package com.accmss.creativeworld;


import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;




public class CreativeWorldPlayer implements Listener 
{


Player player;





public CreativeWorldPlayer(CreativeWorld plugin) 
{
	
}
@EventHandler (priority = EventPriority.NORMAL)
public void onPlayerRespawn(final PlayerRespawnEvent event)
{

	CreativeWorld.zPlugin.getServer().getScheduler().runTaskLaterAsynchronously(CreativeWorld.zPlugin, new Runnable()
	{
		
		public void run()
		{   
		World world = event.getPlayer().getWorld();
			if (!world.getName().equalsIgnoreCase(CreativeWorldConfig.CreativeName)) 
			{
			event.getPlayer().setGameMode(GameMode.SURVIVAL);
			CreativeWorldLib.PlayerEnsureSurvival(event.getPlayer(), world.getName());	
			}

		}

	}, 2L); 

	

}

@EventHandler (priority = EventPriority.NORMAL)
public void onPlayerCommandPreprocess (PlayerCommandPreprocessEvent event)
{

	//NEW Ticket #1
	if (!CreativeWorldConfig.IntereceptGM) return;
	
		if (event.getMessage().toLowerCase().startsWith("/gamemode") || event.getMessage().toLowerCase().startsWith("/gm") ||
				event.getMessage().toLowerCase().startsWith("/gmt") || event.getMessage().toLowerCase().startsWith("/give")
				 || event.getMessage().toLowerCase().startsWith("/defaultgamemode"))
		{
		//CreativeWorldLib.Chat(event.getPlayer(), "CreativeWorld",  "Cancelled command: " + event.getMessage().toLowerCase());
		event.setCancelled(true);
		}

}



@EventHandler (priority = EventPriority.NORMAL)
public void playerTeleport(PlayerTeleportEvent event)
{

		//NEW Ticket #1
		if (!CreativeWorldConfig.IntereceptGM) return;
		
		//NEw exit if they are teleporting from world to world
		if (event.getFrom().getWorld().getName().equalsIgnoreCase(event.getTo().getWorld().getName())) return;
	
	World world = event.getTo().getWorld();


		if (!world.getName().equalsIgnoreCase(CreativeWorldConfig.CreativeName)) 
		{
			//CreativeWorldLib.Chat(event.getPlayer(), "CreativeWorld",  "Reason: " + event.getCause().toString());
			if (event.getPlayer().hasPermission("creativeworld.gamemode")) event.getPlayer().setGameMode(GameMode.SURVIVAL);
			if (event.getPlayer().hasPermission("creativeworld.warp"))	   CreativeWorldLib.PlayerEnsureSurvival(event.getPlayer(), world.getName());
		}

}



@EventHandler (priority = EventPriority.NORMAL)
public void onPlayerQuit (PlayerQuitEvent event)
{

	World world = event.getPlayer().getWorld();
	if (world.getName().equalsIgnoreCase(CreativeWorldConfig.CreativeName)) 
	{
	CreativeWorldLib.PlayerSetSurvival(event.getPlayer());
	}

}


}

