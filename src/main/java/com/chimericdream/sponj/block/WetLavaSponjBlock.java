package com.chimericdream.sponj.block;

import com.chimericdream.sponj.ModInfo;
import com.chimericdream.sponj.SponjMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class WetLavaSponjBlock extends Block {
    private final Identifier BLOCK_ID = new Identifier(ModInfo.MOD_ID, "wet_lava_sponj");

    public WetLavaSponjBlock() {
        super(FabricBlockSettings.copyOf(Blocks.SPONGE));
    }

    public void register() {
        Registry.register(Registries.BLOCK, BLOCK_ID, this);
        Registry.register(Registries.ITEM, BLOCK_ID, new BlockItem(this, new FabricItemSettings().recipeRemainder(SponjMod.LAVA_SPONJ.asItem())));

        // Wet lava sponjes can smelt 128 items!
        FuelRegistry.INSTANCE.add(this, 25600);
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!world.getDimension().bedWorks() && !world.getDimension().ultrawarm()) {
            world.setBlockState(pos, SponjMod.LAVA_SPONJ.getDefaultState(), 3);
            world.syncWorldEvent(2009, pos, 0);
            world.playSound((PlayerEntity) null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, (1.0F + world.getRandom().nextFloat() * 0.2F) * 0.7F);
        }

    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        Direction direction = Direction.random(random);
        if (direction != Direction.UP) {
            BlockPos blockPos = pos.offset(direction);
            BlockState blockState = world.getBlockState(blockPos);
            if (!state.isOpaque() || !blockState.isSideSolidFullSquare(world, blockPos, direction.getOpposite())) {
                double d = (double)pos.getX();
                double e = (double)pos.getY();
                double f = (double)pos.getZ();
                if (direction == Direction.DOWN) {
                    e -= 0.05;
                    d += random.nextDouble();
                    f += random.nextDouble();
                } else {
                    e += random.nextDouble() * 0.8;
                    if (direction.getAxis() == Direction.Axis.X) {
                        f += random.nextDouble();
                        if (direction == Direction.EAST) {
                            ++d;
                        } else {
                            d += 0.05;
                        }
                    } else {
                        d += random.nextDouble();
                        if (direction == Direction.SOUTH) {
                            ++f;
                        } else {
                            f += 0.05;
                        }
                    }
                }

                world.addParticle(ParticleTypes.DRIPPING_LAVA, d, e, f, 0.0, 0.0, 0.0);
            }
        }
    }
}
