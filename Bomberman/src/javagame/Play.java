package javagame;

import java.util.Random;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Play extends BasicGameState {
	
	Image bomberman;
	int bombermanX;
	int bombermanY;
	
	Image block;
	Image brick;
	Image grass;
	Image bomb;
	
	int mapDrawX = 90;
	int mapDrawY = 90;
	int mapOffsetX = 30;
	int mapOffsetY = 30;
	
	public static int NUM_OF_PLAYERS = 1;
	public static int OFFSET = 1;
	public static final int ORIGIN = 90;
	
	public static final int GRASS = 0;
	public static final int BRICK = 1;
	public static final int BLOCK = 2;
	public static final int BOMB = 3;
	public static final int FIRE = 4;
	
	public static int NUM_OF_ROWS = 15;
	public static int NUM_OF_COLS = 21;
	
	public static int[][] map;
//	Image[] bombermanImgs = new Image[NUM_OF_PLAYERS];
	
	Animation bombAnimation;
	Animation player;
	Animation moveUp;
	Animation moveDown;
	Animation moveLeft;
	Animation moveRight;
	
	
	int[] duration = {200, 200};
	
	Bomberman player1 = new Bomberman(120, 120, 1, 1);
	
	
	public Play(int state) {
		
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
//		bomberman = new Image("res/bomberman-white.png");
//		bombermanX = 90;
//		bombermanY = 90;
		
		block = new Image("res/block.png");
		brick = new Image("res/brick.png");
		grass = new Image("res/grass.png");
		bomb = new Image("res/bomb.png");
		
		Image p1F = new Image("res/p1-front.png");
		Image p1B = new Image("res/p1-back.png");
		Image p1L = new Image("res/p1-left.png");
		Image p1R = new Image("res/p1-right.png");
		
		Image[] walkUp = {p1B, p1B};
		Image[] walkDown = {p1F, p1F};
		Image[] walkLeft = {p1L, p1L};
		Image[] walkRight = {p1R, p1R};
		Image[] bombSet = {bomb, bomb};
		
		moveUp = new Animation(walkUp, duration, false);
		moveDown = new Animation(walkDown, duration, false);
		moveLeft = new Animation(walkLeft, duration, false);
		moveRight = new Animation(walkRight, duration, false);
		bombAnimation = new Animation(bombSet, duration, false);
		
		player = moveDown;
		
		map = new int[NUM_OF_ROWS][NUM_OF_COLS];

		// generate random map
		for(int i=0; i<NUM_OF_ROWS; i+=1) {
			for(int j=0; j<NUM_OF_COLS; j+=1) {
				if((i == 0 || i == NUM_OF_ROWS-1)) {	// if edge	
					map[i][j] = BLOCK;
				} else if(j == 0 || j == NUM_OF_COLS-1) {
					map[i][j] = BLOCK;
				} else if((i == 1 || i == NUM_OF_ROWS-2) && (j == 1 || j == 2 || j == NUM_OF_COLS-3 || j == NUM_OF_COLS-2)) {	// if first or last	
					map[i][j] = GRASS;
				} else if((i == 2 || i == NUM_OF_ROWS-3) && (j == 1 || j == NUM_OF_COLS-2)) {
					map[i][j] = GRASS;
				} else if(i%2 == 0) {			// if even row
					if(j%2 == 1) {
						Random rand = new Random();
						int x = rand.nextInt(2);
						// randomize, either grass or brick
						if(x == 0) {	
							map[i][j] = GRASS;
						} else {
							map[i][j] = BRICK;
						}
					} else {	// block only
						map[i][j] = 2;
					}
				} else {						// if odd row
					Random rand = new Random();
					int x = rand.nextInt(2);
					// randomize, either grass or brick
					if(x == 0) {	
						map[i][j] = GRASS;
					} else {
						map[i][j] = BRICK;
					}
				}
			}
		}
		// after creating the map int array, pass it to all players
		player1.setMap(map);
		
		
		
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		Image p1F = new Image("res/p1-front.png");
		
		mapDrawX = 90;
		mapDrawY = 90;
		
		for(int i=0; i<map.length; i+=1) {
			for(int j=0; j<map[i].length; j+=1) {
				if(map[i][j] == GRASS ) {					
					grass.draw(mapDrawX, mapDrawY);
				} else if(map[i][j] == BRICK ) {			
					brick.draw(mapDrawX, mapDrawY);
				} else if(map[i][j] ==  BLOCK) {		
					block.draw(mapDrawX, mapDrawY);
				} else if(map[i][j] ==  BOMB) {		
					bomb.draw(mapDrawX, mapDrawY);
				}
				mapDrawX += mapOffsetX;
			}
			mapDrawY += mapOffsetY;
			mapDrawX = 90;
		}
		
		player.draw(player1.posX(), player1.posY());
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		player1.move(input);
		if(input.isKeyDown(Input.KEY_W)) {
			player = moveUp;	// change sprite
//			player1.setPos(player1.posX(), player1.posY() - OFFSET);
		} else if(input.isKeyDown(Input.KEY_S)) {
			player = moveDown;	// change sprite
//			player1.setPos(player1.posX(), player1.posY() + OFFSET);
		} else if(input.isKeyDown(Input.KEY_A)) {
			player = moveLeft;	// change sprite
//			player1.setPos(player1.posX() - OFFSET, player1.posY());
		} else if(input.isKeyDown(Input.KEY_D)) {
			player = moveRight;	// change sprite
//			player1.setPos(player1.posX() + OFFSET, player1.posY());
		} else if(input.isKeyDown(Input.KEY_SPACE)) {
			map[player1.mapIndexI()][player1.mapIndexJ()] = BOMB;
			// TODO: create bomb thread
			// TODO: try passing this play class
			Bomb bomb = new Bomb(3, map, player1.mapIndexI(), player1.mapIndexJ());
			Thread bombThread = new Thread(bomb);
			bombThread.start();
//			bomb.draw(player1.posX(), player1.posY());
		}
		
		mapDrawX = 90;
		mapDrawY = 90;
		
		for(int i=0; i<map.length; i+=1) {
			for(int j=0; j<map[i].length; j+=1) {
				if(map[i][j] == GRASS ) {					
					grass.draw(mapDrawX, mapDrawY);
				} else if(map[i][j] == BRICK ) {			
					brick.draw(mapDrawX, mapDrawY);
				} else if(map[i][j] ==  BLOCK) {		
					block.draw(mapDrawX, mapDrawY);
				} else if(map[i][j] ==  BOMB) {		
					bomb.draw(mapDrawX, mapDrawY);
				}
				mapDrawX += mapOffsetX;
			}
			mapDrawY += mapOffsetY;
			mapDrawX = 90;
		}
		

	}
	
	public int getID() {
		return 1; 
	}
	
}
