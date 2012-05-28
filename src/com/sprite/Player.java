package com.sprite;


import javax.microedition.lcdui.Image;

import com.game.Const;

public class Player extends Tank
{
	
	public Player(Image image, int frameWidth, int frameHeight, int speed, Image imgshell)
	{
		super(image, frameWidth, frameHeight, imgshell);
		
		this.speed = speed;
		this.direction = Const.UP;

		// TODO Auto-generated constructor stub
	}
	
	
	public boolean judgeCollideAct(Wall[] walls, Enemy[] enemys, int[] spritecount)
	{
		
//		System.out.println("Player Position : (" + ( getX() + getWidth() / 2 ) + ", " + ( getY() + getHeight() / 2 ) + ")");
//		System.out.println("Width & Height : " + width + ", " + height);
		
		boolean isCollide = false;
		
		if (getX() <= -2 && direction == Const.LEFT)
			return true;
		if (getY() <= -2 && direction == Const.UP)
			return true;
		if (getX() + getWidth() >= this.width + 1 && direction == Const.RIGHT)
			return true;
		if (getY() + getHeight() >= this.height + 1 && direction == Const.DOWN)
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
		
		return isCollide;
		
	}
	
}


