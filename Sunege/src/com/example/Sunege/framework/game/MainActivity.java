package com.example.Sunege.framework.game;

import com.example.Sunege.framework.impl.AndroidGame;
import com.example.Sunege.framework.Screen;

public class MainActivity extends AndroidGame {

	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}

}