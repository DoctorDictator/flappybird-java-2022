package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	Texture background;
	SpriteBatch batch;
	Circle birdCircle;
	float birdY = 0.0f;
	Texture[] birds;
	Texture bottomTube;
	Rectangle[] bottomTubeRectangles;
	float distanceBetweenTubes;
	int flapState = 0;
	BitmapFont font;
	int gameState = 0;
	Texture gameover;
	float gap = 500.0f;
	float gravity = 2.0f;
	BitmapFont high;
	int highScore;
	float maxTubeOffset;
	int numberOfTubes = 4;
	Random randomGenerator;
	int score = 0;
	int scoringTube = 0;
	Texture tap;
	Texture topTube;
	Rectangle[] topTubeRectangles;
	float[] tubeOffset;
	float tubeVelocity = 4.0f;
	float[] tubeX;
	float velocity = 0.0f;

	public FlappyBird() {
		int i = this.numberOfTubes;
		this.tubeX = new float[i];
		this.tubeOffset = new float[i];
	}

	public void create() {
		this.batch = new SpriteBatch();
		this.background = new Texture("bg.png");
		this.gameover = new Texture("gameover.jpg");
		this.tap = new Texture("tap.png");
		this.birdCircle = new Circle();
		this.font = new BitmapFont();
		this.high = new BitmapFont();
		this.high.setColor(Color.WHITE);
		this.high.getData().setScale(5.0f);
		this.font.setColor(Color.WHITE);
		this.font.getData().setScale(10.0f);
		this.birds = new Texture[2];
		this.birds[0] = new Texture("bird.png");
		this.birds[1] = new Texture("bird2.png");
		this.topTube = new Texture("pillar.png");
		this.bottomTube = new Texture("pillar.png");
		this.maxTubeOffset = (((float) (Gdx.graphics.getHeight() / 2)) - (this.gap / 2.0f)) - 100.0f;
		this.randomGenerator = new Random();
		double width = (double) Gdx.graphics.getWidth();
		Double.isNaN(width);
		this.distanceBetweenTubes = (float) ((width * 3.5d) / 4.0d);
		int i = this.numberOfTubes;
		this.topTubeRectangles = new Rectangle[i];
		this.bottomTubeRectangles = new Rectangle[i];
		startGame();
	}

	public void startGame() {

		this.birdY = (float) ((Gdx.graphics.getHeight() / 2) - (this.birds[0].getHeight() / 2));
		for (int i = 0; i < this.numberOfTubes; i++) {
			this.tubeOffset[i] = (this.randomGenerator.nextFloat() - 0.5f) * ((((float) Gdx.graphics.getHeight()) - this.gap) - 200.0f);
			this.tubeX[i] = ((float) (((Gdx.graphics.getWidth() / 2) - (this.topTube.getWidth() / 2)) + Gdx.graphics.getWidth())) + (((float) i) * this.distanceBetweenTubes);
			this.topTubeRectangles[i] = new Rectangle();
			this.bottomTubeRectangles[i] = new Rectangle();
		}
	}

	public void render() {
		this.batch.begin();
		this.batch.draw(this.background, 0.0f, 0.0f, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
		if (this.gameState == 0) {
			this.batch.draw(this.tap, (float) ((Gdx.graphics.getWidth() / 2) - (this.gameover.getWidth() / 2)), (float) ((Gdx.graphics.getHeight() / 2) - 500));
		}
		int i = this.gameState;
		if (i == 1) {
			if (this.tubeX[this.scoringTube] < ((float) (Gdx.graphics.getWidth() / 2))) {
				this.score++;
				if(this.score <= 100) {
					if (this.tubeVelocity <= 8.0f) {
						this.tubeVelocity = this.tubeVelocity + 0.1f;
					}
				}
				else if(this.score > 100 && this.score<=200){
					if(this.tubeVelocity <= 11.0f){
						this.tubeVelocity = this.tubeVelocity + 0.1f;
					}
				}
				else{
					this.tubeVelocity = 11.0f;
				}
				Gdx.app.log("Score", String.valueOf(this.score));
				int i2 = this.scoringTube;
				if (i2 < this.numberOfTubes - 1) {
					this.scoringTube = i2 + 1;
				} else {
					this.scoringTube = 0;
				}
			}
			if (Gdx.input.justTouched()) {
				this.velocity = -30.0f;
			}
			for (int i3 = 0; i3 < this.numberOfTubes; i3++) {
				if (this.tubeX[i3] < ((float) (-this.topTube.getWidth()))) {
					float[] fArr = this.tubeX;
					fArr[i3] = fArr[i3] + (((float) this.numberOfTubes) * this.distanceBetweenTubes);
					this.tubeOffset[i3] = (this.randomGenerator.nextFloat() - 0.5f) * ((((float) Gdx.graphics.getHeight()) - this.gap) - 200.0f);
				} else {
					float[] fArr2 = this.tubeX;
					fArr2[i3] = fArr2[i3] - this.tubeVelocity;
				}
				this.batch.draw(this.topTube, this.tubeX[i3], ((float) (Gdx.graphics.getHeight() / 2)) + (this.gap / 2.0f) + this.tubeOffset[i3]);
				this.batch.draw(this.bottomTube, this.tubeX[i3], ((((float) (Gdx.graphics.getHeight() / 2)) - (this.gap / 2.0f)) - ((float) this.bottomTube.getHeight())) + this.tubeOffset[i3]);
				this.topTubeRectangles[i3] = new Rectangle(this.tubeX[i3], ((float) (Gdx.graphics.getHeight() / 2)) + (this.gap / 2.0f) + this.tubeOffset[i3], (float) (this.topTube.getWidth() - 100), (float) (this.topTube.getHeight() - 100));
				this.bottomTubeRectangles[i3] = new Rectangle(this.tubeX[i3], ((((float) (Gdx.graphics.getHeight() / 2)) - (this.gap / 2.0f)) - ((float) this.bottomTube.getHeight())) + this.tubeOffset[i3], (float) (this.bottomTube.getWidth() - 100), (float) (this.bottomTube.getHeight() - 100));
			}
			float f = this.birdY;
			if (f <= 0.0f || f >= ((float) Gdx.graphics.getHeight())) {
				this.gameState = 2;
			} else {
				this.velocity += this.gravity;
				this.birdY -= this.velocity;
				Gdx.app.log("Velocity", String.valueOf(this.birdY));
			}
		} else if (i == 0) {
			if (Gdx.input.justTouched()) {
				this.gameState = 1;
			}
		} else if (i == 2) {
			this.batch.draw(this.gameover, (float) ((Gdx.graphics.getWidth() / 2) - (this.gameover.getWidth() / 2)), (float) ((Gdx.graphics.getHeight() / 2) - (this.gameover.getHeight() / 2)));
			if (Gdx.input.justTouched()) {
				int i4 = this.highScore;
				int i5 = this.score;
				if (i4 < i5) {
					this.highScore = i5;
				}
				this.gameState = 1;
				startGame();
				this.score = 0;
				this.scoringTube = 0;
				this.tubeVelocity = 4.0f;
				this.velocity = 0.0f;
			}
		}
		if (this.flapState == 0) {
			this.flapState = 1;
		} else {
			this.flapState = 0;
		}
		this.batch.draw(this.birds[this.flapState], (float) ((Gdx.graphics.getWidth() / 2) - (this.birds[this.flapState].getWidth() / 2)), this.birdY);
		this.font.draw((Batch) this.batch, (CharSequence) String.valueOf(this.score), (float) ((Gdx.graphics.getWidth() / 2) - (this.birds[this.flapState].getWidth() / 3)), (float) ((Gdx.graphics.getHeight() / 2) + 450));
		this.high.draw((Batch) this.batch, (CharSequence) "High Score", (float) (Gdx.graphics.getWidth() / 10), (float) (Gdx.graphics.getHeight() - 100));
		this.font.draw((Batch) this.batch, (CharSequence) String.valueOf(this.highScore), (float) (Gdx.graphics.getWidth() / 10), (float) (Gdx.graphics.getHeight() - 200));
		this.birdCircle.set((float) (Gdx.graphics.getWidth() / 2), this.birdY + ((float) (this.birds[this.flapState].getHeight() / 2)), (float) (this.birds[this.flapState].getWidth() / 2));
		for (int i6 = 0; i6 < this.numberOfTubes; i6++) {
			if (Intersector.overlaps(this.birdCircle, this.topTubeRectangles[i6]) || Intersector.overlaps(this.birdCircle, this.bottomTubeRectangles[i6])) {
				this.gameState = 2;
			}
		}
		this.batch.end();
	}
}
