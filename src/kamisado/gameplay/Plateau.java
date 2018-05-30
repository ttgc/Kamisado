package kamisado.gameplay;

import java.util.Vector;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import kamisado.exceptions.InvalidArgumentException;
import kamisado.exceptions.MoveException;
import pathfinderlib.basics.Matrix;

public class Plateau {
	
	private Matrix<Couleur> back;
	private Matrix<Piece> pieces;
	private Vector<Couleur> verif;
	private boolean ended;
	private Side winner;
	
	public Plateau() throws InvalidArgumentException {
		// TODO Auto-generated constructor stub
		ended = false;
		back = new Matrix<Couleur>(8,8);
		pieces = new Matrix<Piece>(8,8);
		verif = new Vector<Couleur>(8);
		
		for (int i=0;i<back.getWidth();i++) {
			verif.clear();
			for (int j=0;j<back.getHeight();j++) {
				Couleur temp = Couleur.generateWithExclusion(verif);
				verif.add(temp);
				back.set(i, j, temp);
			}
		}

		for (int i=0;i<pieces.getWidth();i++) {
			Couleur color1 = back.get(i, 0);
			pieces.set(i, 0, new Piece(Side.White, color1));
			
			Couleur color2 = back.get(i, 7);
			pieces.set(i, 7, new Piece(Side.Black, color2));
		}
	}
	
	public void render(Graphics g) {
		for (int i=0;i<back.getWidth();i++) {
			for (int j=0;j<back.getHeight();j++) {
				Couleur color = back.get(i, j);
				g.setColor(color.getSlickcolor());
				g.fillRect(100*i,100*j,100,100);
				g.setColor(Color.black);
				g.drawLine(100*i,100*j,100*(i+1),100*j);
				g.drawLine(100*i,100*j,100*i,100*(j+1));
			}
		}
		
		for (int i=0;i<pieces.getWidth();i++) {
			for (int j=0;j<pieces.getHeight();j++) {
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
		Vector<Piece> blancs = new Vector<>();
		Vector<Piece> noirs = new Vector<>();
		if (!inverted) {
			for (int i=0;i<pieces.getHeight();i++) {
				for (int k=0;k<pieces.getWidth();k++) {
					if (pieces.get(k, i) != null) {
						if (pieces.get(k, i).getSide().equals(Side.Black)) {
							noirs.add(pieces.get(k, i));
						} else {
							blancs.add(pieces.get(k, i));
						}
						pieces.set(k, i, null);
					}
				}
			}
		} else {
			for (int i=0;i<pieces.getHeight();i++) {
				for (int k=pieces.getWidth()-1;k>=0;k--) {
					if (pieces.get(k, i) != null) {
						if (pieces.get(k, i).getSide().equals(Side.Black)) {
							noirs.add(pieces.get(k, i));
						} else {
							blancs.add(pieces.get(k, i));
						}
						pieces.set(k, i, null);
					}
				}
			}
		}
		for (int i=0;i>=pieces.getWidth();i++) {
			pieces.set(i, 0, blancs.get(i));
			pieces.set(i, 7, noirs.get(i));
		}
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
		case Sumo:
			if (Math.abs(dy) > 5) {
				throw new MoveException("a sumo cannot move too far", piece);
			}
			if (Math.abs(dy) == 1 && dx == 0 && fromy+(2*dy) <= 7 && fromy+(2*dy) >= 0 && pieces.get(fromx, fromy+dy) != null && !pieces.get(fromx, fromy+dy).getSide().equals(piece.getSide()) && pieces.get(fromx, fromy+dy).getType().equals(PieceType.Normal)) {
				if (pieces.get(fromx, fromy+(dy*2)) != null) {
					throw new MoveException("too much pieces on this column", piece);
				}
				pieces.set(fromx, fromy+(dy*2), pieces.get(fromx, fromy+dy));
				pieces.set(fromx, fromy+dy, null);
			}
			break;
		case DoubleSumo:
			if (Math.abs(dy) > 3) {
				throw new MoveException("a double sumo cannot move too far", piece);
			}
			break;
		case TripleSumo:
			if (Math.abs(dy) > 1) {
				throw new MoveException("a triple sumo cannot move too far", piece);
			}
			break;
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
		default:
			throw new MoveException("internal move error", piece);
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
				winner = Side.Black;
			}
			if (pieces.get(7, i) != null && pieces.get(7, i).getSide().equals(Side.White)) {
				pieces.get(7, i).upgrade();
				ended = true;
				winner = Side.White;
			}
		}
	}

	public boolean isEnded() {
		return ended;
	}

	public Side getWinner() {
		return winner;
	}
	
	
}
