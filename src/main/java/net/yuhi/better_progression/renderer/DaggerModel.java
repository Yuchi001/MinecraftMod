package net.yuhi.better_progression.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.TridentModel;
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
import net.yuhi.better_progression.BetterProgression;

@OnlyIn(Dist.CLIENT)
public class DaggerModel extends Model {
    // Tekstura używana przez model
    public static final ResourceLocation TEXTURE = new ResourceLocation(BetterProgression.MOD_ID, "textures/entity/dagger.png");
    private final ModelPart root;

    public DaggerModel(ModelPart pRoot) {
        super(RenderType::entitySolid);
        this.root = pRoot;
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        // Size of the image
        int width = 16;
        int height = 16;
        float voxelSize = 1.0F / width; // Size of each voxel

        // Binary data from the provided image
        String[] binaryData = new String[] {
                "0000000000000000",
                "0000000000000000",
                "0000000000000000",
                "0000000000000000",
                "0000000000000000",
                "0000000000000000",
                "0000000000000000",
                "0000000000000000",
                "0000000000000000",
                "0000000000000000",
                "0000000000000000",
                "0000000000000000",
                "0000000000001111",
                "0000000000001111",
                "0000000000001111",
                "0000000000011111",
                "0000000000011111",
                "0000000001111111",
                "0000000001111111",
                "0000000001111111",
                "0000000111111111",
                "0000000111111111",
                "0000001111111111",
                "0000001111111100",
                "0000001111111100",
                "0000001111111100",
                "0000001111111000",
                "0000001111111000",
                "0000001111111000",
                "0000011111110000",
                "0000011111110000",
                "0000011111110000",
                "0000111111000000",
                "0000111111000000",
                "0000000000000000",
                "0000000000000000",
                "0000000000000000",
                "0000000000000000",
                "0000000000000000"
        };

        // Create cubes for each '1' in binary data
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (binaryData[y].charAt(x) == '1') {
                    // Create a cube for each '1' pixel
                    partdefinition.addOrReplaceChild("pixel_" + x + "_" + y, CubeListBuilder.create()
                            .texOffs(0, 0) // Texture coordinates for the cube
                            .addBox(
                                    x * voxelSize - 0.5F,   // X position
                                    -y * voxelSize - 0.5F,  // Y position
                                    -0.5F,                  // Z position
                                    voxelSize,              // Width
                                    voxelSize,              // Height
                                    1.0F                    // Depth
                            ), PartPose.ZERO);
                }
            }
        }

        return LayerDefinition.create(meshdefinition, width, height);
    }

    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        this.root.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
    }
}