package com.sprite;

import javax.microedition.lcdui.Image;

import com.game.Const;

public class Shell extends MoveObject
{

	public Shell(Image image, int frameWidth, int frameHeight, int direction)
	{
		super(image, frameWidth, frameHeight);
		this.speed = Const.SHELLSPEED;
		this.direction = direction;
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
				//System.out.println("我是wallcode:" + wallcode + "," + i);
				//System.out.println("我是返回值:" + (Const.COLLIDEWITHWALL + wallcode));
				return Const.COLLIDEWITHWALL + wallcode; 
				
			}
		}
		
		this.doAction();
		return -1;
	}
}
