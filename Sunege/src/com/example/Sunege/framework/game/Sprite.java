package com.example.Sunege.framework.game;

import com.example.Sunege.framework.Graphics;
import com.example.Sunege.framework.Pixmap;

import android.graphics.Rect;

public abstract class Sprite {
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected Pixmap image;
	protected int speedX;
	protected int speedY;
	
	private static int LOOP_COUNT = 30;
	private int count = LOOP_COUNT;

	public void Update(){};
	
	public void Update(float deltaTime){};

	public void draw(Graphics g) {
		g.drawPixmap(this.image, (int) x, (int) y);
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
	

	public boolean nukeru() {
		if (count > 0) {
			x--;
			y--;
			count--;
			return false;
		} else {
			count = LOOP_COUNT;
			return true;
		}
	}
	

}
