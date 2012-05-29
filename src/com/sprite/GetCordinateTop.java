package com.sprite;

import javax.microedition.lcdui.game.Sprite;

public class GetCordinateTop extends GetCordinate{

	public void setSprite(Sprite object){
		super.setSprite(object);
		cordinate = object.getY();
	}
	
	public Sprite getCordinateObject(MoveObject object)
	{
		if (cordinate < object.getY())
		{
			this.object = object;
			cordinate = object.getY();
		}
		
		return this.object;
		
		
	}
}
