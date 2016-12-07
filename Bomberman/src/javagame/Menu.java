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
	TextField serverField;
	
	static String nickname;
	static String serverName;
	
	public Menu(int state) {
		
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		nicknameField = new TextField(gc, gc.getDefaultFont(), 300, 250, 100, 30, new ComponentListener() {
			public void componentActivated(AbstractComponent source) {
				nicknameField.setFocus(true);
			}
		});
		nicknameField.setFocus(true);
		
		serverField = new TextField(gc, gc.getDefaultFont(), 300, 300, 100, 30, new ComponentListener() {
			public void componentActivated(AbstractComponent source) {
				serverField.setFocus(true);
			}
		});
		serverField.setFocus(true);
		
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		g.drawString("Bomberman", 150, 80);
		g.drawString("Press F1 to submit nickname and play game", 200, 180);
		g.drawString("Nickname", 200, 250);
		nicknameField.render(gc, g);
		g.drawString("Server", 200, 300);
		serverField.render(gc, g);
		
		g.drawString("Press F2 to input nickname, F3 to input server, F4 for instructions", 200, 450);
		
		
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		if(input.isKeyPressed(Input.KEY_F1)) {
			nickname = nicknameField.getText();
//			serverName = serverField.getText();
			serverName = "localhost";
			System.out.println(nickname);
			input.clearKeyPressedRecord();
			Game.createClient(Menu.serverName, 9999, Menu.nickname);
			sbg.enterState(1);
		} else if(input.isKeyPressed(Input.KEY_F2)) {
			if(nicknameField.hasFocus()) {
				nicknameField.setFocus(false);
			} else {
				nicknameField.setFocus(true);
			}
		} else if(input.isKeyPressed(Input.KEY_F3)) {
			if(serverField.hasFocus()) {
				serverField.setFocus(false);
			} else {
				serverField.setFocus(true);
			}
		} else if(input.isKeyPressed(Input.KEY_F4)) {
			input.clearKeyPressedRecord();
			sbg.enterState(2);
		}
	}
	
	public int getID() {
		return 0; 
	}
	
}
