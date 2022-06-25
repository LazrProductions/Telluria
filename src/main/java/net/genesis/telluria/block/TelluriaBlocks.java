package net.genesis.telluria.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.genesis.telluria.TelluriaMod;
import net.genesis.telluria.block.custom.flora.BulrushBlock;
import net.genesis.telluria.block.custom.flora.ReedsBlock;
import net.genesis.telluria.item.TelluriaItemGroups;

public class TelluriaBlocks {

    /////Register Blocks here

    public static final Block BULRUSH = registerBlock("bulrush", new BulrushBlock(FabricBlockSettings.copy(Blocks.TALL_GRASS)), TelluriaItemGroups.FLORA);
    public static final Block REEDS = registerBlock("reeds", new ReedsBlock(FabricBlockSettings.copy(Blocks.SUGAR_CANE)), TelluriaItemGroups.FLORA);

    /////


    ///// Registering Blocks
    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(TelluriaMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(TelluriaMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group)));
    }

    public static void registerModBlocks() {
        TelluriaMod.LOGGER.info("Registering ModBlocks for " + TelluriaMod.MOD_ID);
    }
}
