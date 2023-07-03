package BulletStrategy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.User;


public class BulletEnemy extends BulletSpace{

	public BulletEnemy(float x, float y) {
		super(x,y);
		spr.setTexture(new Texture(Gdx.files.internal("bulletEnemy.png")));
		xSpeed=0;
		ySpeed=3;
		spr.setScale(3f,2f);
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
	
	public boolean checkCollision(User nav) {
        if(spr.getBoundingRectangle().overlaps(nav.getArea())){
        	// Se destruyen ambos
            this.destroyed = true;
            return true;

        }
        return false;
    }
	
	

}
