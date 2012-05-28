package com.sprite;

import java.util.Vector;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

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
	
	public boolean judgeCollideAct(Wall[] walls, Enemy[] enemys, Shell[] shell, int[] spritecount)
	{
		
//		System.out.println("Player Position : (" + ( getX() + getWidth() / 2 ) + ", " + ( getY() + getHeight() / 2 ) + ")");
//		System.out.println("Width & Height : " + width + ", " + height);
		
		boolean isCollide = false;
		
		if (getX() <= 0 && direction == Const.LEFT)
			return true;
		if (getY() <= 0 && direction == Const.UP)
			return true;
		if (getX() + getWidth() >= width && direction == Const.RIGHT)
			return true;
		if (getY() + getHeight() >= height && direction == Const.DOWN)
			return true;
		
		
		this.doAction();
		for(int i = 0; i < spritecount[Const.WALLCOUNT]; i++)
		{
			if(this.collidesWith(walls[i], false))
			{
				this.undo();
				isCollide = true;
				break;
			}
		}
		
		for(int i = 0; i < spritecount[Const.ENEMYCOUNT]; i++)
		{
			if(this.collidesWith(enemys[i], false))
			{
				this.undo();
				isCollide = true;
				break;
			}
		}
		
		for(int i = 0; i < spritecount[Const.SHELLCOUNT]; i++)
		{
			if(this.collidesWith(shell[i], false))
			{
				this.undo();
				isCollide = true;
				break;
			}
		}
		
		return isCollide;
		
	}
	
}


