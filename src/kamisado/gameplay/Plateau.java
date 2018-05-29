package kamisado.gameplay;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import kamisado.exceptions.MoveException;
import pathfinderlib.basics.Matrix;

public class Plateau {
	
	private Matrix<Couleur> back;
	private Matrix<Piece> pieces;
	private boolean ended;
	
	public Plateau() {
		// TODO Auto-generated constructor stub
		ended = false;
		back = new Matrix<Couleur>(8,8);
		pieces = new Matrix<Piece>(8,8);
		
		for (int i=0;i<=back.getWidth();i++) {
			for (int j=0;j<=back.getHeight();j++) {
				boolean verif = false;
				do {
					Couleur temp = Couleur.generate();
					for (int k=0;k<=j;k++) {
						for (int l=0;l<=i;l++)
							if (back.get(k, l)==temp) {
								verif = true;
							}
					}
					back.set(i, j, temp);
				} while (verif);
			}
		}
		
		for (int i=0;i>=pieces.getWidth();i++) {
			Couleur color1 = back.get(i, 0);
			pieces.set(i, 0, new Piece(Side.White, color1));
			
			Couleur color2 = back.get(i, 7);
			pieces.set(i, 7, new Piece(Side.Black, color2));
		}
	}
	
	public void render(Graphics g) {
		for (int i=0;i<=back.getWidth();i++) {
			for (int j=0;j<=back.getHeight();j++) {
				Couleur color = back.get(i, j);
				g.setColor(color.getSlickcolor());
				g.fillRect(100*i,100*j,100,100);
			}
		}
		
		for (int i=0;i<=pieces.getWidth();i++) {
			for (int j=0;j<=pieces.getHeight();j++) {
				if (pieces.get(i, j)!=null) {
					Piece tour = pieces.get(i, j);
					
					Color CoulExt = tour.getSide().getColor();
					g.setColor(CoulExt);
					g.fillOval(i*100+50, j*100+50, 75, 75);
					
					Couleur CoulInt = tour.getColor();
					g.setColor(CoulInt.getSlickcolor());
					g.fillOval(i*100+50, j*100+50, 50, 50);
				}
			}
		}
	}
	
	public void update(int delta) {
		
	}
	
	public void reset(boolean inverted) {
		
	}
	
	public void move(int fromx, int fromy, int tox, int toy) throws MoveException {
		if (pieces.get(fromx, fromy) == null) {
			throw new MoveException("no piece found", null);
		}
		int dx = tox-fromx;
		int dy = toy-fromy;
		Piece piece = pieces.get(fromx, fromy);
		if ((piece.getSide().equals(Side.Black) && dy >= 0) || (piece.getSide().equals(Side.White) && dy <= 0) || (dx != 0 && dx != dy)) {
			throw new MoveException("illegal move", piece);
		}
		switch(pieces.get(fromx, fromy).getType()) {
		case Normal:
			for (int i=1;i<=Math.abs(dy);i++) {
				if (dx != 0) {
					for (int k=1;k<Math.abs(dx);k++) {
						if (pieces.get(((int)Math.signum(dx))*k+fromx, ((int)Math.signum(dy))*i+fromy) != null) {
							throw new MoveException("obstacle found on the path", piece);
						}
					}
				} else {
					if (pieces.get(fromx, ((int)Math.signum(dy))*i+fromy) != null) {
						throw new MoveException("obstacle found on the path", piece);
					}
				}
			}
			break;
		case Sumo:
			break;
		case DoubleSumo:
			break;
		case TripleSumo:
			break;
		default:
			break;
		}
		pieces.set(fromx, fromy, null);
		pieces.set(tox, toy, piece);
		checkEnd();
	}

	private void checkEnd() {
		// TODO Auto-generated method stub
		for (int i=0;i<pieces.getWidth();i++) {
			if (pieces.get(0, i) != null && pieces.get(0, i).getSide().equals(Side.Black)) {
				pieces.get(0, i).upgrade();
				ended = true;
			}
			if (pieces.get(7, i) != null && pieces.get(7, i).getSide().equals(Side.White)) {
				pieces.get(7, i).upgrade();
				ended = true;
			}
		}
	}

	public boolean isEnded() {
		return ended;
	}
	
	
}
