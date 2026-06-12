package com.depixar.sizemod.event;

import com.depixar.sizemod.potion.ModPotions;
import com.depixar.sizemod.potion.RestorationPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.brewing.BrewingRecipeRegisterEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.SimpleBrewingRecipe;
import net.minecraftforge.common.brewing.IBrewingRecipe;

/**
 * Хендлер двух типов событий:
 * 1. BrewingRecipeRegisterEvent — добавляем рецепты в варочную стойку
 * 2. LivingEntityUseItemEvent.Finish — перехватываем выпивание зелья восстановления
 */
public class BrewingRecipeHandler {

    /**
     * Рецепты варочной стойки.
     *
     * Стандартный рецепт: Неловкое зелье (Awkward Potion) + ингредиент = результат
     *
     * Ингредиенты:
     * - Уменьшение:   Chorus Fruit (фрукт хора) — редкий, из Энда, тематически подходит
     * - Увеличение:   Golden Carrot (золотая морковь) — дорогой, усиливающий
     * - Восстановление: Dandelion (одуванчик) — простой, "исцеляющий" символически
     */
    @SubscribeEvent
    public void onBrewingRegister(BrewingRecipeRegisterEvent event) {
        // Заготовка: Неловкое зелье (awkward)
        ItemStack awkward = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD);

        // --- Зелье уменьшения: Неловкое зелье + Chorus Fruit ---
        event.getRecipeRegistry().addRecipe(new SimpleBrewingRecipe(
                Potions.AWKWARD,                          // входной тип зелья
                Items.CHORUS_FRUIT,                       // ингредиент
                PotionUtils.setPotion(                    // результат
                        new ItemStack(Items.POTION),
                        ModPotions.SHRINKING_POTION.get()
                )
        ));

        // --- Зелье увеличения: Неловкое зелье + Golden Carrot ---
        event.getRecipeRegistry().addRecipe(new SimpleBrewingRecipe(
                Potions.AWKWARD,
                Items.GOLDEN_CARROT,
                PotionUtils.setPotion(
                        new ItemStack(Items.POTION),
                        ModPotions.GROWTH_POTION.get()
                )
        ));

        // --- Зелье восстановления: Неловкое зелье + Dandelion ---
        event.getRecipeRegistry().addRecipe(new SimpleBrewingRecipe(
                Potions.AWKWARD,
                Items.DANDELION,
                PotionUtils.setPotion(
                        new ItemStack(Items.POTION),
                        ModPotions.RESTORATION_POTION.get()
                )
        ));
    }

    /**
     * Перехватываем момент когда существо заканчивает пить предмет.
     * Нужно для зелья восстановления — оно не имеет обычных эффектов,
     * логика сброса размера срабатывает здесь.
     */
    @SubscribeEvent
    public void onItemUseFinish(LivingEntityUseItemEvent.Finish event) {
        ItemStack stack = event.getItem();
        LivingEntity entity = event.getEntity();

        // Проверяем: это зелье? И это зелье восстановления?
        if (stack.getItem() == Items.POTION) {
            net.minecraft.world.item.alchemy.Potion potion = PotionUtils.getPotion(stack);
            if (potion == ModPotions.RESTORATION_POTION.get()) {
                RestorationPotion.applyRestoration(entity);
            }
        }
    }
}
