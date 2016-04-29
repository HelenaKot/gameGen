package com.fancytank.generated.game.mesh;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ShapeShaderProgram {
    static final String VERT_SHADER =
            "attribute vec2 a_position;\n" +
                    "attribute vec4 a_color;\n" +
                    "uniform mat4 u_projTrans;\n" +
                    "varying vec4 vColor;\n" +
                    "void main() {\n" +
                    "	vColor = a_color;\n" +
                    "	gl_Position =  u_projTrans * vec4(a_position.xy, 0.0, 1.0);\n" +
                    "}";

    static final String FRAG_SHADER =
            "#ifdef GL_ES\n" +
                    "precision mediump float;\n" +
                    "#endif\n" +
                    "varying vec4 vColor;\n" +
                    "void main() {\n" +
                    "	gl_FragColor = vColor;\n" +
                    "}";

    public static ShaderProgram createMeshShader() {
        ShaderProgram.pedantic = false;
        ShaderProgram shader = new ShaderProgram(VERT_SHADER, FRAG_SHADER);
        String log = shader.getLog();
        if (!shader.isCompiled())
            throw new GdxRuntimeException(log);
        if (log != null && log.length() != 0)
            System.out.println("Shader Log: " + log);
        return shader;
    }
}
