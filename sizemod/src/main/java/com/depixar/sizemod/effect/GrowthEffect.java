package com.depixar.sizemod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleTypes;

/**
 * Эффект увеличения — 2.0x размер.
 * Логика аналогична ShrinkEffect.
 */
public class GrowthEffect extends MobEffect {

    public GrowthEffect() {
        // BENEFICIAL = зелёная иконка
        super(MobEffectCategory.BENEFICIAL, 0xFF6600); // оранжевые частицы
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        setScale(entity, 2.0f);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 20 == 0;
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, net.minecraft.world.entity.ai.attributes.AttributeMap attributeMap, int amplifier) {
        if (!entity.hasEffect(ModEffects.SHRINK_EFFECT.get())) {
            setScale(entity, 1.0f);
        }
    }

    public static void setScale(LivingEntity entity, float scale) {
        ScaleData scaleData = ScaleTypes.BASE.getScaleData(entity);
        scaleData.setScale(scale);
    }
}
