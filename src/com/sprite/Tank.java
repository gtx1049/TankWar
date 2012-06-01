package com.sprite;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.LayerManager;

import com.game.Const;

//̹���࣬�����˺���Ҽ̳�

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
	
	//��ͬӵ�п�����
	public void onFire(LayerManager im, Shell[] shells, int[] spritecount, boolean isEnemy)
	{
		int shellType = isEnemy? Const.ENEMYFIRE : Const.PLAYERFIRE;
		
		Shell s = new Shell(imgshell, Const.GRIDSIZE, Const.GRIDSIZE, this.getDirection(), shellType);
		
		if (isEnemy)
			s.setFrame(1);
		
		//��player����onfireʱ����洢��moveobject�Ŀ���߽��ᴫ��
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
		//���Դ���
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
	
	//��ը���������4֡
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