package com.mygdx.cwg;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.awt.Rectangle;

public class CWGame implements Screen {
	final Starter game;
	// Constant rows and columns of the sprite sheet
	private static final int WALKFRAME_COLS = 8, WALKFRAME_ROWS = 1;
	private static final int SHOOTFRAME_COLS = 7, SHOOTFRAME_ROWS = 1;
	


	Animation<TextureRegion> walkAnimation; // Must declare frame type (TextureRegion)
	Animation<TextureRegion> walkLeftAnimation; // Must declare frame type (TextureRegion)
	Animation<TextureRegion> shootAnimation; // Must declare frame type (TextureRegion)
	Animation<TextureRegion> shootLeftAnimation; // Must declare frame type (TextureRegion)
	TextureRegion bgRegion;
	Texture walkSheet;
	Texture walkLeftSheet;
	Texture shootSheet;
	Texture shootLeftSheet;
	Texture background;

	Sprite backgroundSprite;
	Rectangle Clone;

	Stage stage;
	Sound shootSound;
	boolean Rig;
	// A variable for tracking elapsed time for the animation
	float stateTime;


	public CWGame(Starter game) {
		this.game = game;


		shootSound = Gdx.audio.newSound(Gdx.files.internal("blasterSound.wav"));
		// Load the sprite sheet as a Texture

		background = new Texture(Gdx.files.internal("Background.png"));
		backgroundSprite = new Sprite(background);

		bgRegion = new TextureRegion(background, 0, 0, 600, 600);
		int fonsx = 0;
		int fonsy = 0;

		walkSheet = new Texture(Gdx.files.internal("CloneRegularWalk.png"));
		walkLeftSheet = new Texture(Gdx.files.internal("CloneRegularWalkLeft.png"));
		shootSheet = new Texture(Gdx.files.internal("CloneRegularShoot.png"));
		shootLeftSheet = new Texture(Gdx.files.internal("CloneRegularShootLeft.png"));
		// Use the split utility method to create a 2D array of TextureRegions. This is
		// possible because this sprite sheet contains frames of equal size and they are
		// all aligned.





		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / WALKFRAME_COLS,
				walkSheet.getHeight() / WALKFRAME_ROWS);
		TextureRegion[][] tmpL = TextureRegion.split(walkLeftSheet,
				walkLeftSheet.getWidth() / WALKFRAME_COLS,
				walkLeftSheet.getHeight() / WALKFRAME_ROWS);
		TextureRegion[][] tmpS = TextureRegion.split(shootSheet,
				shootSheet.getWidth() / SHOOTFRAME_COLS,
				shootSheet.getHeight() / SHOOTFRAME_ROWS);
		TextureRegion[][] tmpSL = TextureRegion.split(shootLeftSheet,
				shootLeftSheet.getWidth() / SHOOTFRAME_COLS,
				shootLeftSheet.getHeight() / SHOOTFRAME_ROWS);


		// Place the regions into a 1D array in the correct order, starting from the top
		// left, going across first. The Animation constructor requires a 1D array.




		TextureRegion[] walkFrames = new TextureRegion[WALKFRAME_COLS * WALKFRAME_ROWS];
		int index = 0;
		for (int i = 0; i < WALKFRAME_ROWS; i++) {
			for (int j = 0; j < WALKFRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}

		TextureRegion[] walkLeftFrames = new TextureRegion[WALKFRAME_COLS * WALKFRAME_ROWS];
		int indexL = 0;
		for (int i = 0; i < WALKFRAME_ROWS; i++) {
			for (int j = 0; j < WALKFRAME_COLS; j++) {
				walkLeftFrames[indexL++] = tmpL[i][j];
			}
		}

		TextureRegion[] shootFrames = new TextureRegion[SHOOTFRAME_COLS * SHOOTFRAME_ROWS];
		int indexS = 0;
		for (int i = 0; i < SHOOTFRAME_ROWS; i++) {
			for (int j = 0; j < SHOOTFRAME_COLS; j++) {
				shootFrames[indexS++] = tmpS[i][j];
			}
		}

		TextureRegion[] shootLeftFrames = new TextureRegion[SHOOTFRAME_COLS * SHOOTFRAME_ROWS];
		int indexSL = 0;
		for (int i = 0; i < SHOOTFRAME_ROWS; i++) {
			for (int j = 0; j < SHOOTFRAME_COLS; j++) {


				shootLeftFrames[indexSL++] = tmpSL[i][j];
			}
		}

		// Initialize the Animation with the frame interval and array of frames
		walkAnimation = new Animation<TextureRegion>(0.155f, walkFrames);
		walkLeftAnimation = new Animation<TextureRegion>(0.155f, walkLeftFrames);
		shootAnimation = new Animation<TextureRegion>(0.10f, shootFrames);
		shootLeftAnimation = new Animation<TextureRegion>(0.10f, shootLeftFrames);
		// Instantiate a SpriteBatch for drawing and reset the elapsed animation
		// time to 0
		game.spriteBatch = new SpriteBatch();


		Clone =  new Rectangle();
		Clone.x=250;
		Clone.y=250;

		stateTime = 0f;
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear screen
		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

		// Get current frame of animation for the current stateTime
		TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);

		game.spriteBatch.begin();
		renderBackground();
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			Rig=false;
			game.spriteBatch.draw(walkLeftAnimation.getKeyFrame(stateTime, true), Clone.x, Clone.y);
			if(Clone.x>0){

				Clone.x -= 100 * Gdx.graphics.getDeltaTime();
			}

		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			Rig=true;
			game.spriteBatch.draw(walkAnimation.getKeyFrame(stateTime, true), Clone.x, Clone.y);
			if(Clone.x<600){
				Clone.x += 120 * Gdx.graphics.getDeltaTime();
			}


		} else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if (Rig){
				game.spriteBatch.draw(walkAnimation.getKeyFrame(stateTime, true), Clone.x, Clone.y);
			}else{
				game.spriteBatch.draw(walkLeftAnimation.getKeyFrame(stateTime, true), Clone.x, Clone.y);
			}
			if(Clone.y<550){
				Clone.y += 130 * Gdx.graphics.getDeltaTime();
			}


		}
		else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			if (Rig){
				game.spriteBatch.draw(walkAnimation.getKeyFrame(stateTime, true), Clone.x, Clone.y);
			}else{
				game.spriteBatch.draw(walkLeftAnimation.getKeyFrame(stateTime, true), Clone.x, Clone.y);
			}
			if (Clone.y>0){
				Clone.y -= 110 * Gdx.graphics.getDeltaTime();
			}

		}
		else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			if (Rig){
				if (shootAnimation.getKeyFrameIndex(0.10f)==7){
					shootSound.play();
				}
				game.spriteBatch.draw(shootAnimation.getKeyFrame(stateTime, true), Clone.x, Clone.y);
			}else{

				game.spriteBatch.draw(shootLeftAnimation.getKeyFrame(stateTime, true), Clone.x, Clone.y);
			}

		}
		else{
			if (Rig){
				game.spriteBatch.draw(walkAnimation.getKeyFrame(7) , Clone.x, Clone.y);
			}else{
				game.spriteBatch.draw(walkLeftAnimation.getKeyFrame(7) , Clone.x, Clone.y);
			}



		}
		game.spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {

	}



	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() { // SpriteBatches and Textures must always be disposed
		game.spriteBatch.dispose();
		walkSheet.dispose();
	}

	public void renderBackground(){
		backgroundSprite.draw(game.spriteBatch);

	}
}
