package rcDusts;

import org.apache.logging.log4j.Logger;
import rcDusts.config.ConfigurationHandler;
import rcDusts.items.RcDustsItems;
//import rcDusts.network.PacketHandler;
import rcDusts.proxies.CommonProxy;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
//import cpw.mods.fml.common.network.NetworkMod;
//import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.FMLLog; 

@Mod(modid=ModInformation.MODID, name=ModInformation.MODNAME, version=ModInformation.VERSION, dependencies = "required-after:Railcraft;after:BiomesOPlenty")
//@NetworkMod(channels={ModInformation.CHANNEL}, clientSideRequired=true, serverSideRequired=false, packetHandler=PacketHandler.class)
public class RcDusts {
 
	@Instance("rcDusts")
	public static RcDusts instance;
 
	@SidedProxy(clientSide="rcDusts.proxies.ClientProxy", serverSide="rcDusts.proxies.CommonProxy")
	public static CommonProxy proxy;
	
	private static final Logger logger = FMLLog.getLogger();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		RcDustsItems.init();
	}
 
	@EventHandler
	public void init(FMLInitializationEvent event) {
		RcDustsItems.isIC2Installed = Boolean.valueOf(Loader.isModLoaded("IC2")).booleanValue();
		logger.info("RcDusts: IC installed: "+String.valueOf(RcDustsItems.isIC2Installed));
		RcDustsItems.isGregTechInstalled = Boolean.valueOf(Loader.isModLoaded("gregtech_addon")).booleanValue();
		logger.info("RcDusts: GregTech installed: "+String.valueOf(RcDustsItems.isGregTechInstalled));
		RcDustsItems.isTinkersConstructInstalled = Boolean.valueOf(Loader.isModLoaded("TConstruct")).booleanValue();
		logger.info("RcDusts: TinkersConstruct installed: "+String.valueOf(RcDustsItems.isTinkersConstructInstalled));
		RcDustsItems.isNetherOresInstalled = Boolean.valueOf(Loader.isModLoaded("NetherOres")).booleanValue();
		logger.info("RcDusts: NetherOres installed: "+String.valueOf(RcDustsItems.isNetherOresInstalled));
		
		//config sanity check
		if(!RcDustsItems.isIC2Installed && ConfigurationHandler.doIC2CrushedOres) {
			ConfigurationHandler.doIC2CrushedOres = false;
		}
		
//		LanguageRegistry.instance().addStringLocalization("itemGroup." + ModInformation.MODNAME, "en_US", ModInformation.MODNAME);
//		RcDustsItems.addNames();
		RcDustsItems.registerDustsWithOreDict();
//		RcDustsItems.biomesOPlentyOreDictRoundup();
	}
 
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		RcDustsItems.checkWhatIngotsWeHave();
		if(RcDustsItems.isIC2Installed) {
			RcDustsItems.removeSomeRecipes();
		}
		RcDustsItems.registerBaseRecipes();
		if(!RcDustsItems.isGregTechInstalled) {
			RcDustsItems.registerRcDustsRecipes();
			if(RcDustsItems.isNetherOresInstalled && ConfigurationHandler.doNetherOreRecipes) {
				RcDustsItems.registerNetherOresRecipes();
			}
		}
		RcDustsItems.otherOresRoundup();
		if(ConfigurationHandler.removeBlastFurnaceSteel) {
			RcDustsItems.removeBlastFurnaceSteel();
		}
		if(RcDustsItems.isGregTechInstalled && RcDustsItems.isTinkersConstructInstalled) {
			RcDustsItems.doGregTechTinkersFixes();
		}
	}
}