package com.depixar.sizemod.event;

import com.depixar.sizemod.potion.ModPotions;
import com.depixar.sizemod.potion.RestorationPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import com.depixar.sizemod.SizeMod;

@Mod.EventBusSubscriber(modid = SizeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BrewingRecipeHandler {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Уменьшение: Неловкое зелье + Фрукт хора
            BrewingRecipeRegistry.addRecipe(
                new net.minecraftforge.common.brewing.SimpleBrewingRecipe<>(
                    net.minecraft.world.item.alchemy.Potions.AWKWARD,
                    Items.CHORUS_FRUIT,
                    ModPotions.SHRINKING_POTION.get()
                )
            );

            // Увеличение: Неловкое зелье + Золотая морковь
            BrewingRecipeRegistry.addRecipe(
                new net.minecraftforge.common.brewing.SimpleBrewingRecipe<>(
                    Potions.AWKWARD,
                    Items.GOLDEN_CARROT,
                    ModPotions.GROWTH_POTION.get()
                )
            );

            // Восстановление: Неловкое зелье + Одуванчик
            BrewingRecipeRegistry.addRecipe(
                new net.minecraftforge.common.brewing.SimpleBrewingRecipe<>(
                    Potions.AWKWARD,
                    Items.DANDELION,
                    ModPotions.RESTORATION_POTION.get()
                )
            );
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
            net.minecraft.world.item.alchemy.Potion potion = PotionUtils.getPotion(stack);
            if (potion == ModPotions.RESTORATION_POTION.get()) {
                RestorationPotion.applyRestoration(entity);
            }
        }
    }
}
