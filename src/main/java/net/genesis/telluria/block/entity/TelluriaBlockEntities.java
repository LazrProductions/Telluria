package net.genesis.telluria.block.entity;

import net.genesis.telluria.TelluriaMod;
import net.genesis.telluria.block.TelluriaBlocks;
import net.genesis.telluria.block.entity.custom.TelluriaSignBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TelluriaBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(ForgeRegistries.BLOCK_ENTITIES, TelluriaMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<TelluriaSignBlockEntity>> SIGN_BLOCK_ENTITIES = BLOCK_ENTITIES
            .register("sign_block_entity", () -> BlockEntityType.Builder.of(TelluriaSignBlockEntity::new, null).build(null));
    //                TelluriaBlocks.WILLOW_WALL_SIGN.get(),            //The wall sign block
    //                TelluriaBlocks.WILLOW_SIGN.get()   //The standing sign block
    //  To register sign Block entities add the Sign blocks here

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
