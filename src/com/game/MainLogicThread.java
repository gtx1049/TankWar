package com.game;

import java.util.Vector;

import com.sprite.Enemy;
import com.sprite.Player;
import com.sprite.Shell;
import com.sprite.Wall;

public class MainLogicThread implements Runnable{

	private int action;
	
	private Shell[] shells;
	private Enemy[] enemys;
	private Wall[] walls;
	
	private Player player;
	
	private SceneManager scene;
	
	private int[] spritecount;
	
	public MainLogicThread(int action, Shell[] shells, Enemy[] enemys, Wall[] walls, 
						   int[] count,	Player player, SceneManager scene)
	{
		this.action = action;
		
		this.shells = shells;
		this.enemys = enemys;
		this.walls = walls;
		
		this.player = player;
		
		this.scene = scene;
		
		spritecount = count;
	}
	
	
	public void gameLogic()
	{
		for(int i = 0; i < spritecount[Const.SHELLCOUNT]; i++)
		{			
			shells[i].collideObject(shells[i]); 
			shells[i].doAction();
		}
		for(int i = 0; i < spritecount[Const.ENEMYCOUNT]; i++)
		{
			
			enemys[i].doAction();
		}
		
		if(action == Const.MOVE)
		{
			if (!player.judgeCollideAct(walls, enemys, shells, spritecount))
			{
				if (player.getX() + player.getWidth()>= scene.getCenterX() && player.getDirection() == Const.RIGHT)
					scene.setXMoving(true);
				else if (player.getX() <= scene.getCenterX() && player.getDirection() == Const.LEFT)
					scene.setXMoving(true);
				
				if (player.getY() + player.getHeight() >= scene.getCenterY() && player.getDirection() == Const.DOWN)
					scene.setYMoving(true);
				else if (player.getY() + player.getHeight() <= scene.getCenterY() && player.getDirection() == Const.UP)
					scene.setYMoving(true);
					
				scene.move(player.getDirection());
					
			}
		}
		else if(action == Const.FIRE)
		{
			
		}
	}
	

	public void run() {
		gameLogic();
	}

}
