package com.example.Sunege.framework.game;

import com.example.Sunege.framework.Graphics;

public class Blood extends Sprite {

	public static final float TICK_INITIAL = 10.0f; // すね毛が伸びる速度
	private static float tick = TICK_INITIAL; // 更新速度
	private float tickTime;

	public Blood() {
		this.image = Assets.image_blood;
	}

	@Override
	public void Update(float deltaTime) {
		tickTime += deltaTime;
		while (tickTime > tick) {
			tickTime -= tick;
		}
	}

	public void draw(Graphics g) {
	}

}
