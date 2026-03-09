package com.voriol.daoistgu.entity.client.render;

import com.voriol.daoistgu.DaoistGu;
import com.voriol.daoistgu.entity.client.model.GolemModel;
import com.voriol.daoistgu.entity.custom.GolemEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GolemRenderer extends MobRenderer<GolemEntity, GolemModel<GolemEntity>> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(DaoistGu.MOD_ID, "textures/entity/golem.png");

    public GolemRenderer(EntityRendererProvider.Context context) {
        super(context, new GolemModel<>(context.bakeLayer(GolemModel.LAYER_LOCATION)), 0.75f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GolemEntity entity) {
        return TEXTURE;
    }
}