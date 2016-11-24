package javagame;

import org.newdawn.slick.*;

public class Bomberman {
	public static int OFFSET = 2;
	public static int MAP_OFFSET_X = 30;
	public static int MAP_OFFSET_Y = 30;
	public static int NUM_OF_ROWS = 13;
	public static int NUM_OF_COLS = 19;
	static final int GRASS = 0;
	static final int BRICK = 1;
	static final int BLOCK = 2;
	static final int BOMB = 3;
	static final int FIRE = 4;
	static final int EDGE = 5;
	
	int[][] map = new int[NUM_OF_ROWS][NUM_OF_COLS];
	
	// starting canvas position
	private int startPosX;
	private int startPosY;
	
	// current canvas position
	private int posX;
	private int posY;
	
	// current corresponding map index coordinates
	private int mapIndexI;
	private int mapIndexJ;
//	private Image img;
	private int power = 1; 
	
	public Bomberman(int startPosX, int startPosY, int mapIndexI, int mapIndexJ/*, Image img*/) {
//		this.startPosX = startPosX;
		this.posX = startPosX;
//		this.startPosY = startPosY;
		this.posY = startPosY;
		this.mapIndexI = mapIndexI;
		this.mapIndexJ = mapIndexJ;
//		this.img = img;
//		this.map = map;
		
	}
	
	private int tempX = 0;
	private int tempY = 0;
	
	public void move(Input input) {
//		Input input = gc.getInput();
		if(input.isKeyDown(Input.KEY_W)) {			// UP
			if(map[mapIndexI-1][mapIndexJ] != BRICK && map[mapIndexI-1][mapIndexJ] != BLOCK) {
				if(Math.abs(this.tempY - OFFSET) != MAP_OFFSET_Y) {	
					this.tempY -= OFFSET;
				} else {
					this.tempY = 0;
					this.mapIndexI -= 1;
				}
				this.posY -= OFFSET;
			}
		} else if(input.isKeyDown(Input.KEY_S)) {	// DOWN
			if(map[mapIndexI+1][mapIndexJ] != BRICK && map[mapIndexI+1][mapIndexJ] != BLOCK) {
				if(Math.abs(this.tempY + OFFSET) != MAP_OFFSET_Y) {	
					this.tempY += OFFSET;
				} else {
					this.tempY = 0;
					this.mapIndexI += 1;
				}
				this.posY += OFFSET;
			}
		} else if(input.isKeyDown(Input.KEY_A)) {	// LEFT
			if(map[mapIndexI][mapIndexJ-1] != BRICK && map[mapIndexI][mapIndexJ-1] != BLOCK) {
				if(Math.abs(this.tempX - OFFSET) != MAP_OFFSET_X) {	
					this.tempX -= OFFSET;
				} else {
					this.tempX = 0;
					this.mapIndexJ -= 1;
				}
				this.posX -= OFFSET;
			}
		} else if(input.isKeyDown(Input.KEY_D)) {	// RIGHT
			if(map[mapIndexI][mapIndexJ+1] != BRICK && map[mapIndexI][mapIndexJ+1] != BLOCK) {
				if(Math.abs(this.tempX + OFFSET) != MAP_OFFSET_X) {	
					this.tempX += OFFSET;
				} else {
					this.tempX = 0;
					this.mapIndexJ += 1;
				}
				this.posX += OFFSET;
			}
		}
		// TODO: print mapIndexI, mapIndexJ on screen
//		System.out.println("(" + this.mapIndexI + ", " + this.mapIndexJ + ")");
//		System.out.print("current row: ");
//		for(int j=0; j<NUM_OF_COLS; j+=1) {
//			System.out.print(map[mapIndexI][j]);
//		}
//		System.out.println();
	}
	
	public int posX() {
		return this.posX;
	}
	
	public int posY() {
		return this.posY;
	}
	
	public int mapIndexI() {
		return this.mapIndexI;
	}
	
	public int mapIndexJ() {
		return this.mapIndexJ;
	}
	
//	public Image img() {
//		return this.img;
//	}

	public void setPos(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	
	public void setMap(int[][] map) {
		for(int i=0; i<NUM_OF_ROWS; i+=1) {
			for(int j=0; j<NUM_OF_COLS; j+=1) {
				this.map[i][j] = map[i][j];
			}
		}
	}
}
