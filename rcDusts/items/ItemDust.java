package rcDusts.items;

import java.util.List;

import rcDusts.ModInformation;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDust extends Item {

	private final String name = "itemDust";
	private IIcon[] dustIcons = new IIcon[12];
	
	public ItemDust() {
		super();
		this.setUnlocalizedName(ModInformation.MODID + "_" + name);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setHasSubtypes(true);
		this.setMaxStackSize(64);
		GameRegistry.registerItem(this, name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		for(int i = 0; i < dustIcons.length; i++) {
			dustIcons[i] = register.registerIcon(ModInformation.MODID + ":" + RcDustsItemInfo.DUST_ICONS[i]);
		}
	}

//	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int dmg) {
		return dustIcons[dmg];
	}
	
	 @SideOnly(Side.CLIENT)
	 public IIcon getIcon(int side, int meta) {
		 return dustIcons[meta];
	 }
	 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list){
		for(int i = 0; i < RcDustsItemInfo.DUST_UNLOCALIZED_NAMES.length; i++){
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
        return "item." + ModInformation.MODID + "_" + RcDustsItemInfo.DUST_UNLOCALIZED_NAMES[itemstack.getItemDamage()];
    }
	
	@Override
    public int getMetadata(int meta) { 
        return meta;
    }

}