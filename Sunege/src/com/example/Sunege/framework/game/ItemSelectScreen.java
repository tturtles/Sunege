package com.example.Sunege.framework.game;

import java.util.List;

import android.graphics.Color;
import android.view.MotionEvent;

import com.example.Sunege.framework.Game;
import com.example.Sunege.framework.Graphics;
import com.example.Sunege.framework.Screen;
import com.example.Sunege.framework.Input.TouchEvent;

public class ItemSelectScreen extends Screen {

	enum TouchType {
		ItemSelect, DoSelect, NoBuy
	}

	private TouchType touchtype = TouchType.ItemSelect;
	private World world;
	private User user;
	private boolean flag_Nobuy;
	private boolean flag_GameEnd = true; // true = ゲーム終了
	private int select = -1; // 選択されない間は-1

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
		int[] sick_hps = { 100, 200, 300, 400, 500 }; // 剃れる毛の量の初期値
		int[] minus_points = { 50, 100, 200, 300, 400 }; // 消費するすね毛ポイント数
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			switch (event.type) {
			case MotionEvent.ACTION_UP:
				// アイテム選択画面

				if (touchtype == TouchType.ItemSelect) {
					if (isBounds(event, 0, 750, 480, 800)) {
						flag_GameEnd = false;
						game.setScreen(new PlayScreen(game, world, user));
					}
					if (isBounds(event, 0, 170, 480, 100))
						select = 0;
					else if (isBounds(event, 0, 270, 480, 100))
						select = 1;
					else if (isBounds(event, 0, 370, 480, 100))
						select = 2;
					else if (isBounds(event, 0, 470, 480, 100))
						select = 3;
					else if (isBounds(event, 0, 570, 480, 100))
						select = 4;

					if (select > -1
							&& user.getShaved_sum() - minus_points[select] > 0) { // もし購入できた場合
						touchtype = TouchType.DoSelect;
					} else if (select > -1) { // もし購入できなかった場合
						touchtype = TouchType.NoBuy;
					}
				}

				// 購入確認ダイアログ
				if (touchtype == TouchType.DoSelect) {
					if (isBounds(event, 60, 370, 100, 80)) { // 購入する
						user.setSaved_sum(user.getShaved_sum()
								- minus_points[select]);
						user.setHp(select, sick_hps[select]);
						touchtype = TouchType.ItemSelect;
						select = -1;
					} else if (isBounds(event, 300, 370, 130, 80)) {
						// 購入しない
						touchtype = TouchType.ItemSelect;
						select = -1;
					}
				}

				// 購入不可能ダイアログ
				if (touchtype == TouchType.NoBuy) {
					if (isBounds(event, 410, 240, 50, 50))
						touchtype = TouchType.ItemSelect;
				}
			}
		}

	}

	@Override
	public void present(float deltaTime) {
		// ItemSelect時のUI(描画系)
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 481, 801, Color.BLACK);
		g.drawTextAlp("ItemSelectScreen", 50, 100, Color.RED, 50);
//		for (int i = 0; i < 5; i++) {
//			g.drawRect(0, (i * 100) + 170, 480, 100, Color.RED);
//			g.drawTextAlp("" + (i + 1), 230, (i * 100) + 230, Color.WHITE, 40);
//		}
		g.drawPixmap(Assets.image_sick01_button, 0, 170);
		g.drawPixmap(Assets.image_sick02_button, 0, 270);
		g.drawPixmap(Assets.image_sick03_button, 0, 370);
		g.drawPixmap(Assets.image_sick04_button, 0, 470);
		g.drawPixmap(Assets.image_sick05_button, 0, 570);
		
		g.drawRect(0, 750, 480, 800, Color.WHITE, 255);

		// 「購入できない」というポップがあるとき
		if (touchtype == TouchType.NoBuy) {
			g.drawRect(30, 250, 420, 200, Color.WHITE);
			g.drawTextAlp("ポイントが足りないため", 70, 320, Color.BLACK, 30);
			g.drawTextAlp("購入できません", 130, 370, Color.BLACK, 30);
			g.drawTextAlp("✖", 420, 285, Color.BLACK, 50);
			// g.drawRect(410, 240, 50, 50, Color.BLUE, 125);
		}

		// 購入確認ダイアログ
		if (touchtype == TouchType.DoSelect) {
			g.drawRect(30, 250, 420, 200, Color.WHITE);
			g.drawTextAlp("購入しますか？", 70, 320, Color.BLACK, 30);
			g.drawTextAlp("はい", 80, 420, Color.BLACK, 30);
			g.drawTextAlp("いいえ", 320, 420, Color.BLACK, 30);
//			g.drawRect(60, 370, 100, 80, Color.BLUE, 125);
//			g.drawRect(300, 370, 130, 80, Color.BLUE, 125);
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
		if (flag_GameEnd) {
			user.DataSave(game);
			world.DataSave(game);
		}
	}

}
