package javagame;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.*;

public class Instructions extends BasicGameState {
	
	int currInst = 0;
	
	String[] instructions = {
	    "Use the ARROW KEYS to move Bomberman",
	    "Press SHIFT to drop a bomb. Bombs will self-destruct after 3 seconds.",
	    "The goal of every player is to survive.",
	    "A player will die if hit by a bomb.",
	    "Bombs can only destroy bricks (brown), but not blocks (gray).",
	    "The game will end when there's only 0 or 1 player left."
	};
	
//	Image[] images = {}
	
	public Instructions(int state) {
		
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("HOW TO PLAY", 150, 150);
		if(currInst<instructions.length) {
			g.drawString(instructions[currInst], 200, 200);
		}
		g.drawString("Press ENTER to continue", 200, 450);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		if(input.isKeyPressed(Input.KEY_ENTER)) {
			currInst+=1;
			if(currInst == instructions.length) {
				input.clearKeyPressedRecord();
				sbg.enterState(0);	// back to main menu
			}
		}
	}
	
	public int getID() {
		return 2; 
	}
	
}
