/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cristorey.Mapas;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class Bola extends Image {
    TextureRegion stand, jump;
    Animation walkDown;
    Animation walkUp;

    float GRAVITY = -2.5f;
    final float MAX_VELOCITY = 10f;
    final float DAMPING = 0.87f;
    
    float time = 0;
    float xVelocity = MAX_VELOCITY;
    float yVelocity = 0;
    boolean canJump = false;
    boolean isDown = true;
    boolean cambiar;
    boolean isFacing = true;
    TiledMapTileLayer layer;

    public Bola() {
        final float width = 28;
        final float height = 26;
        this.setSize(1, height / width);
        
        cambiar = true;

        Texture bolaDownTexture = new Texture("bola.png");
        TextureRegion[][] grid = TextureRegion.split(bolaDownTexture, (int) 32, (int) 32);

        stand = grid[0][0];
        jump = grid[0][0];
        walkDown = new Animation(0.20f, grid[0][0], grid[1][0], grid[2][0], grid[3][0], grid[6][0], grid[6][0], grid[4][0], grid[5][0]);
        //walkDown = new Animation(0.05f, grid[0][0], grid[0][1], grid[0][2], grid[0][3], grid[0][4], grid[0][5], grid[0][6], grid[0][7], grid[0][8], grid[0][9]);

        walkDown.setPlayMode(Animation.PlayMode.LOOP);
        
        Texture bolaUpTexture = new Texture("bola2.png");
        TextureRegion[][] grid2 = TextureRegion.split(bolaUpTexture, (int) 32, (int) 32);

        stand = grid[0][0];
        jump = grid[0][0];
        walkUp = new Animation(0.20f, grid2[0][0], grid2[1][0], grid2[2][0], grid2[3][0], grid2[6][0], grid2[6][0], grid2[4][0], grid2[5][0]);
        walkUp.setPlayMode(Animation.PlayMode.LOOP);        
        
    }
    
    public void setIsDown(boolean isDown){
        this.isDown = isDown;
        
        if(cambiar == false){
            cambiar = true;
        }else if(cambiar == true){
            cambiar = false;
        }
    }
    
    public boolean getIsDown(){
        return this.isDown;
    }

    public void act(float delta) {
        time = time + delta;
        
        System.out.println(xVelocity);
        System.out.println(yVelocity);
        System.out.println("La x es: " + this.getX());
        System.out.println("La y es: " + this.getY());
        System.out.println(isDown);
        System.out.println(canJump);

        boolean upTouched = Gdx.input.isTouched() && Gdx.input.getY() < Gdx.graphics.getHeight() / 2;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || upTouched) {
            if (canJump) {
                if(isDown == true){
                    this.GRAVITY = 2.5f;
                    isDown = false;
                }else if(isDown == true){
                    this.GRAVITY = -2.5f;
                    isDown = false;
                }
            }
            canJump = true;
        }
        
        boolean downTouched = Gdx.input.isTouched() && Gdx.input.getX() < Gdx.graphics.getWidth() / 3;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || downTouched) {
            if (canJump) {
                if(isDown == false){
                    this.GRAVITY = -2.5f;
                    isDown = true;
                }else if(isDown == false){
                    this.GRAVITY = 2.5f;
                    isDown = true;
                }
            }
            canJump = true;
        }
        
        /*
        boolean leftTouched = Gdx.input.isTouched() && Gdx.input.getX() < Gdx.graphics.getWidth() / 3;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || leftTouched) {
            xVelocity = -1 * MAX_VELOCITY;
            isFacingRight = false;
        }
        

        boolean rightTouched = Gdx.input.isTouched() && Gdx.input.getX() > Gdx.graphics.getWidth() * 2 / 3;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || rightTouched) {
            xVelocity = MAX_VELOCITY;
            isFacingRight = true;
        }
        */

        yVelocity = yVelocity + GRAVITY;

        float x = this.getX();
        float y = this.getY();
        float xChange = xVelocity * delta;
        float yChange = yVelocity * delta;

        
        if (canMoveTo(x + xChange, y) == false) {
            xVelocity = xChange = 0;
        }
        
        
        if (canMoveTo(x, y + yChange) == false) {
            yVelocity = yChange = 0;
        }
        
        this.setPosition(x + xChange, y + yChange);
        

        //xVelocity = xVelocity * DAMPING;
        //if (Math.abs(xVelocity) < 0.5f) {
            xVelocity = MAX_VELOCITY;
        //}
        
    }

    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame;

        if (this.isDown == false) {
            frame = (TextureRegion) walkUp.getKeyFrame(time);
        } else if (this.isDown == true) {
            frame = (TextureRegion) walkDown.getKeyFrame(time);
        } else {
            frame = stand;
        }

        if (isFacing) {
            batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } 
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
