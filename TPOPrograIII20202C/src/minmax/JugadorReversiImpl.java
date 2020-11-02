package minmax;

import reversi.Celda;
import reversi.JugadorReversi;

public class JugadorReversiImpl implements JugadorReversi{
	
	private Celda transformarEnCelda(int[] posicionEntrada)
	{
		return new Celda(posicionEntrada[0], posicionEntrada[1]);
	}
	
	private int calcularOpcion(int[][]tablero,int jugador, int nroHijo)
	{
		int Seleccion = 0;
		int[][] tableroAux = tablero;
		if(esHoja(tablero))
		{
			return calcularPuntaje(tablero);
		}
		else
		{
			int proximojugador = (jugador == 1)? -1:1;
			while(!esHoja(tablero))
			{
				proximojugador = (proximojugador == 1)? -1:1;
				int[] puntajeDeJugadas = Auxiliar(tableroAux,proximojugador,nroHijo +1);
				if(proximojugador == 1)
				{
					return max(puntajeDeJugadas);
				}
				else
				{
					return min(puntajeDeJugadas);
				}	
				
			}
		}
		return Seleccion;
	}
	
	private int[][] obtenerJugadasValidas(int[][] tablero, int jugador)
	{
		int[][] jugadasValidas = new int[64][2];
		int n=0;
		for(int x=0;x<7;x++)
			for(int y=0;y<7;y++)
			{
				if(esJugadaValida(tablero,jugador,x,y)){
					jugadasValidas[n][0] = x;
					jugadasValidas[n][1] = y;
					n++;
				}
			}
		return jugadasValidas;
	}
	
	private int max(int[] puntajesDeJugadas)
	{
		int maxPuntaje = 0;
		for(int x =0; x<puntajesDeJugadas.length;x++)
			if(maxPuntaje < puntajesDeJugadas[x])
				maxPuntaje = puntajesDeJugadas[x];
		return maxPuntaje;
	}
	
	private int min(int[] puntajesDeJugadas)
	{
		int minPuntaje = 0;
		for(int x =0; x<puntajesDeJugadas.length;x++)
			if(minPuntaje > puntajesDeJugadas[x])
				minPuntaje = puntajesDeJugadas[x];
		return minPuntaje;
	}
	
	private int[] Auxiliar(int[][] tablero, int jugador, int nroHijo)
	{
		int[][] tableroAux = tablero;
		int[][] listadoJugadasValidas = obtenerJugadasValidas(tablero,jugador);
		int[] puntajesDeJugadas = new int[64];
		for(int i =0; i< listadoJugadasValidas.length; i++)
		{
			int[] jugada = listadoJugadasValidas[i];
			tableroAux[jugada[0]][jugada[1]] = jugador;
			calcularOpcion(tableroAux,jugador,nroHijo);
			tableroAux[jugada[0]][jugada[1]] = 0;
			puntajesDeJugadas[i] = calcularPuntaje(tableroAux);
			
		}
		return puntajesDeJugadas;
	}
	private int calcularPuntaje(int[][] tablero)
	{
		int contadorPC = 0;
		int contadorJugador = 0;
		for(int x = 0; x<7; x++)
			for(int y =0;y<7;y++)
				if(tablero[x][y] == 1)
					contadorPC++;
				else
					contadorJugador++;
		if(contadorPC > contadorJugador)
			return 1;
		else
			if(contadorPC< contadorJugador)
				return -1;
			else
				return 0;
				
	}
	private boolean esHoja(int[][] tablero )
	{
		for(int x = 0; x <7 ; x++)
			for(int y = 0; y < 7; y++)
				if(tablero[x][y] == 0)
					return false;
		return true;
	}
	private boolean esJugadaValida(int[][] tablero, int jugador, int comienzox, int comienzoy)
	{
		if(tablero[comienzox][comienzoy] != 0)
		{
			return false;
		}
		
		
		tablero[comienzox][comienzoy] = jugador;
		int otrojugador = (jugador == 1)?-1:1;
		
		int[][] direcciones = {{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1}};
		boolean flag = true;
		for(int i = 0; i < 7;  i++)
		{
			int x = comienzox;
			int y = comienzoy;
			if( estaEnTablero(x,y) && tablero[x][y] == otrojugador)
			{
				while(tablero[x][y] == otrojugador)
				{
					x = x + direcciones[i][0];
					y = y + direcciones[i][1];
					flag =( estaEnTablero(x,y) && tablero[x][y] == otrojugador);
				}
			}
		}
		return flag;
	}
	private boolean estaEnTablero(int x, int y)
	{
		return (x > -1 && y < 8 && y > -1 && y <8);
	}
	
	@Override
	public Celda devolverJugadaOptima(int[][] tablero) {
		
		int[][] listadoJugadasValidas = obtenerJugadasValidas(tablero,1);
		
		int[][] tableroAux = tablero;
		
		int[] Jugada = listadoJugadasValidas[0];
		tableroAux[Jugada[0]][Jugada[1]] = 1;
		int puntajeSeleccion = calcularOpcion(tableroAux,0,1);
		tableroAux[Jugada[0]][Jugada[1]] = 0;
		int puntaSeleccionActual = puntajeSeleccion;
		int[] mejorJugada = Jugada;
		for(int i = 0; i < listadoJugadasValidas.length; i++)
		{
			Jugada = listadoJugadasValidas[i];
			tableroAux[Jugada[0]][Jugada[1]]=1;
			puntajeSeleccion = calcularOpcion(tableroAux,0,1);
			tableroAux[Jugada[0]][Jugada[1]] = 0;
			if(puntajeSeleccion == 1)
				return transformarEnCelda(Jugada);
			else
				if(puntajeSeleccion > puntaSeleccionActual)
				{
					mejorJugada = listadoJugadasValidas[i];
					puntajeSeleccion = puntaSeleccionActual;
				}
					
					
		}
		return transformarEnCelda(mejorJugada);
	}

}
