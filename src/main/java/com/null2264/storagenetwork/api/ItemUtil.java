package com.null2264.storagenetwork.api;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Objects;

public class ItemUtil
{
    public static boolean canMerge(ItemStack stack1, ItemStack stack2) {
        if (!stack1.isItemEqual(stack2) || stack1.isEmpty() || stack1.hasTag() != stack2.hasTag())
            return false;
        return !stack2.hasTag() || Objects.equals(stack2.getTag(), stack1.getTag());
    }

    public static void merge(ItemStack from, ArrayList<ItemStack> to) {
        boolean added = false;
        for (ItemStack item : to) {
            if (canMerge(from, item)) {
                item.setCount(item.getCount() + from.getCount());
                added = true;
                break;
            }
        }
        if (!added)
            to.add(from);
    }
}
