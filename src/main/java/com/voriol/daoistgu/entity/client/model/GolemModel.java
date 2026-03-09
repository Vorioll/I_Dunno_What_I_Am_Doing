package com.voriol.daoistgu.entity.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class GolemModel<T extends Entity> extends EntityModel<T> {

	public static final ModelLayerLocation LAYER_LOCATION =
			new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("daoistgu","golem"),"main");

	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart rightArm;
	private final ModelPart leftArm;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;

	public GolemModel(ModelPart root) {
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.rightArm = root.getChild("right_arm");
		this.leftArm = root.getChild("left_arm");
		this.rightLeg = root.getChild("right_leg");
		this.leftLeg = root.getChild("left_leg");
	}

	public static LayerDefinition createBodyLayer() {

		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		root.addOrReplaceChild("head",
				CubeListBuilder.create()
						.texOffs(0,0)
						.addBox(-4F,-8F,-4F,8,8,8),
				PartPose.offset(0F,4F,0F));

		root.addOrReplaceChild("body",
				CubeListBuilder.create()
						.texOffs(0,16)
						.addBox(-5F,0F,-3F,10,12,6),
				PartPose.offset(0F,4F,0F));

		root.addOrReplaceChild("right_arm",
				CubeListBuilder.create()
						.texOffs(32,0)
						.addBox(-3F,-2F,-2F,4,16,4),
				PartPose.offset(-6F,6F,0F));

		root.addOrReplaceChild("left_arm",
				CubeListBuilder.create()
						.texOffs(32,0)
						.mirror()
						.addBox(-1F,-2F,-2F,4,16,4),
				PartPose.offset(6F,6F,0F));

		root.addOrReplaceChild("right_leg",
				CubeListBuilder.create()
						.texOffs(0,34)
						.addBox(-2F,0F,-2F,4,12,4),
				PartPose.offset(-2F,16F,0F));

		root.addOrReplaceChild("left_leg",
				CubeListBuilder.create()
						.texOffs(0,34)
						.mirror()
						.addBox(-2F,0F,-2F,4,12,4),
				PartPose.offset(2F,16F,0F));

		return LayerDefinition.create(mesh,64,64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

		head.yRot = netHeadYaw * 0.017453292F;
		head.xRot = headPitch * 0.017453292F;

		rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + Mth.PI) * 1.4F * limbSwingAmount;

		rightArm.xRot = leftLeg.xRot;
		leftArm.xRot = rightLeg.xRot;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer,
							   int packedLight, int packedOverlay, int color) {

		head.render(poseStack, buffer, packedLight, packedOverlay);
		body.render(poseStack, buffer, packedLight, packedOverlay);
		rightArm.render(poseStack, buffer, packedLight, packedOverlay);
		leftArm.render(poseStack, buffer, packedLight, packedOverlay);
		rightLeg.render(poseStack, buffer, packedLight, packedOverlay);
		leftLeg.render(poseStack, buffer, packedLight, packedOverlay);
	}
}