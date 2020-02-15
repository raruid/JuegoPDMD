package com.cristorey;

import com.cristorey.Mapas.Mapa1;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cristorey.Mapas.MainMenu;
import com.cristorey.Mapas.Mapa2;

public class MyGdxGame extends Game {
    
    public SpriteBatch batch;
    public BitmapFont font;
    
	public void create() {
            batch = new SpriteBatch();
            font = new BitmapFont(Gdx.files.internal("RollerRuler.fnt"));
            this.setScreen(new Mapa2(this));
	}
        
        public void dispose() {
            batch.dispose();
            font.dispose();    
	}   
}
