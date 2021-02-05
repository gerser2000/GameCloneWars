package com.mygdx.cwg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Starter extends Game{

    SpriteBatch spriteBatch;
    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        BitmapFont font = new BitmapFont();
        this.setScreen (new CWGame(this));


    }




}
