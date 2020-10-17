package minmax;

import reversi.Celda;
import reversi.JugadorReversi;

public class JugadorReversiImpl implements JugadorReversi{

	@Override
	public Celda devolverJugadaOptima(int[][] tablero) {
		
		// Dentro del tablero, los números de cada celda indican
		// 1  -> Pieza de la computadora "O"
		// -1 -> Pieza de la persona contra la cual competimos "X"
		// 0  -> No hay ninguna pieza
		
		// Los valores de fila y columna pueden ir de 0 a 7 para poner una ficha
		// Si se decide pasar de turno porque no hay jugadas posibles, el valor de ambas debe ser -1
		int fila = -1;
		int columna = -1;
		
		return new Celda(fila, columna);
	}

}
