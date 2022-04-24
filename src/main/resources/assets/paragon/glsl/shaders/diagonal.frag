#version 120

uniform sampler2D texture;
uniform vec2 texelSize;
uniform vec2 resolution;
uniform float time;

uniform float size;
uniform float spacing;
uniform vec4 colour;

void main() {
    vec4 centerCol = texture2D(texture, gl_TexCoord[0].xy);

    if (centerCol.a > 0) {
        float x = time + gl_FragCoord.x / size;
        float y = gl_FragCoord.y / size;

        if (int(mod(float(x + y), float(spacing))) == 0) {
            gl_FragColor = colour;
        } else {
            gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);
        }
    }
}