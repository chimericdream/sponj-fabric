package com.chimericdream.sponj.block;

import com.chimericdream.sponj.BlockUtils;
import com.chimericdream.sponj.ModInfo;
import com.chimericdream.sponj.SponjMod;
import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class LavaSponjBlock extends Block {
    private final Identifier BLOCK_ID = new Identifier(ModInfo.MOD_ID, "lava_sponj");
    private List<Block> LAVA_SPONJ_BLOCKS = new ArrayList<>();

    public LavaSponjBlock() {
        super(FabricBlockSettings.copyOf(Blocks.SPONGE));
    }

    public void register() {
        LAVA_SPONJ_BLOCKS = new ArrayList<>(List.of(SponjMod.LAVA_SPONJ, SponjMod.WET_LAVA_SPONJ));

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
            world.syncWorldEvent(2001, pos, Block.getRawIdFromState(Blocks.LAVA.getDefaultState()));
        }

    }

    private boolean absorbLava(World world, BlockPos pos) {
        List<BlockPos> sponjes = BlockUtils.getConnectedBlocksByType(world, pos, LAVA_SPONJ_BLOCKS, 32);
        int sponjCount = sponjes.size();

        int absorptionRadius = 6 + (3 * (sponjCount - 1));
        int maxAbsorption = 64 * sponjCount;

        Queue<Pair<BlockPos, Integer>> queue = Lists.newLinkedList();
        queue.add(new Pair<>(pos, 0));
        int i = 0;

        while(!queue.isEmpty()) {
            Pair<BlockPos, Integer> pair = queue.poll();
            BlockPos blockPos = pair.getLeft();
            int j = pair.getRight();

            Direction[] directions = Direction.values();
            int dirLength = directions.length;

            for (int idx = 0; idx < dirLength; ++idx) {
                Direction direction = directions[idx];
                BlockPos blockPos2 = blockPos.offset(direction);
                BlockState blockState = world.getBlockState(blockPos2);
                FluidState fluidState = world.getFluidState(blockPos2);

                if (fluidState.isIn(FluidTags.LAVA)) {
                    if (blockState.getBlock() instanceof FluidDrainable && !((FluidDrainable)blockState.getBlock()).tryDrainFluid(world, blockPos2, blockState).isEmpty()) {
                        ++i;
                        if (j < absorptionRadius) {
                            queue.add(new Pair<>(blockPos2, j + 1));
                        }
                    } else if (blockState.getBlock() instanceof FluidBlock) {
                        world.setBlockState(blockPos2, Blocks.AIR.getDefaultState(), 3);
                        ++i;
                        if (j < absorptionRadius) {
                            queue.add(new Pair<>(blockPos2, j + 1));
                        }
                    }
                }
            }

            if (i > maxAbsorption) {
                break;
            }
        }

        if (i > 0) {
            for (BlockPos sponjPos : sponjes) {
                if (world.getBlockState(sponjPos).getBlock().equals(SponjMod.LAVA_SPONJ)) {
                    world.setBlockState(sponjPos, SponjMod.WET_LAVA_SPONJ.getDefaultState(), 2);
                    world.syncWorldEvent(2001, sponjPos, Block.getRawIdFromState(Blocks.LAVA.getDefaultState()));
                }
            }
        }

        return i > 0;
    }
}
