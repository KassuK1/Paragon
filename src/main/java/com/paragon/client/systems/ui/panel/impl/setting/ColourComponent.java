package com.paragon.client.systems.ui.panel.impl.setting;

import com.paragon.Paragon;
import com.paragon.api.event.client.SettingUpdateEvent;
import com.paragon.api.util.calculations.MathUtil;
import com.paragon.api.util.render.ColourUtil;
import com.paragon.api.util.render.GuiUtil;
import com.paragon.api.util.render.RenderUtil;
import com.paragon.client.systems.module.settings.Setting;
import com.paragon.client.systems.ui.animation.Animation;
import com.paragon.client.systems.ui.panel.impl.module.ModuleButton;
import com.paragon.client.systems.module.impl.client.GUI;
import com.paragon.client.systems.module.settings.impl.ColourSetting;
import com.paragon.client.systems.module.settings.impl.NumberSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wolfsurge
 */
public class ColourComponent extends SettingComponent {

    private final NumberSetting hue;
    private final NumberSetting alpha;
    private Color finalColour;
    private final List<SliderComponent> sliders = new ArrayList<>();
    private final Animation animation = new Animation(200, false);
    private boolean dragging = false;

    public ColourComponent(ModuleButton moduleButton, ColourSetting setting, float offset, float height) {
        super(moduleButton, setting, offset, height);

        float[] hsbColour = Color.RGBtoHSB(setting.getColour().getRed(), setting.getColour().getGreen(), setting.getColour().getBlue(), null);

        this.hue = new NumberSetting("Hue", "The hue of the colour", hsbColour[0] * 360, 0, 360, 1);
        this.alpha = new NumberSetting("Alpha", "The alpha (transparency) of the colour", setting.getColour().getAlpha(), 0, 255, 1);
        sliders.add(new SliderComponent(moduleButton, hue, offset + (height * 2), height));
        sliders.add(new SliderComponent(moduleButton, alpha, offset + (height * 3), height));

        finalColour = setting.getColour();
    }

    @Override
    public void renderSetting(int mouseX, int mouseY) {
        this.animation.time = GUI.animationSpeed.getValue();

        RenderUtil.drawRect(getModuleButton().getPanel().getX(), getModuleButton().getOffset() + getOffset(), getModuleButton().getPanel().getWidth(), getHeight(), GuiUtil.mouseOver(getModuleButton().getPanel().getX(), getModuleButton().getOffset() + getOffset(), getModuleButton().getPanel().getX() + getModuleButton().getPanel().getWidth(), getModuleButton().getOffset() + getOffset() + 12, mouseX, mouseY) ? new Color(23, 23, 23).brighter().getRGB() : new Color(23, 23, 23).getRGB());

        GL11.glPushMatrix();
        GL11.glScalef(0.7f, 0.7f, 0.7f);
        float scaleFactor = 1 / 0.7f;
        renderText(getSetting().getName(), (getModuleButton().getPanel().getX() + 5) * scaleFactor, (getModuleButton().getOffset() + getOffset() + 4) * scaleFactor, ((ColourSetting) getSetting()).getColour().getRGB());
        GL11.glScalef(scaleFactor, scaleFactor, scaleFactor);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("...", (getModuleButton().getPanel().getX() + getModuleButton().getPanel().getWidth() - 6.5f) * 2, (getModuleButton().getOffset() + getOffset() + 3.5f) * 2, -1);
        GL11.glPopMatrix();

        float off = getOffset() + 12;
        for (SliderComponent sliderComponent : sliders) {
            sliderComponent.setOffset(off);
            off += 12;
        }

        if (isExpanded()) {
            // Render sliders
            sliders.forEach(sliderComponent -> sliderComponent.renderSetting(mouseX, mouseY));

            float hue = this.hue.getValue();

            float x = getModuleButton().getPanel().getX() + 4;
            float y = getModuleButton().getOffset() + getOffset() + 39;
            float dimension = 87;
            float height = dimension * GUI.animation.getCurrentMode().getAnimationFactor(animation.getAnimationFactor());

            Color colour = Color.getHSBColor(hue / 360, 1, 1);

            // Background
            RenderUtil.drawRect(getModuleButton().getPanel().getX(), getModuleButton().getOffset() + getOffset() + 36, getModuleButton().getPanel().getWidth(), 94, new Color(23, 23, 23).getRGB());

            // GL shit pt 1
            GlStateManager.pushMatrix();
            GlStateManager.disableTexture2D();
            GlStateManager.enableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.shadeModel(7425);

            // Get tessellator and buffer builder
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();

            // Add positions
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            bufferbuilder.pos(x + dimension, y, 0).color(colour.getRed(), colour.getGreen(), colour.getBlue(), colour.getAlpha()).endVertex();
            bufferbuilder.pos(x, y, 0).color(255, 255, 255, 255).endVertex();
            bufferbuilder.pos(x, y + height, 0).color(0, 0, 0, 255).endVertex();
            bufferbuilder.pos(x + dimension, y + height, 0).color(0, 0, 0, 255).endVertex();

            // Draw rect
            tessellator.draw();

            // GL shit pt 2
            GlStateManager.shadeModel(7424);
            GlStateManager.enableAlpha();
            GlStateManager.enableTexture2D();
            GlStateManager.popMatrix();

            // Check we are dragging
            if (dragging) {
                float saturation;
                float brightness;

                float satDiff = Math.min(dimension, Math.max(0, mouseX - x));

                if (satDiff == 0) {
                    saturation = 0;
                } else {
                    saturation = (float) MathUtil.roundDouble(((satDiff / dimension) * 100), 0);
                }

                float brightDiff = Math.min(height, Math.max(0, y + height - mouseY));

                if (brightDiff == 0) {
                    brightness = 0;
                } else {
                    brightness = (float) MathUtil.roundDouble(((brightDiff / height) * 100), 0);
                }

                finalColour = new Color(Color.HSBtoRGB(hue / 360, saturation / 100, brightness / 100));
            }

            // awful thing to check if we are dragging the hue slider
            for (SliderComponent sliderComponent : sliders) {
                if (sliderComponent.getSetting() == this.hue && sliderComponent.isDragging()) {
                    hue = ((NumberSetting) sliderComponent.getSetting()).getValue();
                    float[] hsb2 = Color.RGBtoHSB(finalColour.getRed(), finalColour.getGreen(), finalColour.getBlue(), null);
                    finalColour = new Color(Color.HSBtoRGB(hue / 360, hsb2[1], hsb2[2]));
                }
            }

            // Get final HSB colours
            float[] finHSB = Color.RGBtoHSB(finalColour.getRed(), finalColour.getGreen(), finalColour.getBlue(), null);

            // Picker X and Y
            float pickerX = x + (finHSB[1]) * dimension;
            float pickerY = y + (1 - (finHSB[2])) * height;

            // Draw picker highlight
            RenderUtil.drawRect(pickerX - 1.5f, pickerY - 1.5f, 3, 3, -1);
            RenderUtil.drawRect(pickerX - 1, pickerY - 1, 2, 2, finalColour.getRGB());
        }

        // Set final colour
        ((ColourSetting) getSetting()).setColour(ColourUtil.integrateAlpha(finalColour, alpha.getValue()));

        super.renderSetting(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (GuiUtil.mouseOver(getModuleButton().getPanel().getX(), getModuleButton().getOffset() + getOffset(), getModuleButton().getPanel().getX() + getModuleButton().getPanel().getWidth(), getModuleButton().getOffset() + getOffset() + 12, mouseX, mouseY)) {
            // Toggle open state
            animation.setState(!isExpanded());
        }

        float x = getModuleButton().getPanel().getX() + 4;
        float y = getModuleButton().getOffset() + getOffset() + 39;
        float dimension = 87;

        if (GuiUtil.mouseOver(x, y, x + dimension, y + dimension, mouseX, mouseY)) {
            dragging = true;
        }

        if (isExpanded()) {
            sliders.forEach(sliderComponent -> {
                sliderComponent.mouseClicked(mouseX, mouseY, mouseButton);

                SettingUpdateEvent settingUpdateEvent = new SettingUpdateEvent(getSetting());
                Paragon.INSTANCE.getEventBus().post(settingUpdateEvent);
            });
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        dragging = false;

        if (isExpanded()) {
            sliders.forEach(sliderComponent -> {
                sliderComponent.mouseReleased(mouseX, mouseY, mouseButton);
            });
        }

        super.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public float getHeight() {
        return 12 + (118 * animation.getAnimationFactor());
    }

    @Override
    public float getAbsoluteHeight() {
        return getHeight();
    }

    @Override
    public boolean isExpanded() {
        return animation.getAnimationFactor() > 0;
    }

}
