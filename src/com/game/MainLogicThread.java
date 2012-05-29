package com.game;

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
			int dividecode = shells[i].judgeCollideAct(walls, enemys, player, shells, spritecount);
			
			//System.out.println("我是循环:" + i);
			
			if(dividecode == -1)
			{
				continue;
			}
			if(dividecode == Const.OVERBOARDER)
			{
				removeShell(shells[i]);
				i--;
			}
			else
			{
				removeShell(shells[i]);
				
				int index = dividecode >> 16;
				//System.out.println("我是index:" + index);
				dividecode = dividecode & 0x00001111;
				//System.out.println("我是还原的分离码:" + dividecode);
				if(dividecode == Const.COLLIDEWITHWALL)
				{
					int tempdirection = shells[i].getDirection();
					
					//返回false，说明墙已经被彻底破坏
					if(!walls[index].beBroken(tempdirection))
					{
						removeWall(walls[index]);
					}
					
				}
				else if(dividecode == Const.COLLIDWITHTANK)
				{
					boolean isOver = enemys[index].onHit();
					
					if (isOver)
						removeEnemy(enemys[index]);
				}
				
				i--;
			}
			
		}
		for(int i = 0; i < spritecount[Const.ENEMYCOUNT]; i++)
		{
			
			enemys[i].doAction();
		}
		
		if(action == Const.MOVE)
		{
			if (!player.judgeCollideAct(walls, enemys, spritecount))
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
			//System.out.println("firing");
			player.onFire(scene, shells, spritecount);
		}
	}
	

	public void run() {
		gameLogic();
	}
	
	public void removeShell(Shell shelltoremove)
	{
		for(int i = 0; i < spritecount[Const.SHELLCOUNT]; i++)
		{
			if(shelltoremove.equals(shells[i]))
			{
				scene.remove(shelltoremove);
				
				shells[i] = shells[spritecount[Const.SHELLCOUNT] - 1];
				
				shells[spritecount[Const.SHELLCOUNT] - 1] = null;
				spritecount[Const.SHELLCOUNT]--;
				
			}
		}
	}
	
	public void removeEnemy(Enemy enemyToRemove)
	{
		for (int i = 0; i != spritecount[Const.ENEMYCOUNT]; i++)
		{
			if (enemyToRemove == enemys[i])
			{
				scene.remove(enemyToRemove);
				
				enemys[i] = enemys[spritecount[Const.ENEMYCOUNT] - 1];
				
				enemys[spritecount[Const.ENEMYCOUNT] - 1] = null;
				spritecount[Const.ENEMYCOUNT]--;
			}
		}
	}
	
	public void removeWall(Wall walltoremove)
	{
		for(int i = 0; i < spritecount[Const.WALLCOUNT]; i++)
		{
			if(walltoremove.equals(walls[i]))
			{
				scene.remove(walltoremove);
				
				walls[i] = walls[spritecount[Const.WALLCOUNT] - 1];
				
				walls[spritecount[Const.WALLCOUNT] - 1] = null;
				spritecount[Const.WALLCOUNT]--;
				
			}
		}
	}
}
