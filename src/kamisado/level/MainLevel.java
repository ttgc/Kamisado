package kamisado.level;

import java.awt.Point;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import kamisado.exceptions.InvalidArgumentException;
import kamisado.exceptions.MoveException;
import kamisado.gameplay.Couleur;
import kamisado.gameplay.Plateau;
import kamisado.gameplay.Side;
import kamisado.gameplay.StructureSwitch;

public class MainLevel extends BasicGameState {
	private Plateau board;
	private Point selected;
	private Side playing;
	private Couleur nextcolor;

	public MainLevel() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		try {
			board = new Plateau();
		} catch (InvalidArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		selected = null;
		playing = Side.Black;
		nextcolor = null;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		board.render(g);
	}
	
	/*@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		// TODO Auto-generated method stub
		// Only used during game
		super.mouseDragged(oldx, oldy, newx, newy);
		if (!board.isEnded()) { //if game is not finished yet
			if (oldx/100 != newx/100 && oldy/100 != newy/100) {
				try {
					board.move(oldx/100, oldy/100, newx/100, newy/100);
					//real area is 100x100 px ; have to divide each pixel position given by slick
				} catch (MoveException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}*/
	
	@Override
	public void mouseClicked(int button, int x, int y, int nbr) {
		// TODO Auto-generated method stub
		super.mouseClicked(button, x, y, nbr);
		if (button == Input.MOUSE_LEFT_BUTTON) {
			if (!board.isEnded()) {
				if (selected == null && board.isPieceHere(x/100, y/100) && board.getSideHere(x/100, y/100).equals(playing) && (nextcolor == null || nextcolor.equals(board.getCouleurHere(x/100, y/100)))) {
					selected = new Point(x/100,y/100);
				} else if (selected != null) {
					if (selected.x != x/100 || selected.y != y/100) {
						try {
							StructureSwitch sw = board.move(selected.x, selected.y, x/100, y/100);
							playing = sw.side;
							nextcolor = sw.color;
						} catch (MoveException e) {
							// TODO Auto-generated catch block
							if (e.getMover() != null) {
								e.printStackTrace();
							}
							System.out.println("error");
						}
						selected = null;
					}
				}
			} else {			
				if (y < 100) {//first column
					board.reset(false);
				} else if (y > 700) {//last column
					board.reset(true);
				}
				switch (board.getWinner()) {
				case Black:
					playing = Side.White;
					break;
				case White:
					playing = Side.Black;
					break;				
				}
				nextcolor = null;
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub
		board.update(delta);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
