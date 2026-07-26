package com.xenon135.sablecreatures.content.floater;

import com.mojang.serialization.MapCodec;
import com.xenon135.sablecreatures.CommonClass;
import com.xenon135.sablecreatures.Constants;
import com.xenon135.sablecreatures.index.CreaturesBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FloaterBlock extends BaseEntityBlock implements EntityBlock {
    public FloaterBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(FloaterBlock::new);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FloaterBlockEntity(blockPos, blockState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, CreaturesBlockEntities.FLOATER.get(), FloaterBlockEntity::tick);
    }
}
