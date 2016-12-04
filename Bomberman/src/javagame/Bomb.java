package javagame;

public class Bomb implements Runnable {
	
	static final int GRASS = 0;
	static final int BRICK = 1;
	static final int BLOCK = 2;
	static final int BOMB = 3;
	static final int FIRE_H = 4;
	static final int FIRE_V = 5;
	
	static int NUM_OF_ROWS = 15;
	static int NUM_OF_COLS = 21;
	
	private int time;
//	int[][] map;
	private int mapI;
	private int mapJ;
	private int power;
	
	public Bomb(int time, int power, int mapI, int mapJ) {
		this.time = time;
		this.mapI = mapI;
		this.mapJ = mapJ;
		this.power = power;
	}
	
	public int mapI() {
		return this.mapI;
	}
	
	public int mapJ() {
		return this.mapJ;
	}
	
	public int time() {
		return this.time;
	}
	
	public int power() {
		return this.power;
	}
	
	private boolean isRunning;
	
	public void run() {
		// no need
	}
    
    public boolean isRunning(){
        return this.isRunning;
    }
    
    public void stop() {
    	this.isRunning = false;
    }

}
