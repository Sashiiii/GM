package com.mygdx.game;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import screens.PantallaJuego;

public interface Nave {
    public void draw(SpriteBatch batch, PantallaJuego juego);
    public boolean checkCollision(Ball2 b);
    public boolean estaDestruido();
    public boolean estaHerido();
    public int getVidas();
    public int getX();
    public int getY();
	public void setVidas(int vidas2);
}
