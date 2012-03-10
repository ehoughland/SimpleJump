package ehoughl.krtyler1.simplejump;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class PlatformObject 
{
	private FloatBuffer vertexBuffer;   // buffer holding the vertices
	
	public float getYPosition()
	{
		return vertices[5];
	}
	
	public float[] getXRange()
	{
		float[] f = new float[2];
		f[0] = vertices[4];
		f[1] = vertices[10];
		
		return f;
		
	}
	
    private float vertices[];
 
    public PlatformObject(float vertices[]) 
    {
    	this.vertices = vertices;
        // a float has 4 bytes so we allocate for each coordinate 4 bytes
        ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        vertexByteBuffer.order(ByteOrder.nativeOrder());
 
        // allocates the memory from the byte buffer
        vertexBuffer = vertexByteBuffer.asFloatBuffer();
 
        // fill the vertexBuffer with the vertices
        vertexBuffer.put(vertices);
 
        // set the cursor position to the beginning of the buffer
        vertexBuffer.position(0);
    }
 
    /** The draw method for the square with the GL context */
    public void draw(GL10 gl) 
    {
        //gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        
        // set the colour for the square
        gl.glColor4f(0.0f, 1.0f, 0.0f, 0.5f);
 
        // Point to our vertex buffer
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
 
        // Draw the vertices as triangle strip
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);
 
        //Disable the client state before leaving
        //gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}