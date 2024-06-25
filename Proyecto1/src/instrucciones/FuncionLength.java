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
public class FuncionLength extends Instruccion {
    private Instruccion expresion;

    public FuncionLength(Instruccion expresion, int linea, int col) {
        super(new Tipo(tipoDato.ENTERO), linea, col);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Object valor = expresion.interpretar(arbol, tabla);
        if (valor instanceof Errores) {
            return valor;
        }

        if (valor instanceof LinkedList) {
            return ((LinkedList<?>) valor).size();
        } else if (valor instanceof String) {
            return ((String) valor).length();
        } else if (valor instanceof Object[]) {
            return ((Object[]) valor).length;
        } else {
            return new Errores("SEMANTICO", "Tipo de dato no soportado para length", this.linea, this.col);
        }
    }
}
