package com.xenon135.sablecreatures.index;

import com.xenon135.sablecreatures.Constants;
import com.xenon135.sablecreatures.content.floater.FloaterBlockEntity;
import com.xenon135.sablecreatures.platform.Services;
import foundry.veil.platform.registry.RegistrationProvider;
import foundry.veil.platform.registry.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class CreaturesBlockEntities {
    public static final RegistrationProvider<BlockEntityType<?>> REGISTER = RegistrationProvider.get(BuiltInRegistries.BLOCK_ENTITY_TYPE, Constants.MOD_ID);

    public static final RegistryObject<BlockEntityType<FloaterBlockEntity>> FLOATER = REGISTER.register("floater", () -> Services.PLATFORM.blockEntityTypeBuilder(
            FloaterBlockEntity::new,
            CreaturesBlocks.FLOATER_BLOCK.get()
    ).build(null));

    public static void init(){}
}
