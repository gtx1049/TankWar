package com.sprite;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.game.Const;

public class MoveObject extends Sprite
{

	protected int speed;
	protected int direction;
	
	protected boolean isMoving = false;
	
	protected int nextCordinate = -1;
	
	protected int width;
	protected int height;
	
	public MoveObject(Image image, int frameWidth, int frameHeight)
	{
		super(image, frameWidth, frameHeight);
		// TODO Auto-generated constructor stub
	}
	
	public void doAction()
	{
		move();
	}
	
	public void setMoveArea(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public boolean isMoving(){
		return isMoving;
	}
	
	public boolean isMoveFinish()
	{
		if (direction == Const.UP || direction == Const.DOWN)
		{
			if (nextCordinate == getY())
			{
				nextCordinate = -1;
				return true;
			}
		}
		else if (direction == Const.LEFT || direction == Const.RIGHT)
		{
			if (nextCordinate == getX())
			{
				nextCordinate = -1;
				return true;
			}
		}
		
		return false;
	}
	
	
	public void move()
	{
			switch(direction)
			{
			case Const.UP:
				nextCordinate = getY() - speed;
				move(0, -speed);
				break;
			case Const.DOWN:
				nextCordinate = getY() + speed;
				move(0, speed);
				break;
			case Const.LEFT:
				nextCordinate = getX() - speed;
				move(-speed, 0);
				break;
			case Const.RIGHT:
				nextCordinate = getX() + speed;
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
		//≤‚ ‘¥˙¬Î
		switch(direction)
		{
		case Const.UP:
			System.out.println(getX()+","+getY());
			setTransform(TRANS_NONE);
			break;
		case Const.DOWN:
			System.out.println(getX()+","+getY());
			setTransform(TRANS_ROT180);
			break;
		case Const.RIGHT:
			System.out.println(getX()+","+getY());
			setTransform(TRANS_ROT90);
			break;
		case Const.LEFT:
			System.out.println(getX()+","+getY());
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
