package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class bulletEnemy extends Bullet{

	public bulletEnemy(float x, float y, int xSpeed, int ySpeed, Texture tx) {
		super(x,y,xSpeed,ySpeed, tx);
		// TODO Auto-generated constructor stub
	}
	public void update() {
        spr.setPosition(spr.getX()-xSpeed, spr.getY()-ySpeed);
        if (spr.getX() < 0 || spr.getX()+spr.getWidth() > Gdx.graphics.getWidth()) {
            destroyed = true;
        }
        if (spr.getY() < 0 || spr.getY()+spr.getHeight() > Gdx.graphics.getHeight()) {
        	destroyed = true;
        }
        
    }

}
