package clashsoft.cslib.minecraft.item.datatools;

import java.util.Set;

import clashsoft.cslib.collections.CSCollections;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ItemDataAxe extends ItemDataTool
{
	public static final Set<Block>	blocksEffectiveAgainst	= CSCollections.createSet(Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.chest, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.pumpkin, Blocks.lit_pumpkin);
	
	public ItemDataAxe(ToolMaterial toolMaterial)
	{
		super(3F, toolMaterial, blocksEffectiveAgainst, "Axe");
	}
	
	@Override
	public float getDigSpeed(ItemStack stack, Block block, int metadata)
	{
		Material material = block.getMaterial();
		if (material == Material.wood || material == Material.plants || material == Material.vine)
		{
			super.getDigSpeed(stack, block, metadata);
		}
		return super.func_150893_a(stack, block);
	}
}