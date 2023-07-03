package BulletStrategy;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Ball2;
import com.mygdx.game.Enemy;
import com.mygdx.game.User;

public interface BulletStrategy{
	public void update();
	public void draw(SpriteBatch batch);
    public void disparar();
	public boolean checkCollision(User nav);
	public boolean checkCollision(Enemy enemy1);
	public boolean isDestroyed();
	public Rectangle getArea();
	public boolean checkCollision(Ball2 b2); 
}
