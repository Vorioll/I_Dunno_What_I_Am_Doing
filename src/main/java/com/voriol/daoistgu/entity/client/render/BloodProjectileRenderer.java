package com.voriol.daoistgu.entity.client.render;

import com.voriol.daoistgu.entity.custom.BloodProjectileEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BloodProjectileRenderer extends EntityRenderer<BloodProjectileEntity> {

    public BloodProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(BloodProjectileEntity entity) {
        return null;
    }
}