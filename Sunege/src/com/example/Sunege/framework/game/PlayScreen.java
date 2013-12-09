package com.example.Sunege.framework.game;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.app.UiModeManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import com.example.Sunege.framework.Game;
import com.example.Sunege.framework.Graphics;
import com.example.Sunege.framework.Screen;
import com.example.Sunege.framework.Input.TouchEvent;

public class PlayScreen extends Screen {

	enum GameState {
		ItemSelecting, Playing, Paused, GameOver
	}

	GameState state = GameState.Playing;

	private World world;
	private Sickhydro sick;
	private int x = 0;
	private int shaved_sum = 0; // 剃った毛の総数
	private Point pos;
	private boolean flag_s = false; // 横スライド、つまりスネをカミソリで切った場合
	private int hps[];
	private int sick_no = 1; // 刃の枚数　1 = 1枚刃
	private LinkedList<Ke> kelist;

	public PlayScreen(Game game) {
		super(game);
		hps = new int[5];
		long difference;
		world = new World();
		pos = new Point(); // [0]=前の位置　[1]=次の位置

		String[][] list = Utils.readSaveData(game.getFileIO());
		shaved_sum = Integer.parseInt(list[0][0]); // 剃ったすね毛の総数取得
		for (int i = 0; i < 5; i++)
			// 前回よりの経過時間の取得
			hps[i] = Integer.parseInt(list[0][i + 2]);
		difference = (System.currentTimeMillis()) - Long.parseLong(list[0][7]);
		if (shaved_sum == 0)
			world.load();
		else
			world.load(Utils.readSunegeData(game.getFileIO()),
					(int) difference / 1000);
		world.addSunege((int) difference / 1000); // 1秒単位にして渡す
		for (int i = 0; i < hps.length; i++)
			// 各カミソリのＨＰを取得
			hps[i] = Integer.parseInt(list[0][i + 2]);
		sick = new Sickhydro(hps[0]);
		TimeLog(list, difference);
	}

	private void TimeLog(String[][] list, long difference) {
		SimpleDateFormat D = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Log.d("TIME1", "" + D.format(new Date(System.currentTimeMillis())));
		Log.d("TIME2", "" + D.format(new Date(Long.parseLong(list[0][7]))));
		D = new SimpleDateFormat("HHHH:mm:ss");
		Log.d("TIME3", "" + D.format(new Date(difference)));
		Log.d("追加するすね毛の本数", "" + (difference / 60000)); // 60秒ごと
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		if (state == GameState.ItemSelecting)
			updateItemSelect(touchEvents, deltaTime);
		if (state == GameState.Playing)
			updatePlaying(touchEvents, deltaTime);
		if (state == GameState.GameOver) {
			updateGameOver(touchEvents);
		}
	}

	private void updateItemSelect(List<TouchEvent> touchEvents, float deltaTime) {
		// ItemSelectScreenのタッチ処理書き込み
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			switch (event.type) {
			case MotionEvent.ACTION_UP:
				if (isBounds(event, 0, 750, 480, 800))
					state = GameState.Playing;
				if (isBounds(event, 0, 170, 480, 100))
					hps[0] = 100;
				if (isBounds(event, 0, 270, 480, 100))
					hps[1] = 200;
				if (isBounds(event, 0, 370, 480, 100))
					hps[2] = 300;
				if (isBounds(event, 0, 470, 480, 100))
					hps[3] = 400;
				if (isBounds(event, 0, 570, 480, 100))
					hps[4] = 500;
				return;
			}
		}
	}

	private void updatePlaying(List<TouchEvent> touchEvents, float deltaTime) {
		// ゲーム中のタッチ処理書き込み
		int interval_y = 5;
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			switch (event.type) {
			case MotionEvent.ACTION_MOVE:
				if (event.y > 100 && event.y < 700) {
					switch (sick_no) {
					case 0: // 毛を抜く判定（毛抜き時）
						int move_range = 10;
						if (move_range < -(pos.x - event.x)
								|| move_range < (pos.x - event.x))
							flag_s = true;

					default: // カミソリで肌を傷つけた時の判定（カミソリ時）
						if (interval_y < -(pos.x - event.x)
								|| interval_y < (pos.x - event.x)) {
							if (!flag_s)
								Assets.voice01.play(1);
							flag_s = true;
							break;
						}
					}
				}

			case MotionEvent.ACTION_DOWN:
				if (!isBounds(event, 440, 0, 40, 40)
						&& isBounds(event, 0, 100, 480, 600)) {
					sick.setFlag(true);
					sick.setXY(event.x, event.y);
					pos.x = event.x;
					pos.y = event.y;
					break;
				}

			case MotionEvent.ACTION_UP:
				if (isBounds(event, 0, 0, 200, 100))
					state = GameState.ItemSelecting;
				else if (isBounds(event, 440, 0, 40, 40))
					world.load();
				else if (isBounds(event, 0, 700, 480, 100)) {
					if (isBounds(event, 0, 700, 80, 100))
						sick_no = 0;
					else if (isBounds(event, 80, 700, 80, 100))
						sick_no = 1;
					else if (isBounds(event, 160, 700, 80, 100))
						sick_no = 2;
					else if (isBounds(event, 240, 700, 80, 100))
						sick_no = 3;
					else if (isBounds(event, 320, 700, 80, 100))
						sick_no = 4;
					else if (isBounds(event, 400, 700, 80, 100))
						sick_no = 5;
					sick = new Sickhydro(sick_no > 0 ? hps[sick_no - 1] : 0);
				} else {
					sick.setFlag(false);
					sick.setXY(-sick.width, -sick.height);
				}
				pos.x = -1;
				pos.y = -1;
				flag_s = false;
				break;
			}
		}
		world.update(deltaTime);
		// 毛の更新
		LinkedList sprites = world.getSprites();
		Iterator iterator = sprites.iterator();
		while (iterator.hasNext()) {
			Sprite sprite = (Sprite) iterator.next();
			if (sprite instanceof Ke) {
				Ke ke = (Ke) sprite;
				ke.Update(deltaTime);
			}
		}
		sick.Update();
		if (sick_no > 0)
			hps[sick_no - 1] = sick.getHp();
	}

	private void updateGameOver(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			switch (event.type) {
			case MotionEvent.ACTION_DOWN:
				break;
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		if (state == GameState.ItemSelecting)
			drawItemSelectUI();
		if (state == GameState.Playing)
			drawPlayingUI();
		if (state == GameState.GameOver)
			drawGameOverUI();
	}

	private void drawItemSelectUI() {
		// ItemSelect時のUI(描画系)
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 481, 801, Color.BLACK);
		g.drawTextAlp("ItemSelectScreen", 50, 100, Color.RED, 50);
		for (int i = 0; i < 5; i++) {
			g.drawRect(0, (i * 100) + 170, 480, 100, Color.RED);
			g.drawTextAlp("" + (i + 1), 230, (i * 100) + 230, Color.WHITE, 40);
		}
		g.drawRect(0, 750, 480, 800, Color.WHITE, 255);
	}

	private void drawPlayingUI() {
		// ゲーム中のUI(描画系)
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 481, 801, Color.rgb(255, 241, 207));
		world.draw(g);
		sick.draw(g, sick_no);
		if (x > 480)
			x = 0;
		else
			x++;
		if (flag_s) {
			g.drawRect(100, 100, 100, 100, Color.BLUE);
		}

		g.drawLine(0, 3, x, 3, Color.BLUE, 5); // 描画されているか確認用
		LinkedList sprites = world.getSprites();
		Iterator iterator = sprites.iterator(); // Iterator=コレクション内の要素を順番に取り出す方法
		while (iterator.hasNext()) { // iteratorの中で次の要素がある限りtrue
			Sprite sprite = (Sprite) iterator.next();
			sprite.Update();
			if (sick.isCollision(sprite)) {
				if (sprite instanceof Ke) {
					Ke ke = (Ke) sprite;
					if (!sick.getFlag_end() && sick_no > 0) { // 各カミソリ処理
						if (sprites.remove(ke)) {
							if (sick_no > 0) {
								shaved_sum++;
								sick.setHp();
							}
						}
					} else  if(sick_no==0){ // 毛抜き処理
						if (isBounds(pos, (int) ke.x, (int) ke.y,
								ke.getimage_width(), ke.getimage_height())) {
							ke.setFlag_select(true);
						} else if (flag_s && ke.isFlag_select()) {
							sprites.remove(ke);
							shaved_sum++;
						}
					}
					break;
				}
			}
		}

		iterator = sprites.iterator();
		while (iterator.hasNext()) {
			Sprite sprite = (Sprite) iterator.next();
			sprite.draw(g);
		}

		g.drawRect(440, 0, 40, 40, Color.RED, 150);
		g.drawPixmap(Assets.bt_itemselect, 0, 0);
		g.drawTextAlp("すね毛ポイント:", 210, 30, Color.BLACK, 30);
		g.drawTextAlp("" + shaved_sum, 400, 70, Color.BLACK, 50);
		g.drawLine(0, 700, 480, 700, Color.BLACK, 2);
		g.drawTextAlp("sick_no" + sick_no, 300, 100, Color.BLACK, 20);
		for (int i = 0; i < 5; i++) {
			g.drawTextAlp((i + 1) + "枚刃", 10 + 80 * (i + 1), 730, Color.BLACK,
					20);
			g.drawTextAlp("" + hps[i], 10 + 80 * (i + 1), 780, Color.BLACK, 40);
			g.drawLine(80 * (i + 1), 700, 80 * (i + 1), 800, Color.BLACK, 2);
		}
		g.drawRect((sick_no * 80) + 1, 701, 78, 100, Color.BLUE, 125);
	}

	private void drawGameOverUI() {
		// ゲームオーバー時のUI(描画系)
		Graphics g = game.getGraphics();
		world.draw(g);
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setTextSize(100);
		g.drawTextAlp("GameOver", 0, 300, paint);
	}

	// タップ時の当たり判定 目標がタップされた場合true、違う場合false
	private boolean isBounds(TouchEvent event, int x, int y, int width,
			int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}

	private boolean isBounds(Point pos, int x, int y, int width, int height) {
		if (pos.x > x && pos.x < x + width - 1 && pos.y > y
				&& pos.y < y + height - 1)
			return true;
		else
			return false;
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		Long time = System.currentTimeMillis();
		Utils.addSaveData(game.getFileIO(), shaved_sum, 0, hps[0], hps[1],
				hps[2], hps[3], hps[4], time);
		LinkedList sprites = world.getSprites();
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
}
