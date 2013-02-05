package com.accmss.creativeworld;


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World.Environment;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class CreativeWorldLib {


public static Map<String, String> playerGear = new HashMap<String, String>();
//public static HashMap<String, ItemStack> armour = new HashMap<String, List<ItemStack>>();
//public static HashMap<String, ArrayList<Block>> armour = new HashMap<String, ArrayList<Block>>();
//public static HashMap<String, List<ItemStack>> armour = new HashMap<String, List<ItemStack>>();


//LOGGING
public static void Chat(CommandSender sender, String PluginName, String message)
{

	sender.sendMessage("[" + PluginName + "] " + message);
	
}
public static void LogCommand(String player, String command){
	 
	CreativeWorld.zLogger.info("[PLAYER_COMMAND] " + player + ": " + command);

}


//INVENTORY
public static void InventoryRestore(Player player)
{
	
		if(playerGear.containsKey(player.getName()))
		{
		//restore inventory
		Inventory i = StringToInventory(playerGear.get(player.getName()));
		player.getInventory().setContents(i.getContents());
		CreativeWorldLib.Chat(player, "CreativeWorld", "Inventory loaded");
		playerGear.remove(player.getName());
		

		/*
		//RESTORE ARMOUR
		String pname = player.getName();
			if(armour.containsKey(pname));
			{
				for(ItemStack it : armour.get(pname))
				{
				player.getInventory().addItem(it);
				}
			}

*/
		
		}

		
}
public static void InventorySave(Player player)
{

	
	
		//quick check to prevent wiping of inventorys when /creative is called twice
		if(playerGear.containsKey(player.getName()))
		{
			if (playerGear.get(player.getName()).length() > InventoryToString(player.getInventory()).length())
			{
				CreativeWorldLib.Chat(player, "CreativeWorld", "Inventory already saved..");
			return;
			}
		}
	
	CreativeWorldLib.Chat(player, "CreativeWorld", "Inventory saved");
	playerGear.put(player.getName(), InventoryToString(player.getInventory()));

	/*
	//SAVE ARMOUR
	String pname = player.getName();
	List<ItemStack> list = new ArrayList<ItemStack>();
		for(ItemStack it : player.getInventory().getArmorContents())
		{
			if(it != null)
			{
			list.add(it);
			}
		}
	if(!list.isEmpty())
	{
	armour.put(pname, list);
	}
*/
	
	player.getInventory().clear();

	
}
public static String InventoryToString (Inventory invInventory)
{
    String serialization = invInventory.getSize() + ";";
    for (int i = 0; i < invInventory.getSize(); i++)
    {
        ItemStack is = invInventory.getItem(i);
        if (is != null)
        {
            String serializedItemStack = new String();
           
            String isType = String.valueOf(is.getType().getId());
            serializedItemStack += "t@" + isType;
           
            if (is.getDurability() != 0)
            {
                String isDurability = String.valueOf(is.getDurability());
                serializedItemStack += ":d@" + isDurability;
            }
           
            if (is.getAmount() != 1)
            {
                String isAmount = String.valueOf(is.getAmount());
                serializedItemStack += ":a@" + isAmount;
            }
           
            Map<Enchantment,Integer> isEnch = is.getEnchantments();
            if (isEnch.size() > 0)
            {
                for (Entry<Enchantment,Integer> ench : isEnch.entrySet())
                {
                    serializedItemStack += ":e@" + ench.getKey().getId() + "@" + ench.getValue();
                }
            }
           
            serialization += i + "#" + serializedItemStack + ";";
        }
    }
    return serialization;
}
public static Inventory StringToInventory (String invString)
{
    String[] serializedBlocks = invString.split(";");
    String invInfo = serializedBlocks[0];
    Inventory deserializedInventory = Bukkit.getServer().createInventory(null, Integer.valueOf(invInfo));
   
    for (int i = 1; i < serializedBlocks.length; i++)
    {
        String[] serializedBlock = serializedBlocks[i].split("#");
        int stackPosition = Integer.valueOf(serializedBlock[0]);
       
        if (stackPosition >= deserializedInventory.getSize())
        {
            continue;
        }
       
        ItemStack is = null;
        Boolean createdItemStack = false;
       
        String[] serializedItemStack = serializedBlock[1].split(":");
        for (String itemInfo : serializedItemStack)
        {
            String[] itemAttribute = itemInfo.split("@");
            if (itemAttribute[0].equals("t"))
            {
            //is = new ItemStack(MaterialData.getMaterial(Integer.valueOf(itemAttribute[1])));
            is = new ItemStack(Integer.valueOf(itemAttribute[1]));
            createdItemStack = true;
            }
            else if (itemAttribute[0].equals("d") && createdItemStack)
            {
                is.setDurability(Short.valueOf(itemAttribute[1]));
            }
            else if (itemAttribute[0].equals("a") && createdItemStack)
            {
                is.setAmount(Integer.valueOf(itemAttribute[1]));
            }
            else if (itemAttribute[0].equals("e") && createdItemStack)
            {
          
            	try
            	{
                is.addEnchantment(Enchantment.getById(Integer.valueOf(itemAttribute[1])), Integer.valueOf(itemAttribute[2]));
            	}
    		    catch (Exception e)
            	{
    			CreativeWorldLib.Chat(CreativeWorld.zPlugin.getServer().getConsoleSender(),e.toString(), e.getMessage());
    			e.printStackTrace();
            	}
            	

            }
        }
        deserializedInventory.setItem(stackPosition, is);
    }
   
    return deserializedInventory;
}


//PLAYER
public static void PlayerSetCreative(Player player)
{

	Location worldC;
	
	worldC = CreativeWorld.zPlugin.getServer().getWorld(CreativeWorldConfig.CreativeName).getSpawnLocation();
	worldC.setWorld(CreativeWorld.zPlugin.getServer().getWorld(CreativeWorldConfig.CreativeName));
	player.teleport(worldC,TeleportCause.COMMAND);
	
	//save inventory
	InventorySave(player);
	CreativeWorldLib.Chat(player, "CreativeWorld", ChatColor.GREEN + "Warping to " + worldC.getWorld().getName() + ".");

}
public static void PlayerSetSurvival(Player player)
{

Location worldC;

	worldC = CreativeWorld.zPlugin.getServer().getWorld(CreativeWorld.zPlugin.getServer().getWorlds().get(0).getName()).getSpawnLocation();
	worldC.setWorld(CreativeWorld.zPlugin.getServer().getWorld(CreativeWorld.zPlugin.getServer().getWorlds().get(0).getName()));
	player.teleport(worldC,TeleportCause.COMMAND);
	InventoryRestore(player);
	CreativeWorldLib.Chat(player, "CreativeWorld", ChatColor.GREEN + "Warping to " + worldC.getWorld().getName() + ".");


}
public static void PlayerEnsureSurvival(Player player, String world)
{

	InventoryRestore(player);
	CreativeWorldLib.Chat(player, "CreativeWorld", ChatColor.GREEN + "Warping to " + world + ".");

}

/*
public static boolean PlayerEnforceSurvivalz(Player player, String WorldName)
{
	
		//EXIT 1 - WORLD
		if (WorldName.equalsIgnoreCase(CreativeWorld.onCommand_creative_world_name)) 
		{
		return false;	
		}
	
		//KICK 2 - Creative mode
		if (player.getGameMode() == GameMode.CREATIVE)
		{
		InventoryRestore(player);
		player.setGameMode(GameMode.SURVIVAL);
		return true;
		}

	return false;
	
}
*/


//WORLD
public static void WorldAutoGen()
{

		if(CreativeWorld.zPlugin.getServer().getWorld(CreativeWorldConfig.CreativeName) == null)
		{
		Chat(CreativeWorld.zPlugin.getServer().getConsoleSender(),"CreatingWorld", "§fCreating world " + CreativeWorldConfig.CreativeName);
		WorldCreator wc = new WorldCreator(CreativeWorldConfig.CreativeName);
			//FLAT
			if (CreativeWorldConfig.CreativeType.equalsIgnoreCase("flat"))
			{
			wc.type(WorldType.FLAT);
			wc.generateStructures(false);
			}
			else
			{
			wc.type(WorldType.NORMAL);
			wc.generateStructures(true);
				//SEED
				if (CreativeWorldConfig.CreativeSeed.length() > 2)
				{
				wc.seed(Long.parseLong(CreativeWorldConfig.CreativeSeed));
				}
			}

		wc.environment(Environment.NORMAL);
		wc.createWorld();
		}

}
public static void WorldWarp(Player player, GameMode xGameMode)
{

		if (CreativeWorldConfig.CreativeMake) 
		{

			
			
			//NEW LOGIC 2. Toggle world
			//if (player.getWorld().getName().equalsIgnoreCase(CreativeWorld.onCommand_creative_world_name))
			if (player.getGameMode() == GameMode.SURVIVAL) //then we are moving into creative mode
			{
			PlayerSetCreative(player);
			}
			else
			{
			PlayerSetSurvival(player);
			}
	
		}

}


}