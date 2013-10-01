package mtRoboLib;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MTRobo {
	private Robot r;
	private double sensitivity;
	
	public MTRobo()
	{
		try {
			r = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sensitivity = 3; // Make an option to change
	}

	public void click(int leftRightCenter)
	{
		switch(leftRightCenter){
		case 0:
			r.mousePress(InputEvent.BUTTON1_MASK);
			r.mouseRelease(InputEvent.BUTTON1_MASK);
			break;
		case 1:
			r.mousePress(InputEvent.BUTTON3_MASK);
			r.mouseRelease(InputEvent.BUTTON3_MASK);
			break;
		case 2:
			r.mousePress(InputEvent.BUTTON2_MASK);
			r.mouseRelease(InputEvent.BUTTON2_MASK);
			break;
		default:
			break;
		}
	}
	
	public void mousePress(int leftRightCenter)
	{
		switch(leftRightCenter){
		case 0:
			r.mousePress(InputEvent.BUTTON1_MASK);
			break;
		case 1:
			r.mousePress(InputEvent.BUTTON3_MASK);
			break;
		case 2:
			r.mousePress(InputEvent.BUTTON2_MASK);
			break;
		default:
			break;
		}
	}
	
	public void mouseRelease()
	{
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON3_MASK);
		r.mouseRelease(InputEvent.BUTTON2_MASK);
	}
	
	public void moveMouse(float dx, float dy)
	{
		dx *= sensitivity;
		dy *= sensitivity;
		
		Point ml = MouseInfo.getPointerInfo().getLocation();
		float x = ml.x;
		float y = ml.y;
		
		float newx = x+dx;
		float newy = y+dy;
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		
		if (newx<0)
			newx = 0;
		if (newy<0)
			newy = 0;
		if (newx>d.width)
			newx = d.width-1;
		if (newy>d.height)
			newy = d.height-1;
		
		r.mouseMove((int)newx, (int)newy);
	}
	
	public void hScroll(float x) // Not Implemented...
	{
		
	}
	
	public void vScroll(float y)
	{
		r.mouseWheel((int)(y/sensitivity));
	}
	
	public void zoomOut()
	{
		r.keyPress(KeyEvent.VK_META);
		r.keyPress(KeyEvent.VK_MINUS);
		r.keyRelease(KeyEvent.VK_META);
		r.keyRelease(KeyEvent.VK_MINUS);
	}
	
	public void zoomIn()
	{
		r.keyPress(KeyEvent.VK_CONTROL);
		r.mouseWheel(50);
		r.keyRelease(KeyEvent.VK_CONTROL);
		
//		r.keyPress(KeyEvent.VK_META);
//		r.keyPress(KeyEvent.VK_EQUALS);
//		r.keyRelease(KeyEvent.VK_META);
//		r.keyRelease(KeyEvent.VK_EQUALS);
	}
	
}
