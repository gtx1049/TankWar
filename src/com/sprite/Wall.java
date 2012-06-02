package com.sprite;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.game.Const;

//墙类，分为铁墙和木墙

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
	
	
	
	//这里得到墙的格子位置
	
	public int getType() {
		return type;
	}



	public void setType(int type) {
		this.type = type;
	}



	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}
	
	//判断子弹方向决定如何被破坏
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
