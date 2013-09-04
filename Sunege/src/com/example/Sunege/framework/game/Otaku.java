package com.example.project2nd.framework.game;

import android.graphics.Rect;

import com.example.project2nd.framework.Graphics;
import com.example.project2nd.framework.Pixmap;

public class Otaku extends Sprite {

	enum Status {
		Abnormality, NONE
	}

	private static final float TICK_INITIAL = 3.0f;
	private static float tick = TICK_INITIAL; // 更新速度
	private double vx;
	private double speed; // 移動速度
	private float tickTime = 0;
	private boolean flag = false; // true = 異常状態, false = 通常状態
	private Status status;

	public Otaku(double x, double y) {
		this.image = Assets.image_otaku;
		this.x = x;
		this.y = y;
		width = 50;
		height = 60;
		speed = 10;
		status = Status.NONE;
		vx = 0;
	}

	@Override
	public void Update() {
	}

	public void Update(float deltaTime) {
		if (status == Status.Abnormality) {
			tickTime += deltaTime;
			while (tickTime > tick) {
				tickTime -= tick;
				flag = false;
				status = Status.NONE;
				image = Assets.image_otaku;
			}
		} else {
			if (x + vx < 0 || x + vx + width > 480)
				vx = 0;
			x += vx;
		}
	}

	// 左移動
	public void accelerateLeft() {
		vx = -speed;
	}

	// 右移動
	public void accelerateRight() {
		vx = speed;
	}

	public void Cancel() {
		vx = 0;
	}

	/*
	 * 他のスプライトと接触しているか
	 */
	public boolean isCollision(Sprite sprite) {
		Rect playerRect = new Rect((int) x, (int) y, width + (int) x, height
				+ (int) y);
		Rect spriteRect = new Rect((int) sprite.getX(), (int) sprite.getY(),
				(int) sprite.getWidth() + (int) sprite.getX(),
				(int) sprite.getHeight() + (int) sprite.getY());
		if (playerRect.intersect(spriteRect)) {
			return true;
		} // //Rect同士ぶつかり合っていたらtrue
		return false;
	}

	// 異常状態にする
	public void setFlag() {
		flag = true;
		status = Status.Abnormality;
		image = Assets.image_nototaku; // 無敵状態は画像変えるよ！
	}

	public boolean getflag() {
		return flag; // true = 異常状態, false = 通常状態
	}

}
