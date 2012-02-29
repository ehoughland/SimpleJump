package ehoughl.krtyler1.simplejump;

import android.graphics.Canvas;

public interface GameObject 
{
	public GameObjectType getType();
	public void draw(Canvas canvas, int x, int y);
	public int getX();
	public int getY();
	public void setX(int x);
	public void setY(int y);
}
