package kamisado.gameplay;

import org.newdawn.slick.Color;

public enum Side {
	White(Color.white),
	Black(Color.black);
	
	private Color color;
	
	Side(Color col) {
		color = col;
	}

	public Color getColor() {
		return color;
	}

}
