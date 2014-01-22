package com.example.Sunege.framework;

import android.graphics.Paint;
import android.graphics.Rect;

public interface Graphics {
	public static enum PixmapFormat {
		ARGB8888, ARGB4444, RGB565
	}

	public Pixmap newPixmap(String fileName, PixmapFormat format);

	public void clear(int color);

	public void drawPixel(int x, int y, int color);

	public void drawLine(int x, int y, int x2, int y2, int color);

	public void drawLine(int x, int y, int x2, int y2, int color, int width);

	public void drawRect(int x, int y, int width, int height, int color);

	public void drawRect(int x, int y, int width, int height, int color,
			int alpha);

	public void drawPixmap(Pixmap pixmap, int x, int y);

	public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
			int srcWidth, int srcHeight);

	public void drawPixmap(Pixmap pixmap, Rect src, Rect dst);

	public int getWidth();

	public int getHeight();

	public void drawTextAlp(String line, float x, float y, Paint paint);

	public void drawTextAlp(String line, float x, float y, int color, float size);

	public void drawPixmapTurn(Pixmap pixmap, Rect src, Rect dst, int angle);
	
	public void drawPixmap(Pixmap pixmap, Rect src, Rect dst, int alpha);
}
