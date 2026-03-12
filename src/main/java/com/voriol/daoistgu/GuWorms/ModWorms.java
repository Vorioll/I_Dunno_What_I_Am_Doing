package com.voriol.daoistgu.GuWorms;

import com.voriol.daoistgu.DaoistGu;
import com.voriol.daoistgu.GuWorms.BloodPath.BloodArrowGu;
import com.voriol.daoistgu.GuWorms.GuWormRank;

import com.voriol.daoistgu.GuWorms.PowerPath.PowerStepGu;
import com.voriol.daoistgu.GuWorms.StarPath.LightGu;
import com.voriol.daoistgu.GuWorms.StarPath.LunarLightGu;
import net.minecraft.world.item.Item;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

import static com.voriol.daoistgu.item.ModItems.*;

public class ModWorms {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.createItems(DaoistGu.MOD_ID);


    // ===== BLOOD GU =====

    public static final DeferredHolder<Item, Item> BLOOD_ARROW_GU_RANK_1 =
            ITEMS.register("blood_arrow_gu_rank_1",
                    () -> new BloodArrowGu(
                            new Item.Properties(),
                            GuWormRank.ONE,
                            VINE_2
                    ));


    public static final DeferredHolder<Item, Item> BLOOD_ARROW_GU_RANK_5 =
            ITEMS.register("blood_arrow_gu_rank_5",
                    () -> new BloodArrowGu(
                            new Item.Properties(),
                            GuWormRank.FIVE,
                            VINE_2
                    ));

    public static final DeferredHolder<Item, Item> POWER_STEP_GU_RANK_1 =
            ITEMS.register("power_step_gu_rank_1",
                    () -> new PowerStepGu(
                            new Item.Properties(),
                            GuWormRank.ONE,
                            RAW_BISMUTH
                    ));
    public static final DeferredHolder<Item, Item> POWER_STEP_GU_RANK_5 =
            ITEMS.register("power_step_gu_rank_5",
                    () -> new PowerStepGu(
                            new Item.Properties(),
                            GuWormRank.FIVE,
                            BISMUTH
                    ));
    public static final DeferredHolder<Item, Item> LUNAR_LIGHT_GU_RANK_1 =
            ITEMS.register("lunar_light_gu_rank_1",
                    () -> new LunarLightGu(
                            new Item.Properties(),
                            GuWormRank.ONE,
                            JADE
                    ));
    public static final DeferredHolder<Item, Item> LIGHT_GU_RANK_1 =
            ITEMS.register("light_gu_rank_1",
                    () -> new LightGu(
                            new Item.Properties(),
                            GuWormRank.FIVE,
                            JADE
                    ));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}