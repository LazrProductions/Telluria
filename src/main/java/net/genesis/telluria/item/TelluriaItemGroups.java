package net.genesis.telluria.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class TelluriaItemGroups {

    public static final ItemGroup BLOCKS = FabricItemGroupBuilder.build(new Identifier("telluria", "blocks"), () -> new ItemStack(Blocks.BRICKS.asItem()));
    public static final ItemGroup FLORA = FabricItemGroupBuilder.build(new Identifier("telluria", "flora"), () -> new ItemStack(Items.POPPY));
    public static final ItemGroup FAUNA = FabricItemGroupBuilder.build(new Identifier("telluria", "fauna"), () -> new ItemStack(Items.BEEF));
}
