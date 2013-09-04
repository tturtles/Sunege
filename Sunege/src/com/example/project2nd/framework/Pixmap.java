package com.example.project2nd.framework;

import com.example.project2nd.framework.Graphics.PixmapFormat;

public interface Pixmap {
	
    public int getWidth();

    public int getHeight();

    public PixmapFormat getFormat();

    public void dispose();
}
