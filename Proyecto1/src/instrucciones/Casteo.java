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
public class Casteo extends Instruccion {
    private Tipo tipoDestino;
    private Instruccion expresion;

    public Casteo(Tipo tipoDestino, Instruccion expresion, int linea, int col) {
        super(tipoDestino, linea, col);
        this.tipoDestino = tipoDestino;
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Object valorExpresion = this.expresion.interpretar(arbol, tabla);
        if (valorExpresion instanceof Errores) {
            return valorExpresion;
        }

        switch (tipoDestino.getTipo()) {
            case tipoDato.ENTERO:
                return castToInteger(valorExpresion);
            case tipoDato.DECIMAL:
                return castToDouble(valorExpresion);
            case tipoDato.CARACTER:
                return castToChar(valorExpresion);
            default:
                return new Errores("SEMANTICO", "Casteo no válido a " + tipoDestino, this.linea, this.col);
        }
    }

    private Object castToInteger(Object valor) {
        if (valor instanceof Integer) {
            return valor;
        } else if (valor instanceof Double) {
            return ((Double) valor).intValue();
        } else if (valor instanceof Character) {
            return (int) ((Character) valor);
        } else {
            return new Errores("SEMANTICO", "No se puede convertir " + valor.getClass().getSimpleName() + " a int", this.linea, this.col);
        }
    }

    private Object castToDouble(Object valor) {
        if (valor instanceof Double) {
            return valor;
        } else if (valor instanceof Integer) {
            return ((Integer) valor).doubleValue();
        } else if (valor instanceof Character) {
            return (double) ((Character) valor);
        } else {
            return new Errores("SEMANTICO", "No se puede convertir " + valor.getClass().getSimpleName() + " a double", this.linea, this.col);
        }
    }

    private Object castToChar(Object valor) {
        if (valor instanceof Character) {
            return valor;
        } else if (valor instanceof Integer) {
            return (char) ((Integer) valor).intValue();
        } else {
            return new Errores("SEMANTICO", "No se puede convertir " + valor.getClass().getSimpleName() + " a char", this.linea, this.col);
        }
    }
}
