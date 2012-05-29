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
		
		for(int i = 0; i < spritecount[Const.WALLCOUNT]; i++)
		{
			if(this.collidesWith(walls[i], true))
			{
				
				int wallcode = i << 16;
				return Const.COLLIDEWITHWALL + wallcode; 
				
			}
		}
		
		if (type == Const.PLAYERFIRE)
			for (int i = 0; i < spritecount[Const.ENEMYCOUNT]; i++)
			{
				if (this.collidesWith(enemy[i], true))
				{
					int enemyCode = i << 16;
					return Const.COLLIDWITHTANK + enemyCode;
				}
			}
		else if (type == Const.ENEMYFIRE)
			if (this.collidesWith(player, true))
				return Const.COLLIDEWITHPLAYER;
		
		this.doAction();
		return -1;
	}
}