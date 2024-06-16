/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;


import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;
import simbolo.*;

/**
 *
 * @author opadi
 */

public class Match extends Instruccion {
    
    private Instruccion condicion;
    private LinkedList<Case> list_Cases;    

    public Match(Instruccion condicion, LinkedList<Case> list_Cases, int linea, int columna) {
        super(new Tipo(tipoDato.VOID), linea, columna);
        this.condicion = condicion;
        this.list_Cases = list_Cases;
    }
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        var cond = this.condicion.interpretar(arbol, tabla);
        
        if (cond instanceof Errores) {
            return cond;
        }
        
        var newTabla = new tablaSimbolos(tabla);
        for (var caso : list_Cases) {
            if (caso.isResultado()) {
                var casoCond = caso.interpretar(arbol, tabla);
                if (casoCond instanceof Errores) {
                    return casoCond;
                }
                if (this.condicion.tipo.getTipo() == caso.tipo.getTipo()) {
                    if (cond instanceof String && casoCond instanceof String) {
                        if (((String)cond).equalsIgnoreCase((String)casoCond)) {
                            return ejecutarCaso(caso, arbol, newTabla);
                        }
                    } else if (cond.equals(casoCond)) {
                        return ejecutarCaso(caso, arbol, newTabla);
                    }
                }
            } else {
                return ejecutarCaso(caso, arbol, newTabla);
            }
        }
        return null;
    }
    
    private Object ejecutarCaso(Case caso, Arbol arbol, tablaSimbolos tabla) {
        for (var instruccion : caso.getInstrucciones()) {
            var result = instruccion.interpretar(arbol, tabla);
            if (result instanceof Errores) {
                return result;
            }
        }
        return null;
    }
}