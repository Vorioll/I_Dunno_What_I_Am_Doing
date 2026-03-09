package com.voriol.daoistgu.playerdata;

import com.voriol.daoistgu.DaoistGu;
import com.voriol.daoistgu.playerdata.ClientGenesData;
import com.voriol.daoistgu.playerdata.PlayerGenes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

@EventBusSubscriber(modid = DaoistGu.MOD_ID, value = Dist.CLIENT)
public class GeneHudOverlay {

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Pre event) {
        renderGeneBars(event.getGuiGraphics(),
                Minecraft.getInstance().getWindow().getGuiScaledWidth(),
                Minecraft.getInstance().getWindow().getGuiScaledHeight());
    }

    private static void renderGeneBars(GuiGraphics graphics, int screenWidth, int screenHeight) {
        PlayerGenes genes = ClientGenesData.get();
        if (genes == null) return;

        int barWidth = 100;
        int barHeight = 8;
        int spacing = 2;
        int startX = 5;
        int startY = 5;

        record GeneBar(int value, int color) {}
        GeneBar[] bars = {
                new GeneBar(genes.getNormal_genes(), 0xFFFFFFFF),
                new GeneBar(genes.getPrimitive_genes(), 0xFF00FF00),
                new GeneBar(genes.getMutant_genes(), 0xFFFF00FF),
                new GeneBar(genes.getSacred_genes(), 0xFFFFFF00),
                new GeneBar(genes.getSuper_genes(), 0xFFFFA500)
        };

        int y = startY;
        for (GeneBar bar : bars) {
            if (bar.value <= 0) continue; // не рисуем нулевые

            // Фон
            graphics.fill(startX, y, startX + barWidth, y + barHeight, 0x80AAAAAA);
            // Заполнение
            int fillWidth = (int) (barWidth * Math.min(bar.value, 100) / 100.0);
            if (fillWidth > 0) {
                graphics.fill(startX, y, startX + fillWidth, y + barHeight, bar.color);
            }
            y += barHeight + spacing;
        }
    }
}