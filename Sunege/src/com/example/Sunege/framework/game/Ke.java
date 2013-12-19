package com.example.Sunege.framework.game;

import java.util.Random;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.example.Sunege.framework.Graphics;

public class Ke extends Sprite {

	public static final float TICK_INITIAL = 10.0f; // すね毛が伸びる速度
	private static float tick = TICK_INITIAL; // 更新速度
	private float tickTime;
	private static int ADD_LEVEL = 10;
	public int level;
	public String type = "A";
	private int image_width;
	private int image_height;
	private int touch_width;
	private int touch_height;
	private boolean flag_select = false;
	private int move_x = 0;
	private int move_y = 0;
	protected int angle = 10;

	public Ke() {
		this.image = Assets.image_ke;
		image_width = 24;
		image_height = 145;
		touch_width = 25;
		touch_height = 25;
		Random rand = new Random();
		this.x = rand.nextInt(480 - image_width);
		int w_y = 0;
		while (w_y < 100)
			w_y = rand.nextInt(700 - image_height);
		this.y = w_y;
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
		this.x += move_x;
		this.y += move_y;
	}

	public void draw(Graphics g) {
		Rect src = new Rect(0, 0, image_width, image_height);
		Rect dst = new Rect((int) x, (int) y, (int) x + image_width,
				(int) (y + (image_height * (level * 0.01))));
		// if (flag_select)
		// g.drawPixmapTurn(Assets.image_ke, src, dst, 1,
		// (float) (level * 0.01), angle);
		// else
		g.drawPixmap(Assets.image_ke, src, dst);
		// g.drawRect((int)x, (int)y, 2, 2, Color.RED);
	}

	public void moveKe(int move_x, int move_y) {
		this.move_x += move_x;
		this.move_y += move_y;
	}

	public void setLevel(int newlevel) {
		this.level = newlevel;
	}

	public void setFlag_select(boolean flag_select) {
		this.flag_select = flag_select;
	}

	public int getimage_width() {
		return image_width;
	}

	public int getimage_height() {
		return (int) (image_height * (level * 0.01));
	}

	public boolean isFlag_select() {
		return flag_select;
	}

	public void setAngle(Point now_point) {
		if (!flag_select)
			return;
		int r = 10;
		float center_x = (float) (x + image_width / 2);
		x += center_x;
		// float center_y = (float)(y+image_height/2);
		double degree = Math.cos(Math.PI * r) / 180 * now_point.x;
		angle = (int) degree;
	}

}
