package kamisado.gameplay;

import org.newdawn.slick.Graphics;

import pathfinderlib.basics.Matrix;

public class Plateau {
	
	private Matrix<Couleur> back;
	private Matrix<Piece> pieces;

	public Plateau() {
		// TODO Auto-generated constructor stub
		back = new Matrix<Couleur>(8,8);
		//pieces = new Matrix<Pion>(8,8);
		
		/*for (int i = 0;i<=back.getWidth();i++) {
			for (int j = 0;j<=back.getHeight();j++) {
				boolean verif = false;
				do {
					Couleur temp = Couleur.generate();
					if ()
				} while (verif);
			}
		}*/
	}
	
	public void render(Graphics g) {
		
	}
	
	public void update(int delta) {
		
	}
	
	public void move(int fromx, int fromy, int tox, int toy) {
		if (pieces.get(fromx, fromy) != null) {
			
		}
	}
	
	
}
