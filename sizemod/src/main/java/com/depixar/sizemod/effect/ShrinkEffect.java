package com.depixar.sizemod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

/**
 * Эффект уменьшения — устанавливает масштаб игрока на 0.5x.
 * Эффект "постоянный": не снимается сам по себе, только через зелье восстановления.
 * Для этого задаём очень долгую длительность при применении зелья.
 */
public class ShrinkEffect extends MobEffect {

    public ShrinkEffect() {
        // HARMFUL = визуально красный иконка эффекта
        super(MobEffectCategory.HARMFUL, 0x00AAFF); // голубой цвет частиц
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // applyEffectTick вызывается каждый тик пока эффект активен
        // Мы применяем размер здесь, чтобы он держался
        setScale(entity, 0.5f);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // true = applyEffectTick вызывается каждый тик
        // false = только в определённые моменты
        // Нам нужно постоянно держать размер, поэтому true каждые 20 тиков (1 сек)
        return duration % 20 == 0;
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, net.minecraft.world.entity.ai.attributes.AttributeMap attributeMap, int amplifier) {
        // Когда эффект снимается — возвращаем размер к 1.0
        // Но только если нет других эффектов размера (например, Growth)
        // Проверяем: если нет GrowthEffect — сбрасываем
        if (!entity.hasEffect(ModEffects.GROWTH_EFFECT.get())) {
            setScale(entity, 1.0f);
        }
    }

    public static void setScale(LivingEntity entity, float scale) {
        // Pehkui API: получаем ScaleData для типа "общий размер"
        // ScaleTypes.BASE — базовый масштаб, влияет на всё (хитбокс, модель, и т.д.)
        ScaleData scaleData = ScaleTypes.BASE.getScaleData(entity);
        scaleData.setScale(scale);
    }
}
