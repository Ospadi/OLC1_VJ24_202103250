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
public class Case extends Instruccion {
    
    private Instruccion condicion;
    private LinkedList<Instruccion> instrucciones;
    private boolean resultado;

    public Case(Instruccion condicion, LinkedList<Instruccion> instrucciones, boolean resultado, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.condicion = condicion;
        this.instrucciones = instrucciones;
        this.resultado = resultado;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        
        var cond = this.condicion.interpretar(arbol, tabla);
        
        if (cond instanceof Errores) {
            return cond;
        }
        
        this.tipo.setTipo(this.condicion.tipo.getTipo());
        return cond;
    }

    // Getters y Setters
    public Instruccion getCondicion() {
        return condicion;
    }

    public void setCondicion(Instruccion condicion) {
        this.condicion = condicion;
    }

    public LinkedList<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public boolean isResultado() {
        return resultado;
    }

    public void setResultado(boolean resultado) {
        this.resultado = resultado;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return col;
    }

    public void setColumna(int columna) {
        this.col = columna;
    }
}