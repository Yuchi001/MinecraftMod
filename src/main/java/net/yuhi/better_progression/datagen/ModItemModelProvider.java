package net.yuhi.better_progression.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.item.ModItems;

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
