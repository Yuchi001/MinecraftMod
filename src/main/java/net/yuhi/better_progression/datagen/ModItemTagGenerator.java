package net.yuhi.better_progression.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.block.ModBlocks;
import net.yuhi.better_progression.item.ModItems;
import net.yuhi.better_progression.item.enums.EItemCategory;
import net.yuhi.better_progression.item.enums.EItemType;
import net.yuhi.better_progression.item.enums.EMaterialType;
import net.yuhi.better_progression.tag.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static net.yuhi.better_progression.item.utils.ItemsUtilsMethods.getItems;
import static net.yuhi.better_progression.item.utils.ItemsUtilsMethods.getLayerableTools;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, BetterProgression.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModTags.Items.STACKS_TO_16).add(Items.POTION, Items.LINGERING_POTION, Items.SPLASH_POTION, Items.GOLDEN_APPLE, Items.GOLDEN_CARROT, Items.BEETROOT_SOUP, Items.MUSHROOM_STEW, Items.RABBIT_STEW);
        tag(ModTags.Items.STACKS_TO_32).add(Items.BEEF, Items.COOKED_BEEF, Items.PORKCHOP, Items.COOKED_PORKCHOP, Items.COOKED_MUTTON, Items.MUTTON, Items.RABBIT, Items.COOKED_RABBIT);
        tag(ModTags.Items.NO_STACKING).add(Items.ENCHANTED_GOLDEN_APPLE);
        
        for (var tool : getLayerableTools()) {
            tag(ModTags.Items.LAYERABLE_TAG).add(tool);
        }
        
        for (var block : ModBlocks.BLOCKS_DATA) {
            for (var tag : block.itemTags) {
                tag(tag).add(block.block.get().asItem());
            }
        }

        for (var itemInfo : ModItems.REGISTERED_ITEMS) {
            for(var tagKey : itemInfo.tags) {
                tag(tagKey).add(itemInfo.item.get());
            }
        }
        tag(ModTags.Items.HEAVY_ARMOR_TAG).add(Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS);
    }
}
