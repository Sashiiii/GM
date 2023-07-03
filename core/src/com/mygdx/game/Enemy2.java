package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Enemy2 extends Enemy{

	public Enemy2(int x, int y, Texture tx) {
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

	@Override
	public boolean checkCollision(Ball2 b) {
		// TODO Auto-generated method stub
		return false;
	}
}
