package com.example.Sunege.framework.game;

import java.util.Iterator;
import java.util.LinkedList;

import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import com.example.Sunege.framework.Game;
import com.example.Sunege.framework.Graphics;

public class World {
	private static final float TICK_INITIAL = 60.0f;
	private static final float TICK_INITIAL_RECKLESS = 0.5f;
	private static float tick = TICK_INITIAL; // 更新速度
	private float tickTime;
	private static LinkedList sprites;

	public World() {
		tickTime = 0;
		sprites = new LinkedList();
		// load();
	}

	public void load() {
		for (; sprites.size() < 150;) {
			sprites.add(new Ke());
		}
	}

	public void load(String[][] list, int dif) {
		int i = 0;
		for (i = 0; i < list.length && i < 100; i++) {
			if (list[i][0] != null) {
				int level = Integer.parseInt(list[i][2]);
				level += (int) (dif / Ke.TICK_INITIAL) * 10;
				if (100 < level)
					level = 100;
				sprites.add(new Ke(Integer.parseInt(list[i][0]), Integer
						.parseInt(list[i][1]), level, list[i][3]));
			}
		}
	}

	public void addSunege(int dif) {
		for (int i = 1; true && i < 150; i++) {
			int w_dif = dif;
			w_dif -= TICK_INITIAL * i;
			if (w_dif < 0)
				break;
			Ke ke = new Ke();
			if (10 < w_dif / Ke.TICK_INITIAL)
				ke.setLevel(100);
			else
				ke.setLevel((int) (w_dif / ke.TICK_INITIAL) * 10);
			sprites.add(ke);
		}
	}

	public void update(float deltaTime) {
		tickTime += deltaTime;
		while (tickTime > tick) {
			tickTime -= tick;
			sprites.add(new Ke());
		}
	}

	public void addBlood(Point pos) {
		sprites.addFirst(new Blood(pos));
	}

	public LinkedList getSprites() {
		return sprites;
	}

	public void DataSave(Game game) {
		Long time = System.currentTimeMillis();
		LinkedList sprites = getSprites();
		Iterator iterator = sprites.iterator();
		int i = 0;
		if (Utils.deleteSunegeRecode(game.getFileIO())) {
			// // 毛の更新
			while (iterator.hasNext()) {
				i++;
				Sprite sprite = (Sprite) iterator.next();
				if (sprite instanceof Ke) {
					Ke ke = (Ke) sprite;
					Utils.addSunegeData(game.getFileIO(), (int) ke.x,
							(int) ke.y, ke.level, ke.type, time);
				}
			}
		}
	}

	public void setReckless() {
		tick = TICK_INITIAL_RECKLESS;
	}
}
