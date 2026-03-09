// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class golem<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "golem"), "main");
	private final ModelPart bone;
	private final ModelPart bone2;
	private final ModelPart bone3;
	private final ModelPart bone4;
	private final ModelPart bone5;
	private final ModelPart bone6;

	public golem(ModelPart root) {
		this.bone = root.getChild("bone");
		this.bone2 = root.getChild("bone2");
		this.bone3 = root.getChild("bone3");
		this.bone4 = root.getChild("bone4");
		this.bone5 = root.getChild("bone5");
		this.bone6 = root.getChild("bone6");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(24, 17).addBox(-6.0F, -11.0F, -3.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 5.0F));

		PartDefinition bone2 = partdefinition.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(32, 0).addBox(-6.0F, -11.0F, -3.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, 5.0F));

		PartDefinition bone3 = partdefinition.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = bone3.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -13.0F, -3.0F, 12.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -11.0F, 5.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition bone4 = partdefinition.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r2 = bone4.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 17).addBox(-2.0F, -18.0F, -2.0F, 3.0F, 18.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -2.0F, -6.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition bone5 = partdefinition.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offset(-11.0F, 24.0F, 0.0F));

		PartDefinition cube_r3 = bone5.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(12, 17).addBox(-2.0F, -18.0F, -2.0F, 3.0F, 18.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -2.0F, -6.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition bone6 = partdefinition.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r4 = bone6.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(24, 32).addBox(-3.0F, -4.0F, -1.0F, 6.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -20.0F, -6.0F, 0.2618F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone5.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone6.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}