package net.yuhi.better_progression.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DaggerModel extends Model {
    // Tekstura używana przez model
    public static final ResourceLocation TEXTURE = new ResourceLocation("yourmodid", "textures/entity/dagger.png");
    private final ModelPart root;

    public DaggerModel(ModelPart pRoot) {
        super(RenderType::entitySolid);
        this.root = pRoot;
    }

    // Definiowanie warstw modelu daggera
    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        // Definiowanie części daggera
        PartDefinition blade = partdefinition.addOrReplaceChild("blade", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-0.5F, 0.0F, -0.1F, 1.0F, 8.0F, 0.2F), PartPose.ZERO);

        // Dodanie dodatkowych części, np. uchwytu
        blade.addOrReplaceChild("handle", CubeListBuilder.create()
                .texOffs(4, 0).addBox(-0.2F, 8.0F, -0.2F, 0.4F, 1.0F, 0.4F), PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    // Renderowanie modelu
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        this.root.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
    }
}