package com.example.Sunege.framework.game;

import java.util.List;

import android.graphics.Color;
import android.view.MotionEvent;

import com.example.Sunege.framework.Game;
import com.example.Sunege.framework.Graphics;
import com.example.Sunege.framework.Screen;
import com.example.Sunege.framework.Input.TouchEvent;

public class ItemSelectScreen extends Screen {

	private World world;
	private User user;
	private boolean flag_Nobuy;

	public ItemSelectScreen(Game game, World world, User user) {
		super(game);
		this.world = world;
		this.user = user;
	}

	@Override
	public void update(float deltaTime) {
		// ItemSelectScreenのタッチ処理書き込み
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvents.size();
		int select = -1; // 選択されない間は-1
		int[] sick_hps = { 100, 200, 300, 400, 500 }; // 剃れる毛の量の初期値
		int[] minus_points = { 50, 100, 200, 300, 400 }; // 消費するすね毛ポイント数
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			switch (event.type) {
			case MotionEvent.ACTION_UP:
				if (!flag_Nobuy) {
					if (isBounds(event, 0, 750, 480, 800))
						game.setScreen(new PlayScreen(game, world, user));
					if (isBounds(event, 0, 170, 480, 100))
						select = 0;
					if (isBounds(event, 0, 270, 480, 100))
						select = 1;
					if (isBounds(event, 0, 370, 480, 100))
						select = 2;
					if (isBounds(event, 0, 470, 480, 100))
						select = 3;
					if (isBounds(event, 0, 570, 480, 100))
						select = 4;
				} else if (isBounds(event, 410, 240, 50, 50))
					flag_Nobuy = false;
			}
		}
		if (select > -1 && user.getShaved_sum() - minus_points[select] > 0) { // もし購入できた場合
			flag_Nobuy = false;
			user.setSaved_sum(user.getShaved_sum() - minus_points[select]);
			user.setHp(select, sick_hps[select]);
		} else if (select > -1) { // もし購入できなかった場合
			flag_Nobuy = true;
		}
	}

	@Override
	public void present(float deltaTime) {
		// ItemSelect時のUI(描画系)
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 481, 801, Color.BLACK);
		g.drawTextAlp("ItemSelectScreen", 50, 100, Color.RED, 50);
		for (int i = 0; i < 5; i++) {
			g.drawRect(0, (i * 100) + 170, 480, 100, Color.RED);
			g.drawTextAlp("" + (i + 1), 230, (i * 100) + 230, Color.WHITE, 40);
		}
		g.drawRect(0, 750, 480, 800, Color.WHITE, 255);
		if (flag_Nobuy) { // 「購入できない」というポップがあるとき
			g.drawRect(30, 250, 420, 200, Color.WHITE);
			g.drawTextAlp("ポイントが足りないため", 70, 320, Color.BLACK, 30);
			g.drawTextAlp("購入できません", 130, 370, Color.BLACK, 30);
			g.drawTextAlp("✖", 420, 285, Color.BLACK, 50);
			// g.drawRect(410, 240, 50, 50, Color.BLUE, 125);
		}
	}

	private boolean isBounds(TouchEvent event, int x, int y, int width,
			int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}

	@Override
	public void pause() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void resume() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void dispose() {
		user.DataSave(game);
		world.DataSave(game);
	}

}
