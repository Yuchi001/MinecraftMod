package net.yuhi.better_progression.mixin;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.yuhi.better_progression.item.interfaces.BetterArmorMaterial;
import net.yuhi.better_progression.mixin.accessor.ArmorMaterialsAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.EnumMap;

@Mixin(ArmorMaterials.class)
public class ArmorMaterialsMixin implements BetterArmorMaterial {
    
    @Unique
    private int[] betterProgression$lifeMod;
    
    @Inject(method = "<init>", at = @At("RETURN"))
    private void modifyEnumValues(String name, int ordinal, String pName, int pDurabilityMultiplier, EnumMap<ArmorItem.Type, Integer> pProtectionFunctionForType, int pEnchantmentValue, net.minecraft.sounds.SoundEvent pSound, float pToughness, float pKnockbackResistance, java.util.function.Supplier<net.minecraft.world.item.crafting.Ingredient> pRepairIngredient, CallbackInfo ci) {
        var accessor = (ArmorMaterialsAccessor)(Object)this;
        
        switch (pName) {
            case "leather" -> {
                pProtectionFunctionForType.put(ArmorItem.Type.HELMET, 1);
                pProtectionFunctionForType.put(ArmorItem.Type.CHESTPLATE, 2);
                pProtectionFunctionForType.put(ArmorItem.Type.LEGGINGS, 2);
                pProtectionFunctionForType.put(ArmorItem.Type.BOOTS, 1);

                this.betterProgression$lifeMod = new int[]{ 1, 1, 1, 1 };
            }
            case "chainmail" -> {
                pProtectionFunctionForType.put(ArmorItem.Type.HELMET, 2);
                pProtectionFunctionForType.put(ArmorItem.Type.CHESTPLATE, 3);
                pProtectionFunctionForType.put(ArmorItem.Type.LEGGINGS, 3);
                pProtectionFunctionForType.put(ArmorItem.Type.BOOTS, 2);

                accessor.setRepairIngredient(new LazyLoadedValue<>(() -> Ingredient.of(Items.IRON_INGOT)));

                this.betterProgression$lifeMod = new int[]{ 1, 2, 2, 1 };
            }
            case "iron" -> {
                pProtectionFunctionForType.put(ArmorItem.Type.HELMET, 2);
                pProtectionFunctionForType.put(ArmorItem.Type.CHESTPLATE, 6);
                pProtectionFunctionForType.put(ArmorItem.Type.LEGGINGS, 5);
                pProtectionFunctionForType.put(ArmorItem.Type.BOOTS, 2);
                
                accessor.setToughness(1.0f);
                accessor.setKnockbackResistance(0.2f);
                
                this.betterProgression$lifeMod = new int[]{ 2, 4, 3, 1 };
            }
            case "gold" -> {
                pProtectionFunctionForType.put(ArmorItem.Type.HELMET, 3);
                pProtectionFunctionForType.put(ArmorItem.Type.CHESTPLATE, 8);
                pProtectionFunctionForType.put(ArmorItem.Type.LEGGINGS, 6);
                pProtectionFunctionForType.put(ArmorItem.Type.BOOTS, 3);

                accessor.setToughness(2.0f);
                accessor.setKnockbackResistance(0.3f);

                this.betterProgression$lifeMod = new int[]{ 3, 5, 4, 2 };
            }
        }
    }

    @Override
    public int getLifeMod(ArmorItem.Type type) {
        return betterProgression$lifeMod[type.ordinal()];
    }
}