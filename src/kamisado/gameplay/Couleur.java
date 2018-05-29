package kamisado.gameplay;

import java.util.Random;

import org.newdawn.slick.Color;

public enum Couleur {
	Marron(new Color(91, 60, 17)),
	Orange(new Color(204, 85, 0)),
	Jaune(new Color(255, 215, 0)),
	Rouge(new Color(255, 0, 0)),
	Vert(new Color(20, 148, 20)),
	Bleu(new Color(0, 47, 167)),
	Violet(new Color(121, 28, 248)),
	Rose(new Color(253, 108, 158));
	
	private Color slickcolor;
	
	Couleur(Color slk) {
		slickcolor = slk;
	}

	public Color getSlickcolor() {
		return slickcolor;
	}
	
	public static Couleur generate() {
		Random rdm = new Random();
		switch(rdm.nextInt(8)) {
		case 0:
			return Marron;
		case 1:
			return Orange;
		case 2:
			return Jaune;
		case 3:
			return Rouge;
		case 4:
			return Vert;
		case 5:
			return Bleu;
		case 6:
			return Violet;
		case 7:
			return Rose;
		default:
			return null;
		}
	}
}
