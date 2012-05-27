package com.sprite;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.game.Const;

public class MoveObject extends Sprite
{

	protected int speed;
	protected int direction;
	
	public MoveObject(Image image, int frameWidth, int frameHeight)
	{
		super(image, frameWidth, frameHeight);
		// TODO Auto-generated constructor stub
	}
	
	public void doAction()
	{
		move();
	}
	
	public void move()
	{
		switch(direction)
		{
		case Const.UP:
			move(0, -speed);
			break;
		case Const.DOWN:
			move(0, speed);
			break;
		case Const.LEFT:
			move(-speed, 0);
			break;
		case Const.RIGHT:
			move(speed, 0);
			break;
		}
	}
	
	public void undo()
	{
		switch(direction)
		{
		case Const.UP:
			move(0, speed);
			break;
		case Const.DOWN:
			move(0, -speed);
			break;
		case Const.LEFT:
			move(speed, 0);
			break;
		case Const.RIGHT:
			move(-speed, 0);
			break;
		}
	}
	
	public void initDirection(int direction)
	{
		this.direction = direction;
	}

	public int getSpeed() 
	{
		return speed;
	}

	public void setSpeed(int speed) 
	{
		this.speed = speed;
	}

	public int getDirection() 
	{
		return direction;
	}

	public void setDirection(int direction) 
	{
		
		
		int time = direction - this.direction;
		//≤‚ ‘¥˙¬Î
		switch(direction)
		{
		case Const.UP:
			setTransform(TRANS_NONE);
			break;
		case Const.DOWN:
			setTransform(TRANS_ROT180);
			break;
		case Const.RIGHT:
			setTransform(TRANS_ROT90);
			break;
		case Const.LEFT:
			setTransform(TRANS_ROT270);
			break;

		default:
			break;
		}
		
		this.direction = direction;
	}
	
	public boolean collideObject(Sprite s)
	{
		return collidesWith(s, true);
	}
	
}
