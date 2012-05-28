package com.game;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.TiledLayer;

import com.sprite.Enemy;
import com.sprite.Home;
import com.sprite.Player;
import com.sprite.Shell;
import com.sprite.Wall;

public class TankCanvas extends GameCanvas implements Runnable
{
	private Player player;
	private Vector enemys;
	private Vector shells;
	private Vector walls;
	private Item item;
	private Home home;
	private TiledLayer background;
	private SceneManager scene;
	
	private int status;
	
	private int width;
	private int height;

	private int layerManagerX;
	private int layerManagerY;
	
	private boolean onrun;	
	
	//resouce
	private Image imgplayer;
	private Image imglandform;
	private Image imgshell;
	private Image imgwall;
	private Image imghardwall;
	private Image imghome;
	private Image imgexplsion;
	private Image imgenemy;
	
	private Graphics graphics;
	
	public TankCanvas(boolean suppressKeyEvents, int[][] map)
	{
		super(suppressKeyEvents);
		// TODO Auto-generated constructor st ub
		graphics = this.getGraphics();
		
		try
		{
			imgplayer   = Image.createImage("/player.png");
			imglandform = Image.createImage("/landforms.png");
			imgshell    = Image.createImage("/shell.png");
			imgwall     = Image.createImage("/wall.png");
			imghardwall = Image.createImage("/hardwall.png");
			imghome     = Image.createImage("/home.png");
			imgexplsion = Image.createImage("/explosion.png");
			imgenemy    = Image.createImage("/enemy.png");
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
		player = new Player(imgplayer, Const.GRIDSIZE, Const.GRIDSIZE, Const.TANKSPEED);
		walls = new Vector();
		enemys = new Vector();
		shells = new Vector();
		home = new Home(imghome, Const.GRIDSIZE, Const.GRIDSIZE);

		scene = new SceneManager(60, 68, background.getWidth(), background.getHeight(), width, height);
		
		player.setMoveArea(background.getWidth(), background.getHeight());
		
		loadScene(map);
		
		layerManagerX = 60;
		layerManagerY = 68;
		
		//显示这个图层
		scene.append(background);
		scene.setViewWindow(layerManagerX, layerManagerY, width, height);
		
		scene.paint(graphics, 0, 0);
		
		//游戏开始
		onrun = true;

		new Thread(this).start();
	}

	public void run()
	{
		// TODO Auto-generated method stub
		while(onrun)
		{
			
			
			int action = playerControl();
			
			//游戏逻辑控制
			new Thread(new MainLogicThread(action,shells, enemys, walls, player, scene)).start();
			
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			scene.paint(graphics, 0, 0);
			flushGraphics();
		}
	}
	
	//通过数组读取场景
	public void loadScene(int[][] map)
	{
		for(int i = 0; i < 15; i++)
			
		{
			for(int j = 0; j < 15; j++)
			{
				if(map[i][j] == Const.LAWN)
				{
					background.setCell(j, i, 1);
				}
				else if(map[i][j] == Const.FOREST)
				{
					background.setCell(j, i, 3);
				}
				else if(map[i][j] == Const.WATER)
				{
					background.setCell(j, i, 2);
				}
				else if(map[i][j] == Const.PLAYER)
				{
					background.setCell(j, i, 1);
					player.setPosition(j * Const.GRIDSIZE - 1, i * Const.GRIDSIZE);
					player.setFrame(0);
					player.defineReferencePixel(Const.GRIDSIZE >> 1, Const.GRIDSIZE >> 1);
					scene.append(player);
				}
				else if(map[i][j] == Const.HOME)
				{
					background.setCell(j, i, 1);
					home.setPosition(j * Const.GRIDSIZE, i * Const.GRIDSIZE);
					home.setFrame(0);
					scene.append(home);
				}
				else if(map[i][j] == Const.BRICK)
				{
					background.setCell(j, i, 1);
					Wall wall = new Wall(imgwall, Const.GRIDSIZE, Const.GRIDSIZE);
					wall.setPosition(j * Const.GRIDSIZE, i * Const.GRIDSIZE);
					wall.setFrame(0);
					walls.addElement(wall);
					scene.append(wall);
				}
				else if(map[i][j] == Const.HARDBRICK)
				{
					background.setCell(j, i, 1);
					Wall wall = new Wall(imghardwall, Const.GRIDSIZE, Const.GRIDSIZE);
					wall.setPosition(j * Const.GRIDSIZE, i * Const.GRIDSIZE);
					wall.setFrame(0);
					walls.addElement(wall);
					scene.append(wall);
				}
				else if(map[i][j] == Const.REDENEMY)
				{
					background.setCell(j, i, 1);
					Enemy e = new Enemy(imgenemy, Const.GRIDSIZE, Const.GRIDSIZE);
					e.setPosition(j * Const.GRIDSIZE, i * Const.GRIDSIZE);
					e.setFrame(0);
					e.defineReferencePixel(Const.GRIDSIZE >> 1, Const.GRIDSIZE >> 1);
					enemys.addElement(e);
					scene.append(e);
				}
				else if(map[i][j] == Const.NORMALENEMY)
				{
					background.setCell(j, i, 1);
					Enemy e = new Enemy(imgenemy, Const.GRIDSIZE, Const.GRIDSIZE);
					e.setPosition(j * Const.GRIDSIZE, i * Const.GRIDSIZE);
					e.setFrame(1);
					e.defineReferencePixel(Const.GRIDSIZE >> 1, Const.GRIDSIZE >> 1);
					enemys.addElement(e);
					scene.append(e);
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
			return Const.FIRE;
		}
		
		return Const.COLD;
		
	}
}
