package com.paragon.client.shader.shaders

import com.paragon.client.shader.Shader
import org.lwjgl.opengl.GL20.*
import java.awt.Color

class DiamondsShader : Shader("/assets/paragon/glsl/shaders/diamonds.frag") {

    private var colour: Color? = null
    private var size = 0f
    private var spacing = 0f

    fun setColor(colour: Color?) {
        this.colour = colour
    }

    fun setSize(size: Float) {
        this.size = size
    }

    fun setSpacing(spacing: Float) {
        this.spacing = spacing
    }

    override fun setupUniforms() {
        setupUniform("texture")
        setupUniform("time")
        setupUniform("size")
        setupUniform("spacing")
        setupUniform("colour")
    }

    override fun updateUniforms() {
        glUniform1i(getUniform("texture"), 0)
        glUniform1f(getUniform("time"), time.toFloat())
        glUniform4f(getUniform("colour"), colour!!.red / 255f, colour!!.green / 255f, colour!!.blue / 255f, colour!!.alpha / 255f)
        glUniform1f(getUniform("size"), size)
        glUniform1f(getUniform("spacing"), spacing)
    }

}