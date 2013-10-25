package com.example.Sunege.framework.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import com.example.Sunege.framework.Graphics;

public class World {

	private static final float TICK_INITIAL = 1.0f;
	private static float tick = TICK_INITIAL; // 更新速度
	private float tickTime;
	private LinkedList sprites;
	private ArrayList list;

	public World() {
		tickTime = 0;
		list = new ArrayList();
		sprites = new LinkedList();
		load();
	}

	public void load() {
		for (int i = 0; i < 100; i++) {
			list.add(new Ke());
		}
		
		for (int i = 0; i < 100; i++) {
			sprites.add(list.get(i));
		}
	}

	public void update(float deltaTime) {
//		tickTime += deltaTime;
//		while (tickTime > tick) {
//		}
	}

	public void draw(Graphics g) {

	}
	
	public LinkedList getSprites() {
		return sprites;
	}
}
