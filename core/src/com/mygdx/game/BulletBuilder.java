package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;



public class BulletBuilder {
		private int xSpeed;
		private int ySpeed;
		private boolean destroyed = false;
		private Sprite spr;
		private float x;
		private float y;
		
		public BulletBuilder (int xSpeed){
			this.xSpeed = xSpeed;
		}
		public BulletBuilder setYSpeed(int ySpeed){
			this.ySpeed = ySpeed;
			return this;
		}
		public BulletBuilder setX(float x){
			this.x = x;
			return this;
		}
		public BulletBuilder setY(float y){
			this.y = y;
			return this;
		}
		public BulletBuilder setSpr(Sprite spr){
			this.spr = spr;
			return this;
		}
		public Bullet build(){
			return new Bullet(this);
		}
		
		public int getXSpeed() {
			return xSpeed;
		}
		public int getYSpeed() {
			return ySpeed;
		}
		public float getX() {
			return x;
		}
		public float getY() {
			return y;
		}
		public boolean getDestroyed() {
			return destroyed;
		}
		public Sprite getSpr() {
			return spr;
		}
}