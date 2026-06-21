package com.xenon135.sablecreatures.index;

import com.xenon135.sablecreatures.Constants;
import com.xenon135.sablecreatures.content.floater.FloaterBlock;
import foundry.veil.platform.registry.RegistrationProvider;
import foundry.veil.platform.registry.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class CreaturesBlocks {

    public static final RegistrationProvider<Block> REGISTER = RegistrationProvider.get(BuiltInRegistries.BLOCK, Constants.MOD_ID);
    public static final RegistrationProvider<Item> REGISTER_ITEMS = RegistrationProvider.get(BuiltInRegistries.ITEM, Constants.MOD_ID);

    public static final RegistryObject<Block> FLOATY_BLOCK = register("floaty_block", () -> new Block(BlockBehaviour.Properties.of()), true, properties -> properties);
    public static final RegistryObject<Block> FLOATER_BLOCK = register("floater", () -> new FloaterBlock(BlockBehaviour.Properties.of()), true, properties -> properties);

    public static RegistryObject<Block> register(String id, Supplier<Block> supplier, boolean blockItem, UnaryOperator<Item.Properties> properties){
        RegistryObject<Block> block = REGISTER.register(id, supplier);
        if (blockItem) {
            REGISTER_ITEMS.register(id, () -> new BlockItem(block.get(), properties.apply(new Item.Properties())));
        }
        return block;
    }

    public static void init(){}


}
