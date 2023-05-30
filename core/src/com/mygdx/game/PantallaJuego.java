package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class PantallaJuego implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;	
	private SpriteBatch batch;
	private Sound explosionSound;
	private Music gameMusic;
	private int score;
	private int ronda;
	private int velXAsteroides; 
	private int velYAsteroides; 
	private int cantAsteroides;
	private int cantEnemy1;
	private Texture fondo;
	
	private User nave;
	private  ArrayList<Ball2> balls1 = new ArrayList<>();
	private  ArrayList<Ball2> balls2 = new ArrayList<>();
	private  ArrayList<Bullet> balas = new ArrayList<>();
	private  ArrayList<BulletEnemy> balasEnemy = new ArrayList<>();
	private  ArrayList<Enemy1> naves1 = new ArrayList<>();
	private  ArrayList<Enemy1> naves2 = new ArrayList<>();

	public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score,  
		int velXAsteroides, int velYAsteroides, int cantAsteroides, int cantEnemy1) {
		this.game = game;
		this.ronda = ronda;
		this.score = score;
		this.velXAsteroides = velXAsteroides;
		this.velYAsteroides = velYAsteroides;
		this.cantAsteroides = cantAsteroides;
		this.cantEnemy1 = cantEnemy1;
		this.fondo = new Texture("fondo.jpg");
		
		batch = game.getBatch();
		camera = new OrthographicCamera();	
		camera.setToOrtho(false, 800, 640);
		//inicializar assets; musica de fondo y efectos de sonido
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
		explosionSound.setVolume(1,0.5f);
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav")); //
		
		gameMusic.setLooping(true);
		gameMusic.setVolume(0.5f);
		gameMusic.play();
		
	    // cargar imagen de la nave, 64x64   
	    nave = new User(); 
        nave.setVidas(vidas);
        //crear asteroides
	    for (int i = 0; i < cantAsteroides; i++) {
	        Ball2 bb = new Ball2( velXAsteroides, velYAsteroides);	   
	  	    balls1.add(bb);
	  	    balls2.add(bb);
	  	}
	    int altura = Gdx.graphics.getHeight()-50;
	    for (int i = 0; i < cantEnemy1; i++) {
	        Enemy1 ne = new Enemy1(); 
	        altura = altura - 60;
	  	    naves1.add(ne);
	  	    naves2.add(ne);
	  	}
	}
    
	public void dibujaEncabezado() {
		CharSequence str = "Vidas: "+nave.getVidas()+" Ronda: "+ronda;
		game.getFont().getData().setScale(2f);		
		game.getFont().draw(batch, str, 10, 30);
		game.getFont().draw(batch, "Score:"+this.score, Gdx.graphics.getWidth()-150, 30);
		game.getFont().draw(batch, "HighScore:"+game.getHighScore(), Gdx.graphics.getWidth()/2-100, 30);
	}
	@Override
	public void render(float delta) {
		  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		  batch.begin();
          batch.draw(fondo,0,0,fondo.getWidth(),fondo.getHeight());
          float deltaTime = Gdx.graphics.getDeltaTime(); // Obtiene el tiempo transcurrido desde el último cuadro
          
		  dibujaEncabezado();
	      if (!nave.estaHerido()) {
		      // colisiones entre balas y asteroides y su destruccion  
	    	  for (int i = 0; i < balas.size(); i++) {
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
	    	  
	    	  for (int i = 0; i < balasEnemy.size(); i++) {
		            BulletEnemy bE = balasEnemy.get(i);
		            bE.update();
		            bE.checkCollision(nave);   
		            nave.checkCollision(bE);
		            	  	  
		  	  }



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
	    	  
		      //actualizar movimiento de asteroides dentro del area
		      for (Ball2 ball : balls1) {
		          ball.update();
		      }
		      for (Enemy1 enn : naves1) {
		          enn.update();
		          enn.setDeltaTime(deltaTime);
		      }
		      //colisiones entre asteroides y sus rebotes  
		      for (int i=0;i<balls1.size();i++) {
		    	Ball2 ball1 = balls1.get(i);   
		        for (int j=0;j<balls2.size();j++) {
		          Ball2 ball2 = balls2.get(j); 
		          if (i<j) {
		        	  ball1.checkCollision(ball2);
		     
		          }
		        }
		      } 
	}
	      //dibujar balas
	     for (Bullet b : balas) {       
	          b.draw(batch);
	      }
	     for (Enemy1 enemyy: naves1) {       
	          enemyy.draw(batch,this);
	     }
	      nave.draw(batch, this);
	      //dibujar asteroides y manejar colision con nave
	      for (int i = 0; i < balls1.size(); i++) {
	    	    Ball2 b = balls1.get(i);
	    	    b.draw(batch);
		          //perdió vida o game over
	              if (nave.checkCollision(b)) {
		            //asteroide se destruye con el choque             
	            	 balls1.remove(i);
	            	 balls2.remove(i);
	            	 i--;
              }   	  
  	        }
	      
	      if (nave.estaDestruido()) {
  			if (score > game.getHighScore())
  				game.setHighScore(score);
	    	Screen ss = new PantallaGameOver(game);
  			ss.resize(1200, 800);
  			game.setScreen(ss);
  			dispose();
  		  }
	      batch.end();
	      //nivel completado
	      if (balls1.size()==0) {
			Screen ss = new PantallaJuego(game,ronda+1, nave.getVidas(), score, 
					velXAsteroides+3, velYAsteroides+3, cantAsteroides+10, 1);
			ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
		  }
	    	 
	}
    
    public boolean agregarBala(Bullet bb) {
    	return balas.add(bb);
    }
    
    public boolean agregarBalaEnemy(Bullet bb) {
    	return balas.add(bb);
    }
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		gameMusic.play();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		this.explosionSound.dispose();
		this.gameMusic.dispose();
	}
   
}
