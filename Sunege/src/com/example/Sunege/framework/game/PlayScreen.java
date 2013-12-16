package com.example.Sunege.framework.game;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.R.bool;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SlidingDrawer;

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
	private int shaved_sum = 0; // 剃った毛の総数
	private Point down_Pos; // Down時の座標
	private Point now_pos;
	private boolean flag_slide = false; // 横スライド、つまりスネをカミソリで切った場合
	private int hps[];
	private int sick_no = 0; // 刃の枚数　1 = 1枚刃
	private boolean flag_select; // 1本でも毛を選択している状態ならtrue
	private boolean flag_bloodedit = false;	// 血を編集しているかどうか

	public PlayScreen(Game game) {
		super(game);
		hps = new int[5];
		long difference;
		world = new World();
		down_Pos = new Point(); // [0]=前の位置　[1]=次の位置
		now_pos = new Point();
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
		sick = new Sickhydro(0);
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
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			switch (event.type) {
			case MotionEvent.ACTION_MOVE:
				if (event.y > 100 && event.y < 700) {
					switch (sick_no) {
					case 0: // 毛を抜く判定（毛抜き時）
						int move_range = 15; // 毛を選択してどのくらい全方向に指をスライドさせたら抜くかの値
						if (move_range < -(down_Pos.x - event.x)
								|| move_range < (down_Pos.x - event.x)
								|| move_range < -(down_Pos.y - event.y)
								|| move_range < (down_Pos.y - event.y))
							flag_slide = true;
						break;

					default: // カミソリで肌を傷つけた時の判定（カミソリ時）
						int move_x = 10; // どのくらいの指をx軸にスライドさせたら血が出るかの値
						int move_y = 30; // この範囲内のy軸の移動があった場合出血しない
						now_pos.x = event.x;
						now_pos.y = event.y;
						if (move_x < -(down_Pos.x - event.x)
								|| move_x < (down_Pos.x - event.x)) {
							if ((down_Pos.y > event.y && move_y > (down_Pos.y - event.y))
									|| (down_Pos.y < event.y && move_y > -(down_Pos.y - event.y))) {
								if (!sick.isFlag_end()) {
									if (!flag_slide)
										Assets.voice01.play(1);
									if (!flag_slide && !flag_bloodedit) {
										world.addBlood(down_Pos);
										Log.d("血", "作成");
									}
									flag_slide = flag_bloodedit = true;
								}
							} else {
								flag_slide = false;
							}
						}
						break;
					}
				}

			case MotionEvent.ACTION_DOWN:
				if (!isBounds(event, 440, 0, 40, 40)
						&& isBounds(event, 0, 100, 480, 600)) {
					sick.setFlag(true);
					sick.setXY(event.x, event.y);
					if (down_Pos.x < 0) {
						down_Pos.x = now_pos.x = event.x;
						down_Pos.y = now_pos.y = event.y;
					}
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
					flag_select = sick_no > 0 ? false : flag_select;
				} else {
					sick.setFlag(false);
					sick.setXY(-sick.width, -sick.height);
				}
				down_Pos.x = now_pos.x = -1;
				down_Pos.y = now_pos.y = -1;
				flag_slide = flag_bloodedit = false;
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
			} else
				sprite.Update();
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
		sick.draw(g, sick_no);
		boolean flag = false; // 血を新しく生成するかどうか

		LinkedList sprites = world.getSprites();
		Iterator iterator = sprites.iterator(); // Iterator=コレクション内の要素を順番に取り出す方法
		while (iterator.hasNext()) { // iteratorの中で次の要素がある限りtrue
			Sprite sprite = (Sprite) iterator.next();
			if (sprite instanceof Ke) {
				Ke ke = (Ke) sprite;
				/********************************** ここから↓　switch分の方がよいかも...(12/12) **********************************/
				// カミソリ処理
				if (sick_no > 0) {
					if (sick.isCollision(sprite) && !sick.isFlag_end()) { // カミソリに毛がぶつかり、カミソリが使えるのなら
						if (sprites.remove(ke)) {
							shaved_sum++;
							sick.minusHp();
							break;
						}
					}
				}

				// 毛抜き処理
				else if (sick_no == 0) {
					if (isBounds(down_Pos, (int) ke.x, (int) ke.y,
							ke.getimage_width(), ke.getimage_height())
							&& !flag_select) { // すね毛選択処理
						ke.setFlag_select(true);
						flag_select = true;
						break;
					} else if (flag_slide && ke.isFlag_select()) { // すね毛を剃る処理
						if (sprites.remove(ke)) {
							shaved_sum++;
							flag_select = false;
							break;
						}
					}
				}
			} else if (sprite instanceof Blood) {
				Blood blood = (Blood) sprite;
				if (!flag_bloodedit)
					blood.setFlag_edit(false);
				if (flag_slide && blood.isFlag_edit()) {
					flag = !blood.point_Move(now_pos);
				}
			}

		}
		/**************************************************** ここまで ****************************************************/
		iterator = sprites.iterator();
		while (iterator.hasNext()) {
			Sprite sprite = (Sprite) iterator.next();
			sprite.draw(g);
		}

		if (flag_slide && sick_no > 0 && flag) { // 往復スライド時の出血
			if (world.addBlood(now_pos))
				down_Pos = now_pos;
		}

		g.drawRect(440, 0, 40, 40, Color.RED, 150);
		g.drawPixmap(Assets.bt_itemselect, 0, 0);
		// g.drawTextAlp("すね毛ポイント:", 210, 30, Color.BLACK, 30);
		// g.drawTextAlp("" + shaved_sum, 400, 70, Color.BLACK, 50);
		g.drawTextAlp("すね毛ポイント:", 210, 25, Color.BLACK, 20);
		g.drawTextAlp("" + shaved_sum, 360, 25, Color.BLACK, 20);
		g.drawTextAlp("sick_no" + sick_no, 300, 50, Color.BLACK, 20);
		g.drawTextAlp("now.x : " + now_pos.x + "  now.y : " + now_pos.y, 210,
				70, Color.BLACK, 20);
		g.drawTextAlp("pos.x : " + down_Pos.x + "  pos.y : " + down_Pos.y, 210,
				90, Color.BLACK, 20);
		g.drawTextAlp("flag_bloodedit : " + flag_bloodedit, 210, 110,
				Color.BLACK, 20);
		g.drawLine(0, 700, 480, 700, Color.BLACK, 2);
		for (int i = 0; i < 5; i++) {
			g.drawTextAlp((i + 1) + "枚刃", 10 + 80 * (i + 1), 730, Color.BLACK,
					20);
			g.drawTextAlp("" + hps[i], 10 + 80 * (i + 1), 780, Color.BLACK, 40);
			g.drawLine(80 * (i + 1), 700, 80 * (i + 1), 800, Color.BLACK, 2);
		}
		g.drawTextAlp("毛抜き", 10, 730, Color.BLACK, 20);
		g.drawRect((sick_no * 80) + 1, 701, 78, 100, Color.BLUE, 125);
	}

	private void drawGameOverUI() {
		// ゲームオーバー時のUI(描画系)
		Graphics g = game.getGraphics();
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
