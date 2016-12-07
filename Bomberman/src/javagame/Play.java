package javagame;

import java.util.Random;
import java.util.ArrayList;

import java.io.IOException;
import java.net.*;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;

public class Play extends BasicGameState {

	
//	TextField textField = new TextField(null, null, 10, 10, 100, 50);
	
	Image bomberman;
	int bombermanX;
	int bombermanY;
	
	Image block;
	Image brick;
	Image grass;
	Image bomb;
	Image h_fire;
	Image v_fire;
	Image c_fire;
	Image dead;
	
	int mapDrawX = 90;
	int mapDrawY = 90;
	int mapOffsetX = 30;
	int mapOffsetY = 30;
	
//	public boolean ALIVE = true;
	
	public static int MAP_OFFSET_X = 30;
	public static int MAP_OFFSET_Y = 30;
	
	public static int NUM_OF_PLAYERS = 1;
	public static int OFFSET = 2;
	public static final int ORIGIN = 90;
	
	public static final int PLAYER = -1;
	public static final int GRASS = 0;
	public static final int BRICK = 1;
	public static final int BLOCK = 2;
	public static final int BOMB = 3;
	public static final int FIRE_H = 4;
	public static final int FIRE_V = 5;
	public static final int FIRE_C = 6;
	public static final int DEAD = 6;
	
	public static int NUM_OF_ROWS = 15;
	public static int NUM_OF_COLS = 21;
	
	public int[][] map;
	
	public static String message;
	public static String udpMsg;
	
	public static ArrayList<String> messages = new ArrayList<String>();
	
	int tempX = 0;
	int tempY = 0;
	
	public int[][] map() {
		return this.map;
	}
//	Image[] bombermanImgs = new Image[NUM_OF_PLAYERS];
	
	Animation bombAnimation;
	Animation player;
	Animation moveUp;
	Animation moveDown;
	Animation moveLeft;
	Animation moveRight;
	
	TextField field;
	TextField feed;
	
	int[] duration = {200, 200};
	
	
	Bomberman player1 = new Bomberman(120, 120, 1, 1);
//	Bomberman player1 = new Bomberman(660, 480, 13, 19);
//	public static Play play = new Play(1);
	
	String serverHostName = "localhost";
	int serverPortNumber = 5555;
	InetAddress serverIPAddress;
	
	public Play(int state) {
		
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		try {
			serverIPAddress = null;
			serverIPAddress = InetAddress.getByName(serverHostName);
		} catch(IOException e) {}
		
		field = new TextField(gc, gc.getDefaultFont(), 90, 550, 850, 60, new ComponentListener() {
			public void componentActivated(AbstractComponent source) {
//				String message = "Entered1: "+field.getText();
				field.setFocus(true);
				
			}
		});
		
		feed = new TextField(gc, gc.getDefaultFont(), 750, 90, 200, 440, new ComponentListener() {
			public void componentActivated(AbstractComponent source) {
//				String message = "Entered1: "+field.getText();
				feed.setFocus(false);
				
			}
		});
		
		field.setBorderColor(Color.white);
//		field.setBackgroundColor(Color.white);
//		field.setTextColor(Color.black);
		
		
		block = new Image("res/block.png");
		brick = new Image("res/brick.png");
		grass = new Image("res/grass.png");
		bomb = new Image("res/bomb.png");
		h_fire = new Image("res/fire-h.png");
		v_fire = new Image("res/fire-v.png");
		c_fire = new Image("res/fire.png");
		dead = new Image("res/dead.png");
		
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
//		player1.setMap(map);
		map[player1.mapIndexI()][player1.mapIndexJ()] = PLAYER;
		
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		g.drawString(Menu.nickname, 60, 30);
		field.render(gc, g);
		feed.render(gc, g);
		mapDrawX = 90;
		mapDrawY = 90;
		
		for(int i=0; i<map.length; i+=1) {
			for(int j=0; j<map[i].length; j+=1) {
				if(map[i][j] == GRASS || map[i][j] == PLAYER) {					
					grass.draw(mapDrawX, mapDrawY);
				} else if(map[i][j] == BRICK ) {			
					brick.draw(mapDrawX, mapDrawY);
				} else if(map[i][j] ==  BLOCK) {		
					block.draw(mapDrawX, mapDrawY);
				} else if(map[i][j] ==  BOMB) {		
					bomb.draw(mapDrawX, mapDrawY);
				} else if(map[i][j] ==  FIRE_H) {		
					h_fire.draw(mapDrawX, mapDrawY);
				} else if(map[i][j] ==  FIRE_V) {		
					v_fire.draw(mapDrawX, mapDrawY);
				} else if(map[i][j] ==  FIRE_C) {		
					c_fire.draw(mapDrawX, mapDrawY);
				} else if(map[i][j] ==  DEAD) {		
					c_fire.draw(mapDrawX, mapDrawY);
				}
				mapDrawX += mapOffsetX;
			}
			mapDrawY += mapOffsetY;
			mapDrawX = 90;
		}
		if(player1.alive()) {
			player.draw(player1.posX(), player1.posY());
		}
		
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		
		if(player1.alive()) {
			if(input.isKeyDown(Input.KEY_UP)) {
				player = moveUp;	// change sprite
				if(map[player1.mapIndexI()-1][player1.mapIndexJ()] != BRICK && map[player1.mapIndexI()-1][player1.mapIndexJ()] != BLOCK && map[player1.mapIndexI()-1][player1.mapIndexJ()] != BOMB) {
					if(Math.abs(tempY - OFFSET) != MAP_OFFSET_Y) {	
						player1.setPos(player1.posX(), player1.posY()-OFFSET);
						tempY -= OFFSET;
					} else {
						tempY = 0;
						player1.addToMapIndexI(-1);
					}
					
				}
			} else if(input.isKeyDown(Input.KEY_DOWN)) {
				player = moveDown;	// change sprite
				if(map[player1.mapIndexI()+1][player1.mapIndexJ()] != BRICK && map[player1.mapIndexI()+1][player1.mapIndexJ()] != BLOCK && map[player1.mapIndexI()+1][player1.mapIndexJ()] != BOMB) {
					if(Math.abs(tempY + OFFSET) != MAP_OFFSET_Y) {	
						player1.setPos(player1.posX(), player1.posY()+OFFSET);
						tempY += OFFSET;
					} else {
						tempY = 0;
						player1.addToMapIndexI(1);
					}
					
				}
			} else if(input.isKeyDown(Input.KEY_LEFT)) {
				player = moveLeft;	// change sprite
				if(map[player1.mapIndexI()][player1.mapIndexJ()-1] != BRICK && map[player1.mapIndexI()][player1.mapIndexJ()-1] != BLOCK && map[player1.mapIndexI()+1][player1.mapIndexJ()] != BOMB) {
					if(Math.abs(tempX - OFFSET) != MAP_OFFSET_X) {	
						player1.setPos(player1.posX()-OFFSET, player1.posY());
						tempX -= OFFSET;
					} else {
						tempX = 0;
						player1.addToMapIndexJ(-1);
					}
					
				}
			} else if(input.isKeyDown(Input.KEY_RIGHT)) {
				player = moveRight;	// change sprite
				if(map[player1.mapIndexI()][player1.mapIndexJ()+1] != BRICK && map[player1.mapIndexI()][player1.mapIndexJ()+1] != BLOCK && map[player1.mapIndexI()+1][player1.mapIndexJ()] != BOMB) {
					if(Math.abs(tempX + OFFSET) != MAP_OFFSET_X) {	
						player1.setPos(player1.posX()+OFFSET, player1.posY());
						tempX += OFFSET;
					} else {
						tempX = 0;
						player1.addToMapIndexJ(1);
					}
					
				}
			} else if((input.isKeyPressed(Input.KEY_LSHIFT) || input.isKeyPressed(Input.KEY_RSHIFT)) && map[player1.mapIndexI()][player1.mapIndexJ()] != BOMB) {
				map[player1.mapIndexI()][player1.mapIndexJ()] = BOMB;
				final Bomb bomb = new Bomb(3, player1.power(), player1.mapIndexI(), player1.mapIndexJ());
				Thread bombThread = new Thread(bomb) {
				    public void run() {
				        try{
				        	// bomb countdown
				        	Thread.sleep(bomb.time()*1000);
				        	// draw fires
	//			        	for(int x = (bomb.power()*-1); x <= bomb.power(); x+=1) {
	//							// vertical
	//							if(bomb.mapI()+x > 0 && bomb.mapI()+x < NUM_OF_ROWS-1) {
	//								System.out.println(x);
	//								if(map[bomb.mapI()+x][bomb.mapJ()] != BLOCK) {
	//									map[bomb.mapI()+x][bomb.mapJ()] = FIRE_V;
	//								} else {
	//									break;
	//								}
	//							}
	//							System.out.println("_");
	//							// horizontal
	//							if(bomb.mapJ()+x > 0 && bomb.mapJ()+x < NUM_OF_COLS-1) {
	//								System.out.println(x);
	//								if(map[bomb.mapI()][bomb.mapJ()+x] != BLOCK) {
	//									map[bomb.mapI()][bomb.mapJ()+x] = FIRE_H;
	//								} else {
	//									break;
	//								}
	//							}
	//						}
				        	for(int x = 0; x <= bomb.power(); x+=1) {
								// vertical
								if(bomb.mapI()+x > 0 && bomb.mapI()+x < NUM_OF_ROWS-1) {
									if(map[bomb.mapI()+x][bomb.mapJ()] != BLOCK) {
										map[bomb.mapI()+x][bomb.mapJ()] = FIRE_V;
										if(bomb.mapI()+x == player1.mapIndexI() && bomb.mapJ() == player1.mapIndexJ()) {
											System.out.println("DEAD");
											player1.setAlive(false);
										}
										if(map[bomb.mapI()+x][bomb.mapJ()] == BRICK) {
											break;
										}
									} else {
										break;
									}
								}
								// horizontal
								if(bomb.mapJ()+x > 0 && bomb.mapJ()+x < NUM_OF_COLS-1) {
									if(map[bomb.mapI()][bomb.mapJ()+x] != BLOCK) {
										map[bomb.mapI()][bomb.mapJ()+x] = FIRE_H;
										if(bomb.mapI() == player1.mapIndexI() && bomb.mapJ()+x == player1.mapIndexJ()) {
											System.out.println("DEAD");
											player1.setAlive(false);
										}
										if(map[bomb.mapI()][bomb.mapJ()+x] == BRICK) {
											break;
										}
									} else {
										break;
									}
								}
							}
				        	
				        	for(int x = 0; x >= -bomb.power(); x-=1) {
								// vertical
								if(bomb.mapI()+x > 0 && bomb.mapI()+x < NUM_OF_ROWS-1) {
									if(map[bomb.mapI()+x][bomb.mapJ()] != BLOCK) {
										map[bomb.mapI()+x][bomb.mapJ()] = FIRE_V;
										if(bomb.mapI()+x == player1.mapIndexI() && bomb.mapJ() == player1.mapIndexJ()) {
											System.out.println("DEAD");
											player1.setAlive(false);
										}
										if(map[bomb.mapI()+x][bomb.mapJ()] == BRICK) {
											break;
										}
									} else {
										break;
									}
								}
								// horizontal
								if(bomb.mapJ()+x > 0 && bomb.mapJ()+x < NUM_OF_COLS-1) {
									if(map[bomb.mapI()][bomb.mapJ()+x] != BLOCK) {
										map[bomb.mapI()][bomb.mapJ()+x] = FIRE_H;
										if(bomb.mapI() == player1.mapIndexI() && bomb.mapJ()+x == player1.mapIndexJ()) {
											System.out.println("DEAD");
											player1.setAlive(false);
										}
										if(map[bomb.mapI()][bomb.mapJ()+x] == BRICK) {
											break;
										}
									} else {
										break;
									}
								}
							}
				        	
				        	map[bomb.mapI()][bomb.mapJ()] = FIRE_C;
				        	Thread.sleep(1000);
				        	// reset to grass
				        	for(int x = 0; x <= bomb.power(); x+=1) {
								// vertical
								if(bomb.mapI()+x > 0 && bomb.mapI()+x < NUM_OF_ROWS-1) {
									if(map[bomb.mapI()+x][bomb.mapJ()] != BLOCK) {
										map[bomb.mapI()+x][bomb.mapJ()] = GRASS;
									} else {
										break;
									}
								}
								// horizontal
								if(bomb.mapJ()+x > 0 && bomb.mapJ()+x < NUM_OF_COLS-1) {
									if(map[bomb.mapI()][bomb.mapJ()+x] != BLOCK) {
										map[bomb.mapI()][bomb.mapJ()+x] = GRASS;
									} else {
										break;
									}
								}
							}
				        	for(int x = 0; x >= -bomb.power(); x-=1) {
								// vertical
								if(bomb.mapI()+x > 0 && bomb.mapI()+x < NUM_OF_ROWS-1) {
									if(map[bomb.mapI()+x][bomb.mapJ()] != BLOCK) {
										map[bomb.mapI()+x][bomb.mapJ()] = GRASS;
									} else {
										break;
									}
								}
								// horizontal
								if(bomb.mapJ()+x > 0 && bomb.mapJ()+x < NUM_OF_COLS-1) {
									if(map[bomb.mapI()][bomb.mapJ()+x] != BLOCK) {
										map[bomb.mapI()][bomb.mapJ()+x] = GRASS;
									} else {
										break;
									}
								}
							}
				        	map[bomb.mapI()][bomb.mapJ()] = GRASS;
				        	
				        	this.interrupt();
				        }catch(Exception e){
				        	
				        }
				    }
				};
				bombThread.start();
	
			} else if(input.isKeyPressed(Input.KEY_ENTER)) {
				if(field.hasFocus()) {
					
					message = field.getText();
					field.setText("");
					field.setFocus(false);
					System.out.println(Menu.nickname + ": " + message);
	//				message = null;
				} else {
					field.setFocus(true);
				}
			}
		}
		
		if(messages.size() == 7) {
			messages.remove(0);
		}
		
		feed.setText("");
		for(int z=0; z<messages.size(); z+=1) {
			feed.setText(feed.getText() + "\n" +  messages.get(z));
		}
		
		//UDP Stuff
		try {
			
			udpMsg = "UDPMESSAGEFOO";
			
			DatagramSocket clientSocket = new DatagramSocket();
			byte[] sendData = new byte[udpMsg.length() * 8];
			sendData = udpMsg.getBytes();
			
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIPAddress, serverPortNumber);
			clientSocket.send(sendPacket);
		}catch(IOException e) {}

	}
	
	public int getID() {
		return 1; 
	}
	
}
