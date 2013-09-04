//package com.example.project2nd.framework.game;
//
//import com.example.project2nd.framework.Graphics;
//import com.example.project2nd.framework.Pixmap;
//
//public class Esa extends Sprite {
//
//	private World world;
//	private int vy;
//	private int vx;
//	private boolean flag;
//
//	private static final float TICK_INITIAL = 0.5f;
//	private static float tick = TICK_INITIAL; // 更新速度
//	private float tickTime;
//
//	public Esa(double x, double y, int speed, boolean flag,Pixmap pixmap) {
//		super(x, y, pixmap);
//		this.x = x;
//		this.y = y;
//		this.flag = flag;
//		this.speedX = 0;
//		speedY = speed;
//		width = 100;
//		height = 100;
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
//	public void Use(Animal animal) {
//			animal.setFlag();
//	}
//
//	public boolean getFlag() {
//		return flag;
//	}
//	
//	public void setY(int y) {
//		this.y = y;
//	}
//}
