package javagame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Game extends StateBasedGame {
	
	public static final String gameName = "Bomberman";
	public static final int menu = 0;
	public static final int play = 1;
	 
	public Game(String gameName) {
		super(gameName);
		this.addState(new Menu(menu));
		this.addState(new Play(play));
	}
	
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		// GameContainer manages game loop, frame rate, behind the scene stuff
		this.getState(menu).init(gameContainer, this);
		this.getState(play).init(gameContainer, this);
		
		//redirect to menu state
		this.enterState(menu);
	}
	
	public static void main(String[] args) {
		AppGameContainer appGC;
		
		try {
			// create a window that would hold a game
			appGC = new AppGameContainer(new Game(gameName));

			appGC.setDisplayMode(768, 640, false);
			
			appGC.start();
		} catch(SlickException e) {
			e.printStackTrace();
		}
		
	}

}
