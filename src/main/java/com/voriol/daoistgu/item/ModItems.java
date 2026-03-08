package com.voriol.daoistgu.item;

import com.voriol.daoistgu.DaoistGu;
import com.voriol.daoistgu.item.custom.ChiselItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DaoistGu.MOD_ID);
    public static final DeferredItem<Item> CHISEL = ITEMS.register("chisel",
            () -> new ChiselItem(new Item.Properties().durability(32)));


    public static final DeferredItem<Item> BISMUTH = ITEMS.register("bismuth",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_BISMUTH = ITEMS.register("raw_bismuth",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> JADE = ITEMS.register("jade",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PRIMORDIAL_STONE = ITEMS.register("primordial_stone",
            () -> new Primal_Stone(new Item.Properties()));
    public static final DeferredItem<Item> BASE_COIN = ITEMS.register("base_coin",
            () -> new Primal_Stone(new Item.Properties()));
    public static final DeferredItem<Item> JADE_COIN = ITEMS.register("jade_coin",
            () -> new Primal_Stone(new Item.Properties()));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
