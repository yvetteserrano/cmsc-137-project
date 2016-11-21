package javagame;

import java.util.Random;
import java.util.ArrayList;
import java.lang.String;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Play extends BasicGameState {
	
	Image bomberman;
	int bombermanX;
	int bombermanY;
	int offset = 2;
	Image block;
	Image brick;
	Image grass;
	int mapDrawX = 90;
	int mapDrawY = 90;
	int mapOffsetX = 30;
	int mapOffsetY = 30;
	int numRows = 9;
	int numCols = 13;
	String[] mapStr;
	
	public Play(int state) {
		
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		bomberman = new Image("res/bomberman-white.png");
		bombermanX = 90;
		bombermanY = 90;
		block = new Image("res/block.png");
		brick = new Image("res/brick.png");
		grass = new Image("res/grass.png");
		
		mapStr = new String[numRows];

		// generate random map
		for(int i=0; i<numRows; i+=1) {
			mapStr[i] = "";
			for(int j=0; j<numCols; j+=1) {
				if((i == 0 || i == numRows-1) && (j == 0 || j == 1 || j == numCols-2 || j == numCols-1)) {	// if first or last	
					mapStr[i] += "g";
				} else if((i == 1 || i == numRows-2) && (j == 0 || j == numCols-1)) {
					mapStr[i] += "g";
				} else if(i%2 == 1) {			// if odd row
					if(j%2 == 0) {
						Random rand = new Random();
						int x = rand.nextInt(2);
						// randomize, either brick or grass
						if(x == 0) {	
							mapStr[i] += "r";
						} else {
							mapStr[i] += "g";
						}
					} else {	// block only
						mapStr[i] += "b";
					}
				} else {						// if even row
					Random rand = new Random();
					int x = rand.nextInt(2);
					// randomize, either brick or grass
					if(x == 0) {	
						mapStr[i] += "r";
					} else {
						mapStr[i] += "g";
					}
				}
			}
		}
		
		
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		mapDrawX = 90;
		mapDrawY = 90;
		
		for(int i=0; i<mapStr.length; i+=1) {
			for(int j=0; j<mapStr[i].length(); j+=1) {
				if(mapStr[i].charAt(j) == 'g' ) {					// grass
					grass.draw(mapDrawX, mapDrawY);
				} else if(mapStr[i].charAt(j) == 'b' ) {			// block
					block.draw(mapDrawX, mapDrawY);
				} else if(mapStr[i].charAt(j) == 'r' ) {			// random (grass or brick)
					brick.draw(mapDrawX, mapDrawY);
				}
				mapDrawX += mapOffsetX;
			}
			mapDrawY += mapOffsetY;
			mapDrawX = 90;
		}
		
		g.drawImage(bomberman, bombermanX, bombermanY);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		if(input.isKeyDown(Input.KEY_W)) {
			bombermanY -= offset;
		} else if(input.isKeyDown(Input.KEY_S)) {
			bombermanY += offset;
		} else if(input.isKeyDown(Input.KEY_A)) {
			bombermanX -= offset;
		} else if(input.isKeyDown(Input.KEY_D)) {
			bombermanX += offset;
		}
		
		mapDrawX = 90;
		mapDrawY = 90;
		
		for(int i=0; i<mapStr.length; i+=1) {
			for(int j=0; j<mapStr[i].length(); j+=1) {
				if(mapStr[i].charAt(j) == 'g' ) {					// grass
					grass.draw(mapDrawX, mapDrawY);
				} else if(mapStr[i].charAt(j) == 'b' ) {			// block
					block.draw(mapDrawX, mapDrawY);
				} else if(mapStr[i].charAt(j) == 'r' ) {			// random (grass or brick)
					brick.draw(mapDrawX, mapDrawY);
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
