package com.example.project2nd.framework.game;

import java.util.Random;

import android.graphics.Rect;

import com.example.project2nd.framework.Graphics;
import com.example.project2nd.framework.Pixmap;

public abstract class Sprite {
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected Pixmap image;
	protected int speedX;
	protected int speedY;
	protected boolean flag_update = false;

	public Sprite() {
	}

	public abstract void Update();

	public void draw(Graphics g) {
		g.drawPixmap(image, (int) x, (int) y);
	}

	/*
	 * 他のスプライトとの当たり判定
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

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setFlag_update(boolean flag_update) {
		this.flag_update = flag_update;
	}
	

}
