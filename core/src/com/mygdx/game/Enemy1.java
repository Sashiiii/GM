package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import BulletStrategy.Bullet;

public class Enemy1 extends Enemy{
	private static int x= Gdx.graphics.getWidth()/2-50;
	private static int y= Gdx.graphics.getHeight()-50;
	private static Texture tx= new Texture(Gdx.files.internal("ovni.png"));
	private  ArrayList<Bullet> balasEnemy = new ArrayList<>();
	

	public Enemy1() {
		super(x, y, tx);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void drawBalas(SpriteBatch batch) {
	     for (int i = 0; i < balasEnemy.size(); i++) {
             Bullet b = balasEnemy.get(i);
             b.update();
             b.draw(batch);
             if (b.isDestroyed()) {
                 balasEnemy.remove(b);
                 i--; //para no saltarse 1 tras eliminar del arraylist
             }
       
	     }
	}
	
	public ArrayList<Bullet> getBalasEnemy() {
		return balasEnemy;
	}

	public void setBalasEnemy(ArrayList<Bullet> balasEnemy) {
		this.balasEnemy = balasEnemy;
	}

	@Override
	public boolean checkCollision(Ball2 b) {
		// TODO Auto-generated method stub
		return false;
	}
}
