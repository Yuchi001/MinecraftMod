package net.yuhi.better_progression.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.item.ModItems;
import net.yuhi.better_progression.item.enums.EItemCategory;
import net.yuhi.better_progression.item.enums.EItemType;
import net.yuhi.better_progression.item.enums.EMaterialType;
import net.yuhi.better_progression.tag.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static net.yuhi.better_progression.item.utils.ItemsUtilsMethods.getLayerableTools;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, BetterProgression.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        for (var tool : getLayerableTools()) {
            tag(ModTags.Items.LAYERABLE_TAG).add(tool);
        }

        for (var armor : ModItems.REGISTERED_ITEMS
                .stream()
                .filter(itemItemInfo -> itemItemInfo.type == EItemType.Armor || itemItemInfo.type == EItemType.Chainmail)
                .toList()) {
            if(armor.type == EItemType.Armor) tag(ModTags.Items.HEAVY_ARMOR_TAG).add(armor.item.get());
            if(armor.material_type == EMaterialType.NETHERITE) tag(ModTags.Items.NETHERITE_ARMOR_TAG).add(armor.item.get());
            if(armor.material_type == EMaterialType.ENDERITE) tag(ModTags.Items.ENDERITE_ARMOR_TAG).add(armor.item.get());
        }
        tag(ModTags.Items.HEAVY_ARMOR_TAG).add(Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS);
    }
}
