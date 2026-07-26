package com.xenon135.sablecreatures.content.floater;

import com.xenon135.sablecreatures.index.CreaturesBlockEntities;
import dev.ryanhcode.sable.api.block.BlockEntitySubLevelActor;
import dev.ryanhcode.sable.api.physics.force.ForceGroups;
import dev.ryanhcode.sable.api.physics.force.QueuedForceGroup;
import dev.ryanhcode.sable.api.physics.handle.RigidBodyHandle;
import dev.ryanhcode.sable.companion.math.JOMLConversion;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class FloaterBlockEntity extends BlockEntity implements BlockEntitySubLevelActor {

    Vector3d targetPos = new Vector3d(0,20,0);
    Vector3d localPos = new Vector3d();
    Vector3d globalPos = new Vector3d();
    Vector3d force = new Vector3d();
    double P = 0.5;
    BlockPos targetBlock = null;


    public FloaterBlockEntity(BlockPos pos, BlockState state){
        super(CreaturesBlockEntities.FLOATER.get(), pos, state);
    }

    @Override
    public void sable$physicsTick(ServerSubLevel subLevel, RigidBodyHandle handle, double timeStep) {
        final QueuedForceGroup forceGroup = subLevel.getOrCreateQueuedForceGroup(ForceGroups.PROPULSION.get());
        JOMLConversion.atLowerCornerOf(this.getBlockPos(), localPos);
        subLevel.logicalPose().transformPosition(localPos, globalPos);
        if (targetBlock == null) { return; }
        targetPos.sub(globalPos, force).mul(P);
        clampVectorLength(force, 0, 1);
        forceGroup.applyAndRecordPointForce(localPos, subLevel.logicalPose().transformNormalInverse(force));
    }

    public void clampVectorLength(Vector3d vector, double lengthMin, double lengthMax){
        double clampedLength = Math.clamp(vector.length(), lengthMin, lengthMax);
        vector.div(vector.length()).mul(clampedLength);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, FloaterBlockEntity blockEntity){
        if (level instanceof ClientLevel) return;
        blockEntity.tickBE(level);
    }

    private void tickBE(Level level){
        BlockPos pos = new BlockPos((int) globalPos.x, (int) globalPos.y, (int) globalPos.z);
        targetBlock = findNearestBlockInstance(pos, 10, Blocks.BAMBOO, level);
        if (targetBlock != null){
            JOMLConversion.atCenterOf(targetBlock, targetPos);
        }
    }

    @Nullable
    //if null, no instance found
    private BlockPos findNearestBlockInstance(BlockPos pos, int cubeSize, Block block, Level level){
        BlockPos corner1 = pos.offset(cubeSize, cubeSize, cubeSize);
        BlockPos corner2 = pos.offset(-cubeSize,-cubeSize,-cubeSize);
        double distance = cubeSize + 1;
        BlockPos.MutableBlockPos closestPos = null;
        Iterable<BlockPos> positions = BlockPos.betweenClosed(corner1, corner2);
        for (BlockPos posToCheck : positions){
            if (level.getBlockState(posToCheck).getBlock() == block) {
                double distToCheck = JOMLConversion.atLowerCornerOf(posToCheck.subtract(pos)).length();
                if (distance > distToCheck) {
                    distance = distToCheck;
                    if (closestPos == null) closestPos = new BlockPos.MutableBlockPos();
                    closestPos.set(posToCheck);
                }
            }
        }
        return closestPos;
    }
}
