package net.yuhi.better_progression.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.block.ModBlocks;
import net.yuhi.better_progression.item.enums.EItemCategory;
import net.yuhi.better_progression.item.enums.EMaterialType;

import static net.yuhi.better_progression.item.utils.ItemsUtilsMethods.getItem;

public class ModCreativeModTabs {
    public static CreativeModeTab BETTER_PROGRESSION_TAB;

    @SubscribeEvent
    public static void registerCreativeTab(CreativeModeTabEvent.Register event) {
        BETTER_PROGRESSION_TAB = event.registerCreativeModeTab(new ResourceLocation(BetterProgression.MOD_ID, "general"),
                builder -> builder.icon(() -> new ItemStack(getItem(EItemCategory.Ingot, EMaterialType.TIN)))
                        .title(Component.translatable("creativetab.better_progression.title"))
                        .build());
    }
    
    @SubscribeEvent
    public static void onBuildContents(CreativeModeTabEvent.BuildContents event)  {
        if (event.getTab() != BETTER_PROGRESSION_TAB) return;

        for (var item : ModItems.REGISTERED_ITEMS)
            event.accept(item.item.get().getDefaultInstance());

        for (var block : ModBlocks.BLOCKS_DATA)
            if (block.blockType != ModBlocks.EBlockType.Vanilla) event.accept(block.block.get().asItem().getDefaultInstance());
    }
}
