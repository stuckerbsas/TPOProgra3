package minmax;

/**
 *
 * @author Valentina
 */
public class listadoValido {
    
    public int longitud;
    public int[][] jugada= new int[60][2];
    
    
    public void inicializarLista(){
    longitud=0;
    jugada[0][0]=-1;
    jugada[0][1]=-1;
    
    }
    public void agregarElemento(int x, int y){
        longitud=longitud+1;
        jugada[longitud][0]=x;
        jugada[longitud][1]=y;
    }
    
}
