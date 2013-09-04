package com.example.project2nd.framework.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.widget.Button;

import com.example.project2nd.framework.Graphics;
import com.example.project2nd.framework.Pixmap;

public class World {
	private static final float TICK_INITIAL = 1.0f;
	private static float tick = TICK_INITIAL; // 更新速度
	private float tickTime;
	private LinkedList sprites;
	private ArrayList list;
	private int list_count = 0;
	private int item_sun = 40;
	private int item_kind = 4;
	private boolean flag = false;

	public World() {
		sprites = new LinkedList();
		tickTime = 0;
		load();
	}

	public void load() {
		list = new ArrayList();
		for (int i = 0; i < item_sun / item_kind; i++) {
			list.add(new Doujinshi());
			list.add(new Figure());
			list.add(new Tapestry());
			list.add(new Bl());
		}
		Collections.shuffle(list);
		sprites.add(list.get(list_count++));
	}

	public void update(float deltaTime) {
		tickTime += deltaTime;
		while (tickTime > tick) {
			tickTime -= tick;
			if (list_count < item_sun) {
				sprites.add(list.get(list_count));
				list_count++;
			} else {
				flag = true;
			}
		}
	}

	public LinkedList getSprite() {
		return sprites;
	}

	public void draw(Graphics g) {
		Paint paint = new Paint();
		paint.setTextSize(20);
		paint.setColor(Color.BLACK);
		g.drawTextAlp("count : " + list_count, 0, 30, paint); // 試験的
	}

	public int getItem_sun() {
		return item_sun;
	}

	public int getList_count() {
		return list_count;
	}

	public LinkedList getSprites() {
		return sprites;
	}

	public boolean isFlag() {
		return flag;
	}

}
