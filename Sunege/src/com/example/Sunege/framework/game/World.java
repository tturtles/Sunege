package com.example.Sunege.framework.game;

import java.util.LinkedList;

import com.example.Sunege.framework.Graphics;

public class World {

	private static final float TICK_INITIAL = 5.0f;
	private static float tick = TICK_INITIAL; // 更新速度
	private float tickTime;
	private static LinkedList sprites;

	public World() {
		tickTime = 0;
		sprites = new LinkedList();
//		load();
	}

	public static void load() {
		for (; sprites.size() < 100;) {
			sprites.add(new Ke());
		}
	}
	
	public void addSunege(int sum) {
		for (; sprites.size() < sum;) {
			sprites.add(new Ke());
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
