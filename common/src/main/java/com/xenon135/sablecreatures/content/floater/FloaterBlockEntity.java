package com.xenon135.sablecreatures.content.floater;

import com.xenon135.sablecreatures.CommonClass;
import com.xenon135.sablecreatures.Constants;
import com.xenon135.sablecreatures.index.CreaturesBlockEntities;
import dev.ryanhcode.sable.api.block.BlockEntitySubLevelActor;
import dev.ryanhcode.sable.api.physics.force.ForceGroups;
import dev.ryanhcode.sable.api.physics.force.QueuedForceGroup;
import dev.ryanhcode.sable.api.physics.handle.RigidBodyHandle;
import dev.ryanhcode.sable.companion.math.JOMLConversion;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3d;
import org.lwjgl.opengl.INTELConservativeRasterization;

public class FloaterBlockEntity extends BlockEntity implements BlockEntitySubLevelActor {




    public FloaterBlockEntity(BlockPos pos, BlockState state){
        super(CreaturesBlockEntities.FLOATER.get(), pos, state);
    }

    @Override
    public void sable$physicsTick(ServerSubLevel subLevel, RigidBodyHandle handle, double timeStep) {
        final QueuedForceGroup forceGroup = subLevel.getOrCreateQueuedForceGroup(ForceGroups.PROPULSION.get());
        Vector3d thrustPos = JOMLConversion.toJOML(this.getBlockPos().getCenter());
        forceGroup.applyAndRecordPointForce(thrustPos, new Vector3d(0, 1, 0));
    }
}
