package com.voriol.daoistgu.GuWorms.StarPath;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class LightGuLightHandler {

    /**
     * Вызывается на клиенте для определения уровня света от активного LightGu в инвентаре.
     * @return 15, если есть активный LightGu, иначе 0.
     */
    public static int getPlayerLight(Player player) {
        for (ItemStack stack : player.getInventory().items) {
            if (!(stack.getItem() instanceof LightGu)) continue;
            if (LightGu.isLightActive(stack)) {
                return 15;
            }
        }
        return 0;
    }

    /**
     * Вызывается на клиенте для определения уровня света от LightGu в руках (всегда, без активации).
     * @return 12, если предмет в основной или дополнительной руке, иначе 0.
     */
    public static int getHandLight(Player player) {
        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();
        if (main.getItem() instanceof LightGu) return 12;
        if (off.getItem() instanceof LightGu) return 12;
        return 0;
    }
}