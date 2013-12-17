package com.example.Sunege.framework.game;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.example.Sunege.framework.Graphics;

public class Blood extends Sprite {

	public static final float TICK_INITIAL = 0.2f; // 更新速度
	private static float tick = TICK_INITIAL;
	private float tickTime;
	private boolean flag_edit = true; // 数値の編集が可能かどうかを表す
	private double end_x, end_y;
	private int level = 10;
	private static int ADD_LEVEL = 3;

	public Blood(Point pos) {
		this.image = Assets.image_blood;
		this.x = pos.x;
		this.y = pos.y;
		this.end_x = this.x + 1;
		this.end_y = this.y + 1;
	}

	@Override
	public void Update(float deltaTime) {
		tickTime += deltaTime;
		while (tickTime > tick) {
			tickTime -= tick;
			if ((y + (100 * (level * 0.01))) < 700)
				level += ADD_LEVEL;
		}
	}

	public boolean point_Move(Point end_pos) {
		if (!flag_edit)
			return false;
		if (end_x == 0 && end_y == 0) {
			this.end_x = end_pos.x;
			this.end_y = end_pos.y;
			return true;
		} else if (end_x <= end_pos.x && x < end_pos.x) {
			this.end_x = end_pos.x;
			this.end_y = end_pos.y;
			return true;
		} else if (end_x >= end_pos.x && x > end_pos.x) {
			this.end_x = end_pos.x;
			this.end_y = end_pos.y;
			return true;
		} else {
			flag_edit = false;
		}
		return false;
	}

	public void draw(Graphics g) {
		int alpha = 255; // 画像の濃さ
		Rect src = new Rect(0, 0, 150, 100);
		Rect dst;
		if (x < end_x)
			dst = new Rect((int) x, (int) y, (int) (x + (end_x - x)),
					(int) (y + (100 * (level * 0.01))));
		else
			dst = new Rect((int) end_x, (int) end_y,
					(int) (end_x + (x - end_x)),
					(int) (end_y + (100 * (level * 0.01))));
		g.drawPixmap(Assets.image_blood, src, dst, alpha);
	}

	public void setFlag_edit(boolean flag_edit) {
		this.flag_edit = flag_edit;
	}

	public boolean isFlag_edit() {
		return flag_edit;
	}

}
