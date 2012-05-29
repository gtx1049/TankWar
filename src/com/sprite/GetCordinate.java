package com.sprite;

import javax.microedition.lcdui.game.Sprite;

import com.game.Const;

public class GetCordinate {
	
	protected Sprite object;
	
	protected int cordinate = -1;
	
	public static GetCordinate createCordinateGetter(int direction){
		
		switch (direction)
		{
		case Const.UP:
			return new GetCordinateTop();
		case Const.DOWN:
			return new GetCordinateBottom();
		case Const.LEFT:
			return new GetCordinateLeft();
		case Const.RIGHT:
			return new GetCordinateRight();
		}
		
		return null;
		 
	}
	
	public void setSprite(Sprite object){
		this.object = object;
	}
	
	public Sprite getCordinateObject(Sprite object){
		
		return null;
	}
}
