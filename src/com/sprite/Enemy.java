package com.sprite;

import java.util.Date;
import java.util.Random;

import javax.microedition.lcdui.Image;

import com.game.Const;

public class Enemy extends Tank
{
	
	private Random random;

	public Enemy(Image image, int frameWidth, int frameHeight, Image imgshell)
	{
		super(image, frameWidth, frameHeight, imgshell);
		// TODO Auto-generated constructor stub
		
		Date date = new Date();
		
		random = new Random(date.getTime());
		
	}
	
	public MoveObject emitShell(Image image, int width, int height)
	{
		if (random.nextInt() % 3 == 0)
		{
			Shell shell = new Shell(image, width, height, this.getDirection());
			
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
