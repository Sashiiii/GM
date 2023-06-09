package com.mygdx.game;

import java.awt.event.KeyEvent;
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
import BulletStrategy.BulletSpace;
import BulletStrategy.BulletStrategy;
import BulletStrategy.BulletXButton;
import screens.PantallaJuego;

public class User implements Nave {
	private boolean destruida = false;
    private static int vidas = 3;
    private float xVel = 0;
    private float yVel = 0;
    private static Sprite spr;
    private Sound sonidoHerido = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
   // private Texture txBala = new Texture(Gdx.files.internal("Rocket2.png"));
    private boolean herido = false;
    private int tiempoHeridoMax = 50;
    private int tiempoHerido;
    private int xMax=3;
   // private BulletFactory bulletFactory;
    private  ArrayList<Bullet> balas = new ArrayList<>();
    
    //instancia unica y privada de User
    private static User instance;
    
    //constructor privado de User para cumplir con singleton
	private User() {
		int x = Gdx.graphics.getWidth()/2-50;
		int y = 30; 
		vidas = 3;
		sonidoHerido = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
    	
    	//this.txBala = new Texture(Gdx.files.internal("Rocket2.png"));
    	spr = new Sprite(new Texture(Gdx.files.internal("MainShip3.png")));
    	spr.setPosition(x, y);
    	spr.setBounds(x, y, 45, 45);
    	//bulletFactory= new BulletFactory();
	}
	
	
	//Singleton
	public static User getInstance() {
        if (instance == null) {
            instance = new User();
            int x = Gdx.graphics.getWidth()/2-50;
    		int y = 30; 
        	spr = new Sprite(new Texture(Gdx.files.internal("MainShip3.png")));
        	spr.setPosition(x, y);
        	spr.setBounds(x, y, 45, 45);
        	vidas = 3;
        }        
        
        return instance;
    }
	
	public static void setNull() {
        instance = null;
    }
	
	@Override
	public void draw(SpriteBatch batch, PantallaJuego juego) {
		// TODO Auto-generated method stub
		float x =  spr.getX();
        float y =  spr.getY();
        String key;
        if (!herido) {
	        // que se mueva con teclado
	        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) xVel--;
	        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) xVel++;
        	if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) yVel--;     
	        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) yVel++;
        	

	        // que se mantenga dentro de los bordes de la ventana
	        if (x+xVel < 0 || x+xVel+spr.getWidth() > Gdx.graphics.getWidth())
	        	xVel*=-1;
	        if (y+yVel < 0 || y+yVel+spr.getHeight() > Gdx.graphics.getHeight())
	        	yVel*=-1;
	        
	        spr.setPosition(x+xVel, y+yVel);   
         
 		    spr.draw(batch);
        } else {
           spr.setX(spr.getX()+MathUtils.random(-2,2));
 		   spr.draw(batch); 
 		  spr.setX(x);
 		   tiempoHerido--;
 		   if (tiempoHerido<=0) herido = false;
 		 }
        
        // disparo
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) { 
        	Bullet bala = BulletFactory.createBullet("s", spr.getX()+spr.getWidth()/2-5, spr.getY()+ spr.getHeight()-5);
        	bala.disparar();
        	this.agregarBala(bala);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.X) && xMax>0) { 
        	Bullet bala = BulletFactory.createBullet("x", spr.getX()+spr.getWidth()/2-5, spr.getY()+ spr.getHeight()-5);
        	bala.disparar();
        	this.agregarBala(bala);
        	xMax--;
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
	
	public boolean checkCollision(Bullet b) {
		if(!herido && b.getArea().overlaps(spr.getBoundingRectangle())){
        	
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
	
	public void colisionNaveAsteroides(SpriteBatch batch, ArrayList<Ball2> balls1,ArrayList<Ball2> balls2) {
	      for (int i = 0; i < balls1.size(); i++) {
	    	    Ball2 b = balls1.get(i);
	    	    b.draw(batch);
		          //perdió vida o game over
	              if (this.checkCollision(b)) {
		            //asteroide se destruye con el choque             
	            	 balls1.remove(i);
	            	 balls2.remove(i);
	            	 i--;
	            }   	  
	      
	      }
	}
	
	public int  colisionBalasAsteroides(Sound explosionSound, ArrayList<Ball2> balls1, ArrayList<Ball2> balls2) {
		int score=0;
		

	  for (int i = 0; i < balas.size(); i++) {
            Bullet b = balas.get(i);
            b.update();
            for (int j = 0; j < balls1.size(); j++) {    
              if (b.checkCollision(balls1.get(j))) {          
            	 explosionSound.play();
            	 balls1.remove(j);
            	 balls2.remove(j);
            	 j--;
            	 score += 100;
              }   	  
  	        }
                
         //   b.draw(batch);
            if (b.isDestroyed()) {
                balas.remove(b);
                i--; //para no saltarse 1 tras eliminar del arraylist
            }
	  }
	  return score;
	}
	

	public void drawBalas(SpriteBatch batch) {
		for (Bullet b : balas) {       
	          b.draw(batch);
	      }
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

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return (int) spr.getX();
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return (int) spr.getY();
	}

	@Override
	public void setVidas(int vidas2) {
		vidas = vidas2;
	}

	public Rectangle getArea() {
		// TODO Auto-generated method stub
		return  spr.getBoundingRectangle();
	}

	public ArrayList<Bullet> getBalas() {
		return balas;
	}
	
	public boolean agregarBala(Bullet bb) {
    	return balas.add(bb);
    }
}