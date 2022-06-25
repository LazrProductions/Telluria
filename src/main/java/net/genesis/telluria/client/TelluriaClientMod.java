package net.genesis.telluria.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.genesis.telluria.TelluriaMod;
import net.genesis.telluria.block.TelluriaBlocks;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.render.RenderLayer;

// This class is an entrypoint
public class TelluriaClientMod implements ClientModInitializer {

	private static final BlockColorProvider FOLIAGE_BLOCK_COLORS =
			(block, world, pos, layer) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor();

    @Override
    public void onInitializeClient() {
        TelluriaMod.LOGGER.info("Initializing telluria client");
        
        //Cutout Blocks
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), TelluriaBlocks.BULRUSH);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), TelluriaBlocks.REEDS);

        //Register Block Colors
		ColorProviderRegistry.BLOCK.register(
				FOLIAGE_BLOCK_COLORS,
                TelluriaBlocks.BULRUSH
		);

        //Register Item Colors
        ColorProviderRegistry.ITEM.register(
            (stack, tintIndex) -> {
                return GrassColors.getColor(0.5D, 1.0D);
            },
            TelluriaBlocks.BULRUSH.asItem()
        );

    }
    
}
