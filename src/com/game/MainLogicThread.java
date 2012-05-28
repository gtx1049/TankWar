package com.game;

import java.util.Vector;

import com.sprite.Enemy;
import com.sprite.Player;
import com.sprite.Shell;

public class MainLogicThread implements Runnable{

	private int action;
	
	private Vector shells;
	private Vector enemys;
	private Vector walls;
	
	private Player player;
	
	private SceneManager scene;
	
	public MainLogicThread(int action, Vector shells, Vector enemys, Vector walls, Player player, SceneManager scene){
		this.action = action;
		
		this.shells = shells;
		this.enemys = enemys;
		this.walls = walls;
		
		this.player = player;
		
		this.scene = scene;
	}
	
	
	public void gameLogic()
	{
		for(int i = shells.size() - 1; i >= 0; i--)
		{
			Shell s = (Shell)shells.elementAt(i);
			s.collideObject(s); 
			s.doAction();
		}
		for(int i = enemys.size() - 1; i >= 0; i--)
		{
			Enemy e = (Enemy)enemys.elementAt(i);
			
			e.doAction();
		}
		
		if(action == Const.MOVE)
		{
			if (!player.judgeCollideAct(walls, enemys, shells))
			{
				if (player.getX() >= scene.getCenterX() && player.getDirection() == Const.RIGHT)
					scene.setXMoving(true);
				else if (player.getX() <= scene.getCenterX() && player.getDirection() == Const.LEFT)
					scene.setXMoving(true);
				
				if (player.getY() >= scene.getCenterY() && player.getDirection() == Const.DOWN)
					scene.setYMoving(true);
				else if (player.getY() <= scene.getCenterY() && player.getDirection() == Const.UP)
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
