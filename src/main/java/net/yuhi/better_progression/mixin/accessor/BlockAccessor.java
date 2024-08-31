package net.yuhi.better_progression.mixin.accessor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Blocks.class)
public interface BlockAccessor {
    @Invoker("log")
    static RotatedPillarBlock log(MaterialColor pTopColor, MaterialColor pBarkColor){
        return new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, (p_152624_) -> {
            return p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? pTopColor : pBarkColor;
        }).strength(2.0F).sound(SoundType.WOOD));
    }
    
    @Invoker("ocelotOrParrot")
    static Boolean ocelotOrParrot(BlockState p_50822_, BlockGetter p_50823_, BlockPos p_50824_, EntityType<?> p_50825_) {
        return false;
    }

    @Invoker("never")
    static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }
    
    @Invoker("leaves")
    static LeavesBlock leaves(SoundType pType) {
        return new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(pType).noOcclusion().isValidSpawn(BlockAccessor::ocelotOrParrot).isSuffocating(BlockAccessor::never).isViewBlocking(BlockAccessor::never)){
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return 20;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return 60;
            }
        };
    }
    
    @Invoker("woodenButton")
    static ButtonBlock woodenButton(BlockSetType blockSetType) {
        return new ButtonBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5F), blockSetType, 30, true);
    }
}
