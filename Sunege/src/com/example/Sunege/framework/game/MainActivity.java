package com.example.project2nd.framework.game;

import com.example.project2nd.framework.Screen;
import com.example.project2nd.framework.impl.AndroidGame;

public class MainActivity extends AndroidGame {

	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}

}