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
import com.badlogic.gdx.scenes.scene2d.*;
import com.cristorey.MyGdxGame;

public class Mapa1 implements Screen {
    
    MyGdxGame game;
    Stage stage;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    Bola bola;
    int totalIntentos = 0;
    
    final float CAMERA_VELOCITY = 10f;

    public Mapa1(MyGdxGame game) {
        this.game = game;
    }
    
    

    public void show() {
        map = new TmxMapLoader().load("finalo.tmx");
        final float pixelsPerTile = 16;
        renderer = new OrthogonalTiledMapRenderer(map, 1 / pixelsPerTile);
        camera = new OrthographicCamera();

        stage = new Stage();
        stage.getViewport().setCamera(camera);

        bola = new Bola();
        bola.layer = (TiledMapTileLayer) map.getLayers().get("walls");
        bola.setPosition(2, 5);
        stage.addActor(bola);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //if(bola.getX() > 15 ){
            camera.position.x = bola.getX();
            camera.update();
        //}else{
        //    camera.position.x = 14;
        //}    

        renderer.setView(camera);
        renderer.render();
        
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
            game.setScreen(new Mapa1(this.game));
            this.dispose();
            totalIntentos++;
        }
    }
    
    private void checkWin(){
        if(bola.getX() > 212){
            game.setScreen(new Mapa2(this.game));
            this.dispose();
        }
    }
    
    private void miraColisiones(float x, float y){
        if(bola.getX() < x + 0.5 && bola.getX() > x - 0.5 && bola.getY() < y + 0.5 && bola.getY() > y - 0.5){
            System.out.println("Mepinchao");
            
            game.setScreen(new Mapa1(this.game));
            this.dispose();
            totalIntentos++;
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

    
}
