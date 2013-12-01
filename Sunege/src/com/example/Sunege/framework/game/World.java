package com.example.Sunege.framework.game;

import java.util.LinkedList;

import android.graphics.Color;
import android.util.Log;

import com.example.Sunege.framework.Graphics;

public class World {

	private static final float TICK_INITIAL = 5.0f;
	private static float tick = TICK_INITIAL; // 更新速度
	private float tickTime;
	private static LinkedList sprites;

	public World() {
		tickTime = 0;
		sprites = new LinkedList();
		// load();
	}

	public void load() {
		for (; sprites.size() < 100;) {
			sprites.add(new Ke());
		}
	}

	public void load(String[][] list) {
		int i = 0;
		for (i = 0; i < list.length && i < 500; i++) {
			if (list[i][0] != null)
				sprites.add(new Ke(Integer.parseInt(list[i][0]), Integer
						.parseInt(list[i][1]), Integer.parseInt(list[i][2]),
						list[i][3]));
		}
	}

	public void addSunege(int sum) {
		for (; sprites.size() < sum;) {
			Ke ke = new Ke();
			sprites.add(ke);
			ke.setLevel(100);
		}
	}

	public void update(float deltaTime) {
		tickTime += deltaTime;
		while (tickTime > tick) {
			tickTime -= tick;
			sprites.add(new Ke());
		}
	}

	public void draw(Graphics g) {

	}

	public LinkedList getSprites() {
		return sprites;
	}
}
