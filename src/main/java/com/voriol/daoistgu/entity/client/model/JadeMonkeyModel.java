package com.voriol.daoistgu.entity.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.voriol.daoistgu.DaoistGu;
import com.voriol.daoistgu.entity.custom.JadeMonkeyEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class JadeMonkeyModel<T extends JadeMonkeyEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(DaoistGu.MOD_ID, "jade_monkey"), "main");

    private final ModelPart arml;
    private final ModelPart armr;
    private final ModelPart head;
    private final ModelPart legl;
    private final ModelPart legr;
    private final ModelPart body;
    private final ModelPart tail;

    public JadeMonkeyModel(ModelPart root) {
        this.arml = root.getChild("arml");
        this.armr = root.getChild("armr");
        this.head = root.getChild("head");
        this.legl = root.getChild("legl");
        this.legr = root.getChild("legr");
        this.body = root.getChild("body");
        this.tail = root.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition arml = partdefinition.addOrReplaceChild("arml",
                CubeListBuilder.create()
                        .texOffs(18, 34).addBox(-6.0F, -3.0F, -6.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(44, 0).addBox(-6.0F, -1.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(44, 2).addBox(-4.0F, -1.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(28, 34).addBox(-5.0F, -8.0F, -5.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition armr = partdefinition.addOrReplaceChild("armr",
                CubeListBuilder.create()
                        .texOffs(34, 18).addBox(-15.0F, -3.0F, -6.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(44, 4).addBox(-15.0F, -1.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(6, 44).addBox(-13.0F, -1.0F, -7.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(36, 34).addBox(-15.0F, -8.0F, -5.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(18.0F, 24.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head",
                CubeListBuilder.create()
                        .texOffs(26, 23).addBox(-3.0F, -15.0F, -6.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(18, 32).addBox(-3.0F, -13.0F, -7.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 44).addBox(1.0F, -13.0F, -7.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition legl = partdefinition.addOrReplaceChild("legl",
                CubeListBuilder.create()
                        .texOffs(18, 39).addBox(-4.0F, -4.0F, 4.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 40).addBox(-4.0F, -1.0F, 1.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition legr = partdefinition.addOrReplaceChild("legr",
                CubeListBuilder.create()
                        .texOffs(26, 41).addBox(-4.0F, -1.0F, 1.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(10, 40).addBox(-4.0F, -4.0F, 4.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(6.0F, 24.0F, 0.0F));

        PartDefinition body = partdefinition.addOrReplaceChild("body",
                CubeListBuilder.create()
                        .texOffs(0, 23).addBox(-3.0F, -6.0F, -1.0F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 12).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-6.0F, -10.0F, -7.0F, 12.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition tail = partdefinition.addOrReplaceChild("tail",
                CubeListBuilder.create()
                        .texOffs(0, 32).addBox(-1.0F, -5.0F, 6.0F, 2.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(34, 12).addBox(-1.0F, -6.0F, 11.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(36, 41).addBox(-1.0F, -7.0F, 13.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        arml.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        armr.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        legl.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        legr.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        tail.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}