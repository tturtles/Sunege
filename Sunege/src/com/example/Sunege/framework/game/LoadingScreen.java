package com.example.Sunege.framework.game;

import com.example.Sunege.framework.Game;
import com.example.Sunege.framework.Graphics;
import com.example.Sunege.framework.Screen;
import com.example.Sunege.framework.Graphics.PixmapFormat;

public class LoadingScreen extends Screen {

	public LoadingScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		Assets.image_ke = g.newPixmap("ke.png", PixmapFormat.ARGB4444);
		Assets.image_blood = g.newPixmap("blood.png", PixmapFormat.ARGB4444);
		Assets.bt_itemselect = g.newPixmap("bt_itemselect.png", PixmapFormat.ARGB4444);
		Assets.voice01 = game.getAudio().newSound("voice01.aac");
		Utils.load(game.getFileIO());
		game.setScreen(new PlayScreen(game));
	}

	@Override
	public void present(float deltaTime) {
		// TODO 自動生成されたメソッド・スタブ

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
		// TODO 自動生成されたメソッド・スタブ

	}

}
