//package com.example.project2nd.framework.game;
//
//import com.example.project2nd.framework.Graphics;
//import com.example.project2nd.framework.Pixmap;
//
//public class Truk extends Sprite {
//
//	private World world;
//	private int vy;
//	private int vx;
//
//	private static final float TICK_INITIAL = 0.5f;
//	private static float tick = TICK_INITIAL; // 更新速度
//	private float tickTime;
//
//	public Truk(double _x, double _y, int speed, Pixmap pixmap) {
//		super(_x, _y, pixmap);
//		x = _x;
//		y = _y;
//		this.world = world;
//		speedY = speed;
//		width = 100;
//		height = 300;
//	}
//
//	public void Update() {
//		vy = speedY;
//		y += vy;
//		vx = speedX;
//		x += vx;
//	}
//
//	public void draw(Graphics g, float deltaTime) {
//		g.drawPixmap(Assets.animal, (int) x, (int) y);
//	}
//
//	/*
//	 * 以下getter,setter群
//	 */
//	public double getX() {
//		return x;
//	}
//
//	public void setX(double x) {
//		this.x = x;
//	}
//
//	public double getY() {
//		return y;
//	}
//
//	public void setY(double y) {
//		this.y = y;
//	}
//
//	public int getHeight() {
//		return height;
//	}
//
//	public int getWidth() {
//		return width;
//	}
//
//}
