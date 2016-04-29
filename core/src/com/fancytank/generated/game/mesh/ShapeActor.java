//package com.fancytank.generated.game.mesh;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Mesh;
//import com.badlogic.gdx.graphics.VertexAttribute;
//import com.badlogic.gdx.graphics.VertexAttributes;
//import com.badlogic.gdx.graphics.g2d.Batch;
//import com.badlogic.gdx.graphics.glutils.ShaderProgram;
//import com.badlogic.gdx.scenes.scene2d.Actor;
//
//import static com.fancytank.generated.game.mesh.ShapeShaderProgram.createMeshShader;
//
//public class ShapeActor extends Actor {
//    public static final int POSITION_COMPONENTS = 2;
//    public static final int COLOR_COMPONENTS = 1;
//    //Total number of components for all attributes
//    public static final int NUM_COMPONENTS = POSITION_COMPONENTS + COLOR_COMPONENTS;
//    public static final int MAX_VERTS = 3;
//
//    public static ShaderProgram shader = createMeshShader();
//
//    Mesh mesh = new Mesh(true, MAX_VERTS, 0,
//            new VertexAttribute(VertexAttributes.Usage.Position, POSITION_COMPONENTS, "a_position"),
//            new VertexAttribute(VertexAttributes.Usage.ColorPacked, 4, "a_color"));
//
//    protected float[] verts = new float[MAX_VERTS * NUM_COMPONENTS];
//
//    ShapeActor() {
//
//    }
//
//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        //super.draw(batch, parentAlpha);
//        batch.setShader(shader);
//        //PolygonSpriteBatch a;
//    }
//
//    @Override
//    public void act(float delta) {
//        super.act(delta);
//    }
//
//    int idx = 0;
//
//    void createVerts(float x, float y, float width, float height, Color color) {
//        //we don't want to hit any index out of bounds exception...
//        //so we need to flush the batch if we can't store any more verts
////        if (idx == verts.length)
////            flush();
//        float c = color.toFloatBits();
//
//        //now we push the vertex data into our array
//        //we are assuming (0, 0) is lower left, and Y is up
//
//        //bottom left vertex
//        verts[idx++] = x;           //Position(x, y)
//        verts[idx++] = y;
//        verts[idx++] = c;
//
//        //top left vertex
//        verts[idx++] = x;           //Position(x, y)
//        verts[idx++] = y + height;
//        verts[idx++] = c;
//
//        //bottom right vertex
//        verts[idx++] = x + width;    //Position(x, y)
//        verts[idx++] = y;
//        verts[idx++] = c;
//    }
//
//    void flush() {
//        //if we've already flushed
//        if (idx == 0)
//            return;
//
//        //sends our vertex data to the mesh
//        mesh.setVertices(verts);
//
//        //no need for depth...
//        Gdx.gl.glDepthMask(false);
//
//        //enable blending, for alpha
//        Gdx.gl.glEnable(GL20.GL_BLEND);
//        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//
//        //number of vertices we need to render
//        int vertexCount = (idx / NUM_COMPONENTS);
//
//        //update the camera with our Y-up coordiantes
//        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//
//        //start the shader before setting any uniforms
//        shader.begin();
//
//        //update the projection matrix so our triangles are rendered in 2D
//        shader.setUniformMatrix("u_projTrans", cam.combined);
//
//        //render the mesh
//        mesh.render(shader, GL20.GL_TRIANGLES, 0, vertexCount);
//
//        shader.end();
//
//        //re-enable depth to reset states to their default
//        Gdx.gl.glDepthMask(true);
//
//        //reset index to zero
//        idx = 0;
//    }
//}