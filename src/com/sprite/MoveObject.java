package com.sprite;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.game.Const;

public class MoveObject extends Sprite
{

	private int speed;
	private int direction;
	
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
		this.direction = direction;
	}
	
	public boolean collideObject(Sprite s)
	{
		return collidesWith(s, true);
	}
	
}
