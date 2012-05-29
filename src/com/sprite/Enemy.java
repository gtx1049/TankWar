package com.sprite;

import java.util.Date;
import java.util.Random;

import javax.microedition.lcdui.Image;

import com.game.Const;

public class Enemy extends Tank
{
	private int type;
	private Random random;

	public Enemy(Image image, int frameWidth, int frameHeight, Image imgshell, int type)
	{
		super(image, frameWidth, frameHeight, imgshell);
		// TODO Auto-generated constructor stub
		
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
}
