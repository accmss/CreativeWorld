package com.accmss.creativeworld;


import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;



public class CreativeWorldPlayer implements Listener 
{


Player player;





public CreativeWorldPlayer(CreativeWorld plugin) 
{
	
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
		event.setCancelled(true);
		}

}



@EventHandler (priority = EventPriority.NORMAL)
public void playerTeleport(PlayerTeleportEvent event)
{

		//NEW Ticket #1
		if (!CreativeWorldConfig.IntereceptGM) return;
	
	World world = event.getTo().getWorld();
	//CreativeWorldLib.Chat(event.getPlayer(), "CreativeWorld",  "Reason: " + event.getCause().toString());

		if (!world.getName().equalsIgnoreCase(CreativeWorldConfig.CreativeName)) 
		{
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

