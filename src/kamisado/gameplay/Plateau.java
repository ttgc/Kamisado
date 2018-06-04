package kamisado.gameplay;

import java.util.Arrays;
import java.util.Collections;
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

		/*for (int i=0;i<pieces.getWidth();i++) {
			Couleur color1 = back.get(i, 0);
			pieces.set(i, 0, new Piece(Side.White, color1));
			
			Couleur color2 = back.get(i, 7);
			pieces.set(i, 7, new Piece(Side.Black, color2));
		}*/
		Vector<Couleur> colors = new Vector<>();
		colors.addAll(Arrays.asList(Couleur.Marron,Couleur.Bleu,Couleur.Jaune,Couleur.Orange,Couleur.Rose,Couleur.Rouge,Couleur.Vert,Couleur.Violet));
		Collections.shuffle(colors);
		for (int i=0;i<pieces.getWidth();i++) {
			Couleur color1 = colors.get(i);
			pieces.set(i, 0, new Piece(Side.White, color1));
			
			Couleur color2 = colors.get(colors.size()-1-i);
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
					g.fillOval(i*100+12, j*100+12, 75, 75);
					
					Couleur CoulInt = tour.getColor();
					g.setColor(CoulInt.getSlickcolor());
					g.fillOval(i*100+25, j*100+25, 50, 50);
					
					g.setColor(Color.black);
					g.setLineWidth(2);
					switch (tour.getType()) {
					case DoubleSumo:
						g.drawLine(i*100+43+4, j*100+23+4, i*100+10+65, j*100+10+45);
						g.drawLine(i*100+23+4, j*100+43+4, i*100+9+45, j*100+9+65);
						break;
					case Sumo:
						g.drawLine(i*100+33, j*100+33, i*100+12+55, j*100+12+55);
						break;
					case TripleSumo:
						g.drawLine(i*100+33, j*100+33, i*100+12+55, j*100+12+55);
						g.drawLine(i*100+43+4, j*100+23+4, i*100+10+65, j*100+10+45);
						g.drawLine(i*100+23+4, j*100+43+4, i*100+9+45, j*100+9+65);
						break;
					default:
						break;
					}
					g.setLineWidth(1);
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
							if (i==8) {
								noirs.add(7,pieces.get(k, i));
							} else {
								noirs.add(pieces.get(k, i));
							}
						} else {
							if (i==0) {
								blancs.add(pieces.get(k, i));
							} else {
								blancs.add(0,pieces.get(k, i));
							}
						}
						pieces.set(k, i, null);
					}
				}
			}
			for (int i=0;i<pieces.getWidth();i++) {
				pieces.set(pieces.getWidth()-1-i, 0, blancs.get(i));
				pieces.set(i, 7, noirs.get(i));
			}
		} else {
			for (int i=0;i<pieces.getHeight();i++) {
				for (int k=pieces.getWidth()-1;k>=0;k--) {
					if (pieces.get(k, i) != null) {
						if (pieces.get(k, i).getSide().equals(Side.Black)) {
							if (i==8) {
								noirs.add(7,pieces.get(k, i));
							} else {
								noirs.add(pieces.get(k, i));
							}
						} else {
							if (i==0) {
								blancs.add(pieces.get(k, i));
							} else {
								blancs.add(0,pieces.get(k, i));
							}
						}
						pieces.set(k, i, null);
					}
				}
			}
			for (int i=0;i<pieces.getWidth();i++) {
				pieces.set(i, 0, blancs.get(i));
				pieces.set(pieces.getWidth()-1-i, 7, noirs.get(i));
			}
		}
		ended = false;
	}
	
	public StructureSwitch move(int fromx, int fromy, int tox, int toy) throws MoveException {
		if (pieces.get(fromx, fromy) == null) {
			throw new MoveException("no piece found", null);
		}
		int dx = tox-fromx;
		int dy = toy-fromy;
		Piece piece = pieces.get(fromx, fromy);
		if ((piece.getSide().equals(Side.Black) && dy >= 0) || (piece.getSide().equals(Side.White) && dy <= 0) || (dx != 0 && Math.abs(dx) != Math.abs(dy))) {
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
						if (pieces.get(((int)Math.signum(dx))*k+fromx, ((int)Math.signum(dy))*k+fromy) != null) {
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
		return transferColor(back.get(tox, toy), piece.getSide());
	}

	private boolean isBlocked(Couleur couleur, Side playing) {
		// TODO Auto-generated method stub
		for (int i=0;i<pieces.getWidth();i++) {
			for (int k=0;k<pieces.getHeight();k++) {
				if (pieces.get(i, k) != null && pieces.get(i, k).getSide().equals(playing) && pieces.get(i, k).getColor().equals(couleur)) {
					int dy = 0;
					switch(playing) {
					case Black:
						dy = -1;
						break;
					case White:
						dy = 1;
						break;
					}
					boolean blocked = true;
					for(int dx=-1;dx<=1;dx++) {
						if (k+dy < pieces.getHeight() && k+dy >= 0 && i+dx >= 0 && i+dx < pieces.getWidth()) {
							if (pieces.get(i+dx, k+dy) == null) {
								blocked = false;
							}
						}
					}
					return blocked;
				}
			}
		}
		return false;
	}
	
	private StructureSwitch transferColor(Couleur couleur, Side playing) {
		switch (playing) {
		case Black:
			playing = Side.White;
			break;
		case White:
			playing = Side.Black;
			break;
		}
		Vector<Piece> analyzed = new Vector<>();
		while (isBlocked(couleur, playing)) {
			boolean switched = false;
			for (int i=0;i<pieces.getWidth();i++) {
				for (int k=0;k<pieces.getHeight();k++) {
					if (!switched && pieces.get(i, k) != null && pieces.get(i, k).getSide().equals(playing) && pieces.get(i, k).getColor().equals(couleur)) {
						couleur = back.get(i, k);
						if (analyzed.contains(pieces.get(i, k))) {
							ended = true;
							winner = analyzed.get(0).getSide();
							analyzed.get(0).upgrade();
							return new StructureSwitch(analyzed.get(0).getColor(), analyzed.get(0).getSide());
						} else {
							analyzed.add(pieces.get(i, k));
						}
						switched = true;
					}
				}
			}
			switch (playing) {
			case Black:
				playing = Side.White;
				break;
			case White:
				playing = Side.Black;
				break;
			}
		}
		return new StructureSwitch(couleur, playing);
	}

	private void checkEnd() {
		// TODO Auto-generated method stub
		for (int i=0;i<pieces.getWidth();i++) {
			if (pieces.get(i, 0) != null && pieces.get(i, 0).getSide().equals(Side.Black)) {
				pieces.get(i, 0).upgrade();
				ended = true;
				winner = Side.Black;
			}
			if (pieces.get(i, 7) != null && pieces.get(i, 7).getSide().equals(Side.White)) {
				pieces.get(i, 7).upgrade();
				ended = true;
				winner = Side.White;
			}
		}
	}
	
	public Side getSideHere(int x, int y) {
		if (pieces.get(x, y) != null) {
			return pieces.get(x, y).getSide();
		}
		return null;
	}
	
	public Couleur getCouleurHere(int x, int y) {
		if (pieces.get(x, y) != null) {
			return pieces.get(x, y).getColor();
		}
		return null;
	}
	
	public boolean isPieceHere(int x, int y) {
		return (pieces.get(x, y) != null);
	}

	public boolean isEnded() {
		return ended;
	}

	public Side getWinner() {
		return winner;
	}
	
	
}
