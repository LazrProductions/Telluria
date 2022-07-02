package net.genesis.telluria.world.biome;

import net.genesis.telluria.TelluriaMod;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

public class TelluriaBiomes {
    public static final ResourceKey<Biome> SALT_MARSH = register("salt_marsh");

    private static ResourceKey<Biome> register(String name)
    {
        return ResourceKey.create(ForgeRegistries.Keys.BIOMES, new ResourceLocation(TelluriaMod.MOD_ID, name));
    }
}
