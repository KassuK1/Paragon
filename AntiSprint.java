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
        super("AntiSprint", Category.MOVEMENT, "But why? Why would you use this?");

        INSTANCE = this;
    }

    //if anyone ever says this module is bloat they are wrong
    @Override
    public void onTick() {
        if (nullCheck()) {
            return;
        }

        {
        mc.player.setSprinting(false);
        }
}}