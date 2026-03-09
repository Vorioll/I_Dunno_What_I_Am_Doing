package com.voriol.daoistgu.item;

import com.voriol.daoistgu.DaoistGu;
import com.voriol.daoistgu.entity.ModEntities;
import com.voriol.daoistgu.item.custom.ChiselItem;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
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
            () -> new Primal_Stone(new Item.Properties(), 1, "heaven"));
    public static final DeferredItem<Item> BASE_COIN = ITEMS.register("base_coin",
            () -> new DaoIngredients(new Item.Properties(), 1, "ore"));
    public static final DeferredItem<Item> JADE_COIN = ITEMS.register("jade_coin",
            () -> new DaoIngredients(new Item.Properties(), 2, "ore"));

    public static final DeferredItem<Item> VINE_255 = ITEMS.register("vine",
            () -> new Vine(new Item.Properties(), 255, 5, "alcohol"));

    public static final DeferredItem<Item> VINE_2 = ITEMS.register("vine2",
            () -> new Vine(new Item.Properties(), 2, 2, "alcohol"));
    public static final DeferredItem<Item> JADE_MONKEY_SPAWN_EGG = ITEMS.register("jade_monkey_spawn_egg",
            () -> new SpawnEggItem(ModEntities.JADE_MONKEY.get(), 0xb1b1b1, 0x57b832, new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
