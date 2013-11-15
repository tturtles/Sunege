package com.example.Sunege.framework.game;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
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
	private int x;
	private int count_shaved = 0;

	public PlayScreen(Game game) {
		super(game);
		world = new World();
		sick = new Sickhydro();
		x = 0;
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
			case MotionEvent.ACTION_DOWN:
				state = GameState.Playing;
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
			case MotionEvent.ACTION_DOWN:
				if (isBounds(event, 0, 0, 200, 100)) {
					state = GameState.ItemSelecting;
				} else {
					sick.setFlag(true);
					sick.setXY(event.x, event.y);
				}
				break;
			case MotionEvent.ACTION_UP:
				sick.setFlag(false);
				break;
			}
		}
		world.update(deltaTime);
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
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setTextSize(100);
		g.drawRect(0, 0, 481, 801, Color.BLACK);
		g.drawTextAlp("ItemSelectScreen", 70, 300, paint);
		paint.setTextSize(30);
		g.drawTextAlp("未実装段階", 150, 500, paint);
	}

	private void drawPlayingUI() {
		// ゲーム中のUI(描画系)
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, 481, 801, Color.rgb(255, 241, 207));
		world.draw(g);
		sick.draw(g);
		if (x > 480)
			x = 0;
		else
			x++;

		g.drawLine(0, 3, x, 3, Color.BLUE, 5); // 描画されているか確認用
		LinkedList sprites = world.getSprites();
		Iterator iterator = sprites.iterator(); // Iterator=コレクション内の要素を順番に取り出す方法
		while (iterator.hasNext()) { // iteratorの中で次の要素がある限りtrue
			Sprite sprite = (Sprite) iterator.next();
			sprite.Update();
			if (sick.isCollision(sprite)) {
				if (sprite instanceof Ke) {
					Ke ke = (Ke) sprite;
					sprites.remove(ke);
					count_shaved++;
					break;
				}
			}
		}

		iterator = sprites.iterator();
		while (iterator.hasNext()) {
			Sprite sprite = (Sprite) iterator.next();
			sprite.draw(g);
		}
		
		g.drawPixmap(Assets.bt_itemselect, 0, 0);
		g.drawTextAlp("剃った本数:", 210, 40, Color.BLACK, 35);
		g.drawTextAlp("" + count_shaved, 400, 70, Color.BLACK, 50);
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

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
