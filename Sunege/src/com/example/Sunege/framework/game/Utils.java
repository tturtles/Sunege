package com.example.Sunege.framework.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.graphics.AvoidXfermode.Mode;
import android.preference.PreferenceManager;

import com.example.Sunege.framework.FileIO;

public class Utils {
	public static boolean soundEnabled = true;
	public SharedPreferences sharedPref;
	public long old_time;

	public Utils() {
		old_time = 0;
	}

	public static void load(FileIO files) {
		BufferedReader in = null;
		try {
			String[] sql = new String[2];
			sql[0] = "create table SaveData("
					+ "_id integer primary key autoincrement,"
					+ "sum integer default 0," + "loves integer default 0,"
					+ "sick1 integer default 0," + "sick2 integer default 0,"
					+ "sick3 integer default 0," + "sick4 integer default 0,"
					+ "sick5 integer default 0," + "times long,"
					+ "total integer default 0)";

			sql[1] = "create table SunegeData("
					+ "_id integer primary key autoincrement,"
					+ "x integer default 0," + "y integer default 0,"
					+ "level integer default 10," + "type text default A,"
					+ "times long)";

			if (files.CreateDBandTable(sql))
				addSaveData(files, 0, 0, 0, 0, 0, 0, 0,
						System.currentTimeMillis(), 0);
		} catch (Exception e) {
			// デフォルト設定があるのでエラーは無視
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
	}

	public static boolean addSaveData(FileIO files, int sum, int loves,
			int sick1, int sick2, int sick3, int sick4, int sick5, long times,
			int total) {
		ContentValues val = new ContentValues();
		val.put("sum", sum);
		val.put("loves", loves);
		val.put("sick1", sick1);
		val.put("sick2", sick2);
		val.put("sick3", sick3);
		val.put("sick4", sick4);
		val.put("sick5", sick5);
		val.put("times", times);
		val.put("total", total);
		return files.writeFile("SaveData", val);
	}

	public static boolean addSunegeData(FileIO files, int x, int y, int level,
			String type, long times) {
		ContentValues val = new ContentValues();
		val.put("x", x);
		val.put("y", y);
		val.put("level", level);
		val.put("type", type);
		val.put("times", times);
		return files.writeFile("SunegeData", val);
	}

	public static String[][] readSaveData(FileIO files) {
		String[] columns = { "sum", "loves", "sick1", "sick2", "sick3",
				"sick4", "sick5", "times", "total" };
		String order = "times desc";
		// String order = null;
		return files.readFile("SaveData", columns, null, null, order, 1);
	}

	public static String[][] readSunegeData(FileIO files) {
		String[] columns = { "x", "y", "level", "type", "times" };
		// String order = null;
		return files.readFile("SunegeData", columns, null, null, null, 110);
	}

	public static boolean deleteSunegeRecode(FileIO files) {
		return files.deleteRecode("SunegeData", null);
	}

}
