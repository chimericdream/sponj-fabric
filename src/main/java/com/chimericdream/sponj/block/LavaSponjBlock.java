package com.chimericdream.sponj.block;

import com.chimericdream.sponj.ModInfo;
import com.chimericdream.sponj.SponjMod;
import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Queue;

public class LavaSponjBlock extends Block {
    private final Identifier BLOCK_ID = new Identifier(ModInfo.MOD_ID, "lava_sponj");

    public LavaSponjBlock() {
        super(FabricBlockSettings.copyOf(Blocks.SPONGE));
    }

    public void register() {
        Registry.register(Registry.BLOCK, BLOCK_ID, this);
        Registry.register(Registry.ITEM, BLOCK_ID, new BlockItem(this, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.isOf(state.getBlock())) {
            this.update(world, pos);
        }
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        this.update(world, pos);
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }

    protected void update(World world, BlockPos pos) {
        if (this.absorbLava(world, pos)) {
            world.setBlockState(pos, SponjMod.WET_LAVA_SPONJ.getDefaultState(), 2);
            world.syncWorldEvent(2001, pos, Block.getRawIdFromState(Blocks.WATER.getDefaultState()));
        }

    }

    private boolean absorbLava(World world, BlockPos pos) {
        Queue<Pair<BlockPos, Integer>> queue = Lists.newLinkedList();
        queue.add(new Pair(pos, 0));
        int i = 0;

        while(!queue.isEmpty()) {
            Pair<BlockPos, Integer> pair = (Pair) queue.poll();
            BlockPos blockPos = (BlockPos)pair.getLeft();
            int j = (Integer)pair.getRight();
            Direction[] var8 = Direction.values();
            int var9 = var8.length;

            for (int var10 = 0; var10 < var9; ++var10) {
                Direction direction = var8[var10];
                BlockPos blockPos2 = blockPos.offset(direction);
                BlockState blockState = world.getBlockState(blockPos2);
                FluidState fluidState = world.getFluidState(blockPos2);
                if (fluidState.isIn(FluidTags.LAVA)) {
                    if (blockState.getBlock() instanceof FluidDrainable && !((FluidDrainable)blockState.getBlock()).tryDrainFluid(world, blockPos2, blockState).isEmpty()) {
                        ++i;
                        if (j < 6) {
                            queue.add(new Pair(blockPos2, j + 1));
                        }
                    } else if (blockState.getBlock() instanceof FluidBlock) {
                        world.setBlockState(blockPos2, Blocks.AIR.getDefaultState(), 3);
                        ++i;
                        if (j < 6) {
                            queue.add(new Pair(blockPos2, j + 1));
                        }
                    }
                }
            }

            if (i > 64) {
                break;
            }
        }

        return i > 0;
    }
}
