package kamisado.exceptions;

import kamisado.gameplay.Piece;

public class MoveException extends Exception {
	private static final long serialVersionUID = -2657653329581091729L;
	private Piece mover;
	
	public MoveException(String message, Piece mover) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public Piece getMover() {
		return mover;
	}

}
