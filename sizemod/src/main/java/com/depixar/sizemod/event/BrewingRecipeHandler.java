package com.depixar.sizemod.event;

import com.depixar.sizemod.SizeMod;
import com.depixar.sizemod.potion.ModPotions;
import com.depixar.sizemod.potion.RestorationPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = SizeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BrewingRecipeHandler {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            registerRecipe(Potions.AWKWARD, Items.CHORUS_FRUIT,  ModPotions.SHRINKING_POTION.get());
            registerRecipe(Potions.AWKWARD, Items.GOLDEN_CARROT, ModPotions.GROWTH_POTION.get());
            registerRecipe(Potions.AWKWARD, Items.DANDELION,     ModPotions.RESTORATION_POTION.get());
        });
    }

    private static void registerRecipe(Potion input, net.minecraft.world.item.Item ingredient, Potion output) {
        BrewingRecipeRegistry.addRecipe(new IBrewingRecipe() {
            @Override
            public boolean isInput(ItemStack stack) {
                return PotionUtils.getPotion(stack) == input
                    && stack.getItem() == Items.POTION;
            }

            @Override
            public boolean isIngredient(ItemStack stack) {
                return stack.getItem() == ingredient;
            }

            @Override
            public ItemStack getOutput(ItemStack input, ItemStack ing) {
                if (!isInput(input) || !isIngredient(ing)) return ItemStack.EMPTY;
                return PotionUtils.setPotion(new ItemStack(Items.POTION), output);
            }
        });
    }
}

@Mod.EventBusSubscriber(modid = SizeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
class ItemUseHandler {

    @SubscribeEvent
    public static void onItemUseFinish(LivingEntityUseItemEvent.Finish event) {
        ItemStack stack = event.getItem();
        LivingEntity entity = event.getEntity();
        if (stack.getItem() == Items.POTION) {
            Potion potion = PotionUtils.getPotion(stack);
            if (potion == ModPotions.RESTORATION_POTION.get()) {
                RestorationPotion.applyRestoration(entity);
            }
        }
    }
}
