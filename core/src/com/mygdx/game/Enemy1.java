package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Enemy1 extends Enemy{
	private static int x= Gdx.graphics.getWidth()/2-50;
	private static int y= Gdx.graphics.getHeight()-50;
	private static Texture tx= new Texture(Gdx.files.internal("ovni.png"));
	private static Texture txBala= new Texture(Gdx.files.internal("Rocket2.png"));
	private static Sound soundChoque= Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
	private static Sound soundBala= Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"));
	private  ArrayList<BulletEnemy> balasEnemy = new ArrayList<>();
	

	public Enemy1() {
		super(x, y, tx, soundChoque, txBala, soundBala);
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
             BulletEnemy b = balasEnemy.get(i);
             b.update();
             b.draw(batch);
             if (b.isDestroyed()) {
                 balasEnemy.remove(b);
                 i--; //para no saltarse 1 tras eliminar del arraylist
             }
       
	     }
	}
	
	public ArrayList<BulletEnemy> getBalasEnemy() {
		return balasEnemy;
	}

	public void setBalasEnemy(ArrayList<BulletEnemy> balasEnemy) {
		this.balasEnemy = balasEnemy;
	}
}
