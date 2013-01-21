package com.accmss.creativeworld;


//IMPORT - JAVA
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


/*
# CreativeWorld

World:
  CreativeMake: true
  CreativeName: creative
  CreativeType: flat
  CreativeSeed: ''
Commands:
  IntereceptGM: true
Version:
  ConfigYMLVer: 1
 */


//SYNC TO VERSION: 1


public class CreativeWorldConfig {

	//VARS - SETTINGS
public static Boolean CreativeMake = true;
public static String CreativeName = null;
public static String CreativeType = null;
public static String CreativeSeed = null;

public static Boolean IntereceptGM = true;


public static int ConfigYMLVer = 0;


//VARS - SETTINGS
public static String SlashChar = null;
public static int SyncVers = 1;
	
public static void LoadSettings(String file)
{

	//Slash
	SetSlash(file);

	//Ensure config
	EnsureConfig();

	//1 World
	CreativeMake = CreativeWorld.zConfig.getBoolean("World.CreativeMake", CreativeMake);
	CreativeName = CreativeWorld.zConfig.getString("World.CreativeName", CreativeName);
	CreativeType = CreativeWorld.zConfig.getString("World.CreativeType", CreativeType);
	CreativeSeed = CreativeWorld.zConfig.getString("World.CreativeSeed", CreativeSeed);

	//2 Commands
	IntereceptGM = CreativeWorld.zConfig.getBoolean("Commands.IntereceptGM", IntereceptGM);

}


public static void SetSlash(String file)
{

	if (file.contains("/"))
	{
	SlashChar = "/"; //Linux
	}
	else
	{
	SlashChar = "\\"; //Windows
	}
	
}
private static void EnsureConfig()
{

	File fileDir = new File("plugins" + SlashChar + "CreativeWorld");
	String zFile = "plugins" + SlashChar + "CreativeWorld" + SlashChar + "config.yml";
	File f = new File(zFile);

		//Directory
		if (!fileDir.exists())
		{
		fileDir.mkdir();
		}
	
		//File
		if(!f.isFile())
		{ 
		CreativeWorldLib.Chat(CreativeWorld.zPlugin.getServer().getConsoleSender(), "CreativeWorld", "§fWriting new configuration.yml.");
		CreateConfig(zFile);
		}
		else
		{
		CreativeWorld.zConfig = CreativeWorld.zPlugin.getConfig();
		}

		//Update
	    try
	    {ConfigYMLVer = CreativeWorld.zConfig.getInt("Version.ConfigYMLVer", ConfigYMLVer);}
		catch (Exception e)
		{ConfigYMLVer = 0;}
	    
		if(ConfigYMLVer != SyncVers)
		{
		CreativeWorldLib.Chat(CreativeWorld.zPlugin.getServer().getConsoleSender(), "CreativeWorld", "§fUpdating new configuration.yml...");
		CreateConfig(zFile);
		}

}
private static void CreateConfig(String file) 
{

	try
	{
	InputStream is = CreativeWorld.zPlugin.getClass().getResourceAsStream("/config.yml");
	OutputStream os = new FileOutputStream(file);  
	byte[] buffer = new byte[4096];  
	int bytesRead;  
		while ((bytesRead = is.read(buffer)) != -1)
		{  
		os.write(buffer, 0, bytesRead);  
		}  
	is.close();  
	os.close(); 
	CreativeWorld.zConfig = CreativeWorld.zPlugin.getConfig();
	} catch (Exception e) {
	CreativeWorldLib.Chat(CreativeWorld.zPlugin.getServer().getConsoleSender(), "CreativeWorld", "§fWriting new configuration.yml failed!");
	CreativeWorldLib.Chat(CreativeWorld.zPlugin.getServer().getConsoleSender(), "CreativeWorld", "§4" + e.getCause() + ": " +  e.getMessage());
	}
	

}


}
