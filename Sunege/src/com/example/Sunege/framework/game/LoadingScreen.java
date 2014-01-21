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
		
		Assets.image_item00_button = g.newPixmap("image_item00_button.png", PixmapFormat.ARGB4444);
		Assets.image_item01_button = g.newPixmap("image_item01_button.png", PixmapFormat.ARGB4444);
		Assets.image_item02_button = g.newPixmap("image_item02_button.png", PixmapFormat.ARGB4444);
		Assets.image_item03_button = g.newPixmap("image_item03_button.png", PixmapFormat.ARGB4444);
		Assets.image_item04_button = g.newPixmap("image_item04_button.png", PixmapFormat.ARGB4444);
		Assets.image_item05_button = g.newPixmap("image_item05_button.png", PixmapFormat.ARGB4444);
		
		Assets.image_sick01_button = g.newPixmap("image_sick01_button.png", PixmapFormat.ARGB4444);
		Assets.image_sick02_button = g.newPixmap("image_sick02_button.png", PixmapFormat.ARGB4444);
		Assets.image_sick03_button = g.newPixmap("image_sick03_button.png", PixmapFormat.ARGB4444);
		Assets.image_sick04_button = g.newPixmap("image_sick04_button.png", PixmapFormat.ARGB4444);
		Assets.image_sick05_button = g.newPixmap("image_sick05_button.png", PixmapFormat.ARGB4444);
		
		
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
