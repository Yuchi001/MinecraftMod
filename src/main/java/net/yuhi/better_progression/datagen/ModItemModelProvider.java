package net.yuhi.better_progression.datagen;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
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
        for (var item : ModItems.REGISTERED_ITEMS) {
            switch (item.type) {
                case Simple -> simpleItem(item.item);
                case HandHeld -> handHeldItem(item.item);
            }
        }
    }

    private final Gson gson = new Gson();
    private ItemModelBuilder handHeldItem(RegistryObject<Item> item) {
        ResourceLocation modelLocation = new ResourceLocation(BetterProgression.MOD_ID, "item/" + item.getId().getPath());
        var path = Paths.get("generated/assets/" + modelLocation.getNamespace() + "/models/" + modelLocation.getPath() + ".json");
        var model = withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(BetterProgression.MOD_ID, "item/" + item.getId().getPath()));

        try {
            JsonObject json = gson.fromJson(Files.newBufferedReader(path), JsonObject.class);

            JsonObject displayObject = new JsonObject();

            JsonObject thirdPersonRightHand = new JsonObject();
            thirdPersonRightHand.add("scale", createVector(1.5f, 1.5f, 1.5f));
            displayObject.add("thirdperson_righthand", thirdPersonRightHand);

            JsonObject firstPersonRightHand = new JsonObject();
            firstPersonRightHand.add("scale", createVector(1.5f, 1.5f, 1.5f));
            displayObject.add("firstperson_righthand", firstPersonRightHand);

            json.add("display", displayObject);

            try (FileWriter writer = new FileWriter(path.toFile())) {
                gson.toJson(json, writer);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return model;
    }

    private JsonArray createVector(float x, float y, float z) {
        JsonArray array = new JsonArray();
        array.add(x);
        array.add(y);
        array.add(z);
        return array;
    }
    
    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0", 
                new ResourceLocation(BetterProgression.MOD_ID, "item/" + item.getId().getPath()));
    }
}
