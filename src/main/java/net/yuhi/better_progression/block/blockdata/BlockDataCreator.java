package net.yuhi.better_progression.block.blockdata;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.registries.RegistryObject;
import net.yuhi.better_progression.BetterProgression;
import net.yuhi.better_progression.block.ModBlocks;
import net.yuhi.better_progression.item.ModItems;
import net.yuhi.better_progression.recipe.utils.EBlockCraftingRecipeType;
import net.yuhi.better_progression.recipe.utils.ESmeltingRecipeType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BlockDataCreator {
    private final Supplier<Block> blockSupplier;
    private final String name;
    private Supplier<Block> dropSupplier;
    private EMineableWith mineableWith = EMineableWith.NONE;
    private ERequireTier requireTier = ERequireTier.NONE;
    private ETextureType textureType = ETextureType.CUBE_ALL;
    private ECustomTag tag = ECustomTag.NONE;
    private boolean dropSelf = true;
    private ResourceLocation textureTop = null;
    private ResourceLocation textureBottom = null;
    private ResourceLocation textureSide = null;
    private ResourceLocation textureItem = null;
    
    private List<TagKey<Item>> itemTags = new ArrayList<>();
    private List<TagKey<Block>> blockTags = new ArrayList<>();
    
    private SmeltingRecipeData smeltingRecipeData;
    
    // For now only signs use this feature
    private Supplier<Block> twinBlockSupplier = null;
    
    // Crafting recipe type havers only
    private final List<RecipeData> recipes = new ArrayList<>();

    // Log blocks only
    private Supplier<Block> strippedLogBlock = null;
    private boolean isLog = false;

    public BlockDataCreator(String name, Supplier<Block> block) {
        this.name = name;
        this.blockSupplier = block;
        //this.dropSupplier = block;

         try {
             textureSide = new ResourceLocation(BetterProgression.MOD_ID, ModelProvider.BLOCK_FOLDER + "/" + name);
         }
         catch (Exception ignored) {}
    }
    
    public BlockDataCreator(String name, Block block, String modId) {
        this.name = name;
        this.blockSupplier = () -> block;
        textureSide = new ResourceLocation(modId, ModelProvider.BLOCK_FOLDER + "/" + name);
    }

    public BlockDataCreator AddRecipe(EBlockCraftingRecipeType recipeType, List<Supplier<ItemLike>> ingredientList) {
        var recipePair = new RecipeData(recipeType, ingredientList, null);
        this.recipes.add(recipePair);
        return this;
    }

    public BlockDataCreator AddRecipe(EBlockCraftingRecipeType recipeType, List<Supplier<ItemLike>> ingredientList, int resultCount) {
        var recipePair = new RecipeData(recipeType, ingredientList, resultCount);
        this.recipes.add(recipePair);
        return this;
    }
    
    public BlockDataCreator AddSmeltingRecipe(ESmeltingRecipeType recipeType, Supplier<ItemLike> output) {
        this.smeltingRecipeData = new SmeltingRecipeData(recipeType, output);
        return this;
    }
    
    public BlockDataCreator SetMineableWith(EMineableWith mineableWith) {
        this.mineableWith = mineableWith;
        return this;
    }
    
    public BlockDataCreator SetTwinBlockSupplier(Supplier<Block> twinBlockSupplier) {
        this.twinBlockSupplier = twinBlockSupplier;
        return this;
    }

    public BlockDataCreator SetTextureType(ETextureType textureType) {
        this.textureType = textureType;
        return this;
    }
    
    public BlockDataCreator SetLogProperties(Supplier<Block> strippedLogBlock) {
        this.strippedLogBlock = strippedLogBlock;
        this.isLog = true;
        return this;
    }

    public BlockDataCreator SetTexture(EBlockSide side, String textureName) {
        switch (side) {
            case TOP: textureTop = new ResourceLocation(BetterProgression.MOD_ID, ModelProvider.BLOCK_FOLDER + "/" + textureName); break;
            case BOTTOM: textureBottom = new ResourceLocation(BetterProgression.MOD_ID, ModelProvider.BLOCK_FOLDER + "/" + textureName); break;
            case SIDE, ALL: textureSide = new ResourceLocation(BetterProgression.MOD_ID, ModelProvider.BLOCK_FOLDER + "/" + textureName); break;
            case ITEM: textureItem = new ResourceLocation(BetterProgression.MOD_ID, ModelProvider.ITEM_FOLDER + "/" + textureName); break;
        }
        return this;
    }
    
    public BlockDataCreator SetSpecialTag(ECustomTag tag) {
        this.tag = tag;
        return this;
    }
    
    public BlockDataCreator AddBlockTag(TagKey<Block> blockTag) {
        this.blockTags.add(blockTag);
        return this;
    }
    
    public BlockDataCreator AddItemTag(TagKey<Item> itemTag) {
        this.itemTags.add(itemTag);
        return this;
    }

    public BlockDataCreator SetTexture(EBlockSide side, String modId, String textureName) {
        switch (side) {
            case TOP: textureTop = new ResourceLocation(modId, ModelProvider.BLOCK_FOLDER + "/" + textureName); break;
            case BOTTOM: textureBottom = new ResourceLocation(modId, ModelProvider.BLOCK_FOLDER + "/" + textureName); break;
            case SIDE, ALL: textureSide = new ResourceLocation(modId, ModelProvider.BLOCK_FOLDER + "/" + textureName); break;
            case ITEM: textureItem = new ResourceLocation(modId, ModelProvider.ITEM_FOLDER + "/" + textureName); break;
        }
        return this;
    }

    public BlockDataCreator SetRequireTier(ERequireTier requireTier) {
        this.requireTier = requireTier;
        return this;
    }
    
    public BlockDataCreator DontDropSelf() {
        this.dropSelf = false;
        this.dropSupplier = null;
        return this;
    }

    public BlockDataCreator SetCustomDrop(Supplier<Block> drop) {
        this.dropSupplier = drop;
        this.dropSelf = false;
        return this;
    }
    
    public RegistryObject<Block> Register() {
        RegistryObject<Block> blockObj = ModBlocks.BLOCKS.register(name, blockSupplier);
        ModItems.ITEMS.register(name, () -> new BlockItem(blockObj.get(), new Item.Properties()));
        ModBlocks.BLOCKS_DATA.add(new BlockData(this, blockObj));
        return blockObj;
    }

    public RegistryObject<Block> RegisterWithoutItem() {
        RegistryObject<Block> blockObj = ModBlocks.BLOCKS.register(name, blockSupplier);
        ModBlocks.BLOCKS_DATA.add(new BlockData(this, blockObj));
        return blockObj;
    }
    
    public RegistryObject<Block> RegisterVanilla() {
        RegistryObject<Block> blockObj = ModBlocks.VANILLA_BLOCKS.register(name, blockSupplier);
        ModItems.ITEMS.register(name, () -> new BlockItem(blockObj.get(), new Item.Properties()));
        return blockObj;
    }

    public record SmeltingRecipeData(ESmeltingRecipeType recipeType, Supplier<ItemLike> output) { }
    public record RecipeData(EBlockCraftingRecipeType recipeType, List<Supplier<ItemLike>> ingredientList, Integer resultCount) { }
    
    public static class BlockData {
        public final RegistryObject<Block> block;
        public final Supplier<Block> drop;
        public final String name;
        public final EMineableWith mineableWith;
        public final ETextureType textureType;
        public final ERequireTier requireTier;
        public final boolean dropSelf;
        public final ResourceLocation textureTop;
        public final ResourceLocation textureBottom;
        public final ResourceLocation textureSide;
        public final ResourceLocation textureItem;
        public final ECustomTag customTag;
        public final List<TagKey<Item>> itemTags;
        public final List<TagKey<Block>> blockTags;
        
        // smelting recipe haver only
        public final SmeltingRecipeData smeltingRecipeData;
        
        // crafting recipe type haver only
        public final List<RecipeData> recipes;
        
        // for now only signs use this feature
        public final Supplier<Block> twinBlockSupplier;

        // Log blocks only
        public final Supplier<Block> strippedLogBlock;
        public final boolean isLog;

        BlockData(BlockDataCreator blockDataCreator, RegistryObject<Block> block) {
            this.block = block;
            this.name = blockDataCreator.name;
            this.mineableWith = blockDataCreator.mineableWith;
            this.textureType = blockDataCreator.textureType;
            this.requireTier = blockDataCreator.requireTier;
            this.dropSelf = blockDataCreator.dropSelf;
            this.textureTop = blockDataCreator.textureTop;
            this.textureBottom = blockDataCreator.textureBottom;
            this.textureSide = blockDataCreator.textureSide;
            this.textureItem = blockDataCreator.textureItem;
            this.customTag = blockDataCreator.tag;
            this.isLog = blockDataCreator.isLog;
            this.strippedLogBlock = blockDataCreator.strippedLogBlock;
            this.recipes = blockDataCreator.recipes;
            this.twinBlockSupplier = blockDataCreator.twinBlockSupplier;
            this.smeltingRecipeData = blockDataCreator.smeltingRecipeData;
            this.drop = blockDataCreator.dropSupplier;
            this.itemTags = blockDataCreator.itemTags;
            this.blockTags = blockDataCreator.blockTags;
        }
        
        public void SaveRecipes(Consumer<FinishedRecipe> writer) {
            for (var recipe : recipes) {
                try {
                    recipe.recipeType.SaveRecipe(writer, () -> block.get().asItem(), recipe.resultCount, recipe.ingredientList);
                } catch (Exception ex) {
                    System.err.println("Exception caught when saving recipe for " + name);
                    System.err.println(ex.getMessage());
                }
            }
            
            if (smeltingRecipeData != null) 
                smeltingRecipeData.recipeType.SaveRecipes(writer, block::get, smeltingRecipeData.output);
        }
    }
}
