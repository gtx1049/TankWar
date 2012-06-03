package com.sprite;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.game.Const;

//�ϼҵľ���
public class Home extends Sprite
{
	private Image imgexplosion;
	
	private boolean beingexplosed;
	private int explosioncount;
	
	public Home(Image image, int frameWidth, int frameHeight, Image explosion)
	{
		super(image, frameWidth, frameHeight);
		// TODO Auto-generated constructor stub
		this.imgexplosion = explosion;
		
		beingexplosed = false;
		explosioncount = 25;
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
