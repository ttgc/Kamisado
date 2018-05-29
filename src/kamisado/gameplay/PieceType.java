package kamisado.gameplay;

public enum PieceType {
	Normal(0),
	Sumo(1),
	DoubleSumo(3),
	TripleSumo(5);
	
	private int score;
	
	PieceType(int sc) {
		score = sc;
	}

	public int getScore() {
		return score;
	}
}
