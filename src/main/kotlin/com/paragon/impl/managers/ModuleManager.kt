package com.paragon.impl.managers

import com.paragon.impl.module.Module
import com.paragon.impl.module.client.*
import com.paragon.impl.module.combat.*
import com.paragon.impl.module.hud.impl.*
import com.paragon.impl.module.hud.impl.graph.GraphCrystals
import com.paragon.impl.module.hud.impl.graph.GraphFPS
import com.paragon.impl.module.hud.impl.graph.GraphPing
import com.paragon.impl.module.hud.impl.graph.GraphSpeed
import com.paragon.impl.module.misc.*
import com.paragon.impl.module.movement.*
import com.paragon.impl.module.render.*
import net.minecraftforge.common.MinecraftForge
import java.util.function.Predicate

class ModuleManager {

    val modules: ArrayList<Module> = arrayListOf(
        // Combat
        Aura,
        AutoCrystal,
        AutoXP,
        BowBomb,
        BowRelease,
        Criticals,
        Offhand,
        Replenish,
        Surround,
        WebAura,

        // Movement
        AntiVoid,
        AutoWalk,
        ElytraFlight,
        EntitySpeed,
        Flight,
        InventoryWalk,
        NoFall,
        NoSlow,
        PacketFlight,
        ReverseStep,
        SafeWalk,
        Sprint,
        Step,
        Strafe,
        TickShift,
        Velocity,

        // Render
        AspectRatio,
        BetterScreenshot,
        BlockHighlight,
        Blur,
        Breadcrumbs,
        BreakESP,
        Chams,
        ChinaHat,
        ClearChat,
        EnchantColour,
        ESP,
        Fullbright,
        HandChams,
        HitColour,
        HoleESP,
        LogoutSpots,
        MobOwner,
        Nametags,
        NewChunks,
        NoRender,
        PhaseESP,
        PopChams,
        Shader,
        ShulkerViewer,
        SoundHighlight,
        SourceESP,
        StorageESP,
        SuperheroFX,
        Tracers,
        Trajectories,
        ViewClip,
        ViewModel,
        VoidESP,
        Xray,

        // Misc
        Alert,
        Announcer,
        AntiGhast,
        AntiHunger,
        AutoLog,
        AutoSable,
        AutoTranslate,
        BookBot,
        Blink,
        ChatModifications,
        ChorusControl,
        CustomWorld,
        ExtraTab,
        FakePlayer,
        FastUse,
        Interact,
        Lawnmower,
        MiddleClick,
        MultiTask,
        NoGlobalSounds,
        NoHandshake,
        NoRotate,
        OnDeath,
        RotationLock,
        TimerModule,
        UnfocusedCPU,
        XCarry,

        // Client
        ClientFont,
        Colours,
        ClickGUI,
        DiscordRPC,
        Editor,
        MainMenu,
        Notifications,

        // HUD
        Armour,
        ArrayListHUD,
        Arrows,
        BindList,
        Biome,
        Coordinates,
        CPS,
        Crystals,
        Direction,
        FPS,
        Hunger,
        GraphCrystals,
        GraphFPS,
        GraphPing,
        GraphSpeed,
        Inventory,
        IP,
        Keystrokes,
        LagNotifier,
        Memory,
        ModelView,
        Ping,
        Potions,
        ServerBrand,
        Speed,
        TargetHUD,
        Totems,
        TPS,
        Watermark
    )

    init {
        MinecraftForge.EVENT_BUS.register(this)
        modules.forEach { it.reflectSettings() }
    }


    /**
     * @return all the modules matching the predicate
     */
    fun getModulesThroughPredicate(predicate: Predicate<Module>): List<Module> = modules.filter { predicate.test(it) }

}