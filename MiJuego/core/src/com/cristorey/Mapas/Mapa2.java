/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cristorey.Mapas;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.*;
import com.cristorey.MyGdxGame;
import com.cristorey.PlantaCarnivora;
import java.util.ArrayList;
import java.util.Iterator;

public class Mapa2 implements Screen {
    
    MyGdxGame game;
    Stage stage;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    Bola bola;
    ArrayList<PlantaCarnivora> plantas = new ArrayList();

    public Mapa2(MyGdxGame game) {
        this.game = game;
    }

    public void show() {
        map = new TmxMapLoader().load("finalo2.tmx");
        final float pixelsPerTile = 16;
        renderer = new OrthogonalTiledMapRenderer(map, 1 / pixelsPerTile);
        camera = new OrthographicCamera();

        stage = new Stage();
        stage.getViewport().setCamera(camera);
        
        planteaCarnivoramente(0, 0);

        bola = new Bola();
        bola.layer = (TiledMapTileLayer) map.getLayers().get("walls");
        bola.setPosition(2, 5);
        stage.addActor(bola);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //float xChange = CAMERA_VELOCITY * delta;
        
        //camera.position.x = xChange;
        
        //if(bola.getX() > 15 ){
            camera.position.x = bola.getX();
            camera.update();
        //}else{
        //    camera.position.x = 14;
        //}    

        renderer.setView(camera);
        renderer.render();
        
        //planteaCarnivoramente(0, 0);
        colisionesPlanta(bola.getX(), bola.getY());
        
        seteaSetamente(bola.getX(), 0);
        checkWin();
        seCae();
        pincha(bola.getX() ,0);

        stage.act(delta);
        stage.draw();
    }

    public void dispose() {
    }

    public void hide() {
    }

    public void pause() {
    }

    public void resize(int width, int height) {
        camera.setToOrtho(false, 20 * width / height, 20);
    }

    public void resume() {
    }
    
    private void seCae(){
        if(bola.getY() < 0 || bola.getY() > 20){
            game.setScreen(new Mapa2(this.game));
            this.dispose();
        }
    }
    
    private void miraColisiones(float x, float y){
        if(bola.getX() < x + 0.5 && bola.getX() > x - 0.5 && bola.getY() < y + 0.5 && bola.getY() > y - 0.5){
            System.out.println("Mepinchao");
            
            game.setScreen(new Mapa2(this.game));
            this.dispose();
        }
    }
    
    private void pincha(float startX, float startY) {
        TiledMapTileLayer pinchos = (TiledMapTileLayer) map.getLayers().get("pinchos");
        
        float endX = startX + pinchos.getWidth();
        float endY = startY + pinchos.getHeight();

        int x = (int) startX;
        
        while (x < endX) {
            
            int y = (int) startY;
            
            while (y < endY) {
                if (pinchos.getCell(x, y) != null) {
                    if(pinchos.getProperties().get("pincha", Boolean.class) == true){
                        miraColisiones(x, y);
                    }
                }
                y = y + 1;
            }
            x = x + 1;
        }
    }    
    
    private void checkWin(){
        if(bola.getX() > 213){
            game.setScreen(new Win1Screen(this.game));
            this.dispose();
        }
    }
    
    private void colisionesPlanta(float x, float y){
        
        for(int i = 0; i < plantas.size(); i++){
            if(plantas.get(i).getX() < x + 0.5 && plantas.get(i).getX() > x - 0.5 && plantas.get(i).getY() < y + 0.5 && plantas.get(i).getY() > y - 0.5){
                game.setScreen(new Mapa2(this.game));
                this.dispose();
            }
        }
    }
    
    private void planteaCarnivoramente(float startX, float startY) {
        
            TiledMapTileLayer plantas = (TiledMapTileLayer) map.getLayers().get("planta");

            float endX = startX + plantas.getWidth();
            float endY = startY + plantas.getHeight();

            int x = (int) startX;

            while (x < endX) {

                int y = (int) startY;

                while (y < endY) {
                    if (plantas.getCell(x, y) != null) {
                        if(plantas.getProperties().get("pl", Boolean.class) == true){
                            this.spawnPlanta(x, y + 1);
                        }
                    }
                    y = y + 1;
                }
                x = x + 1;
            }
        }  
    
    private void miraColisionesSetas(float x, float y){
        if(bola.getX() < x + 0.5 && bola.getX() > x - 0.5 && bola.getY() < y + 0.5 && bola.getY() > y - 0.5){
            System.out.println("LEDAOOOOOO");
            if(bola.getIsDown() == true){
                bola.setIsDown(false);
            }else{
                bola.setIsDown(true);
            }
        }
    }
    
    private void seteaSetamente(float startX, float startY) {
        TiledMapTileLayer setas = (TiledMapTileLayer) map.getLayers().get("setas");
        
        float endX = startX + setas.getWidth();
        float endY = startY + setas.getHeight();

        int x = (int) startX;
        
        while (x < endX) {
            
            int y = (int) startY;
            
            while (y < endY) {
                if (setas.getCell(x, y) != null) {
                    if(setas.getProperties().get("setea", Boolean.class) == true){
                        miraColisionesSetas(x, y);
                    }
                }
                y = y + 1;
            }
            x = x + 1;
        }
    }    

    
    private void spawnPlanta(float x, float y) {
        PlantaCarnivora planta = new PlantaCarnivora();
        planta.setX(x);
        planta.setY(y);
        
        stage.addActor(planta);
        
        plantas.add(planta);
    }

}
