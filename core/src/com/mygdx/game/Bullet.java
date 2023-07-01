package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Bullet {

	protected int xSpeed;
	protected int ySpeed;
	protected int x;
	protected int y;
	protected boolean destroyed = false;
	protected Sprite spr;
	    
	    public Bullet(BulletBuilder builder) { //recibe builder float x, float y, int xSpeed, int ySpeed, Texture tx
	    	spr = builder.getSpr();
	    	spr.setPosition(builder.getX(), builder.getY());
	        this.xSpeed = builder.getXSpeed();
	        this.ySpeed = builder.getYSpeed();
	    }
	    public void update() {
	        spr.setPosition(spr.getX()+xSpeed, spr.getY()+ySpeed);
	        if (spr.getX() < 0 || spr.getX()+spr.getWidth() > Gdx.graphics.getWidth()) {
	            destroyed = true;
	        }
	        if (spr.getY() < 0 || spr.getY()+spr.getHeight() > Gdx.graphics.getHeight()) {
	        	destroyed = true;
	        }
	        
	    }
	    
	    public void draw(SpriteBatch batch) {
	    	spr.draw(batch);
	    }
	    
	    public boolean checkCollision(Ball2 b2) {
	        if(spr.getBoundingRectangle().overlaps(b2.getArea())){
	        	// Se destruyen ambos
	            this.destroyed = true;
	            return true;
	
	        }
	        return false;
	    }
	    
	    public boolean isDestroyed() {return destroyed;}
	    
		public boolean checkCollision(Enemy enemy1) {
			if(spr.getBoundingRectangle().overlaps(enemy1.getArea())){
	        	// Se destruyen ambos
	            this.destroyed = true;
	            return true;
	
	        }
	        return false;
		}
		

	
}
