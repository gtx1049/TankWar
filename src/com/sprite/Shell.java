package com.sprite;

import javax.microedition.lcdui.Image;

import com.game.Const;

public class Shell extends MoveObject
{
	
	private int type;

	public Shell(Image image, int frameWidth, int frameHeight, int direction, int type)
	{
		super(image, frameWidth, frameHeight);
		this.speed = Const.SHELLSPEED;
		this.direction = direction;
		this.type = type;
	}
	
	public int judgeCollideAct(Wall[] walls, Enemy[] enemy, Player player, Shell[] shells, int[] spritecount)
	{
		if ( (getX() <= -10 && direction == Const.LEFT) || 
			 (getY() <= -10 && direction == Const.UP)   ||
			 (getX() + getWidth() - 10 >= this.width && direction == Const.RIGHT) ||
			 (getY() + getHeight() - 10 >= this.height && direction == Const.DOWN) )
		{
			return Const.OVERBOARDER;
		}
		
		GetCordinate getCordinateObject = GetCordinate.createCordinateGetter(direction);
		
		int code = -1;
		int collisionType = -1;
		
		
		for (int i = 0; i < spritecount[Const.WALLCOUNT]; i++)
		{
			if (i == 0)
			{
				getCordinateObject.setSprite(walls[0]);
				code = 0 << 16;
				collisionType = Const.COLLIDEWITHWALL;
				continue;
			}
			
			if(collidesWith(walls[i], true))
			{
				if (getCordinateObject.getCordinateObject(walls[i]) == walls[i])
				{
					code = i << 16;
					collisionType = Const.COLLIDEWITHWALL;
				}
			}
		}
		
		if (type == Const.ENEMYFIRE)
		{
			if (collidesWith(player, true))
			{
				if (getCordinateObject.getCordinateObject(player) == player)
					return Const.COLLIDEWITHPLAYER;
			}
		}
		else if (type == Const.PLAYERFIRE)
		{
			for (int i = 0; i < spritecount[Const.ENEMYCOUNT]; i++)
			if (collidesWith(enemy[i], true))
			{
				if (getCordinateObject.getCordinateObject(walls[i]) == walls[i])
				{
					code = i << 16;
					collisionType = Const.COLLIDWITHTANK;
				}
				
			}
		}
		
		if (collisionType != -1)
			return collisionType + code;
		else
		{
			this.doAction();
			return -1;
		}
	}
}
