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

	private int birthTime = 20;
	
	public Enemy(Image image, int frameWidth, int frameHeight, int speed, Image imgshell, Image imgexplosion, int type, int id)
	{
		super(image, frameWidth, frameHeight, imgshell, imgexplosion);
		// TODO Auto-generated constructor stub
		
		this.speed = speed;
		setDirection(Const.DOWN);
		
		this.type = type;
		
		if (type == Const.GREENENEMY || type == Const.NORMALENEMY)
		{
			setFrame(1);
		}
		else if (type == Const.REDENEMY)
		{
			setFrame(0);
		}
		
		count = 20;
		
		Date date = new Date();
		
		random = new Random(date.getTime() + id);
		
	}
	
	public int getType()
	{
		return type;
	}
	
	public void setBirthTime(int time)
	{
		this.birthTime = time;
	}
	
	//判断是否被击中
	public boolean onHit()
	{
		//System.out.println("On Hit");
		
		System.out.println("Type : " + type);
		
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
			count = 20;
			undo();
			return true;
		}
		if (getY() <= -2 && direction == Const.UP)
		{
			count = 20;
			undo();
			return true;
		}
		if (getX() + getWidth() >= this.width + 1 && direction == Const.RIGHT)
		{
			count = 20;
			undo();
			return true;
		}
		if (getY() + getHeight() >= this.height + 1 && direction == Const.DOWN)
		{
			count = 20;
			undo();
			return true;		
		}
		
		if (birthTime != 0)
		{
			birthTime--;
			return false;
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
				System.out.println("First Tank Position : " + getCenterX() + "," + getCenterY());
				System.out.println("Second Tank Position : " + enemys[i].getCenterX() + "," + enemys[i].getCenterY());
				
				
				if (!( Math.sqrt(((getCenterX() - enemys[i].getCenterX()) * (getCenterX() - enemys[i].getCenterX()) + 
						(getCenterY() - enemys[i].getCenterY()) * (getCenterY() - enemys[i].getCenterY()))) < Const.GRIDSIZE / 4)) 
				{
					this.undo();
					isCollide = true;
					break;
				}
				else
				{
					if (direction - enemys[i].getDirection() == 0)
						this.birthTime = 5;
				}
				
				//System.out.println("Collide With Enemy!");
			}
		}
		
		if (collidesWith(player, false))
		{
			if (!( Math.sqrt(((getCenterX() - player.getCenterX()) * (getCenterX() - player.getCenterX()) + 
					(getCenterY() - player.getCenterY()) * (getCenterY() - player.getCenterY()))) < Const.GRIDSIZE / 2)) 
			{
				undo();
				isCollide = true;
			}
			else
			{
				this.birthTime = 5;
			}
		
		}
		
		if (isCollide)
			count = 20;
		
		return isCollide;
		
	}
}
