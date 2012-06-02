package com.game;

import java.util.Date;
import java.util.Random;

import com.sprite.Enemy;
import com.sprite.Item;
import com.sprite.Player;
import com.sprite.Shell;
import com.sprite.Wall;

//��Ϸ�߼������߳���

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
		
		//�ж����е��ӵ��Ķ���
		for(int i = 0; i < spritecount[Const.SHELLCOUNT]; i++)
		{
			//ͨ�������룬�õ�����ײ�����������Լ��������š�
			int dividecode = shells[i].judgeCollideAct(walls, enemys, player, shells, spritecount);
			
			//System.out.println("Divide code" + dividecode);
			
			//System.out.println("����ѭ��:" + i);
			
			//ʲôҲû�з���
			if(dividecode == -1)
			{
				continue;
			}
			//������޳��ӵ�
			if(dividecode == Const.OVERBOARDER)
			{
				removeShell(shells[i]);
				i--;
			}
			else
			{
				int index = dividecode >> 16;
				//System.out.println("����index:" + index);
//				System.out.println("���Ƿ�����:" + dividecode);
				dividecode = dividecode & 0x0000FFFF;
//				System.out.println("���ǻ�ԭ�ķ�����:" + dividecode);
				if(dividecode == Const.COLLIDEWITHWALL)
				{
					int tempdirection = shells[i].getDirection();
					
					if (!(walls[index].getType() == Const.HARDBRICK && shells[i].getType() != Const.SUPERCANNON))
					{
						//����false��˵��ǽ�Ѿ��������ƻ�
						if(!walls[index].beBroken(tempdirection))
						{
							removeWall(walls[index]);
						}
					}
					
					removeShell(shells[i]);
					i--;
					
				}
				//�ӵ�װ��̹�˵��߼�
				else if(dividecode == Const.COLLIDWITHTANK)
				{
					//���к�ɫ���ˣ��õ�����
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
					
					//�жϵ����Ƿ�����
					boolean isOver = enemys[index].onHit();
					//�Ƴ��ӵ�
					removeShell(shells[i]);
					
					//�����˱���ɱ�����뵽��ը״̬
					if (isOver)
						//removeEnemy(enemys[index]);
						enemys[index].beExplosed();
						
					i--;
				}
				
				//������ҵķ�Ӧ
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
			//��ը����������ը����ʱ���Ƴ�����
			if(enemys[i].getBeingexploesd())
			{
				if(!enemys[i].doExplosed())
				{
					removeEnemy(enemys[i]);
					i--;
				}
				continue;
			}
			
			//�������е��˽��ж�������������ʱ����ʱ����ֹ���˵Ķ���
			if (stopTime != 0)
			{
				//System.out.println("Break!");
				continue;
			}			
			
			//���˵ľ��嶯��
			enemys[i].judgeCollideAct(walls, enemys, spritecount, player);
			if (frameCount % 100 == 0)
				enemys[i].onFire(scene, shells, spritecount, true);
		}
		
		//��ҵı�ը״̬
		if(player.getBeingexploesd())
		{
			if(!player.doExplosed())
			{
				scene.remove(player);
			}
		}
		
		//�Ƿ�õ����ߣ���Ϊ��ʱ��ը�����޵У���������
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
		
		//��TankCanvas������������Ӧ��ҵĶ���
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
		//��ҿ��ƿ���
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
	
	//�߳�
	public void run() {
		gameLogic();
	}
	
	//�Ƴ��ڵ�
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
	
	//�Ƴ�����
	public void removeItem()
	{
		scene.remove(item);
	}
	
	//�Ƴ�����
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
	
	//�Ƴ�ǽ
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
