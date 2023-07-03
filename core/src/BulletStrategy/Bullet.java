package BulletStrategy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Ball2;
import com.mygdx.game.Enemy;
import com.mygdx.game.User;

public class Bullet {
	BulletStrategy b;
	public Bullet(BulletStrategy b) {
		this.b=b;
	}
	public void update() {
		b.update();
	}
	public boolean checkCollision(Object d) {
		if (d instanceof Ball2) {
	        return b.checkCollision((Ball2)d);
	    }
		if (d instanceof Enemy) {
	        return b.checkCollision((Enemy)d);
	    }
		if (d instanceof User) {
	        return b.checkCollision((User)d);
	    }
		return false;
	}
	public void draw(SpriteBatch batch) {
		b.draw(batch);
	}
	public boolean isDestroyed() {
		return b.isDestroyed();
	}
	public Rectangle getArea() {
		return b.getArea();
	}
	public void disparar() {
		b.disparar();
	}
}
