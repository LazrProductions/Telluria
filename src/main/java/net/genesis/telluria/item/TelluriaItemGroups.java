package net.genesis.telluria.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class TelluriaItemGroups {
    public static final CreativeModeTab BLOCKS = new CreativeModeTab("telluria_blocks") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Blocks.BRICKS.asItem());
        }
    };
    
    public static final CreativeModeTab FLORA = new CreativeModeTab("telluria_flora") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.POPPY);
        }
    };
    
    public static final CreativeModeTab FAUNA = new CreativeModeTab("telluria_fauna") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.BEEF);
        }
    };

}
