package com.chimericdream.sponj;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockUtils {
	public static List<BlockPos> getAdjacentBlockPositions(BlockPos pos, boolean down) {
		List<BlockPos> around = new ArrayList<>();

		around.add(pos.north());
		around.add(pos.east());
		around.add(pos.south());
		around.add(pos.west());
		around.add(pos.up());

		if (down) {
			around.add(pos.down());
		}

		return around;
	}

	public static List<BlockPos> getConnectedBlocksByType(World world, BlockPos start, List<Block> blockTypes, int maxDistance) {
		List<BlockPos> checkedBlocks = new ArrayList<>();
		List<BlockPos> blocks = new ArrayList<>();

		if (blockTypes.contains(world.getBlockState(start).getBlock())) {
			blocks.add(start);
			checkedBlocks.add(start);
		}

		return getConnectedBlocksByType(world, start, start, blockTypes, blocks, checkedBlocks, maxDistance);
	}

	private static List<BlockPos> getConnectedBlocksByType(World world, BlockPos start, BlockPos pos, List<Block> blockTypes, List<BlockPos> startingBlocks, List<BlockPos> checkedBlocks, int maxDistance) {
		List<BlockPos> adjacentBlocks = getAdjacentBlockPositions(pos, true);
		List<BlockPos> blocks = new ArrayList<>(startingBlocks);

		for (BlockPos blockPos : adjacentBlocks) {
			if (checkedBlocks.contains(blockPos)) {
				continue;
			}

			checkedBlocks.add(blockPos);

			if (blockTypes.contains(world.getBlockState(blockPos).getBlock())) {
				if (!blocks.contains(blockPos)) {
					blocks.add(blockPos);

					if (isWithinDistance(start, blockPos, maxDistance)) {
						blocks = getConnectedBlocksByType(world, start, blockPos, blockTypes, blocks, checkedBlocks, maxDistance);
					}
				}
			}
		}

		return blocks;
	}

	public static Boolean isWithinDistance(BlockPos start, BlockPos end, int distance) {
		return isWithinDistance(start, end, (double) distance);
	}

	public static Boolean isWithinDistance(BlockPos start, BlockPos end, double distance) {
		return start.getSquaredDistance(end) <= distance;
	}
}
