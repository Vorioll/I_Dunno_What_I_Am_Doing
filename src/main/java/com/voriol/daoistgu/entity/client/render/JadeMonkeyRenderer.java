package com.voriol.daoistgu.entity.client.render;

import com.voriol.daoistgu.DaoistGu;
import com.voriol.daoistgu.entity.client.model.JadeMonkeyModel;
import com.voriol.daoistgu.entity.custom.JadeMonkeyEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class JadeMonkeyRenderer extends MobRenderer<JadeMonkeyEntity, JadeMonkeyModel<JadeMonkeyEntity>> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(DaoistGu.MOD_ID, "textures/entity/jade_monkey.png");

    public JadeMonkeyRenderer(EntityRendererProvider.Context context) {
        super(context, new JadeMonkeyModel<>(context.bakeLayer(JadeMonkeyModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull JadeMonkeyEntity entity) {
        return TEXTURE;
    }
}