package com.sprite;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.LayerManager;

import com.game.Const;

//坦克类，供敌人和玩家继承

public class Tank extends MoveObject
{
	protected Image imgshell;
	protected Image imgexplosion;
	protected int preCordinate = -1;
	
	private boolean beingexplosed;
	private int explosioncount;
	
	public Tank(Image image, int frameWidth, int frameHeight, Image img, Image aimgexplosion)
	{
		super(image, frameWidth, frameHeight);
		// TODO Auto-generated constructor stub
		this.imgshell = img;
		this.imgexplosion = aimgexplosion;
		
		beingexplosed = false;
		explosioncount = 25;
	}
	
	//共同拥有开火函数
	public void onFire(LayerManager im, Shell[] shells, int[] spritecount, boolean isEnemy)
	{
		int shellType = isEnemy? Const.ENEMYFIRE : Const.PLAYERFIRE;
		
		Shell s = new Shell(imgshell, Const.GRIDSIZE, Const.GRIDSIZE, this.getDirection(), shellType);
		
		if (isEnemy)
			s.setFrame(1);
		
		//当player调用onfire时，其存储在moveobject的宽与高将会传入
		s.setMoveArea(this.width, this.height);
		
		shells[spritecount[Const.SHELLCOUNT]] = s;
		spritecount[Const.SHELLCOUNT]++;
		
		//System.out.println(spritecount[Const.SHELLCOUNT]);
		int shellX = this.getX();
		int shellY = this.getY();
		
		switch(getDirection())
		{
		case Const.UP:
		{
			shellY -= 12;
			break;
		}
		case Const.DOWN:
		{
			shellY += 12;
			break;
		}
		case Const.LEFT:
		{
			shellX -= 12;
			break;
		}
		case Const.RIGHT:
		{
			shellX += 12;
			break;
		}
		}
		
		s.setPosition(shellX, shellY);
		im.insert(s, 0);
	}
	
	public void setDirection(int direction) 
	{
		//测试代码
		switch(direction)
		{
		case Const.UP:
			preCordinate = getX();
//			System.out.println(getX()+","+getY());
			setTransform(TRANS_NONE);
			setPosition(preCordinate, getY());
			break;
		case Const.DOWN:
			preCordinate = getX();
//			System.out.println(getX()+","+getY());
			setTransform(TRANS_ROT180);
			setPosition(preCordinate, getY());
			break;
		case Const.RIGHT:
			preCordinate = getY();
//			System.out.println(getX()+","+getY());
			setTransform(TRANS_ROT90);
			setPosition(getX(), preCordinate);
			break;
		case Const.LEFT:
			preCordinate = getY();
//			System.out.println(getX()+","+getY());
			setTransform(TRANS_ROT270);
			setPosition(getX(), preCordinate);
			break;

		default:
			break;
		}
		
		this.direction = direction;
	}
	
	public void beExplosed()
	{
		setImage(imgexplosion, Const.GRIDSIZE, Const.GRIDSIZE);
		beingexplosed = true;
	}
	
	//爆炸的情况，分4帧
	public boolean doExplosed()
	{
		if(explosioncount == 0)
		{
			return false;
		}
		if(beingexplosed)
		{
			explosioncount--;
			if(explosioncount % 5 == 0)
			{
				this.nextFrame();
			}
			return true;
		}
		
		return true;
	}
	
	public boolean getBeingexploesd()
	{
		return beingexplosed;
	}
}