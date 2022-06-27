package net.genesis.telluria.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SignBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodenButtonBlock;
import net.minecraft.block.PressurePlateBlock.ActivationRule;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.genesis.telluria.TelluriaMod;
import net.genesis.telluria.block.custom.flora.BulrushBlock;
import net.genesis.telluria.block.custom.flora.CattailsBlock;
import net.genesis.telluria.block.custom.flora.ReedsBlock;
import net.genesis.telluria.block.custom.flora.WillowLeavesBlock;
import net.genesis.telluria.block.custom.flora.WillowSaplingBlock;
import net.genesis.telluria.block.custom.wood.WillowLogBlock;
import net.genesis.telluria.block.entity.TelluriaSignTypes;
import net.genesis.telluria.item.TelluriaItemGroups;
import net.genesis.telluria.world.feature.tree.WillowSaplingGenerator;

public class TelluriaBlocks {

    /////Register Blocks here

    //Flora
    public static final Block BULRUSH = registerBlock("bulrush", new BulrushBlock(FabricBlockSettings.copy(Blocks.TALL_GRASS)), TelluriaItemGroups.FLORA);
    public static final Block REEDS = registerBlock("reeds", new ReedsBlock(FabricBlockSettings.copy(Blocks.SUGAR_CANE)), TelluriaItemGroups.FLORA);
    public static final Block CATTAILS = registerBlock("cattails", new CattailsBlock(FabricBlockSettings.copy(Blocks.TALL_GRASS)), TelluriaItemGroups.FLORA);

    //Wood
    public static final Block WILLOWLOG = registerBlock("willow_log", new WillowLogBlock(FabricBlockSettings.copy(Blocks.OAK_LOG)), TelluriaItemGroups.BLOCKS);
    public static final Block STRIPPEDWILLOWLOG = registerBlock("stripped_willow_log", new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_LOG)), TelluriaItemGroups.BLOCKS);
    public static final Block WILLOWWOOD = registerBlock("willow_wood", new WillowLogBlock(FabricBlockSettings.copy(Blocks.OAK_WOOD)), TelluriaItemGroups.BLOCKS);
    public static final Block STRIPPEDWILLOWWOOD = registerBlock("stripped_willow_wood", new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_WOOD)), TelluriaItemGroups.BLOCKS);
    public static final Block WILLOWLEAVES = registerBlock("willow_leaves", new WillowLeavesBlock(FabricBlockSettings.copy(Blocks.OAK_LEAVES)), TelluriaItemGroups.FLORA);
    public static final Block WILLOWSAPLING = registerBlock("willow_sapling", new WillowSaplingBlock(new WillowSaplingGenerator(), FabricBlockSettings.copy(Blocks.OAK_SAPLING)), TelluriaItemGroups.FLORA);

        //Willow plank components
        public static final Block WILLOWPLANKS = registerBlock("willow_planks", new Block(FabricBlockSettings.copy(Blocks.OAK_PLANKS)), TelluriaItemGroups.BLOCKS);
        public static final Block WILLOWPREASUREPLATE = registerBlock("willow_pressure_plate", new PressurePlateBlock(ActivationRule.EVERYTHING, FabricBlockSettings.copy(Blocks.OAK_PRESSURE_PLATE)), TelluriaItemGroups.BLOCKS);
        public static final Block WILLOWBUTTON = registerBlock("willow_button", new WoodenButtonBlock(FabricBlockSettings.copy(Blocks.OAK_BUTTON)), TelluriaItemGroups.BLOCKS);
        public static final Block WILLOWFENCE = registerBlock("willow_fence", new FenceBlock(FabricBlockSettings.copy(Blocks.OAK_FENCE)), TelluriaItemGroups.BLOCKS);
        public static final Block WILLOWFENCEGATE = registerBlock("willow_fence_gate", new FenceGateBlock(FabricBlockSettings.copy(Blocks.OAK_FENCE_GATE)), TelluriaItemGroups.BLOCKS);
        public static final Block WILLOWDOOR = registerBlock("willow_door", new DoorBlock(FabricBlockSettings.copy(Blocks.OAK_DOOR)), TelluriaItemGroups.BLOCKS);
        public static final Block WILLOWTRAPDOOR = registerBlock("willow_trapdoor", new TrapdoorBlock(FabricBlockSettings.copy(Blocks.OAK_TRAPDOOR)), TelluriaItemGroups.BLOCKS);
        public static final Block WILLOWSLAB = registerBlock("willow_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.OAK_SLAB)), TelluriaItemGroups.BLOCKS);
        public static final Block WILLOWSTAIRS = registerBlock("willow_stairs", new StairsBlock(TelluriaBlocks.WILLOWPLANKS.getDefaultState(), FabricBlockSettings.copy(Blocks.OAK_STAIRS)), TelluriaItemGroups.BLOCKS);
        public static final Block WILLOWSIGN = registerBlockWithoutItem("willow_sign", new SignBlock(FabricBlockSettings.copy(Blocks.OAK_SIGN), TelluriaSignTypes.WILLOW));
        public static final Block WILLOWWALLSIGN = registerBlockWithoutItem("willow_wall_sign", new WallSignBlock(FabricBlockSettings.copy(Blocks.OAK_TRAPDOOR), TelluriaSignTypes.WILLOW));
        //
    /////


    ///// Registering Blocks
    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(TelluriaMod.MOD_ID, name), block);
    }

    private static Block registerBlockWithoutItem(String name, Block block) {
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
