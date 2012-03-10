package ehoughl.krtyler1.simplejump;

import java.util.ArrayList;

public class Level {
	private ArrayList<PlatformObject> platformList = new ArrayList<PlatformObject>();
	private int levels = 50;
	
	public Level()
	{
		generateLevel();
	}
	
	public ArrayList<PlatformObject> getPlatforms ()
	{
		return platformList;
	}
	
	public void generateLevel()
	{
		float start = -0.05f;
		for(int i = 0; i < levels; i++)
		{
			float[] vertices = vertice(start);
			PlatformObject po = new PlatformObject(vertices);
			platformList.add(po);
			start += 0.5f;
		}
	}
	
	public float[] vertice (float y)
	{
		float x1 = (float) ((float) (Math.random() * 1.6) - 1.0);
		float x2 = x1 + 0.4f;
		float y1 = y;
		float y2 = y1 + 0.1f;
		float z = 0.0f;
		float vertices[] = 
	    {
	            x1,  y1,  z,        // V1 - bottom left
	            x1,  y2,  z,        // V2 - top left
	            x2,  y1,  z,        // V3 - bottom right
	            x2,  y2,  z         // V4 - top right
	    };
		return vertices;
	}
	
}
