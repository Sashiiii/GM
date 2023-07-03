package BulletStrategy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Ball2;
import com.mygdx.game.Enemy;
import com.mygdx.game.User;


public class BulletSpace implements BulletStrategy{

	protected int xSpeed;
	protected int ySpeed;
	protected int x;
	protected int y;
	protected boolean destroyed = false;
	protected Sprite spr;
	protected Sound soundBala;
	    
	    public BulletSpace(float x, float y) { //recibe builder float x, float y, int xSpeed, int ySpeed, Texture tx
	    	spr = new Sprite(new Texture(Gdx.files.internal("Rocket2.png")));
	    	soundBala = Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"));
	    	spr.setPosition(x, y);
	        this.xSpeed = 0;
	        this.ySpeed = 3;
	        
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
		@Override
		public Rectangle getArea() {
			// TODO Auto-generated method stub
			return  spr.getBoundingRectangle();
		}
		@Override
		public boolean checkCollision(User nav) {
			// TODO Auto-generated method stub
			return false;
		}
		public void disparar() {
			soundBala.play();
		}

	
}
