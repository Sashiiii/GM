package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Enemy1 extends Enemy{
	static int x= Gdx.graphics.getWidth()/2-50;
	static int y= Gdx.graphics.getHeight()-50;
	static Texture tx= new Texture(Gdx.files.internal("MainShip3.png"));
	static Texture txBala= new Texture(Gdx.files.internal("Rocket2.png"));
	static Sound soundChoque= Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
	static Sound soundBala= Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"));
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
}
