package com.yuusyaasisutanto.bringme115.content.blocks.blockentity.primitive_machine;

import com.yuusyaasisutanto.bringme115.content.screen.primitivemachine.BM115PrimitivePaPMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BM115PrimitiveMachineBlock extends Block {
    private static final Component CONTAINER_TITLE = Component.translatable("container.crafting");
    public static final VoxelShape SHAPE = Block.box(0,0,0,16,12,16);

    public BM115PrimitiveMachineBlock(Properties p_49224_) {
        super(p_49224_);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState state  , Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide){
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(level, blockPos));
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos blockPos) {
        return new SimpleMenuProvider((num, inv, player) ->
            {return new BM115PrimitivePaPMenu(num, inv, ContainerLevelAccess.create(level, blockPos));}, CONTAINER_TITLE);
    }

    //    @Override
//    public @Nullable BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
//        return null;
//    }
}
