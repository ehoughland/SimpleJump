package ehoughl.krtyler1.simplejump;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class HeroGameObject implements GameObject
{
	private int xPosition;
	private int yPosition;
	private Bitmap bmp;
	private boolean alive = true;
	private double ySpeed;
	private double xSpeed;
	private final static int initialJumpVelocity = -25;
	private double timeSinceJump = 0; 
	
	public HeroGameObject(Bitmap bmp)
	{
		this.bmp = bmp;
	}
	
	public void jump()
	{
		ySpeed = initialJumpVelocity;
		timeSinceJump = 0;
	}
	
	public void move()
	{
		if(alive)
		{
			timeSinceJump += 1;
			ySpeed = ySpeed + (2.5/Math.sqrt(timeSinceJump));
			xPosition += xSpeed;
			yPosition += ySpeed;
		}
	}
	
	@Override
	public GameObjectType getType() 
	{
		return GameObjectType.Hero;
	}

	@Override
	public void draw(Canvas canvas, int x, int y) 
	{
		Rect dest = new Rect(x, y, x + bmp.getWidth(), y + bmp.getHeight()); 
		canvas.drawBitmap(bmp, null, dest, null);
	}
	
	@Override
	public int getX() 
	{
		return xPosition;
	}

	@Override
	public int getY() 
	{
		return yPosition;
	}

	@Override
	public void setX(int x) 
	{
		xPosition += x;
	}

	@Override
	public void setY(int y)
	{
		yPosition = y;
	}
}
