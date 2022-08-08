package com.paragon.client.systems.module.impl.movement;

import com.paragon.api.module.Category;
import com.paragon.api.module.Module;

/**
 * @author KassuK
 * @since 8.8.2022
 */

public class AntiSprint extends Module {

    public static AntiSprint INSTANCE;

    public AntiSprint() {
        super("Sprint", Category.MOVEMENT, "But why? Why would you use this?");

        INSTANCE = this;
    }

    @Override
    public void onTick() {
        mc.player.setSprinting(false);
    }
}