/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.*;

/**
 *
 * @author opadi
 */
public class Decremento extends Instruccion {

    private String variable;

    public Decremento(String variable, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.variable = variable;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Simbolo simbolo = tabla.getVariable(this.variable);
        if (simbolo == null) {
            return new Errores("SEMANTICO", "Variable '" + this.variable + "' no definida", this.linea, this.col);
        }

        switch (simbolo.getTipo().getTipo()) {
            case tipoDato.ENTERO:
                int valorEntero = (int) simbolo.getValor() - 1;
                simbolo.setValor(valorEntero);
                return null;
            case tipoDato.DECIMAL:
                double valorDecimal = (double) simbolo.getValor() - 1.0;
                simbolo.setValor(valorDecimal);
                return null;
            default:
                return new Errores("SEMANTICO", "Decremento no válido para el tipo de dato de la variable '" + this.variable + "'", this.linea, this.col);
        }
    }
}