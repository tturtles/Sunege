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
import android.view.Surface;
import android.widget.SlidingDrawer;

import com.example.Sunege.framework.Game;
import com.example.Sunege.framework.Graphics;
import com.example.Sunege.framework.Screen;
import com.example.Sunege.framework.Input.TouchEvent;

public class PlayScreen extends Screen {

	private World world;
	private Sickhydro sick;
	private User user;
	private Point down_Pos; // Down時の座標
	private Point now_pos;
	private boolean flag_slide = false; // 横スライド、つまりスネをカミソリで切った場合
	private boolean flag_select; // 1本でも毛を選択している状態ならtrue
	private boolean flag_bloodedit = false; // 血を編集しているかどうか
	private boolean flag_GameEnd = true; // true = ゲーム終了

	public PlayScreen(Game game) {
		super(game);
		world = new World();
		user = new User(game);
		down_Pos = new Point(); // [0]=前の位置　[1]=次の位置
		now_pos = new Point();
		if (user.getShaved_sum() == 0)
			world.load();
		else
			world.load(Utils.readSunegeData(game.getFileIO()),
					(int) user.getDifference() / 1000);
		world.addSunege((int) user.getDifference() / 1000); // 1秒単位にして渡す
		sick = new Sickhydro(0);
	}

	public PlayScreen(Game game, World world, User user) {
		super(game);
		this.world = world;
		this.user = user;
		sick = new Sickhydro(0);
		down_Pos = new Point(); // [0]=前の位置　[1]=次の位置
		now_pos = new Point();

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
		// ゲーム中のタッチ処理書き込み
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			switch (event.type) {
			case MotionEvent.ACTION_MOVE:
				if (event.y > 100 && event.y < 700) {
					switch (user.getSick_no()) {
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
				if (isBounds(event, 0, 0, 200, 100)) {
					flag_GameEnd = false;
					game.setScreen(new ItemSelectScreen(game, world, user));
				} else if (isBounds(event, 440, 0, 40, 40))
					world.load();
				else if (isBounds(event, 0, 700, 480, 100)) {
					if (isBounds(event, 0, 700, 80, 100))
						user.setSick_no(0);
					else if (isBounds(event, 80, 700, 80, 100))
						user.setSick_no(1);
					else if (isBounds(event, 160, 700, 80, 100))
						user.setSick_no(2);
					else if (isBounds(event, 240, 700, 80, 100))
						user.setSick_no(3);
					else if (isBounds(event, 320, 700, 80, 100))
						user.setSick_no(4);
					else if (isBounds(event, 400, 700, 80, 100))
						user.setSick_no(5);
					sick = new Sickhydro(
							user.getSick_no() > 0 ? user.getHp(user
									.getSick_no() - 1) : 0);
					flag_select = user.getSick_no() > 0 ? false : flag_select;
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
				// ke.setAngle(now_pos);
			} else if (sprite instanceof Blood) {
				Blood blood = (Blood) sprite;
				blood.Update(deltaTime);
			}
			sprite.Update();
		}
		sick.Update();
		if (user.getSick_no() > 0)
			user.setHp(user.getSick_no() - 1, sick.getHp());
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 481, 801, Color.rgb(255, 241, 207));
		sick.draw(g, user.getSick_no());
		boolean flag = false; // 血を新しく生成するかどうか

		LinkedList sprites = world.getSprites();
		Iterator iterator = sprites.iterator(); // Iterator=コレクション内の要素を順番に取り出す方法
		while (iterator.hasNext()) { // iteratorの中で次の要素がある限りtrue
			Sprite sprite = (Sprite) iterator.next();
			if (sprite instanceof Ke) {
				Ke ke = (Ke) sprite;
				/********************************** ここから↓　switch分の方がよいかも...(12/12) **********************************/
				// カミソリ処理
				if (user.getSick_no() > 0) {
					if (sick.isCollision(sprite) && !sick.isFlag_end()) { // カミソリに毛がぶつかり、カミソリが使えるのなら
						if (user.getSick_no() != 0)
							ke.minusHp(sick.getDamage(user.getSick_no()));
						if (!ke.isAbnum() && sprites.remove(ke)) {
							user.setSaved_sum(user.getShaved_sum() + 1);
							sick.minusHp();
							break;
						}
					}
				}

				// 毛抜き処理
				else if (user.getSick_no() == 0) {
					// 毛の選択処理
					if (isBounds(down_Pos, (int) ke.x, (int) ke.y,
							ke.getimage_width(), ke.getimage_height())
							&& !flag_select) { // すね毛選択処理
						ke.setFlag_select(true);
						flag_select = true;
						break;

						// すね毛を抜く処理
					} else if (flag_slide && ke.isFlag_select()) {
						if (sprites.remove(ke)) {
							user.setSaved_sum(user.getShaved_sum() + 1);
							flag_select = false;
							break;
						}
					}
				}

				// 出血処理
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

		if (flag_slide && user.getSick_no() > 0 && flag) { // 往復スライド時の出血
			world.addBlood(now_pos);
			down_Pos = now_pos;
		}

		g.drawRect(440, 0, 40, 40, Color.RED, 150);
		g.drawPixmap(Assets.bt_itemselect, 0, 0);
		// g.drawTextAlp("すね毛ポイント:", 210, 30, Color.BLACK, 30);
		// g.drawTextAlp("" + user.getShaved_sum(), 400, 70, Color.BLACK, 50);
		g.drawTextAlp("すね毛ポイント:", 210, 25, Color.BLACK, 20);
		g.drawTextAlp("" + user.getShaved_sum(), 360, 25, Color.BLACK, 20);
		g.drawTextAlp("user.getSick_no()" + user.getSick_no(), 300, 50,
				Color.BLACK, 20);
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
			g.drawTextAlp("" + user.getHp(i), 10 + 80 * (i + 1), 780,
					Color.BLACK, 40);
			g.drawLine(80 * (i + 1), 700, 80 * (i + 1), 800, Color.BLACK, 2);
		}
		g.drawTextAlp("毛抜き", 10, 730, Color.BLACK, 20);
		g.drawRect((user.getSick_no() * 81) + 1, 701, 78, 100, Color.BLUE, 125);
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
		if (flag_GameEnd) {
			user.DataSave(game);
			world.DataSave(game);
		}
	}

}
