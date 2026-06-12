package com.depixar.sizemod.potion;

import com.depixar.sizemod.SizeMod;
import com.depixar.sizemod.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Регистрируем Potion-типы.
 * Potion = набор эффектов с длительностью. Это не предмет, это "тип зелья".
 * Предмет (бутылка) берёт данные из Potion.
 *
 * Длительность: Integer.MAX_VALUE — практически бесконечно (постоянный эффект).
 * Снимается только через зелье восстановления (которое вызывает removeEffect).
 */
public class ModPotions {

    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(ForgeRegistries.POTIONS, SizeMod.MOD_ID);

    // Зелье уменьшения: эффект shrink, "вечный" (Integer.MAX_VALUE тиков)
    public static final RegistryObject<Potion> SHRINKING_POTION =
            POTIONS.register("shrinking", () ->
                    new Potion(new MobEffectInstance(
                            ModEffects.SHRINK_EFFECT.get(),
                            Integer.MAX_VALUE, // длительность в тиках (~136 лет, считай постоянный)
                            0,                 // amplifier 0 = уровень I
                            false,             // ambient (частицы менее прозрачные) = false
                            true,              // showParticles
                            true               // showIcon в инвентаре
                    ))
            );

    // Зелье увеличения: эффект growth, тоже постоянный
    public static final RegistryObject<Potion> GROWTH_POTION =
            POTIONS.register("growth", () ->
                    new Potion(new MobEffectInstance(
                            ModEffects.GROWTH_EFFECT.get(),
                            Integer.MAX_VALUE,
                            0,
                            false,
                            true,
                            true
                    ))
            );

    // Зелье восстановления: не имеет постоянного эффекта.
    // Логика сброса размера — в BrewingRecipeHandler через onItemUseFinish или
    // кастомный PreItem event. Зелью нужен хотя бы один эффект для работы как PotionItem,
    // поэтому даём короткий нейтральный эффект (luck на 1 тик — просто как заглушка).
    // Реальный сброс размера — в ItemUseFinishEvent.
    public static final RegistryObject<Potion> RESTORATION_POTION =
            POTIONS.register("restoration", () ->
                    new RestorationPotion()
            );
}
