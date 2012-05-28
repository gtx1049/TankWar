package com.sprite;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.game.Const;

public class Wall extends Sprite
{

	int status;
	
	public Wall(Image image, int frameWidth, int frameHeight)
	{
		super(image, frameWidth, frameHeight);
		// TODO Auto-generated constructor stub
		status = 0;
	}
	
	public boolean beBroken(int direction)
	{
		if(status != 0)
		{
			return false;
		}
		
		switch(direction)
		{
		case Const.UP:
		{
			status = 1;
			this.setFrame(status);
			break;
		}
		case Const.DOWN:
		{
			status = 2;
			this.setFrame(status);
			break;
		}
		case Const.LEFT:
		{
			status = 3;
			this.setFrame(status);
			break;
		}
		case Const.RIGHT:
		{
			status = 4;
			this.setFrame(status);
			break;
		}
		}
		return true;
	}
}
