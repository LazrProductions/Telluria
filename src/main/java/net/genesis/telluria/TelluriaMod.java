package net.genesis.telluria;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.genesis.telluria.block.TelluriaBlocks;
import net.genesis.telluria.block.entity.TelluriaBlockEntities;
import net.genesis.telluria.block.entity.TelluriaWoodTypes;
import net.genesis.telluria.item.TelluriaItems;
import net.genesis.telluria.client.particle.TelluriaParticles;
import net.genesis.telluria.sound.TelluriaSounds;
import net.genesis.telluria.world.biome.TelluriaRegions;
import net.genesis.telluria.world.biome.TelluriaSurfaceRuleData;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

@Mod(TelluriaMod.MOD_ID)
public class TelluriaMod {
    public static final String MOD_ID = "telluria";
	public static final Logger LOGGER = LogManager.getLogger();

	public TelluriaMod() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

		// Register Blocks and Items
		TelluriaItems.register(eventBus);
        TelluriaBlocks.register(eventBus);
		
        TelluriaBlockEntities.register(eventBus);

		TelluriaSounds.register(eventBus);
		TelluriaParticles.PARTICLE_TYPES.register(eventBus);

		//call setup and clientSetup
		eventBus.addListener(this::commonSetup);	
		eventBus.addListener(this::clientSetup);

		GeckoLib.initialize();

		MinecraftForge.EVENT_BUS.register(this);		
	}

	private void clientSetup(final FMLClientSetupEvent event) {
		//Register Render Layers
		ItemBlockRenderTypes.setRenderLayer(TelluriaBlocks.BULRUSH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(TelluriaBlocks.CATTAILS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(TelluriaBlocks.REEDS.get(), RenderType.cutout());
		
		//ItemBlockRenderTypes.setRenderLayer(TelluriaBlocks.WILLOW_SAPLING.get(), RenderType.cutout());
		
		ItemBlockRenderTypes.setRenderLayer(TelluriaBlocks.WILLOW_DOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(TelluriaBlocks.WILLOW_TRAPDOOR.get(), RenderType.cutout());

		WoodType.register(TelluriaWoodTypes.WILLOW);
		BlockEntityRenderers.register(TelluriaBlockEntities.SIGN_BLOCK_ENTITIES.get(), SignRenderer::new);
	}

	public void commonSetup(final FMLCommonSetupEvent event) {
		Sheets.addWoodType(TelluriaWoodTypes.WILLOW);

		event.enqueueWork(() ->
        {
            // Given we only add two biomes, we should keep our weight relatively low.
            Regions.register(new TelluriaRegions(new ResourceLocation(MOD_ID, "overworld"), 5));

            // Register our surface rules
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, TelluriaSurfaceRuleData.makeRules());
        });
	}
}
