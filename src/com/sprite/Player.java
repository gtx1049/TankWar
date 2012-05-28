package com.sprite;

import java.util.Vector;

import javax.microedition.lcdui.Image;

import com.game.Const;

public class Player extends MoveObject
{
	
	private int width;
	private int height;
	
	public Player(Image image, int frameWidth, int frameHeight, int speed)
	{
		super(image, frameWidth, frameHeight);
		
		this.speed = speed;
		this.direction = Const.UP;
		// TODO Auto-generated constructor stub
	}
	
	public void setMoveArea(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	public boolean judgeCollideAct(Vector walls, Vector enemys, Vector shell)
	{
		
		System.out.println("Player Position : (" + ( getX() + getWidth() / 2 ) + ", " + ( getY() + getHeight() / 2 ) + ")");
		System.out.println("Width & Height : " + width + ", " + height);
		
		boolean isCollide = false;
		
		if (getX() <= 0)
			System.out.println("X < 0");
		if (getY() <= 0)
			System.out.println("Y < 0");
		if (getX() >= width)
			System.out.println(" X > Width");
		if (getY() >= height)
			System.out.println("Y > Height");
		
//		if (getX() <= 0 || getY() <= 0 || getX() >= width || getY() >= height)
//			return true;
		
		this.doAction();
		for(int i = walls.size() - 1; i >= 0; i--)
		{
			if(this.collidesWith((Wall)walls.elementAt(i), false))
			{
				this.undo();
				isCollide = true;
				break;
			}
		}
		
		for(int i = enemys.size() - 1; i >= 0; i--)
		{
			if(this.collidesWith((Enemy)enemys.elementAt(i), false))
			{
				this.undo();
				isCollide = true;
				break;
			}
		}
		
		for(int i = shell.size() - 1; i >= 0; i--)
		{
			if(this.collidesWith((Shell)shell.elementAt(i), false))
			{
				this.undo();
				isCollide = true;
				break;
			}
		}
		
		return isCollide;
		
	}
	
}


