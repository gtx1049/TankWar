package com.game;

import javax.microedition.lcdui.game.LayerManager;

public class SceneManager extends LayerManager
{
	private int left;
	private int top;
	private int bottom;
	private int right;
	
	private int width;
	private int height;
	
	private boolean isXMoving = false;
	private boolean isYMoving = false;
	
	public int getCenterX(){
		return (left + width) >> 1;
	}
	
	public int getCenterY(){
		return (top + height) >> 1;
	}
	
	public void setXMoving(boolean isXMoving){
		this.isXMoving = isXMoving;
	}
	
	public void setYMoving(boolean isYMoving){
		this.isYMoving = isYMoving;
	}
	
	public SceneManager(int left, int top, int right, int bottom, int width, int height){
		
		super();
		
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.width = width;
		this.height = height;
	}
	
	public SceneManager()
	{
		super();
	}
	
	public void move(int direction)
	{
		if ((!isXMoving && (direction == Const.LEFT || direction == Const.RIGHT) || (!isYMoving && (direction == Const.UP || direction == Const.DOWN))))
			return;
		
		switch(direction)
		{
		case Const.UP:
			top = top - Const.TANKSPEED < 0 ? 0 : top - Const.TANKSPEED;
			break;
		case Const.DOWN:
			top = top + Const.TANKSPEED > bottom - height ? bottom - height : top + Const.TANKSPEED;
			break;
		case Const.LEFT:
			left = left - Const.TANKSPEED < 0 ? 0 : left - Const.TANKSPEED;
			break;
		case Const.RIGHT:
			left = left + Const.TANKSPEED > right - width ? right - width : left + Const.TANKSPEED;
			break;
		}
		
		if (top == 0 || top == bottom - height)
			isYMoving = false;
		if (left == 0 || left == right - width)
			isXMoving = false;
		
		this.setViewWindow(left, top, width, height);
	}
}
