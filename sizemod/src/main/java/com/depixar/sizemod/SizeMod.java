package com.depixar.sizemod;

import com.depixar.sizemod.effect.ModEffects;
import com.depixar.sizemod.potion.ModPotions;
import com.depixar.sizemod.event.BrewingRecipeHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SizeMod.MOD_ID)
public class SizeMod {
    public static final String MOD_ID = "sizemod";
    public static final Logger LOGGER = LogManager.getLogger();

    public SizeMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Регистрируем эффекты, зелья и предметы через mod event bus
        ModEffects.EFFECTS.register(modEventBus);
        ModPotions.POTIONS.register(modEventBus);

        // Регистрируем ивент-хендлеры (рецепты варочной стойки)
        MinecraftForge.EVENT_BUS.register(new BrewingRecipeHandler());

        LOGGER.info("SizeMod loaded! Time to get big or small.");
    }
}
