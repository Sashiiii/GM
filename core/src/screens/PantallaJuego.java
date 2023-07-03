package screens;

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
import com.mygdx.game.SpaceNavigation;
import com.mygdx.game.User;

import BulletStrategy.Bullet;

import com.mygdx.game.*;

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
	
	private  ArrayList<Ball2> balls1 = new ArrayList<>();
	private  ArrayList<Ball2> balls2 = new ArrayList<>();
	
	private  ArrayList<Enemy1> naves1 = new ArrayList<>();
	//private  ArrayList<Enemy1> naves2 = new ArrayList<>();

	public PantallaJuego(SpaceNavigation game, int velXAsteroides, int velYAsteroides, int cantAsteroides, int cantEnemy1) {
		this.game = game;
		this.ronda = game.getRonda();
		this.score = game.getScore();
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
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("satie.mp3")); //
		
		gameMusic.setLooping(true);
		gameMusic.setVolume(0.5f);
		gameMusic.play();
		
	    // cargar imagen de la nave, 64x64   
	    User.getInstance().setVidas(game.getVida());
        
        //crear asteroides
	    crearAsteroides();
	    crearEnemigos();
	    
	}
    
	public void dibujaEncabezado() {
		CharSequence str = "Vidas: "+User.getInstance().getVidas()+" Ronda: "+ronda;
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
	      if (!User.getInstance().estaHerido()) {
		      // colisiones entre balas y asteroides y su destruccion
	    	  
	    	  colisionBalasEnemy(User.getInstance().getBalas());  
	    	  score += User.getInstance().colisionBalasAsteroides(explosionSound, balls1, balls2);
	    	  colisionBulletEnUs();
	    	  actualizarEnemigos(deltaTime);
		      //actualizar movimiento de asteroides dentro del area
		      actualizarAsteroides();
		      //colisiones entre asteroides y sus rebotes  
		      colisionAsteroides(); 
		  }
	      
	      //dibujar balas
	      User.getInstance().drawBalas(batch);
	      disparoNavesEn();
	      for (Enemy1 enemyy: naves1) {       
	          enemyy.draw(batch,this);
	      }
	      User.getInstance().draw(batch, this);
	      //dibujar asteroides y manejar colision con nave
	      User.getInstance().colisionNaveAsteroides(batch, balls1, balls2);
	    
	      if (User.getInstance().estaDestruido()) {
	    	User.setNull();
	    	User.getInstance();
  			gameover();
  		  }
	      
	     //nivel completado
	      checkBalls();
	      batch.end();
}
    
	public void gameover() {
		if (score > game.getHighScore()) game.setHighScore(score);
		    User.getInstance().setVidas(3);
		    game.setVida(3);
		    game.setRonda(1);
			Screen ss = new PantallaGameOver(game);
			ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
	}
	public void checkBalls() {
		if (balls1.size()==0) {
	      	game.setVida(User.getInstance().getVidas());
	      	game.setRonda(ronda+1);
			Screen ss = new PantallaJuego(game, velXAsteroides+3, velYAsteroides+3, cantAsteroides+10, 1);
			ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
		  }
	}
	public void actualizarEnemigos(float deltaTime) {
		for (Enemy1 enn : naves1) {
	          enn.update();
	          enn.setDeltaTime(deltaTime);
	      }
	}
	public void actualizarAsteroides() {
		for (Ball2 ball : balls1) {
	          ball.update();
	      }
	}
    public void crearAsteroides() {
    	for (int i = 0; i < cantAsteroides; i++) {
	        Ball2 bb = new Ball2( velXAsteroides, velYAsteroides);	   
	  	    balls1.add(bb);
	  	    balls2.add(bb);
	  	}
    }
    public void crearEnemigos() {
    	int altura = Gdx.graphics.getHeight()-50;
    	for (int i = 0; i < cantEnemy1; i++) {
	        Enemy1 ne = new Enemy1(); 
	        altura = altura - 60;
	  	    naves1.add(ne);
	  	    //naves2.add(ne);
	  	}
    }
	
    public boolean agregarBalaEnemy(Bullet bb) {
    	for (int j = 0; j < naves1.size(); j++) {    
            naves1.get(j).getBalasEnemy().add(bb);   	    
			//naves2.get(j).getBalasEnemy().add(bb);
		}
    	return true;  
    }
    
    
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		gameMusic.play();
	}
	
	public void colisionAsteroides() {
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

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
	//colision balas de usuario con naves enemigas
	public void colisionBalasEnemy(ArrayList<Bullet> balas) {
		//int score=0;
		for (int j = 0; j < naves1.size(); j++) {    
              if (naves1.get(j).colisionBalaUser(balas, explosionSound)) {          
            	 naves1.remove(j);
            	 j--;
            	 score +=1000;
              }
          
              /*if (naves2.get(j).colisionBalaUser(balas, explosionSound)) {          
            	  naves2.remove(j);
            	  j--;
            	  score +=1000;
              } */  	  
	    }
                
	}
	
	//colision balas de enemigos con la nave usuario
	public void colisionBulletEnUs() {
		ArrayList<Bullet> bulletsAct;
		Bullet b;
		for (int i = 0; i < naves1.size(); i++) {  
			bulletsAct=naves1.get(i).getBalasEnemy();
			for(int j=0;j<bulletsAct.size();j++) {
				b=bulletsAct.get(i);
				User.getInstance().checkCollision(b);
			}
		}
	}
	
	//disparo de balas enemigas
	public void disparoNavesEn() {
		for (int j = 0; j < naves1.size(); j++) {    
            naves1.get(j).drawBalas(batch);   	    
			//naves2.get(j).drawBalas(batch);
		}
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
