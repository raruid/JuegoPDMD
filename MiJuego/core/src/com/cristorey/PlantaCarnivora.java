/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cristorey;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.cristorey.Mapas.Bola;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class PlantaCarnivora extends Image{
    Animation stayLeft;
    Texture disparo;
    Array<Rectangle> plantas;
    
    float Y_VELOCITY = 10f;
    float GRAVITY = 0.08f;
    
    float time = 0;
    boolean canJump = false;
    boolean isDown = true;
    boolean isFacingRight = true;
    boolean sube = true;
    TiledMapTileLayer layer;
    private ArrayList<Rectangle> vectorDisparos = new ArrayList();
    long tiempoUltimoDisparo;
    Rectangle plantaRectangle;

    public PlantaCarnivora() {
        final float width = 32;
        final float height = 32;
        this.setSize(1, height / width);
        
        plantaRectangle = new Rectangle();
        plantaRectangle.x = this.getX();
	plantaRectangle.y = this.getY();
        
        plantaRectangle.width = 32;
	plantaRectangle.height = 32;
        
        Texture bolaLeftTexture = new Texture("planta2.png");
        TextureRegion[][] grid2 = TextureRegion.split(bolaLeftTexture, (int) 32, (int) 32);

        stayLeft = new Animation(0.05f, grid2[0][0], grid2[0][1], grid2[0][2], grid2[0][3], grid2[0][4], grid2[0][5], grid2[0][6], grid2[0][7], grid2[0][8], grid2[0][9]);
        stayLeft.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);        
        
    }

    public void act(float delta) {
        time = time + delta;
        
        //System.out.println(xVelocity);
        //System.out.println(yVelocity);
        
        System.out.println("La x dedeasdbflsadbfes: " + this.getX());
        System.out.println("La y asdfasdfasdfsfes: " + this.getY());
        System.out.println(isDown);
        System.out.println(canJump);
        
        float x = this.getX();
        float y = this.getY();
        
        Y_VELOCITY = Y_VELOCITY + GRAVITY;
        
        
        if(x < 212){
            GRAVITY = 0.08f;
            float xChange = Y_VELOCITY * delta;
            this.setPosition(x + xChange, y);
        }else if(x > 0){
            GRAVITY = -0.08f;
            Y_VELOCITY = Y_VELOCITY * (-1) - GRAVITY;
            float xChange = Y_VELOCITY * delta;
            this.setPosition(x + xChange, y);
        }
        
        
    }

    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame = null;
        
        if (isFacingRight == true) {
            frame = (TextureRegion) stayLeft.getKeyFrame(time);
        }
        
        //batch.draw(disparo, 32, 8,disparo.getWidth(), disparo.getHeight());
        batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        
        
    }
    
    
    private void crearDisparo() {
        Rectangle disparo = new Rectangle();
        disparo.x = this.getX() - 32;
        disparo.y = this.getY();
        disparo.width = 64;
        disparo.height = 64;
        this.vectorDisparos.add(disparo);
        this.tiempoUltimoDisparo = TimeUtils.nanoTime();
    }
    

    public boolean canMoveTo(float startX, float startY) {
        float endX = startX + this.getWidth();
        float endY = startY + this.getHeight();

        int x = (int) startX;
        
        while (x < endX) {
            
            int y = (int) startY;
            
            while (y < endY) {
                if (layer.getCell(x, y) != null) {
                    return false;
                }
                y = y + 1;
            }
            x = x + 1;
        }
        

        return true;
    }    
}
