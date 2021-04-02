package com.null2264.storagenetwork.block.cables;

import com.null2264.storagenetwork.blockentity.cables.CableLinkBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class CableLinkBlock extends CableBlock
{
    // TODO: Move inventory stuff from masterBlock to here
    public CableLinkBlock() {
        super();
    }

    public BlockEntity createBlockEntity(BlockView world) {
        return new CableLinkBlockEntity();
    }
}
