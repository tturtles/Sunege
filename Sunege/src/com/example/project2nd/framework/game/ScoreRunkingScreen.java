//package com.example.project2nd.framework.game;
//
//import java.util.List;
//
//import android.graphics.Color;
//
//import com.example.project2nd.framework.Game;
//import com.example.project2nd.framework.Graphics;
//import com.example.project2nd.framework.Screen;
//import com.example.project2nd.framework.Input.TouchEvent;
//
//public class ScoreRunkingScreen extends Screen {
//
//	public ScoreRunkingScreen(Game game) {
//		super(game);
//	}
//
//	@Override
//	public void update(float deltaTime) {
//		Graphics g = game.getGraphics();
//		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
//		game.getInput().getKeyEvents();
//
//		int len = touchEvents.size();
//		for (int i = 0; i < len; i++) {
//			TouchEvent event = touchEvents.get(i);
//			if (event.type == TouchEvent.TOUCH_UP) {
//			}
//		}
//	}
//
//
//	private boolean isBounds(TouchEvent event, int x, int y, int width,
//			int height) {
//		if (event.x > x && event.x < x + width - 1 && event.y > y
//				&& event.y < y + height - 1)
//			return true;
//		else
//			return false;
//	}
//
//	@Override
//	public void present(float deltaTime) {
//		Graphics g = game.getGraphics();
//		g.drawRect(0, 0, 440, 800, Color.BLACK);
//		g.drawPixmap(Assets.bt_back, 320, 680);
//	}
//
//	@Override
//	public void pause() {
//
//	}
//
//	@Override
//	public void resume() {
//
//	}
//
//	@Override
//	public void dispose() {
//
//	}
//
//}
