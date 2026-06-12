package com.depixar.sizemod.effect;

import com.depixar.sizemod.SizeMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Регистрируем все кастомные эффекты.
 * DeferredRegister — стандартный Forge-способ регистрации объектов.
 * Объекты создаются лениво (при старте), не сразу.
 */
public class ModEffects {

    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SizeMod.MOD_ID);

    public static final RegistryObject<MobEffect> SHRINK_EFFECT =
            EFFECTS.register("shrink", ShrinkEffect::new);

    public static final RegistryObject<MobEffect> GROWTH_EFFECT =
            EFFECTS.register("growth", GrowthEffect::new);

    // Зелье восстановления не нужно отдельного эффекта —
    // оно просто снимает shrink/growth и сбрасывает размер через ScaleTypes напрямую
}
