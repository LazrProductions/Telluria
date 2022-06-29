package net.genesis.telluria.block;

import java.util.function.Supplier;

import net.genesis.telluria.TelluriaMod;
import net.genesis.telluria.block.custom.flora.BulrushBlock;
import net.genesis.telluria.block.custom.flora.CattailsBlock;
import net.genesis.telluria.block.custom.flora.ReedsBlock;
import net.genesis.telluria.block.custom.flora.WillowLeavesBlock;
import net.genesis.telluria.block.util.LogBlock;
import net.genesis.telluria.block.util.TelluriaStandingSignBlock;
import net.genesis.telluria.block.util.TelluriaWallSignBlock;
import net.genesis.telluria.block.util.TelluriaWoodTypes;
import net.genesis.telluria.item.TelluriaItemGroups;
import net.genesis.telluria.item.TelluriaItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TelluriaBlocks {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			TelluriaMod.MOD_ID);
	
	///// Register Blocks here

    //Flora
    public static final RegistryObject<Block> BULRUSH = registerBlock("bulrush", () -> new BulrushBlock(BlockBehaviour.Properties.copy(Blocks.TALL_GRASS)), TelluriaItemGroups.FLORA);
    public static final RegistryObject<Block> CATTAILS = registerBlock("cattails", () -> new CattailsBlock(BlockBehaviour.Properties.copy(Blocks.TALL_GRASS)), TelluriaItemGroups.FLORA);
    public static final RegistryObject<Block> REEDS = registerBlock("reeds", () -> new ReedsBlock(BlockBehaviour.Properties.copy(Blocks.SUGAR_CANE)), TelluriaItemGroups.FLORA);
    
    //Wood
    public static final RegistryObject<Block> STRIPPED_WILLOW_LOG = registerBlock("stripped_willow_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)) { @Override public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return true; } @Override public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 20; } @Override public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 5; } }, TelluriaItemGroups.BLOCKS); //Call the stripped variant first to register it for the log block
    public static final RegistryObject<Block> WILLOW_LOG = registerBlock("willow_log", () -> new LogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG), TelluriaBlocks.STRIPPED_WILLOW_LOG), TelluriaItemGroups.BLOCKS);
    public static final RegistryObject<Block> STRIPPED_WILLOW_WOOD = registerBlock("stripped_willow_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)) { @Override public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return true; } @Override public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 20; } @Override public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) { return 5; } }, TelluriaItemGroups.BLOCKS);
    public static final RegistryObject<Block> WILLOW_WOOD = registerBlock("willow_wood", () -> new LogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD), TelluriaBlocks.STRIPPED_WILLOW_WOOD), TelluriaItemGroups.BLOCKS);
    public static final RegistryObject<Block> WILLOW_LEAVES = registerBlock("willow_leaves", () -> new WillowLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)), TelluriaItemGroups.BLOCKS);
    //public static final RegistryObject<Block> WILLOW_SAPLING = registerBlock("willow_sapling", () -> new WaterloggableSaplingBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)), TelluriaItemGroups.BLOCKS);
    
    //Willow Wood Components
    public static final RegistryObject<Block> WILLOW_PLANKS = registerBlock("willow_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), TelluriaItemGroups.BLOCKS);
    public static final RegistryObject<Block> WILLOW_PREASURE_PLATE = registerBlock("willow_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE)), TelluriaItemGroups.BLOCKS);
    public static final RegistryObject<Block> WILLOW_BUTTON = registerBlock("willow_button", () -> new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON)), TelluriaItemGroups.BLOCKS);
    public static final RegistryObject<Block> WILLOW_FENCE  = registerBlock("willow_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)), TelluriaItemGroups.BLOCKS);
    public static final RegistryObject<Block> WILLOW_FENCE_GATE = registerBlock("willow_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE)), TelluriaItemGroups.BLOCKS);
    public static final RegistryObject<Block> WILLOW_DOOR = registerBlock("willow_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR)), TelluriaItemGroups.BLOCKS);
    public static final RegistryObject<Block> WILLOW_TRAPDOOR = registerBlock("willow_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR)), TelluriaItemGroups.BLOCKS);
    public static final RegistryObject<Block> WILLOW_SLAB = registerBlock("willow_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)), TelluriaItemGroups.BLOCKS);
    public static final RegistryObject<Block> WILLOW_STAIRS = registerBlock("willow_stairs", () -> new StairBlock(() -> TelluriaBlocks.WILLOW_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)), TelluriaItemGroups.BLOCKS);
    public static final RegistryObject<Block> WILLOW_SIGN = registerBlockWithoutBlockItem("willow_sign", () -> new TelluriaStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), TelluriaWoodTypes.WILLOW));
    public static final RegistryObject<Block> WILLOW_WALL_SIGN = registerBlockWithoutBlockItem("willow_wall_sign", () -> new TelluriaWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), TelluriaWoodTypes.WILLOW));


	///// Registering Blocks
    //register a block without an item
    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    //register a block and a relative item
	private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    //register a block item
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return TelluriaItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }

    //register blocks
	public static void register(IEventBus eventBus) {
		TelluriaMod.LOGGER.info("Registering Blocks for Telluria");
		BLOCKS.register(eventBus);
	}
}
