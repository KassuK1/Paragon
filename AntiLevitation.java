package com.paragon.client.systems.module.impl.movement;
import com.paragon.api.module.Category;
import com.paragon.api.module.Module;
import net.minecraft.potion.Potion;

/**
 * @author KassuK
 * @since 8.8.2022
 */

public class AntiLevitation extends Module {

    public static AntiLevitation INSTANCE;

    public AntiLevitation() {
        super("AntiLevitation", Category.MOVEMENT, "Stops you from levitating");

        INSTANCE = this;
    }

    @Override
    public void onTick() {
        if (nullCheck()) {
            return;
        }
        if (AntiLevitation.mc.player.isPotionActive(Potion.getPotionFromResourceLocation("levitation"))) {
            AntiLevitation.mc.player.removeActivePotionEffect(Potion.getPotionFromResourceLocation("levitation"));
        }
    }
}