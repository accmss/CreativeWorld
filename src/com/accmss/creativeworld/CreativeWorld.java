package com.accmss.creativeworld;


//IMPORTS - JAVA
import java.io.IOException;
import java.util.logging.Logger;

//IMPORTS - BUKKIT
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;



public class CreativeWorld extends JavaPlugin  {

public static CreativeWorld zPlugin;
protected static FileConfiguration zConfig;
public static Logger zLogger = Logger.getLogger("Minecraft");



@Override
public void onEnable() {

	
		//Metrics
		try
		{
		CreativeWorldMetricsLite metrics = new CreativeWorldMetricsLite(this);
		metrics.start();
		} catch (IOException e) {
		CreativeWorldLib.Chat(CreativeWorld.zPlugin.getServer().getConsoleSender(),"MetricsLite", e.getCause() + " : " + e.getMessage());
		}

	zPlugin = this;
		
	//Settings
	CreativeWorldConfig.LoadSettings(this.getFile().getAbsolutePath());
	
	//Listners
	getServer().getPluginManager().registerEvents(new CreativeWorldPlayer(this), this);
	getServer().getPluginManager().registerEvents(new CreativeWorldEntity(this), this);

	//Autogen
	CreativeWorldLib.WorldAutoGen();

}
@Override
public void onDisable() 
{



}




@EventHandler
public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){

Player player;

		if ((sender instanceof Player))
		{
		player = (Player)sender;
		//CreativeWorldLib.LogCommand(player.getName(), cmd.toString());
		}
		else
		{
		//CreativeWorldLib.LogCommand(sender.getName(), cmd.toString());
		return false;
		}
		
		if (cmd.getName().equalsIgnoreCase("creative"))
		{
		//CreativeWorldLib.LogCommand(sender.getName(), "setting creative mode");
		if (player.hasPermission("creativeworld.gamemode")) player.setGameMode(GameMode.CREATIVE);
		//CreativeWorldLib.LogCommand(sender.getName(), "warping creative");
		if (player.hasPermission("creativeworld.warp"))	   CreativeWorldLib.PlayerSetCreative(player);
		return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("survival"))
		{
		if (player.hasPermission("creativeworld.gamemode")) player.setGameMode(GameMode.SURVIVAL);
		if (player.hasPermission("creativeworld.warp"))	    CreativeWorldLib.PlayerSetSurvival(player);
		return true;
		}

		CreativeWorldLib.LogCommand(sender.getName(), cmd.toString());
	return false;

}





}