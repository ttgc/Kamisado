package kamisado.level;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import kamisado.exceptions.MoveException;
import kamisado.gameplay.Plateau;

public class MainLevel extends BasicGameState {
	private Plateau board;

	public MainLevel() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		board = new Plateau();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		board.render(g);
	}
	
	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		// TODO Auto-generated method stub
		super.mouseDragged(oldx, oldy, newx, newy);
		if (oldx/100 != newx/100 && oldy/100 != newy/100) {
			try {
				board.move(oldx, oldy, newx, newy);
			} catch (MoveException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
