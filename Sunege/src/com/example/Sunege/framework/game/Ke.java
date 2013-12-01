package com.example.Sunege.framework.game;

import java.util.Random;

import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

import com.example.Sunege.framework.Graphics;

public class Ke extends Sprite {

	private static final float TICK_INITIAL = 1.0f;
	private static float tick = TICK_INITIAL; // 更新速度
	private float tickTime;
	private static int ADD_LEVEL = 10;
	public int level;
	public String type = "A";
	private int image_width;
	private int image_height;
	private int touch_width;
	private int touch_height;

	public Ke() {
		this.image = Assets.image_ke;
		image_width = 24;
		image_height = 145;
		touch_width = 25;
		touch_height = 25;
		Random rand = new Random();
		this.x = rand.nextInt(480 - width);
		this.y = rand.nextInt((800 - 245)) + 100;
		this.level = ADD_LEVEL;
	}
	
	public Ke(double x, double y, int level, String type) {
		this.x = x;
		this.y = y; 
		this.level = level;
		this.type = type;
		this.image = Assets.image_ke;
		image_width = 24;
		image_height = 145;
		touch_width = 25;
		touch_height = 25;
	}
	
	@Override
	public void Update(float deltaTime) {
		tickTime += deltaTime;
		while (tickTime > tick) {
			tickTime -= tick;
			if (level < 110)
				level += ADD_LEVEL;
		}
	}

	public void draw(Graphics g) {
		Rect src = new Rect(0, 0, image_width, image_height);
		Rect dst = new Rect((int) x, (int) y, (int) x + image_width,
				(int) (y + (image_height * (level * 0.01))));
		g.drawPixmap(Assets.image_ke, src, dst);
	}
	
	public void setLevel(int newlevel) {
		this.level = newlevel;
	}

}
