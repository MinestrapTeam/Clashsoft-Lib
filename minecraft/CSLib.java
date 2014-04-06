package clashsoft.cslib.minecraft;

import clashsoft.cslib.minecraft.command.CSCommand;
import clashsoft.cslib.minecraft.command.CommandModUpdate;
import clashsoft.cslib.minecraft.common.CSLProxy;
import clashsoft.cslib.minecraft.network.CSNetHandler;
import clashsoft.cslib.minecraft.update.CSUpdate;
import clashsoft.cslib.minecraft.util.CSConfig;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.command.ICommand;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

@Mod(modid = CSLib.MODID, name = CSLib.NAME, version = CSLib.VERSION)
public class CSLib extends ClashsoftMod
{
	public static final String	MODID		= "cslib";
	public static final String	NAME		= "Clashsoft Lib";
	public static final String	ACRONYM		= MODID;
	public static final String	VERSION		= CSUpdate.CURRENT_VERSION + "-1.0.0";
	public static final String	DEPENDENCY	= "required-after:" + MODID;
	
	@Instance(MODID)
	public static CSLib			instance;
	
	@SidedProxy(clientSide = "clashsoft.cslib.minecraft.client.CSLClientProxy", serverSide = "clashsoft.cslib.minecraft.common.CSLProxy")
	public static CSLProxy		proxy;
	
	public static boolean		updateCheck	= true;
	public static boolean		autoUpdate	= true;
	
	public CSLib()
	{
		super(MODID, NAME, ACRONYM, VERSION);
		this.hasConfig = true;
		this.netHandler = new CSNetHandler(NAME);
		this.url = "https://github.com/Clashsoft/CSLibMC/wiki/";
	}
	
	@Override
	public void readConfig()
	{
		autoUpdate = CSConfig.getBool("updates", "Auto Updates", "Disables automatic updates", true);
		updateCheck = CSConfig.getBool("updates", "Update Check", "Disables update checks for ALL mods", true);
	}
	
	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
	}
	
	@Override
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		CSUpdate.updateCheck(CSUpdate.CLASHSOFT_UPDATE_NOTES);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		super.postInit(event);
	}
	
	@EventHandler
	public void serverStarted(FMLServerStartedEvent event)
	{
		ServerCommandManager manager = (ServerCommandManager) MinecraftServer.getServer().getCommandManager();
		for (ICommand cmd : CSCommand.commands)
		{
			manager.registerCommand(cmd);
		}
		
		manager.registerCommand(new CommandModUpdate());
	}
	
	@SubscribeEvent
	public void playerJoined(EntityJoinWorldEvent event)
	{
		if (event.world.isRemote && event.entity instanceof EntityPlayer)
		{
			CSUpdate.notifyAll((EntityPlayer) event.entity);
		}
	}
}
