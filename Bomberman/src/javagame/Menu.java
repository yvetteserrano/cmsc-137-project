package javagame;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.*;

public class Menu extends BasicGameState {
	
	int choice = 0;
	// 0 - Play Game
	// 2 - 
	
	TextField nicknameField;
	
	static String nickname;
	
	public Menu(int state) {
		
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		nicknameField = new TextField(gc, gc.getDefaultFont(), 300, 250, 100, 30, new ComponentListener() {
			public void componentActivated(AbstractComponent source) {
				nicknameField.setFocus(true);
			}
		});
		nicknameField.setFocus(true);
		
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		g.drawString("Bomberman", 150, 80);
		g.drawString("Press F1 to submit nickname and play game", 200, 180);
		g.drawString("Nickname", 200, 250);
		nicknameField.render(gc, g);
		g.drawString("Press F2 if textbox loses focus", 200, 380);
		
		
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		if(input.isKeyDown(Input.KEY_F1)) {
			nickname = nicknameField.getText();
			System.out.println(nickname);
			sbg.enterState(1);
		} else if(input.isKeyDown(Input.KEY_F2)) {
			if(nicknameField.hasFocus()) {
				nicknameField.setFocus(false);
			} else {
				nicknameField.setFocus(true);
			}
		}
	}
	
	public int getID() {
		return 0; 
	}
	
}
