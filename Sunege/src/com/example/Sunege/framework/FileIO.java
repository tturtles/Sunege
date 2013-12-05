package com.example.Sunege.framework;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentValues;

public interface FileIO {
	public boolean CreateDBandTable(String[] sql);

	public boolean writeFile(String table, ContentValues val);

	public String[][] readFile(String table, String[] columns, String where, String[] value, String older, int quantity);

	public InputStream readSound(String fileName) throws IOException;
	
	public boolean deleteRecode(String table, String whereClause);
}
