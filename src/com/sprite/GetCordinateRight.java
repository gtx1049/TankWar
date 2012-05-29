package com.sprite;

import javax.microedition.lcdui.game.Sprite;

public class GetCordinateRight extends GetCordinate{
	
	public void setSprite(Sprite object){
		super.setSprite(object);
		cordinate = object.getX();
	}
	
	public Sprite getCordinateObject(Sprite object)
	{
		if (cordinate > object.getX())
		{
			this.object = object;
			cordinate = object.getX();
		}
		
		return this.object;
		
		
	}
}
