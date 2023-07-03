package BulletStrategy;

import com.badlogic.gdx.graphics.g2d.Sprite;



public class BulletFactory {
    public static Bullet createBullet(String type, float x, float y) {
        BulletStrategy strategy;

        switch (type) {
            case "s":
                strategy = new BulletSpace(x, y);
                break;
            case "x":
                strategy = new BulletXButton(x, y);
                break;
            case "e":
            	strategy = new BulletEnemy(x,y);
            	 break;
            // Agrega más casos según las estrategias que desees agregar
            default:
                throw new IllegalArgumentException("Tipo de bala no válido");
        }

        return new Bullet(strategy);
    }
}