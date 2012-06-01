package com.sprite;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.game.Const;

//可动物件包括坦克，子弹

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
	
	//行动规则
	public void doAction()
	{
		move();
	}
	
	//移动范围
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
	
	//感觉方向与速度走一步
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
	
	//悔步，当发生碰撞时，使用此函数回到原来位置
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


	
	public boolean collideObject(Sprite s)
	{
		return collidesWith(s, true);
	}
	
}
