package kamisado.gameplay;

public class Piece {
	private Couleur color;
	private Side side;

	public Piece(Side side, Couleur color) {
		// TODO Auto-generated constructor stub
		this.side = side;
		this.color = color;
	}

	public Couleur getColor() {
		return color;
	}

	public Side getSide() {
		return side;
	}

}
