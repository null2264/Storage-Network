package com.null2264.storagenetwork.block.cable;

import com.null2264.storagenetwork.blockentity.cable.CableLinkBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class CableLinkBlock extends CableBlock
{
    public CableLinkBlock() {
        super();
    }

    public BlockEntity createBlockEntity(BlockView world) {
        return new CableLinkBlockEntity();
    }
}
