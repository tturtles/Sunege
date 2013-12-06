package com.example.Sunege.framework.game;

import android.graphics.Color;

import com.example.Sunege.framework.Graphics;

public class Sickhydro extends Sprite {
	
	private boolean flag_draw = false; // 画像を描画するかのフラグ
	private boolean flag_end = false;
	private int hp;
	
	public Sickhydro(int hp) {
		this.x = -1;
		this.y = -1;
		this.hp = hp;
		this.width = 200;
		this.height = 50;
	}

	public void Update() {
		if(!flag_draw) {
			this.x = -500;
			this.y = -500;
		}
		if(hp<1) flag_end = true;
	}

	public void draw(Graphics g) {
		if (flag_draw && !flag_end) {
			g.drawRect((int) x, (int) y, width, height, Color.BLACK);
		}
		g.drawTextAlp("hp : "+hp, 210, 100, Color.BLACK, 20);
//		g.drawTextAlp("flag_end "+flag_end, 210, 150, Color.BLACK, 20);
	}

	// seter,geter
	public void setFlag(boolean flag) {
		this.flag_draw = flag;
	}
	
	public void setXY(double x, double y) {
		this.x = x-(width/2);
		this.y = y-(height/2);
	}
	
	public void setHp() {
		this.hp -= 1;
	}

	
	public boolean getFlag_end() {
		return flag_end;
	}
}
