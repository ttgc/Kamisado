package kamisado.gameplay;

public class Piece {
	private Couleur color;
	private Side side;
	private PieceType type;

	public Piece(Side side, Couleur color) {
		// TODO Auto-generated constructor stub
		this.side = side;
		this.color = color;
		type = PieceType.Normal;
	}
	
	public boolean upgrade() {
		switch(type) {
		case TripleSumo:
			return true;
		case DoubleSumo:
			type = PieceType.TripleSumo;
			break;
		case Normal:
			type = PieceType.Sumo;
			break;
		case Sumo:
			type = PieceType.DoubleSumo;
			break;
		default:
			break;
		}
		return false;
	}

	public Couleur getColor() {
		return color;
	}

	public Side getSide() {
		return side;
	}
	
	public PieceType getType() {
		return type;
	}

}
