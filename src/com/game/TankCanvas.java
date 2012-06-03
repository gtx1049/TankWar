package com.game;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.TiledLayer;

import com.sprite.Enemy;
import com.sprite.Home;
import com.sprite.Item;
import com.sprite.Player;
import com.sprite.Shell;
import com.sprite.Wall;

public class TankCanvas extends GameCanvas implements Runnable
{
	public class Point
	{
		public Point()
		{
			x = 0;
			y = 0;
		}
		
		public int x;
		public int y;
	}
	
	private Player player;
	private Enemy[] enemys;
	private Shell[] shells;
	private Wall[] walls;
	private Home home;
	private Item item;
	private TiledLayer background;
	private SceneManager scene;
	
	//private int status;
	
	private int width;
	private int height;

	private int layerManagerX;
	private int layerManagerY;
	
	private boolean onrun;	
	private boolean onfire;
	
	private int spritecount[] = new int[3];
	//resouce
	private Image imgplayer;
	private Image imglandform;
	private Image imgshell;
	private Image imgwall;
	private Image imghardwall;
	private Image imghome;
	private Image imgexplosion;
	private Image imgenemy;
	private Image imgItem;
	
	private Graphics graphics;
	
	public static boolean[][] tieldMap = new boolean[15][15];
	
	public Point[] startPoints = new Point[3];
	
	private int tankCount = 3;
	
	public TankCanvas(boolean suppressKeyEvents, int[][] map)
	{
		super(suppressKeyEvents);
		// TODO Auto-generated constructor st ub
		graphics = this.getGraphics();
		
		//加载图片
		try
		{
			imgplayer   = Image.createImage("/player.png");
			imglandform = Image.createImage("/landforms.png");
			imgshell    = Image.createImage("/shell.png");
			imgwall     = Image.createImage("/wall.png");
			imghardwall = Image.createImage("/hardwall.png");
			imghome     = Image.createImage("/home.png");
			imgexplosion = Image.createImage("/explosion.png");
			imgenemy    = Image.createImage("/enemy.png");
			imgItem     = Image.createImage("/items.png");
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		width = getWidth();
		height = getHeight();

		
		//加载地图和精灵
		
		background = new TiledLayer(15, 15, imglandform, Const.GRIDSIZE, Const.GRIDSIZE);
		player = new Player(imgplayer, Const.GRIDSIZE, Const.GRIDSIZE, Const.TANKSPEED, imgshell, imgexplosion);
		walls = new Wall[100];
		enemys = new Enemy[100];
		shells = new Shell[100];
		
		//初始化精灵计数器
		for(int i =0; i < 3; i++)
		{
			spritecount[0] = 0;
		}
		
		home = new Home(imghome, Const.GRIDSIZE, Const.GRIDSIZE);

		scene = new SceneManager(60, 68, background.getWidth(), background.getHeight(), width, height);
		
		player.setMoveArea(background.getWidth(), background.getHeight());
		
		item = new Item(imgItem, Const.GRIDSIZE, Const.GRIDSIZE, -1, background.getWidth(), background.getHeight());
		
		loadScene(map);
		
		layerManagerX = 60;
		layerManagerY = 68;
		
		//显示这个图层
		scene.append(background);
		scene.setViewWindow(layerManagerX, layerManagerY, width, height);
		
		scene.paint(graphics, 0, 0);
		
		//游戏开始
		onrun = true;
		onfire = false;
		
		new Thread(this).start();
	}

	public void run()
	{
		// TODO Auto-generated method stub
		while(onrun)
		{
			
			if (spritecount[Const.ENEMYCOUNT] < 3 && tankCount != 22)
			{
				
				int index = tankCount % 3;
				
				Enemy e = null;
				if (tankCount % 6 == 0)
					e = new Enemy(imgenemy, Const.GRIDSIZE, Const.GRIDSIZE, Const.TANKSPEED, imgshell, imgexplosion, Const.REDENEMY, spritecount[Const.ENEMYCOUNT]);
				else 
					e = new Enemy(imgenemy, Const.GRIDSIZE, Const.GRIDSIZE, Const.TANKSPEED, imgshell, imgexplosion, Const.GREENENEMY, spritecount[Const.ENEMYCOUNT]);
				
				background.setCell(startPoints[index].y, startPoints[index].x, 1);
				
				e.setMoveArea(background.getWidth(), background.getHeight());
				e.setPosition(startPoints[index].y * Const.GRIDSIZE, startPoints[index].x * Const.GRIDSIZE);
				e.defineReferencePixel(Const.GRIDSIZE >> 1, Const.GRIDSIZE >> 1);
				enemys[spritecount[Const.ENEMYCOUNT]] = e;
				spritecount[Const.ENEMYCOUNT]++;
				scene.insert(e, 0);
				
				tankCount ++;
				
			}
			
			
			int action = playerControl();
			
			//游戏逻辑控制
			new Thread(new MainLogicThread(action,shells, enemys, walls, spritecount, player, scene, item)).start();
			
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			//将图像打在屏幕上
			scene.paint(graphics, 0, 0);
			flushGraphics();
		}
	}
	
	//通过数组读取场景
	public void loadScene(int[][] map)
	{
		int startPointCount = 0;
		
		for(int i = 0; i < 15; i++)
			
		{
			for(int j = 0; j < 15; j++)
			{
				if(map[i][j] == Const.LAWN)
				{
					background.setCell(j, i, 1);
					tieldMap[i][j] = true;
				}
				else if(map[i][j] == Const.FOREST)
				{
					background.setCell(j, i, 3);
					tieldMap[i][j] = true;
				}
				else if(map[i][j] == Const.WATER)
				{
					background.setCell(j, i, 2);
					tieldMap[i][j] = false;
				}
				else if(map[i][j] == Const.PLAYER)
				{
					background.setCell(j, i, 1);
					player.setPosition(j * Const.GRIDSIZE - 1, i * Const.GRIDSIZE);
					player.setFrame(0);
					player.defineReferencePixel(Const.GRIDSIZE >> 1, Const.GRIDSIZE >> 1);
					scene.append(player);
					tieldMap[i][j] = true;
				}
				else if(map[i][j] == Const.HOME)
				{
					background.setCell(j, i, 1);
					home.setPosition(j * Const.GRIDSIZE, i * Const.GRIDSIZE);
					home.setFrame(0);
					scene.append(home);
					tieldMap[i][j] = false;
				}
				else if(map[i][j] == Const.BRICK)
				{
					background.setCell(j, i, 1);
					Wall wall = new Wall(imgwall, Const.GRIDSIZE, Const.GRIDSIZE, Const.BRICK, i, j);
					wall.setPosition(j * Const.GRIDSIZE, i * Const.GRIDSIZE);
					wall.setFrame(0);
//					walls.addElement(wall);
					walls[spritecount[Const.WALLCOUNT]] = wall;
					spritecount[Const.WALLCOUNT]++;
					scene.append(wall);
					tieldMap[i][j] = false;
				}
				else if(map[i][j] == Const.HARDBRICK)
				{
					background.setCell(j, i, 1);
					Wall wall = new Wall(imghardwall, Const.GRIDSIZE, Const.GRIDSIZE, Const.HARDBRICK, i, j);
					wall.setPosition(j * Const.GRIDSIZE, i * Const.GRIDSIZE);
					wall.setFrame(0);
//					walls.addElement(wall);
					walls[spritecount[Const.WALLCOUNT]] = wall;
					spritecount[Const.WALLCOUNT]++;
					scene.append(wall);
					tieldMap[i][j] = false;
				}
				else if(map[i][j] == Const.REDENEMY)
				{
					background.setCell(j, i, 1);
					Enemy e = new Enemy(imgenemy, Const.GRIDSIZE, Const.GRIDSIZE, Const.TANKSPEED, imgshell, imgexplosion, Const.REDENEMY, spritecount[Const.ENEMYCOUNT]);
					e.setMoveArea(background.getWidth(), background.getHeight());
					e.setPosition(j * Const.GRIDSIZE, i * Const.GRIDSIZE);
					e.defineReferencePixel(Const.GRIDSIZE >> 1, Const.GRIDSIZE >> 1);
					enemys[spritecount[Const.ENEMYCOUNT]] = e;
					spritecount[Const.ENEMYCOUNT]++;
					scene.append(e);
					tieldMap[i][j] = true;

					startPoints[startPointCount] = new Point();
					
					startPoints[startPointCount].x = i;
					startPoints[startPointCount].y = j;
					
					startPointCount++;
					
				}
				else if(map[i][j] == Const.NORMALENEMY)
				{
					background.setCell(j, i, 1);
					Enemy e = new Enemy(imgenemy, Const.GRIDSIZE, Const.GRIDSIZE, Const.TANKSPEED, imgshell, imgexplosion, Const.GREENENEMY, spritecount[Const.ENEMYCOUNT]);
					e.setMoveArea(background.getWidth(), background.getHeight());
					e.setPosition(j * Const.GRIDSIZE, i * Const.GRIDSIZE);
					e.defineReferencePixel(Const.GRIDSIZE >> 1, Const.GRIDSIZE >> 1);
					enemys[spritecount[Const.ENEMYCOUNT]] = e;
					spritecount[Const.ENEMYCOUNT]++;
					scene.append(e);
					tieldMap[i][j] = true;
					
					startPoints[startPointCount] = new Point();
					
					startPoints[startPointCount].x = i;
					startPoints[startPointCount].y = j;
					
					startPointCount++;
				}
			}
		}
	}
	
	//玩家的控制函数
	public int playerControl()
	{
		int keystate = getKeyStates();
		
		//左
		if((keystate & LEFT_PRESSED) != 0)
		{
			if(player.getDirection() != Const.LEFT)
			{
				player.setDirection(Const.LEFT);
			}
			return Const.MOVE;
		}
		//右
		if((keystate & RIGHT_PRESSED) != 0)
		{
			if(player.getDirection() != Const.RIGHT)
			{
				player.setDirection(Const.RIGHT);
			}
			
			return Const.MOVE;
		}
		//上
		if((keystate & UP_PRESSED) != 0)
		{
			if(player.getDirection() != Const.UP)
			{
				player.setDirection(Const.UP);
			}
			
			return Const.MOVE;
		}
		//下
		if((keystate & DOWN_PRESSED) != 0)
		{
			if(player.getDirection() != Const.DOWN)
			{
				player.setDirection(Const.DOWN);
			}
			
			return Const.MOVE;
		}
		//开炮
		if((keystate & FIRE_PRESSED) != 0)
		{
			if(onfire)
			{
				return Const.COLD;
			}
			onfire = true;
			return Const.FIRE;
		}
		else 
		{
			onfire = false;
		}
		
		return Const.COLD;
		
	}
}
