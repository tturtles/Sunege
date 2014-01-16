package com.example.Sunege.framework.game;

import com.example.Sunege.framework.Game;

public class User {

	private int shaved_sum = 0; // 剃った毛の総数
	private int sick_no = 0; // 刃の枚数　1 = 1枚刃
	private int hps[];

	private long difference;

	public User(Game game) {
		hps = new int[5];
		String[][] list = Utils.readSaveData(game.getFileIO());
		shaved_sum = Integer.parseInt(list[0][0]); // 剃ったすね毛の総数取得
		for (int i = 0; i < 5; i++)
			// 前回よりの経過時間の取得
			hps[i] = Integer.parseInt(list[0][i + 2]);
		difference = (System.currentTimeMillis()) - Long.parseLong(list[0][7]);
		for (int i = 0; i < hps.length; i++)
			// 各カミソリのＨＰを取得
			hps[i] = Integer.parseInt(list[0][i + 2]);
	}

	public void setSaved_sum(int saved_sum) {
		this.shaved_sum = saved_sum;
	}

	public int getShaved_sum() {
		return shaved_sum;
	}

	public int getSick_no() {
		return sick_no;
	}

	public int getHp(int number) {
		return hps[number];
	}

	public void setHp(int number, int new_hp) {
		hps[number] = new_hp;
	}

	public long getDifference() {
		return difference;
	}

	public void setSick_no(int sick_no) {
		this.sick_no = sick_no;
	}

	public void DataSave(Game game) {
		Long time = System.currentTimeMillis();
		Utils.addSaveData(game.getFileIO(), getShaved_sum(), 0, hps[0], hps[1],
				hps[2], hps[3], hps[4], time);
	}
}
