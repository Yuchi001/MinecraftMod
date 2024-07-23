package net.yuhi.better_progression.datagen;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.CustomLoaderBuilder;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.item.ModItems;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BetterProgression.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.HILT);
        
        for (var item : ModItems.REGISTERED_ITEMS) {
            switch (item.type) {
                case Simple -> simpleItem(item.item);
                case HandHeld -> handHeldItem(item.item);
                case HandHeldBig -> bigHandHeldItem(item.item);
            }
        }
    }

    private ItemModelBuilder bigHandHeldItem(RegistryObject<Item> item) {
        var builder = withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(BetterProgression.MOD_ID, "item/" + item.getId().getPath()));
        JsonObject display = new JsonObject();

        // thirdperson_righthand
        JsonObject thirdPersonRightHand = new JsonObject();
        thirdPersonRightHand.add("scale", createJsonArray(1.25, 1.25, 1));
        thirdPersonRightHand.add("translation", createJsonArray(0, 6, 0.5));
        thirdPersonRightHand.add("rotation", createJsonArray(0, -90, 55));

        // thirdperson_lefthand
        JsonObject thirdPersonLeftHand = new JsonObject();
        thirdPersonLeftHand.add("scale", createJsonArray(1.25, 1.25, 1));
        thirdPersonLeftHand.add("translation", createJsonArray(0, 6, 0.5));
        thirdPersonLeftHand.add("rotation", createJsonArray(0, 90, -55));

        // firstperson_righthand
        JsonObject firstPersonRightHand = new JsonObject();
        firstPersonRightHand.add("scale", createJsonArray(0.935, 0.935, 0.68));
        firstPersonRightHand.add("rotation", createJsonArray(0, 90, 65));
        firstPersonRightHand.add("translation", createJsonArray(1.13, 3.2, 1.13));

        // firstperson_lefthand
        JsonObject firstPersonLeftHand = new JsonObject();
        firstPersonLeftHand.add("scale", createJsonArray(0.935, 0.935, 0.68));
        firstPersonLeftHand.add("rotation", createJsonArray(0, -90, -65));
        firstPersonLeftHand.add("translation", createJsonArray(1.13, 3.2, 1.13));

        // Adding all parts to display
        display.add("thirdperson_righthand", thirdPersonRightHand);
        display.add("thirdperson_lefthand", thirdPersonLeftHand);
        display.add("firstperson_righthand", firstPersonRightHand);
        display.add("firstperson_lefthand", firstPersonLeftHand);

        // Adding display to the builder
        var builderJson = builder.toJson();
        builderJson.add("display", display);
        //builder.customLoader(item.getId().getPath(), existingFileHelper, display);

        var loc = item.getId().getPath();
        try {
            FileWriter f = new FileWriter(loc);
            f.write(builderJson.toString());
            f.close();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return builder;
    }
    private JsonArray createJsonArray(double... values) {
        JsonArray array = new JsonArray();
        for (double value : values) {
            array.add(value);
        }
        return array;
    }

    private ItemModelBuilder handHeldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(BetterProgression.MOD_ID, "item/" + item.getId().getPath()));
    }
    
    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0", 
                new ResourceLocation(BetterProgression.MOD_ID, "item/" + item.getId().getPath()));
    }
}
