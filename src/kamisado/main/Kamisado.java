package kamisado.main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import kamisado.level.MainLevel;

public class Kamisado extends StateBasedGame {

	public Kamisado(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		// TODO Auto-generated method stub
		addState(new MainLevel());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AppGameContainer app;
		try {
			app = new AppGameContainer(new Kamisado("Kamisado"));
			app.setDisplayMode(800, 800, false);
			app.setShowFPS(false);
			app.setFullscreen(false);
			app.start();
		} catch(SlickException e){
			e.printStackTrace();
		}
	}

}
