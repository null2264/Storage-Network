package com.null2264.storagenetwork.screen;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.collection.DefaultedList;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NetworkCraftingInventory extends CraftingInventory
{
    private final DefaultedList<ItemStack> itemList;
    private final ScreenHandler handler;
    public boolean skipEvents;

    public NetworkCraftingInventory(ScreenHandler handler) {
        super(handler, 3, 3);
        this.handler = handler;
        this.itemList = DefaultedList.ofSize(3 * 3, ItemStack.EMPTY);
    }

    public NetworkCraftingInventory(ScreenHandler handler, Map<Integer, ItemStack> map) {
        this(handler);
        skipEvents = true;
        for (int i = 0; i < 9; i++)
            if (map.get(i) != null && !map.get(i).isEmpty())
                setStack(i, map.get(i));
        skipEvents = false;
    }
    
    public NetworkCraftingInventory(ScreenHandler handler, List<ItemStack> list) {
        this(handler);
        skipEvents = true;
        for (int i = 0; i < 9; i++)
            if (list.get(i) != null && !list.get(i).isEmpty())
                setStack(i, list.get(i));
        skipEvents = false;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        itemList.set(slot, stack);
        if (!skipEvents)
            handler.onContentChanged(this);
    }

    @Override
    public int size() {
        return itemList.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack item : itemList)
            if (!item.isEmpty())
                return false;
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return slot >= this.size() ? ItemStack.EMPTY : (ItemStack) this.itemList.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.itemList, slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = Inventories.splitStack(this.itemList, slot, amount);
        if (!itemStack.isEmpty())
            this.handler.onContentChanged(this);
        return itemStack;
    }

    @Override
    public void clear() {
        this.itemList.clear();
    }

    @Override
    public void provideRecipeInputs(RecipeMatcher finder) {
        for (ItemStack itemStack : this.itemList) {
            finder.addUnenchantedInput(itemStack);
        }
    }
}
