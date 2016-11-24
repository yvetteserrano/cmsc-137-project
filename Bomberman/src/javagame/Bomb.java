package javagame;

public class Bomb implements Runnable {
	
	static final int GRASS = 0;
	static final int BRICK = 1;
	static final int BLOCK = 2;
	static final int BOMB = 3;
	static final int FIRE = 4;
	
	static int NUM_OF_ROWS = 15;
	static int NUM_OF_COLS = 21;
	
	int time;
	int[][] map;
	int mapI;
	int mapJ;
	
	public Bomb(int time, int[][] map, int mapI, int mapJ) {
		this.time = time;
		this.map = new int[NUM_OF_ROWS][NUM_OF_COLS];
		for(int i=0; i<NUM_OF_ROWS; i+=1) {
			for(int j=0; j<NUM_OF_COLS; j+=1) {
				this.map[i][j] = map[i][j];
			}
		}
		this.mapI = mapI;
		this.mapJ = mapJ;
	}
	
	public void run() {
		try {
			System.out.println("Bomb started...");
			Thread.sleep(time*1000);
			this.map[this.mapI][this.mapJ] = GRASS;
			System.out.println("KABOOM!");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
