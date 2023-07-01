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

import screens.PantallaJuego;

public abstract class Enemy implements Nave {

	private boolean destruida = false;
    private int vidas = 1;
    private int x;
    private int y;
    private int xVel = 2;
    private int yVel = 0;
    private Sprite spr;
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;
    private boolean herido = false;
    private int tiempoHeridoMax = 50;
    private int tiempoHerido;
    private float elapsedTime = 0f;
    private float shootInterval = 1f;
    private float deltaTime= 0f;
    
	public Enemy(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
		// TODO Auto-generated method stub
		sonidoHerido = soundChoque;
    	this.soundBala = soundBala;
    	this.txBala = txBala;
    	this.x = x; 
    	this.y = y; 
    	spr = new Sprite(tx);
    	spr.setPosition(x, y);
    	spr.setBounds(x, y, 45, 45);
    	txBala = new Texture("Rocket2.png");

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
	    	
	    	
	    	
	    	BulletBuilder bul = new BulletBuilder(0);
        	bul.setYSpeed(3);
        	bul.setX(spr.getX()-spr.getWidth()/2-5);
        	bul.setY(spr.getY()- spr.getHeight()-5);
        	bul.setSpr(new Sprite(txBala));
        	//(,,0,3,txBala)
        	BulletEnemy bala = new BulletEnemy(bul);
	    	
	    	
	    	
	        //BulletEnemy bala = new BulletEnemy(spr.getX()-spr.getWidth()/2-5,spr.getY()- spr.getHeight()-5,0,3,);
	        juego.agregarBalaEnemy(bala);
	        soundBala.play();
	        elapsedTime = 0f; // Reinicia el tiempo transcurrido
	    }
	   
	}

	@Override
	public boolean checkCollision(Ball2 b) {
		if(!herido && b.getArea().overlaps(spr.getBoundingRectangle())){
        	// rebote
            if (xVel ==0) xVel += b.getXSpeed()/2;
            if (b.getXSpeed() ==0) b.setXSpeed(b.getXSpeed() + (int)xVel/2);
            xVel = - xVel;
            b.setXSpeed(-b.getXSpeed());
            
            if (yVel ==0) yVel += b.getySpeed()/2;
            if (b.getySpeed() ==0) b.setySpeed(b.getySpeed() + (int)yVel/2);
            yVel = - yVel;
            b.setySpeed(- b.getySpeed());
            // despegar sprites
      /*      int cont = 0;
            while (b.getArea().overlaps(spr.getBoundingRectangle()) && cont<xVel) {
               spr.setX(spr.getX()+Math.signum(xVel));
            }   */
        	//actualizar vidas y herir
            vidas--;
            herido = true;
  		    tiempoHerido=tiempoHeridoMax;
  		    sonidoHerido.play();
            if (vidas<=0) 
          	    destruida = true; 
            return true;
        }
        return false;
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