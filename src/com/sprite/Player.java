package com.sprite;


import javax.microedition.lcdui.Image;

import com.game.Const;

public class Player extends Tank
{
	
	int type = Const.NORMALCANNON;
	
	boolean isUnbeatable = false;
	
	public Player(Image image, int frameWidth, int frameHeight, int speed, Image imgshell)
	{
		super(image, frameWidth, frameHeight, imgshell);
		
		this.speed = speed;
		this.direction = Const.UP;

		// TODO Auto-generated constructor stub
	}
	
	public void getItem(int itemType)
	{
		switch (itemType)
		{
		case Const.SUPERCANNON:
			setFrame(1);
			type = Const.SUPERCANNON;
			break;
		case Const.UNBEATABLE:
			if (type == Const.NORMALCANNON)
				setFrame(2);
			else if (type == Const.SUPERCANNON)
				setFrame(3);
			
			isUnbeatable = true;
			
			break;
			
		}
	}
	
	public void backToNormal()
	{
		if (type == Const.SUPERCANNON)
			setFrame(1);
		else
			setFrame(0);
		
		isUnbeatable = false;
	}
	
	public boolean isUnbeatable() {
		return isUnbeatable;
	}

	public boolean judgeCollideAct(Wall[] walls, Enemy[] enemys, int[] spritecount)
	{
		
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


