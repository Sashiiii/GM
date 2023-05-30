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

public class User implements Nave {
	private boolean destruida = false;
    private int vidas = 3;
    private float xVel = 0;
    private float yVel = 0;
    private Sprite spr;
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;
    private boolean herido = false;
    private int tiempoHeridoMax = 50;
    private int tiempoHerido;
    private  ArrayList<Bullet> balas = new ArrayList<>();
    
	public User() {
		int x= Gdx.graphics.getWidth()/2-50;
		int y= 30; 
		sonidoHerido = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
    	this.soundBala = Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"));
    	this.txBala = new Texture(Gdx.files.internal("Rocket2.png"));
    	spr = new Sprite(new Texture(Gdx.files.internal("MainShip3.png")));
    	spr.setPosition(x, y);
    	spr.setBounds(x, y, 45, 45);
	}

	@Override
	public void draw(SpriteBatch batch, PantallaJuego juego) {
		// TODO Auto-generated method stub
		float x =  spr.getX();
        float y =  spr.getY();
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
          Bullet  bala = new Bullet(spr.getX()+spr.getWidth()/2-5,spr.getY()+ spr.getHeight()-5,0,3,txBala);
	      this.agregarBala(bala);
	      soundBala.play();
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
	
	public boolean checkCollision(BulletEnemy b) {
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
            	 score +=10;
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
	
	public int colisionBalasEnemy(ArrayList<Enemy1> naves1,ArrayList<Enemy1> naves2, Sound explosionSound) {
		int score=0;
		for (int i = 0; i < this.balas.size(); i++) {
            Bullet b = balas.get(i);
            b.update();
            for (int j = 0; j < naves1.size(); j++) {    
              if (b.checkCollision(naves1.get(j))) {          
            	 explosionSound.play();
            	 naves1.remove(j);
            	 naves2.remove(j);
            	 j--;
            	 score +=1000;
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


	public boolean agregarBala(Bullet bb) {
    	return balas.add(bb);
    }
}
