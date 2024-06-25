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
public class FuncionRound extends Instruccion {
    private Instruccion expresion;

    public FuncionRound(Instruccion expresion, int linea, int col) {
        super(new Tipo(tipoDato.ENTERO), linea, col);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Object valor = expresion.interpretar(arbol, tabla);
        if (valor instanceof Errores) {
            return valor;
        }

        if (!(valor instanceof Double || valor instanceof Integer)) {
            return new Errores("SEMANTICO", "Tipo de dato no soportado para round", this.linea, this.col);
        }

        if (valor instanceof Integer) {
            return valor;
        }

        Double numero = (Double) valor;
        return Math.round(numero);
    }
}