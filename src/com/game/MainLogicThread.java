package com.game;

import java.util.Date;
import java.util.Random;

import com.sprite.Enemy;
import com.sprite.Item;
import com.sprite.Player;
import com.sprite.Shell;
import com.sprite.Wall;

//游戏逻辑的主线程类

public class MainLogicThread implements Runnable{

	private int action;
	
	private Shell[] shells;
	private Enemy[] enemys;
	private Wall[] walls;
	
	private Player player;
	
	private SceneManager scene;
	
	private int[] spritecount;
	
	private static int frameCount = 0;
	
	private Item item;
	
	private static int stopTime = 0;
	private static int unbeatableTime = -1;
	
	private static boolean isRemoved = true;
	
	public MainLogicThread(int action, Shell[] shells, Enemy[] enemys, Wall[] walls, int[] count,	Player player, SceneManager scene, Item item)
	{
		this.action = action;
		
		this.shells = shells;
		this.enemys = enemys;
		this.walls = walls;
		
		this.player = player;
		
		this.scene = scene;
		
		this.item = item;
		
		spritecount = count;
	}
	
	
	public void gameLogic()
	{
		
		//判断所有的子弹的动作
		for(int i = 0; i < spritecount[Const.SHELLCOUNT]; i++)
		{
			//通过分离码，得到所碰撞的物体类型以及物体的序号。
			int dividecode = shells[i].judgeCollideAct(walls, enemys, player, shells, spritecount);
			
			//System.out.println("Divide code" + dividecode);
			
			//System.out.println("我是循环:" + i);
			
			//什么也没有发生
			if(dividecode == -1)
			{
				continue;
			}
			//出界后剔除子弹
			if(dividecode == Const.OVERBOARDER)
			{
				removeShell(shells[i]);
				i--;
			}
			else
			{
				int index = dividecode >> 16;
				//System.out.println("我是index:" + index);
//				System.out.println("我是分离码:" + dividecode);
				dividecode = dividecode & 0x0000FFFF;
//				System.out.println("我是还原的分离码:" + dividecode);
				if(dividecode == Const.COLLIDEWITHWALL)
				{
					int tempdirection = shells[i].getDirection();
					
					if (!(walls[index].getType() == Const.HARDBRICK && shells[i].getType() != Const.SUPERCANNON))
					{
						//返回false，说明墙已经被彻底破坏
						if(!walls[index].beBroken(tempdirection))
						{
							removeWall(walls[index]);
						}
					}
					
					removeShell(shells[i]);
					i--;
					
				}
				//子弹装到坦克的逻辑
				else if(dividecode == Const.COLLIDWITHTANK)
				{
					//击中红色敌人，得到道具
					if (enemys[index].getType() == Const.REDENEMY)
					{
						
						if (!isRemoved)
							removeItem();
						
						Random random = new Random(new Date().getTime());
						isRemoved = true;
						
						item.setType(Math.abs(random.nextInt() % 4) + Const.SUPERCANNON);
						
						System.out.println("Item Type:" + item.getType());
						
						//item.setType(Const.SILENCE);
						item.addItem(scene, random);
						item.setReal(true);
						
					}
					
					//判断敌人是否死亡
					boolean isOver = enemys[index].onHit();
					//移除子弹
					removeShell(shells[i]);
					
					//当敌人被击杀，载入到爆炸状态
					if (isOver)
						//removeEnemy(enemys[index]);
						enemys[index].beExplosed();
						
					i--;
				}
				
				//击中玩家的反应
				else if (dividecode == Const.COLLIDEWITHPLAYER)
				{
					removeShell(shells[i]);
					
					if (!player.isUnbeatable())
					{
						player.beExplosed();
					}
					
					i--;
				}

			}
			
		}
		
		for(int i = 0; i < spritecount[Const.ENEMYCOUNT]; i++)
		{	
			//爆炸动作，当爆炸结束时，移除敌人
			if(enemys[i].getBeingexploesd())
			{
				if(!enemys[i].doExplosed())
				{
					removeEnemy(enemys[i]);
					i--;
				}
				continue;
			}
			
			//遍历所有敌人进行动作，当被冻结时，定时器阻止敌人的动作
			if (stopTime != 0)
			{
				//System.out.println("Break!");
				continue;
			}			
			
			//敌人的具体动作
			enemys[i].judgeCollideAct(walls, enemys, spritecount, player);
			if (frameCount % 100 == 0)
				enemys[i].onFire(scene, shells, spritecount, true);
		}
		
		//玩家的爆炸状态
		if(player.getBeingexploesd())
		{
			if(!player.doExplosed())
			{
				scene.remove(player);
			}
		}
		
		//是否得到道具，分为定时，炸弹，无敌，超级大炮
		if (item.judgeCollideAct(player))
		{
			int itemType = item.getType();
			
			if (itemType == Const.SILENCE || itemType == Const.CLEAR)
			{
				if (itemType == Const.SILENCE)
				{
					stopTime = 300;
					//System.out.println("Silence");
				}
				else
				{
					for (int i = 0; i < spritecount[Const.ENEMYCOUNT]; i++)
					{
						enemys[i].beExplosed();
					}
				}
			}
			else
			{
				if (itemType == Const.UNBEATABLE)
					unbeatableTime = 300;
				
				player.getItem(item.getType());
			}
			
			item.setReal(false);
			removeItem();
		}
		
		//从TankCanvas传进的用来响应玩家的动作
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
		//玩家控制开炮
		else if(action == Const.FIRE)
		{
			//System.out.println("firing");
			player.onFire(scene, shells, spritecount, false);
		}
		
		if (unbeatableTime == 0)
		{
			unbeatableTime = -1;
			player.backToNormal();
		}
		else if (unbeatableTime > 0)
			unbeatableTime--;
		
		if (frameCount == 1000)
			frameCount = 0;
		else
			frameCount++;
		
		if (stopTime > 0)
			stopTime -= 1;
	}
	
	//线程
	public void run() {
		gameLogic();
	}
	
	//移除炮弹
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
	
	//移除道具
	public void removeItem()
	{
		scene.remove(item);
	}
	
	//移除敌人
	public void removeEnemy(Enemy enemyToRemove)
	{
		for (int i = 0; i < spritecount[Const.ENEMYCOUNT]; i++)
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
	
	//移除墙
	public void removeWall(Wall walltoremove)
	{
		for(int i = 0; i < spritecount[Const.WALLCOUNT]; i++)
		{
			if(walltoremove.equals(walls[i]))
			{
				TankCanvas.tieldMap[walls[i].getI()][walls[i].getJ()] = true;
				
				scene.remove(walltoremove);
				
				walls[i] = walls[spritecount[Const.WALLCOUNT] - 1];
				
				walls[spritecount[Const.WALLCOUNT] - 1] = null;
				spritecount[Const.WALLCOUNT]--;
				
			}
		}
	}
}
