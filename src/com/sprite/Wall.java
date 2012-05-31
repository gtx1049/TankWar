package com.sprite;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.game.Const;

public class Wall extends Sprite
{

	int status;
	
	int i;
	int j;
	
	int type;
	
	public Wall(Image image, int frameWidth, int frameHeight, int type, int i, int j)
	{
		super(image, frameWidth, frameHeight);

		status = 0;
		this.type = type;
	}
	
	
	
	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
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
