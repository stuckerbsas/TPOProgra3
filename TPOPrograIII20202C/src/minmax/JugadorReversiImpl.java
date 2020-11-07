package minmax;

import reversi.Celda;
import reversi.JugadorReversi;

public class JugadorReversiImpl implements JugadorReversi{
    public Celda devolverJugadaOptima(int[][] tablero){
		
		listadoValido listadoJugadasValidas = obtenerJugadasValidas(tablero,1);
		
		int[][] tableroAux = new int[8][8];
		
		int[] Jugada = new int[2];
                //La jugada 0 es la de inicializacion por lo que no me sirve
                Jugada[0]=listadoJugadasValidas.jugada[1][0];
                Jugada[1]=listadoJugadasValidas.jugada[1][1];
		tableroAux=girarFichas(tablero,1,Jugada[0],Jugada[1]);
		int puntajeSeleccion = calcularOpcion(tableroAux,1,0,Jugada);
		int puntaSeleccionActual = puntajeSeleccion;
		int[] mejorJugada = Jugada;
                if (listadoJugadasValidas.longitud>1){
		for(int i = 1; i < listadoJugadasValidas.longitud; i++){
			Jugada[0]=listadoJugadasValidas.jugada[i][0];
                        Jugada[1]=listadoJugadasValidas.jugada[i][1];
			tableroAux=girarFichas(tablero,1,Jugada[0],Jugada[1]);
			puntajeSeleccion = calcularOpcion(tableroAux,1,0,Jugada);
			if(puntajeSeleccion == 7)
				return new Celda(Jugada[0], Jugada[1]);
			else{
				if(puntajeSeleccion > puntaSeleccionActual){
					mejorJugada[0]=listadoJugadasValidas.jugada[i][0];
                                        mejorJugada[1]=listadoJugadasValidas.jugada[i][1];
					puntaSeleccionActual=puntajeSeleccion;
				}
			}	
					
		}
                }
		return new Celda(mejorJugada[0], mejorJugada[1]);
    }
    private int[][] girarFichas(int[][] tableroInicial, int jugador,int posicionx, int posiciony){
        //copio el tablero para que no me quede ligado al mismo espacio de memoria
        int[][] tableroNuevo= new int[8][8];
        for(int k=0;k<=7;k++){
            for(int h=0;h<=7;h++){			
		tableroNuevo[h][k]=tableroInicial[h][k];
		}
	}
	tableroNuevo[posicionx][posiciony] = jugador;	
        int[][] direcciones = {{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1}};
	int otrojugador = (jugador == 1)? -1:1;	
		for(int i = 0; i <= 7;  i++){
			int x = posicionx;
			int y = posiciony;
                        x = x + direcciones[i][0];
			y = y + direcciones[i][1];
			if( estaEnTablero(x,y) && tableroNuevo[x][y] == otrojugador){
				while(estaEnTablero(x,y) && tableroNuevo[x][y] == otrojugador){
					x = x + direcciones[i][0];
					y = y + direcciones[i][1];
				}
				if(estaEnTablero(x,y) && tableroNuevo[x][y] ==jugador){
                                    x = x - direcciones[i][0];
                                    y = y - direcciones[i][1];
                                    tableroNuevo[x][y] =jugador;
                                    while(tableroNuevo[x][y] == otrojugador){
					x = x - direcciones[i][0];
					y = y - direcciones[i][1];
                                        tableroNuevo[x][y] =jugador;
                                    }
                                }
					
			}
                }
        return tableroNuevo;
    }

	private listadoValido obtenerJugadasValidas(int[][] tablero, int jugador){
		listadoValido jugadasValidas = new listadoValido();
                jugadasValidas.inicializarLista();
		for(int x=0;x<=7;x++){
			for(int y=0;y<=7;y++){			
				if(esJugadaValida(tablero,jugador,x,y)){
					jugadasValidas.agregarElemento(x, y);
				}
			}
		}
		return jugadasValidas;
	}
	private boolean esJugadaValida(int[][] tablero, int jugador, int comienzox, int comienzoy){
		if(tablero[comienzox][comienzoy] != 0){
			return false;
		}
				
		tablero[comienzox][comienzoy] = jugador;
		int otrojugador = (jugador == 1)?-1:1;
		
		int[][] direcciones = {{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1}};
		
		for(int i = 0; i <= 7;  i++){
			int x = comienzox;
			int y = comienzoy;
                        x = x + direcciones[i][0];
			y = y + direcciones[i][1];
			if( estaEnTablero(x,y) && tablero[x][y] == otrojugador){
                            while(estaEnTablero(x,y) && tablero[x][y] == otrojugador){
				x = x + direcciones[i][0];
				y = y + direcciones[i][1];
                            }
                            if(estaEnTablero(x,y) && tablero[x][y] ==jugador){
                          	tablero[comienzox][comienzoy] =0;
				return true;
                            }
                       }
                }
                tablero[comienzox][comienzoy] =0;
		return false;
	}
	private int calcularOpcion(int[][]tablero,int jugador, int nroHijo, int[] jugada){
            int proximojugador;
            int valor;
            if((espaciosVacios(tablero)>=32 && nroHijo>5)|| esHoja(tablero)){
			return calcularPuntaje(tablero,jugada);
		}
            else{
			while(!esHoja(tablero)){
				proximojugador = (jugador == 1)? -1:1;
				int[] puntajeDeJugadas = crearListadoPuntajes(tablero,proximojugador,nroHijo +1);
			if(proximojugador == 1){
                                valor=max(puntajeDeJugadas);
				return valor;
				}
			else{
                                valor=min(puntajeDeJugadas);
				return valor;
				}	
				
			}
		}
		return 0;
        }
	
	
	private int[] crearListadoPuntajes(int[][] tablero, int jugador, int nroHijo){
		int[][] tableroAux = new int[8][8];
		listadoValido listadoJugadasValidas = obtenerJugadasValidas(tablero,jugador);
                if (listadoJugadasValidas.longitud>1){
                    int[] puntajesDeJugadas = new int[listadoJugadasValidas.longitud];
                    for(int i =1; i<listadoJugadasValidas.longitud; i++){
			int[] jugada= new int[2];
                        jugada[0]= listadoJugadasValidas.jugada[i][0];
                        jugada[1]= listadoJugadasValidas.jugada[i][1];
			tableroAux=girarFichas(tablero,jugador,jugada[0],jugada[1]);
			puntajesDeJugadas[i] = calcularOpcion(tableroAux, jugador, nroHijo,jugada);
			
                    }
                    return puntajesDeJugadas;
                }
                int [] puntajesDeJugadas={0};
                return puntajesDeJugadas;
	}
	
	private int max(int[] puntajesDeJugadas){
		int maxPuntaje = -10;
		for(int x =0; x<puntajesDeJugadas.length;x++){
			if(maxPuntaje < puntajesDeJugadas[x])
				maxPuntaje = puntajesDeJugadas[x];
		}
		return maxPuntaje;
	}
	
	private int min(int[] puntajesDeJugadas){
		int minPuntaje = 10;
		for(int x =0; x<puntajesDeJugadas.length;x++){
			if(minPuntaje > puntajesDeJugadas[x])
				minPuntaje = puntajesDeJugadas[x];
		}
		return minPuntaje;
	}
	
	private int espaciosVacios(int[][] tablero){
            int contadorVacios = 0;
		for(int x = 0; x<=7; x++){
			for(int y =0;y<=7;y++){
                            if(tablero[x][y]==0){
                                contadorVacios=contadorVacios+1;
                            }
                        }
                }
            return contadorVacios;
        }
	private int calcularPuntaje(int[][] tablero, int[] jugada){
            int contadorPC = 0;
            int contadorJugador = 0;
            if(esHoja(tablero)){
		for(int x = 0; x<=7; x++){
			for(int y =0;y<=7;y++){
				if(tablero[x][y] == 1)
					contadorPC++;
				else{
					if(tablero[x][y] == -1)
						contadorJugador++;
				}
                        }
                }
                if(contadorPC > contadorJugador)
			return 7;
                    else{
			if(contadorPC< contadorJugador)
				return 0;
			else
				return 1;
                    }
                }
            else{
                    if (estaTurquesa(jugada))
                        return 4;
                    else{ 
                        if (estaAzul(jugada))
                            return 3;
                        else{ 
                            if (estaAmarillo(jugada))
                                return 6;
                            else{
                                if (estaRojo(jugada))
                                    return 5;
                                else
                                    return 2;
                            }
                        }
                    }
            }
        }
    private boolean estaTurquesa(int[] jugada){
        int[][] turquesa={{0,2},{0,3},{0,4},{0,5},
                          {2,0},{3,0},{4,0},{5,0},
                          {2,7},{3,7},{4,7},{5,7},
                          {7,2},{7,3},{7,4},{7,5}};
        for (int x=0; x<turquesa.length;x++){
            if(jugada[0]==turquesa[x][0] && jugada[1]==turquesa[x][1]){
                return true;
            }
        }
        return false;
    }
    private boolean estaAzul(int[] jugada){
        int[][] azul={{1,2},{1,3},{1,4},{1,5},
                          {2,1},{3,1},{4,1},{5,1},
                          {2,6},{3,6},{4,6},{5,6},
                          {6,2},{6,3},{6,4},{6,5}};
        for (int x=0; x<azul.length;x++){
            if(jugada[0]==azul[x][0] && jugada[1]==azul[x][1]){
                return true;
            }
        }
        return false;
    }
    private boolean estaAmarillo(int[] jugada){
        int[][] amarillo={{0,1},{0,7},{7,0},{7,7}};
        for (int x=0; x<amarillo.length;x++){
            if(jugada[0]==amarillo[x][0] && jugada[1]==amarillo[x][1]){
                return true;
            }
        }
        return false;
    }
   private boolean estaRojo(int[] jugada){
        int[][] rojo={{0,1},{1,0},{1,1},
                          {0,6},{1,6},{1,7},
                          {6,0},{6,1},{7,1},
                          {6,6},{6,7},{7,6}};
        for (int x=0; x<rojo.length;x++){
            if(jugada[0]==rojo[x][0] && jugada[1]==rojo[x][1]){
                return true;
            }
        }
        return false;
    }
	//HAY QUE AGREGAR QUE NO HAYA JUGADAS VALIDAS
        private boolean esHoja(int[][] tablero ){
            
            for(int x=0; x <=7; x++){
			for(int y = 0; y <= 7; y++){
				if(tablero[x][y] == 0)
					return false;
                        }
		}
            return true;
        }
		
	
	
	private boolean estaEnTablero(int x, int y)	{
                boolean variable=(x >=0 && x <=7 && y >=0 && y <=7);
		return (variable);
	}
}
