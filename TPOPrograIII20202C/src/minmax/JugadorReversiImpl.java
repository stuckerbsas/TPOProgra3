package minmax;

import reversi.Celda;
import reversi.JugadorReversi;

public class JugadorReversiImpl implements JugadorReversi{
	
	
	private int contador = 0;
	private int contadorb = 0;
	private int calcularOpcion(int[][]tablero,int jugador, int nroHijo)
	{
		
		int Seleccion = 0;
		int[][] tableroAux = tablero;
		try {
			if(esHoja(tablero))
		{
			return calcularPuntaje(tablero);
		}
		else
		{
			//int proximojugador = (jugador == 1)? -1:1;
			while(!esHoja(tablero))
			{
				int proximojugador = (jugador == 1)? -1:1;
				int[] puntajeDeJugadas = crearListadoPuntajes(tableroAux,proximojugador,nroHijo +1);
				if(proximojugador == 1)
				{
					Seleccion= max(puntajeDeJugadas);
				}
				else
				{
					Seleccion= min(puntajeDeJugadas);
				}	
				
			}
		}
		}
		catch(Exception ex)
		{
			System.out.println("calcularOpcion");
		}
		return Seleccion;
		
	}
	
	private int[][] obtenerJugadasValidas(int[][] tablero, int jugador)
	{
		
		int[][] jugadasValidas = new int[64][2];
		int n=0;
		try {
		for(int x=0;x<=7;x++)
			for(int y=0;y<=7;y++)
			{
				if(esJugadaValida(tablero,jugador,x,y)){
					jugadasValidas[n][0] = x;
					jugadasValidas[n][1] = y;
					n++;
				}
			}
		}
		catch(Exception ex)
		{
			System.out.println("obtenerJugadasValidas");
		}
		/*for(int x =0; x< 64;x++)
			if(jugadasValidas[x][0] != 0 && jugadasValidas[x][1] != 0)
				System.out.println(jugadasValidas[x][0] + "," + jugadasValidas[x][1] );
		//System.out.println("-----------------------------------------------------------------------------");
		//mostrarTablero(tablero);*/
		return jugadasValidas;
	}
	
	public void mostrarTablero(int[][]tablero)
	{
		System.out.println("|Y\\X| 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |");
		System.out.println("----------------------------------------");
		int contador = 0;
		for(int x = 0; x< 8 ;x++) {
			for(int y = 0; y< 8 ;y++)
				{
					if(y==0) {
						System.out.print("| " + contador + " ");
						contador++;
					}
					if(tablero[x][y] == 1)
						System.out.print("| O ");
					else
						if(tablero[x][y]== -1)
							System.out.print("| X ");
						else
							System.out.print("|   ");
				}
			System.out.print("|");
			System.out.println();
			System.out.println("-------------------------------------");
		}
		
	}
	
	private int max(int[] puntajesDeJugadas)
	{
		int maxPuntaje = 0;
		try {
		for(int x =0; x<puntajesDeJugadas.length;x++)
			if(maxPuntaje < puntajesDeJugadas[x])
				maxPuntaje = puntajesDeJugadas[x];
		}
		catch(Exception ex)
		{
			System.out.println("man");
		}
		return maxPuntaje;
	}
	
	private int min(int[] puntajesDeJugadas)
	{
		int minPuntaje = 0;
		try {
		for(int x =0; x<puntajesDeJugadas.length;x++)
			if(minPuntaje > puntajesDeJugadas[x])
				minPuntaje = puntajesDeJugadas[x];
		}
		catch(Exception ex)
		{
			System.out.println("min");
		}
		return minPuntaje;
	}
	
	private int[] crearListadoPuntajes(int[][] tablero, int jugador, int nroHijo)
	{
		if(contadorb == 10000) {
			System.out.println(contador);
			contadorb = 0;
			mostrarTablero(tablero);
		}
		else
			contadorb++;
		contador++;
		int[][] tableroAux = tablero;
		
		int[][] listadoJugadasValidas = obtenerJugadasValidas(tablero,jugador);
		int[] puntajesDeJugadas = new int[64];
		try {
			for(int i =0; i< listadoJugadasValidas.length; i++)
		{
			int[] jugada = listadoJugadasValidas[i];
			tableroAux[jugada[0]][jugada[1]] = jugador;
			calcularOpcion(tableroAux,jugador,nroHijo);
			tableroAux[jugada[0]][jugada[1]] = 0;
			puntajesDeJugadas[i] = calcularOpcion(tableroAux,jugador,nroHijo);
			
		}
		}
		catch(Exception ex)
		{
			System.out.println("Auxiliar");
		}
		return puntajesDeJugadas;
	}
	private int calcularPuntaje(int[][] tablero)
	{
		int contadorPC = 0;
		int contadorJugador = 0;
		try {
		for(int x = 0; x<=7; x++)
			for(int y =0;y<=7;y++)
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
		catch(Exception ex)
		{
			System.out.println("calcularPuntaje");
			return 0;
		}
				
	}
	private boolean esHoja(int[][] tablero )
	{
		try {
		for(int x = 0; x <=7 ; x++)
			for(int y = 0; y <= 7; y++)
				if(tablero[x][y] == 0)
					return false;
		}
		catch(Exception ex)
		{
			System.out.println("esHoja");
		}
		return true;
	}
	private boolean esJugadaValida(int[][] tablero, int jugador, int comienzox, int comienzoy)
	{
		try {
		if(tablero[comienzox][comienzoy] != 0)
		{
			return false;
		}
		else {
			if( comienzox == 7)
				System.out.print("");
				tablero[comienzox][comienzoy] = jugador;
			int otrojugador = (jugador == 1)?-1:1;
								//   0    1      2    3       4      5       6       7
			int[][] direcciones = {{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1}};
			boolean flag = false;
			for(int i = 0; i < 8;  i++)
			{
				
				int x = comienzox;
				int y = comienzoy;
				x = x + direcciones[i][0];
				y = y + direcciones[i][1];
				if(x + direcciones[i][0] > -1 && x + direcciones[i][0] <8 && y + direcciones[i][1] > -1 && y + direcciones[i][1] <8) 
				if( estaEnTablero(x,y) && tablero[x][y] == otrojugador)
				{
					while(tablero[x][y] == otrojugador)
					{
						try {
							//if(x + direcciones[i][0] > -1 && x + direcciones[i][0] <8 && y + direcciones[i][1] > -1 && y + direcciones[i][1] <8) {
							x = x + direcciones[i][0];
							y = y + direcciones[i][1];
							tablero[x][y] = jugador;
						
						}
						catch(Exception ex)
						{
							System.out.println("Subif");
						}
					 
					}
					tablero[comienzox][comienzoy] = 0;
					if	( estaEnTablero(x,y) && tablero[x][y] == jugador)
						return true;
					
				}

			}
			tablero[comienzox][comienzoy] = 0;
			
		}
		}
		catch(Exception ex)
		{
			System.out.println(comienzox + " " + comienzoy);
			System.out.println("esJugadaValida " + ex.getMessage());
		}
		return false;
		//return flag;
	}
	private boolean estaEnTablero(int x, int y)
	{
		try {
		return (x > -1 && y < 8 && y > -1 && y <8);
		
		} catch (Exception e) {
			System.out.print("Es el tablero");
			return false;
		}
	}
	
	@Override
	public Celda devolverJugadaOptima(int[][] tablero) {
		
		int[][] tableroAux = new int[8][8];
		for(int x = 0; x < 8; x++)
			for(int y = 0; y<8; y++)
				tableroAux[x][y]=tablero[x][y];
		int[][] listadoJugadasValidas = obtenerJugadasValidas(tableroAux,1);
		
		
		
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
				return new Celda(Jugada[0], Jugada[1]);
			else
				if(puntajeSeleccion > puntaSeleccionActual)
				{
					mejorJugada = listadoJugadasValidas[i];
					puntajeSeleccion = puntaSeleccionActual;
				}
					
					
		}
		return new Celda(mejorJugada[0], mejorJugada[1]);
	}

}
