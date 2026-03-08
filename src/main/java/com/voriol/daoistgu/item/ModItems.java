package com.voriol.daoistgu.item;

import com.voriol.daoistgu.DaoistGu;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DaoistGu.MOD_ID);



    public static final DeferredItem<Item> BISMUTH = ITEMS.register("bismuth",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_BISMUTH = ITEMS.register("raw_bismuth",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PRIMORDIAL_STONE = ITEMS.register("primordial_stone",
            () -> new Primal_Stone(new Item.Properties()));

    public static final DeferredItem<Item> VINE_255 = ITEMS.register("vine",
            () -> new Vine(new Item.Properties(), 255));

    public static final DeferredItem<Item> VINE_2 = ITEMS.register("vine2",
            () -> new Vine(new Item.Properties(), 2));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
