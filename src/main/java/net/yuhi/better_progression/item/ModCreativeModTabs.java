package net.yuhi.better_progression.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;

public class ModCreativeModTabs {
    public static CreativeModeTab BETTER_PROGRESSION_TAB;

    @SubscribeEvent
    public static void registerCreativeTab(CreativeModeTabEvent.Register event) {
        BETTER_PROGRESSION_TAB = event.registerCreativeModeTab(new ResourceLocation(BetterProgression.MOD_ID, "my_tab"),
                builder -> builder.icon(() -> new ItemStack(Items.DIAMOND))
                        .title(Component.translatable("creativetab.better_progression.title"))
                        .build());
    }
    
    @SubscribeEvent
    public static void onBuildContents(CreativeModeTabEvent.BuildContents event)  {
        if (event.getTab() != BETTER_PROGRESSION_TAB) return;

        for (RegistryObject<Item> item : ModItems.ITEMS.getEntries())
            event.accept(item.get().getDefaultInstance());
    }
}
