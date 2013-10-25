package com.example.Sunege.framework.game;

import java.util.Random;

import android.graphics.Color;

import com.example.Sunege.framework.Graphics;

public class Ke extends Sprite {

	private static final float TICK_INITIAL = 0.5f;
	private static float tick = TICK_INITIAL; // 更新速度
	private float tickTime;


	public Ke() {
		this.image = Assets.image_ke;
		this.width = 30;
		this.height = 30;
		Random rand = new Random();
		this.x = rand.nextInt(480 - width);
		this.y = rand.nextInt((800-200) - height)+200;
	}

	@Override
	public void Update() {

	}

	public void draw(Graphics g) {
		g.drawPixmap(image, (int) x, (int) y - (140 - height));
		// g.drawRect((int)x, (int)y, width, height, Color.GRAY);
	}


}
