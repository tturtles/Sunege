package com.example.Sunege.framework.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.example.Sunege.framework.FileIO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public class AndroidFileIO implements FileIO {
	Context con;
	CreateProductHelper helper = null;
	SQLiteDatabase db = null;
	String externalStoragePath;

	public AndroidFileIO(Context con) {
		this.con = con;
		this.externalStoragePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator;
	}

	public boolean CreateDBandTable(String[] sql) {
		helper = new CreateProductHelper(con);
		db = helper.getWritableDatabase();
		try {
			for (int i = 0; i < sql.length; i++) {
				db.execSQL(sql[i]);
			Log.d("テーブル作成", "成功"+i);
			}
			return true;
		} catch (Exception e) {
			Log.d("テーブル作成", "作成済み");
			Log.d("error", e.toString());
			return false;
		}
	}

	public boolean writeFile(String table, ContentValues val) {
		try {
			db = helper.getWritableDatabase();
			db.beginTransaction(); // トランザクション制御開始
			db.insert(table, null, val); // データ登録
			db.setTransactionSuccessful(); // コミット
			Log.d("データ登録", "成功");
			return true;
		} catch (Exception e) {
			Log.d("データ登録", "失敗");
			Log.d("error", e.toString());
			return false;
		} finally {
			db.endTransaction(); // トランザクション制御終了
			db.close();
		}
	}

	public String[][] readFile(String table, String[] columns, String where, String[] value,
			String older, int quantity) {
		String[][] list = new String[quantity][columns.length];
		try {
			db = helper.getReadableDatabase();
			Cursor cursor = db.query(table,  columns, where, value, null,
					null, older);
			for (int i = 0; i < quantity && cursor.moveToNext(); i++)
				for (int j = 0; j < columns.length; j++)
					list[i][j] = cursor.getString(j);
			Log.d("データ取得", "成功");
		} catch (Exception e) {
			Log.d("データ取得", "失敗");
			Log.d("error", e.toString());
		} finally {
			db.close();
		}
		return list;
	}

	public boolean deleteRecode(String table, String whereClause) {
		try {
			db = helper.getWritableDatabase();
			db.beginTransaction(); // トランザクション制御開始
			db.delete(table, whereClause, null); // データ登録
			db.setTransactionSuccessful(); // コミット
			Log.d("データ削除", "成功");
			return true;
		} catch (Exception e) {
			Log.d("データ削除", "失敗");
			Log.d("error", e.toString());
			return false;
		} finally {
			db.endTransaction(); // トランザクション制御終了
			db.close();
		}
	}

	public InputStream readSound(String fileName) throws IOException {
		return new FileInputStream(externalStoragePath + fileName);
	}
}
