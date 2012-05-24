package com.sprite;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class Shell extends Sprite
{

	public Shell(Image image, int frameWidth, int frameHeight)
	{
		super(image, frameWidth, frameHeight);
		// TODO Auto-generated constructor stub
	}
	
	public boolean collideObject(Sprite s)
	{
		return false;
		
	}
	
	public void doAction()
	{
		
	}
}
