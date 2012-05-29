package com.sprite;

import java.util.Date;
import java.util.Random;

import javax.microedition.lcdui.Image;

import com.game.Const;

public class Enemy extends Tank
{
	private int type;
	private Random random;

	public Enemy(Image image, int frameWidth, int frameHeight, int speed, Image imgshell, int type)
	{
		super(image, frameWidth, frameHeight, imgshell);
		// TODO Auto-generated constructor stub
		
		this.speed = speed;
		this.direction = Const.DOWN;
		
		if (type == Const.NORMALENEMY)
		{
			nextFrame();
			nextFrame();
			nextFrame();
		}
		
		Date date = new Date();
		
		random = new Random(date.getTime());
		
	}
	
	public boolean onHit()
	{
		if (type == Const.NORMALENEMY)
			return true;
		else if (type == Const.REDENEMY)
		{
			nextFrame();
			type = Const.GREENENEMY;
		}
		else if (type == Const.GREENENEMY)
		{
			nextFrame();
			type = Const.BLUEENEMY;
		}
		else if (type == Const.BLUEENEMY)
		{
			nextFrame();
			type = Const.NORMALENEMY;
		}
		
		return false;
	}
	
	public MoveObject emitShell(Image image, int width, int height)
	{
		if (random.nextInt() % 3 == 0)
		{
			Shell shell = new Shell(image, width, height, this.getDirection(), Const.ENEMYFIRE);
			
			shell.setPosition(getX(), getY());
			shell.setDirection(getDirection());
			
		}
		return null;
	}
	
	public void doAction()
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
		
		move();
	}
	
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
				System.out.println("Collide with wall");
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
				System.out.println("Collide With Enemy!");
				break;
			}
		}
		
		if (collidesWith(player, false))
		{
			undo();
			isCollide = true;
		}
		
		return isCollide;
		
	}
}
