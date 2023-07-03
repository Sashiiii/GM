package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import BulletStrategy.Bullet;
import BulletStrategy.BulletFactory;
import BulletStrategy.BulletEnemy;
import screens.PantallaJuego;

public abstract class Enemy implements Nave {

	private boolean destruida = false;
    private int vidas = 1;
    private int x;
    private int y;
    private int xVel = 2;
    private int yVel = 0;
    private Sprite spr;
    private boolean herido = false;
 float elapsedTime = 0f;
    private float shootInterval = 1f;
    private float deltaTime= 0f;
    
	public Enemy(int x, int y, Texture tx) {
		// TODO Auto-generated method stub
		
    	this.x = x; 
    	this.y = y; 
    	spr = new Sprite(tx);
    	spr.setPosition(x, y);
    	spr.setBounds(x, y, 45, 45);
    	

	}
	
	public void update() {
        x += getXSpeed();
        
        if (x+getXSpeed() < 0 || x+getXSpeed()+spr.getWidth() > Gdx.graphics.getWidth())
        	setXSpeed(getXSpeed() * -1);
        spr.setPosition(x, y);
    }
	
	@Override
	public void draw(SpriteBatch batch, PantallaJuego juego) {
		spr.draw(batch);
		elapsedTime += deltaTime;
	    if (elapsedTime >= shootInterval) {
	    	
        	//Bullet bala = new Bullet (new BulletEnemy(spr.getX()-spr.getWidth()/2-5,spr.getY()- spr.getHeight()-5));
        	Bullet bala = BulletFactory.createBullet("e", spr.getX()-spr.getWidth()/2-5 , spr.getY()- spr.getHeight()-5);
	    	
	    	
	        //BulletEnemy bala = new BulletEnemy(spr.getX()-spr.getWidth()/2-5,spr.getY()- spr.getHeight()-5,0,3,);
	        juego.agregarBalaEnemy(bala);
	        bala.disparar();
	        elapsedTime = 0f; // Reinicia el tiempo transcurrido
	    }
	   
	}

	
	
	public boolean colisionBalaUser(ArrayList<Bullet> balas, Sound explosionSound) {
		for (int i = 0; i < balas.size(); i++) {
            Bullet b = balas.get(i);
            b.update();
            if (b.checkCollision(this)) {          
            	 explosionSound.play();
            	 
            }   	  
                
         //   b.draw(batch);
            if (b.isDestroyed()) {
                balas.remove(b);
                i--; //para no saltarse 1 tras eliminar del arraylist
                return true;
            }
		}
      return false;
	}
	
	
	@Override
	public boolean estaDestruido() {
		return !herido && destruida;
	}

	@Override
	public boolean estaHerido() {
		return herido;
	}

	@Override
	public int getVidas() {
		// TODO Auto-generated method stub
		return vidas;
	}
	
	public int getXSpeed() {
		return xVel;
	}
	public void setXSpeed(int xSpeed) {
		this.xVel = xSpeed;
	}
	public int getySpeed() {
		return yVel;
	}
	public void setySpeed(int ySpeed) {
		this.yVel = ySpeed;
	}
	public Rectangle getArea() {
    	return spr.getBoundingRectangle();
    }

	@Override
	public void setVidas(int vidas2) {
		vidas = vidas2;
	}
	
	public void setDeltaTime(float deltaTime) {
		this.deltaTime=deltaTime;
	}

}