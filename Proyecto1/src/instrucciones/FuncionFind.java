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

public class FuncionFind extends Instruccion {
    private String identificador;
    private Instruccion expresion;

    public FuncionFind(String identificador, Instruccion expresion, int linea, int col) {
        super(new Tipo(tipoDato.BOOLEANO), linea, col);
        this.identificador = identificador;
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Simbolo s = tabla.getVariable(this.identificador);
        if (s == null) {
            return new Errores("SEMANTICO", "Variable no existente", this.linea, this.col);
        }

        Object valor = expresion.interpretar(arbol, tabla);
        if (valor instanceof Errores) {
            return valor;
        }

        if (s.getValor() instanceof LinkedList) {
            LinkedList<Object> lista = (LinkedList<Object>) s.getValor();
            return lista.contains(valor);
        } else if (s.getValor() instanceof Object[]) {
            Object[] array = (Object[]) s.getValor();
            for (Object obj : array) {
                if (obj.equals(valor)) {
                    return true;
                }
            }
            return false;
        } else {
            return new Errores("SEMANTICO", "Tipo de dato no soportado para find", this.linea, this.col);
        }
    }
}
