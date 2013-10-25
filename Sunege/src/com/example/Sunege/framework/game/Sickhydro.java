package com.example.Sunege.framework.game;

import android.graphics.Color;

import com.example.Sunege.framework.Graphics;

public class Sickhydro extends Sprite {

	private boolean flag = false; // 画像を描画するかのフラグ

	public Sickhydro() {
		this.x = -1;
		this.y = -1;
		this.width = 200;
		this.height = 50;
	}

	public void Update() {
		if(!flag) {
			this.x = -500;
			this.y = -500;
		}
	}

	public void draw(Graphics g) {
		if (flag) {
			g.drawRect((int) x, (int) y, width, height, Color.BLACK);
		}
	}

	// seter,geter
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public void setXY(double x, double y) {
		this.x = x-(width/2);
		this.y = y-(height/2);
	}

}
