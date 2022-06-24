package net.genesis.telluria.client.color.block;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.genesis.telluria.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.state.property.Property;
import net.minecraft.util.collection.IdList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ModBlockColors {
    private static final int NO_COLOR = -1;
    private final IdList<BlockColorProvider> providers = new IdList(32);
    private final Map<Block, Set<Property<?>>> properties = Maps.newHashMap();

    public static ModBlockColors create() {
        ModBlockColors blockColors = new ModBlockColors();

        blockColors.registerColorProvider((state, world, pos, tintIndex) -> {
            return world != null && pos != null ? BiomeColors.getGrassColor(world, pos)
                    : GrassColors.getColor(0.5D, 1.0D);
        }, ModBlocks.BULRUSH);

        return blockColors;
    }

    public int getParticleColor(BlockState state, World world, BlockPos pos) {
        BlockColorProvider blockColorProvider = (BlockColorProvider) this.providers
                .get(Registry.BLOCK.getRawId(state.getBlock()));
        if (blockColorProvider != null) {
            return blockColorProvider.getColor(state, (BlockRenderView) null, (BlockPos) null, 0);
        } else {
            MapColor mapColor = state.getMapColor(world, pos);
            return mapColor != null ? mapColor.color : -1;
        }
    }

    public int getColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex) {
        BlockColorProvider blockColorProvider = (BlockColorProvider) this.providers
                .get(Registry.BLOCK.getRawId(state.getBlock()));
        return blockColorProvider == null ? -1 : blockColorProvider.getColor(state, world, pos, tintIndex);
    }

    public void registerColorProvider(BlockColorProvider provider, Block... blocks) {
        Block[] var3 = blocks;
        int var4 = blocks.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            Block block = var3[var5];
            this.providers.set(provider, Registry.BLOCK.getRawId(block));
        }

    }

    private void registerColorProperties(Set<Property<?>> properties, Block... blocks) {
        Block[] var3 = blocks;
        int var4 = blocks.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            Block block = var3[var5];
            this.properties.put(block, properties);
        }

    }

    private void registerColorProperty(Property<?> property, Block... blocks) {
        this.registerColorProperties(ImmutableSet.of(property), blocks);
    }

    public Set<Property<?>> getProperties(Block block) {
        return (Set) this.properties.getOrDefault(block, ImmutableSet.of());
    }
}
