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
		
		boolean isCollide = false;
		
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


