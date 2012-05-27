package com.sprite;

import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Image;

import com.game.Const;

public class Player extends MoveObject
{

	public Player(Image image, int frameWidth, int frameHeight, int speed)
	{
		super(image, frameWidth, frameHeight);
		
		this.speed = speed;
		this.direction = Const.UP;
		// TODO Auto-generated constructor stub
	}
	
	public void judgeCollideAct(Vector walls, Vector enemys, Vector shell)
	{
		this.doAction();
		for(int i = walls.size() - 1; i >= 0; i--)
		{
			if(this.collidesWith((Wall)walls.elementAt(i), false))
			{
				this.undo();
			}
		}
		
		for(int i = enemys.size() - 1; i >= 0; i--)
		{
			if(this.collidesWith((Enemy)enemys.elementAt(i), false))
			{
				this.undo();
			}
		}
		
		for(int i = shell.size() - 1; i >= 0; i--)
		{
			if(this.collidesWith((Shell)shell.elementAt(i), false))
			{
				this.undo();
			}
		}
	}
	
}


