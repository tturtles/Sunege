package com.example.Sunege.framework.game;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TooManyListenersException;

import android.util.Log;

import com.example.Sunege.framework.Game;

public class User {

	private int sunegePoint = 0; // 現在所持しているすね毛ポイント
	private int sick_no = 0; // 刃の枚数　1 = 1枚刃
	private int hps[];
	private int total = 0; // 剃った毛の総数
	private long difference;

	private boolean[] flags = { false }; // 0=すね毛暴走モード
	private int[] bounders = { 10000 }; // 0=flags[0]が発動するボーダーライン

	public User(Game game) {
		hps = new int[5];
		String[][] list = Utils.readSaveData(game.getFileIO());
		sunegePoint = Integer.parseInt(list[0][0]); // 剃ったすね毛の総数取得
//		sunegePoint = 1000000; // 剃ったすね毛の総数取得
		total = Integer.parseInt(list[0][8]);
		for (int i = 0; i < flags.length; i++)
			flags[i] = bounders[i] < total ? true : false;
		for (int i = 0; i < 5; i++)
			hps[i] = Integer.parseInt(list[0][i + 2]);
		// 前回からの経過時間の取得
		difference = (System.currentTimeMillis()) - Long.parseLong(list[0][7]);
		for (int i = 0; i < hps.length; i++)
			// 各カミソリのＨＰを取得
			hps[i] = Integer.parseInt(list[0][i + 2]);
		// TimeLog(list, difference);
	}

	private void TimeLog(String[][] list, long difference) {
		SimpleDateFormat D = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Log.d("TIME1", "" + D.format(new Date(System.currentTimeMillis())));
		Log.d("TIME2", "" + D.format(new Date(Long.parseLong(list[0][7]))));
		D = new SimpleDateFormat("HHHH:mm:ss");
		Log.d("TIME3", "" + D.format(new Date(difference)));
		Log.d("追加するすね毛の本数", "" + (difference / 60000)); // 60秒ごと
	}

	public void setSaved_sum(int saved_sum) {
		this.sunegePoint = saved_sum;
	}

	public int getShaved_sum() {
		return sunegePoint;
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

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
		for (int i = 0; i < flags.length; i++)
			flags[i] = total == bounders[i] ? true : false;
	}

	public boolean[] getFlags() {
		return flags;
	}

	public void DataSave(Game game) {
		Long time = System.currentTimeMillis();
		Utils.addSaveData(game.getFileIO(), getShaved_sum(), 0, hps[0], hps[1],
				hps[2], hps[3], hps[4], time, getTotal());
	}
}
