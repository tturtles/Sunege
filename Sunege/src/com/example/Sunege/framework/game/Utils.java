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
			String sql = "create table savedata("
					+ "_id integer primary key autoincrement,"
					+ "sum integer default 0," + "loves integer default 0,"
					+ "sick1 integer default 1," + "sick2 integer default 0,"
					+ "sick3 integer default 0," + "sick4 integer default 0,"
					+ "sick5 integer default 0," + "times long)";
			if (files.CreateDBandTable(sql)) 
				addData(files, 0, 0, 1, 0, 0, 0, 0, System.currentTimeMillis());
			in = new BufferedReader(new InputStreamReader(
					files.readSound(".sunege")));
//			soundEnabled = Boolean.parseBoolean(in.readLine());
			soundEnabled = true;
		} catch (IOException e) {
			// デフォルト設定があるのでエラーは無視
		} catch (NumberFormatException e) {
			// 同上
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
			}
		}
	}

	public static boolean addData(FileIO files, int sum, int loves, int sick1,
			int sick2, int sick3, int sick4, int sick5, long times) {
		ContentValues val = new ContentValues();
		val.put("sum", sum);
		val.put("loves", loves);
		val.put("sick1", sick1);
		val.put("sick2", sick2);
		val.put("sick3", sick3);
		val.put("sick4", sick4);
		val.put("sick5", sick5);
		val.put("times", times);
		return files.writeFile(val);
	}

	public static String[][] readFile(FileIO files) {
		String[] columns = { "sum", "loves", "sick1", "sick2", "sick3",
				"sick4", "sick5", "times" };
		String order = "times desc";
		// String order = null;
		return files.readFile(columns, null, null, order, 1);
	}

}
