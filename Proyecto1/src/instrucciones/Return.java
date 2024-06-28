/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.*;

/**
 *
 * @author opadi
 */
public class Return extends Instruccion {
    private Instruccion valorRes;
    
    public Return(Instruccion valorRes, int linea, int col){
        super(new Tipo(tipoDato.VOID), linea, col);
        this.valorRes = valorRes;
    }
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla){
        if (valorRes == null){
            return this;
        }
        
        Object valor = valorRes.interpretar(arbol, tabla);
        if (valor instanceof Errores){
            return valor;
        }
        return valor; 
    }
    
    public Instruccion getValorRes(){
        return valorRes;
    }
}
