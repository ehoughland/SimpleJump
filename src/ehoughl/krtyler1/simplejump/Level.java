package ehoughl.krtyler1.simplejump;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Bitmap;

public class Level 
{
	private ArrayList<PlatformObject> platformList = new ArrayList<PlatformObject>();
	private Bitmap platformBmp;
	private int levels = 15;
	static final private float gravity = 0.005f;
	
	public Level(Bitmap platformBmp)
	{
		this.platformBmp = platformBmp;
		generateLevel();
	}
	
	public float getGravity()
	{
		return gravity;
	}
	
	public ArrayList<PlatformObject> getPlatforms ()
	{
		return platformList;
	}
	
	public void generateLevel()
	{
		float start = -1.2f;
		
		for(int i = 0; i < levels; i++)
		{
			float[] vertices = vertice(start);
			PlatformObject po = new PlatformObject(vertices, platformBmp);
			po.loadBitmap(platformBmp);
			platformList.add(po);
			start += 0.6f;
		}
	}
	
	public void addPlatforms(int numberToAdd)
	{
		float start = -1.2f;
		
		for(int i = levels; i < levels + numberToAdd; i++)
		{
			float[] vertices = vertice(start);
			PlatformObject po = new PlatformObject(vertices, platformBmp);
			po.loadBitmap(platformBmp);
			platformList.add(po);
			start += 0.6f;
		}
		
		levels += numberToAdd;
	}
	
	public float[] vertice (float y)
	{
		Random r = new Random();
		float rand = (float)r.nextInt(1601)/1000.0f;
		rand -= 1;
		
		float x1 = rand;
		float x2 = x1 + 0.4f;
		float y1 = y;
		float y2 = y1 + 0.075f;
		float z = 0.0f;
		
		float vertices[] = 
	    {
            x1,  y1,  z,        // V1 - bottom left
            x1,  y2,  z,        // V2 - top left
            x2,  y1,  z,        // V3 - top right
            x2,  y2,  z         // V4 - bottom right
	    };
		
		return vertices;
	}
}
