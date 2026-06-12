package com.depixar.sizemod.potion;

import com.depixar.sizemod.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

/**
 * Кастомный класс для зелья восстановления.
 * Переопределяем finishUsingItem — вызывается когда игрок допивает зелье.
 *
 * Зачем отдельный класс: стандартный Potion просто применяет список эффектов.
 * Нам нужна кастомная логика — снять shrink/growth и сбросить размер через Pehkui.
 */
public class RestorationPotion extends Potion {

    public RestorationPotion() {
        // Без эффектов — логика вся кастомная
        super();
    }

    /**
     * Этот метод вызывается когда LivingEntity заканчивает пить зелье.
     * Здесь мы: 1) снимаем эффекты shrink/growth  2) сбрасываем Pehkui-размер
     */
    public static void applyRestoration(LivingEntity entity) {
        // Снимаем оба эффекта если они есть
        entity.removeEffect(ModEffects.SHRINK_EFFECT.get());
        entity.removeEffect(ModEffects.GROWTH_EFFECT.get());

        // Принудительно сбрасываем размер через Pehkui API
        // (на случай если эффекты уже были сняты, но размер завис)
        ScaleData scaleData = ScaleTypes.BASE.getScaleData(entity);
        scaleData.setScale(1.0f);
    }
}
