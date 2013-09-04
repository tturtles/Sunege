package com.example.project2nd.framework.game;

import java.util.Random;

import com.example.project2nd.framework.Graphics;
import com.example.project2nd.framework.Pixmap;

public class Tapestry extends Sprite {

	private int vy;
	private int vx;

	private static final float TICK_INITIAL = 0.5f;
	private static float tick = TICK_INITIAL; // 更新速度
	private float tickTime;
	private int farst_x = 0;
	private int move_x = 150; // 左右に移動する限界値

	public Tapestry() {
		image = Assets.image_tapestry;
		width = 50;
		height = 50;
		Random rand = new Random();
		this.x = farst_x = rand.nextInt(480 - width);
		this.y = -height;
		speedY = 3;
		speedX = 3;
		vx = speedX;
		vy = speedY;
	}

	public void Update() {
			if (x - farst_x > move_x || x + width > 480)
				vx = -speedX;
			else if (farst_x - x > move_x || x < 0)
				vx = speedX;
			y += vy;
			x += vx;
	}

	/*
	 * 以下getter,setter群
	 */
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

}
