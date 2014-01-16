package com.example.Sunege.framework.game;

import android.graphics.Color;
import android.graphics.DashPathEffect;

import com.example.Sunege.framework.Graphics;

public class Sickhydro extends Sprite {

	public int getHp() {
		return hp;
	}

	private boolean flag_draw = false; // 画像を描画するかのフラグ
	private boolean flag_end = false;
	private int hp;
	private final int[] DAMEGES = { 1, 5, 10, 15, 30 }; // 剃れる度合い

	public Sickhydro(int hp) {
		this.x = -1;
		this.y = -1;
		this.hp = hp;
		this.width = 200;
		this.height = 50;
	}

	public void Update() {
		if (!flag_draw) {
			this.x = -500;
			this.y = -500;
		}
		if (hp < 1)
			flag_end = true;
	}

	public void draw(Graphics g, int sick_no) {
		if (flag_draw && !flag_end) {
			g.drawRect((int) x, (int) y, width, height, Color.BLACK);
		}
		g.drawTextAlp("hp : " + (sick_no > 0 ? Integer.toString(hp) : "∞"),
				210, 50, Color.BLACK, 20);
	}

	// seter,geter
	public void setFlag(boolean flag) {
		this.flag_draw = flag;
	}

	public void setXY(double x, double y) {
		this.x = x - (width / 2);
		this.y = y - (height / 2);
	}

	public void minusHp() {
		this.hp -= 1;
	}

	public boolean isFlag_end() {
		return flag_end;
	}

	public int getDamage(int sick_no) {
		return DAMEGES[sick_no - 1];
	}
}
