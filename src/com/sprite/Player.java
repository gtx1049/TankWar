package com.sprite;


import javax.microedition.lcdui.Image;

import com.game.Const;

//玩家类

public class Player extends Tank
{
	
	boolean isUnbeatable = false;
	
	private int floatTime;
	
	public Player(Image image, int frameWidth, int frameHeight, int speed, Image imgshell, Image imgexplosion)
	{
		super(image, frameWidth, frameHeight, imgshell, imgexplosion);
		
		this.speed = speed;
		this.direction = Const.UP;

		// TODO Auto-generated constructor stub
	}
	
	
	
	public int getFloatTime() {
		return floatTime;
	}



	public void setFloatTime(int floatTime) {
		this.floatTime = floatTime;
	}



	//得到道具时的反应
	public void getItem(int itemType)
	{
		switch (itemType)
		{
		case Const.SUPERCANNON:
			setFrame(1);
			fireType = Const.SUPERCANNON;
			break;
		case Const.UNBEATABLE:
			if (fireType == Const.NORMALCANNON)
				setFrame(2);
			else if (fireType == Const.SUPERCANNON)
				setFrame(3);
			
			isUnbeatable = true;
			
			break;
			
		}
	}
	
	//返回普通状态
	public void backToNormal()
	{
		if (fireType == Const.SUPERCANNON)
			setFrame(1);
		else
			setFrame(0);
		
		isUnbeatable = false;
	}
	
	//无敌
	public boolean isUnbeatable() {
		return isUnbeatable;
	}
	
	//判断相撞并进行动作
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
		
		if (floatTime != 0)
		{
			floatTime --;
			return false;
		}
		
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
			if(this.collidesWith(enemys[i], false) && !enemys[i].getBeingexploesd())
			{
				this.undo();
				isCollide = true;
				break;
			}
		}
		
		return isCollide;
		
	}
	
}


