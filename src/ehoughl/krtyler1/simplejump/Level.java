package ehoughl.krtyler1.simplejump;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Bitmap;

public class Level 
{
	private ArrayList<PlatformObject> platformList = new ArrayList<PlatformObject>();
	private ArrayList<CloudObject> cloudList = new ArrayList<CloudObject>();
	private Bitmap platformBmp;
	private Bitmap cloudBmp;
	private int levels = 15;
	static final private float gravity = 0.005f;
	
	public Level(Bitmap platformBmp, Bitmap cloudBmp)
	{
		this.platformBmp = platformBmp;
		this.cloudBmp = cloudBmp;
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
	
	public ArrayList<CloudObject> getClouds ()
	{
		return cloudList;
	}
	
	public void generateLevel()
	{
		float start = -1.2f;
		
		for(int i = 0; i < levels; i++)
		{
			float[] vertices = vertice(start);
			PlatformObject po = new PlatformObject(vertices);
			po.loadBitmap(platformBmp);
			platformList.add(po);
			start += 0.6f;
		}
		
		Random r = new Random();
		float cloudStartY = (float)r.nextInt(1601)/1000.0f;
		cloudStartY -= 1;
		
		//Random r2 = new Random();
		//int numberOfClouds = r2.nextInt(25);
		int numberOfClouds = 15;
		
		for(int i = 0; i < numberOfClouds; i++)
		{
			float[] vertices = cloudVertice(cloudStartY);
			CloudObject c = new CloudObject(vertices);
			c.loadBitmap(cloudBmp);
			cloudList.add(c);
			cloudStartY += 1.1f + (float)r.nextInt(1801)/1000.0f;
		}
	}
	
	public float[] cloudVertice (float y)
	{
		Random r = new Random();
		float rand = (float)r.nextInt(1601)/1000.0f;
		rand -= 1;
		
		float x1 = rand;
		float x2 = x1 + 0.9f;
		float y1 = y;
		float y2 = y1 + 0.45f;
		float z = -2f;
		
		float vertices[] = 
	    {
            x1,  y1,  z,        // V1 - bottom left
            x1,  y2,  z,        // V2 - top left
            x2,  y1,  z,        // V3 - top right
            x2,  y2,  z         // V4 - bottom right
	    };
		
		return vertices;
	}
	
	public void addPlatforms(int numberToAdd, float startPoint, float cloudStartPoint)
	{
		float start = startPoint;
		
		for(int i = 0; i < numberToAdd; i++)
		{
			platformList.remove(i);
			//cloudList.remove(i);
		}
		
		for(int i = 0; i < numberToAdd; i++)
		{
			float[] vertices = vertice(start);
			PlatformObject po = new PlatformObject(vertices);
			po.loadBitmap(platformBmp);
			platformList.add(po);
			start += 0.6f;
		}
		
		Random r = new Random();
		float cloudStartY = cloudStartPoint += (float)r.nextInt(1601)/1000.0f;
		cloudStartY -= 1;
		
		//Random r2 = new Random();
		//int numberOfClouds = r2.nextInt(25);
		int numberOfClouds = 15;
		
		for(int i = 0; i < numberOfClouds; i++)
		{
			float[] vertices = cloudVertice(cloudStartY);
			CloudObject c = new CloudObject(vertices);
			c.loadBitmap(cloudBmp);
			cloudList.add(c);
			cloudStartY += 1.1f + (float)r.nextInt(1801)/1000.0f;
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
