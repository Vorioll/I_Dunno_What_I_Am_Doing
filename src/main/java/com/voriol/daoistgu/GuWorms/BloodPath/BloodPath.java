package com.voriol.daoistgu.GuWorms.BloodPath;

import com.voriol.daoistgu.GuWorms.GuWormItem;
import com.voriol.daoistgu.GuWorms.GuWormPath;
import com.voriol.daoistgu.GuWorms.GuWormRank;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public abstract class BloodPath extends GuWormItem {

    public BloodPath(Properties properties, GuWormRank rank, GuWormPath path, Supplier<Item> foodItem, int hpCost) {
        super(properties, rank, path, foodItem);
    }

    protected double getDamageByRank() {
        int r = rank.getLevel();
        return 5 + r * 2;
    }

}
