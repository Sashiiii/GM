package BulletStrategy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Ball2;

public class BulletXButton extends BulletSpace {

	public BulletXButton(float x, float y) {
		super(x,y);
		spr.setTexture(new Texture(Gdx.files.internal("MisilXButton.png")));
		spr.setScale(6f, 3f);
		soundBala = Gdx.audio.newSound(Gdx.files.internal("misilSound.mp3"));
	}
	public boolean checkCollision(Ball2 b2) {
        
		if(spr.getBoundingRectangle().overlaps(b2.getArea())){
        	// Se destruyen ambos
            return true;

        }
        return false;
	}

}
