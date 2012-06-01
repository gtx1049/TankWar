package com.sprite;

import java.util.Date;
import java.util.Random;

import javax.microedition.lcdui.Image;

import com.game.Const;

public class Enemy extends Tank
{
	private int type;
	private Random random;
	
	private int count = 0;

	public Enemy(Image image, int frameWidth, int frameHeight, int speed, Image imgshell, Image imgexplosion, int type, int id)
	{
		super(image, frameWidth, frameHeight, imgshell, imgexplosion);
		// TODO Auto-generated constructor stub
		
		this.speed = speed;
		setDirection(Const.DOWN);
		
		this.type = type;
		
		if (type == Const.GREENENEMY)
		{
			setFrame(2);
		}
		
		Date date = new Date();
		
		random = new Random(date.getTime() + id);
		
	}
	
	public int getType()
	{
		return type;
	}
	
	//判断是否被击中
	public boolean onHit()
	{
		//System.out.println("On Hit");
		
		if (type == Const.NORMALENEMY)
			return true;
		else if (type == Const.REDENEMY)
		{
			setFrame(3);
			type = Const.NORMALENEMY;
		}
		else if (type == Const.GREENENEMY)
		{
			setFrame(2);
			type = Const.BLUEENEMY;
		}
		else if (type == Const.BLUEENEMY)
		{
			setFrame(3);
			type = Const.NORMALENEMY;
		}
		
		return false;
	}
	
	//敌人AI每回要进行的动作
	public void doAction()
	{
		if (count == 20)
		{
			int factor = random.nextInt() % 4;
			
			switch (factor)
			{
			case 0:
				setDirection(Const.UP);
				break;
			case 1:
				setDirection(Const.RIGHT);
				break;
			case 2:
				setDirection(Const.DOWN);
				break;
			case 3:
				setDirection(Const.LEFT);
				break;
			}
			
			count = 0;
		}
		
		move();
		count ++;
	}
	
	//判断是否与传进的精灵以及四周发生了碰撞
	public boolean judgeCollideAct(Wall[] walls, Enemy[] enemys, int[] spritecount, Player player)
	{
		
		boolean isCollide = false;
		
		this.doAction();
		
		if (getX() <= -2 && direction == Const.LEFT)
		{
			undo();
			return true;
		}
		if (getY() <= -2 && direction == Const.UP)
		{
			undo();
			return true;
		}
		if (getX() + getWidth() >= this.width + 1 && direction == Const.RIGHT)
		{
			undo();
			return true;
		}
		if (getY() + getHeight() >= this.height + 1 && direction == Const.DOWN)
		{
			undo();
			return true;		
		}
		
		for(int i = 0; i < spritecount[Const.WALLCOUNT]; i++)
		{
			if(this.collidesWith(walls[i], false))
			{
				this.undo();
				isCollide = true;
				//System.out.println("Collide with wall");
				break;
			}
		}
		
		for(int i = 0; i < spritecount[Const.ENEMYCOUNT]; i++)
		{
			if (this == enemys[i])
				continue;
			if(this.collidesWith(enemys[i], false))
			{
				this.undo();
				isCollide = true;
				//System.out.println("Collide With Enemy!");
				break;
			}
		}
		
		if (collidesWith(player, false))
		{
			undo();
			isCollide = true;
		}
		
		if (isCollide)
			count = 20;
		
		return isCollide;
		
	}
}
