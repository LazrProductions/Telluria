package net.genesis.telluria.block;

import net.genesis.telluria.TelluriaMod;
import net.genesis.telluria.block.custom.flora.BulrushBlock;
import net.genesis.telluria.item.ModItemGroups;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public class ModBlocks {

    /////Register Blocks here

    public static final Block BULRUSH = registerBlock("bulrush", new BulrushBlock(FabricBlockSettings.of(Material.WOOD).nonOpaque().breakInstantly().collidable(false)), ModItemGroups.FLORA);

    /////


    ///// Registering Blocks
    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(TelluriaMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(TelluriaMod.MOD_ID, name),
                new BlockItem(block, new ItemSettings().group(group)));
    }

    public static void registerModBlocks() {
        TelluriaMod.LOGGER.info("Registering ModBlocks for " + TelluriaMod.MOD_ID);
    }
}
