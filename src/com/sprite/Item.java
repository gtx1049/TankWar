package com.sprite;

import java.util.Random;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

import com.game.Const;
import com.game.TankCanvas;

//道具精灵
public class Item extends Sprite
{

	int type;
	
	int width = 0;
	int height = 0;
	
	boolean isReal = false;
	
	public Item(Image image, int frameWidth, int frameHeight, int type, int width, int height)
	{
		super(image, frameWidth, frameHeight);

		this.type = type;
		
		this.width = width;
		this.height = height;
		
		switch (type)
		{
		case Const.SUPERCANNON:
			setFrame(3);
			break;
		case Const.UNBEATABLE:
			setFrame(1);
			break;
		case Const.CLEAR:
			setFrame(2);
			break;
		case Const.SILENCE:
			setFrame(0);
			break;
		}
	}
	
	//道具是否存在
	public void setReal(boolean isReal) {
		this.isReal = isReal;
	}

	public int getType()
	{
		return type;
	}
	
	public void setType(int type)
	{
		this.type = type;
		
		switch (type)
		{
		case Const.SUPERCANNON:
			setFrame(3);
			break;
		case Const.UNBEATABLE:
			setFrame(1);
			break;
		case Const.CLEAR:
			setFrame(2);
			break;
		case Const.SILENCE:
			setFrame(0);
			break;
		}
	}
	
	//根据矩阵的位置决定道具改放在何处，游戏地方不可放置
	public void addItem(LayerManager scene, Random random)
	{
		int i = Math.abs(random.nextInt()) % Const.MAPSIZE;
		int j = Math.abs(random.nextInt()) % Const.MAPSIZE;
		
		while (!TankCanvas.tieldMap[i][j])
		{
			i = Math.abs(random.nextInt()) % Const.MAPSIZE;
			j = Math.abs(random.nextInt()) % Const.MAPSIZE;
		}
		
		setPosition(i * Const.GRIDSIZE, j * Const.GRIDSIZE);
		
		scene.insert(this, 0);
	}
	
	//判断是否与玩家相撞
	public boolean judgeCollideAct(Player player)
	{
		
		if (!isReal)
			return false;
		
		boolean isCollide = false;
		
		if (collidesWith(player, false))
		{
			isCollide = true;
		}
		
		return isCollide;
		
	}

}
