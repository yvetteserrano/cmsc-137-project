package javagame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Menu extends BasicGameState {
	
	int choice = 0;
	// 0 - Play Game
	// 2 - 
	
	public Menu(int state) {
		
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("Bomberman", 150, 80);
		g.drawString("PLAY GAME", 200, 180);
		
		
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		if(input.isKeyDown(Input.KEY_ENTER)) {
			sbg.enterState(1);
		}
	}
	
	public int getID() {
		return 0; 
	}
	
}
