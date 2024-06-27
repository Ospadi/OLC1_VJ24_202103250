/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.*;
import java.util.LinkedList;
import java.util.Arrays;
/**
 *
 * @author opadi
 */
public class Print extends Instruccion {

    private Instruccion expresion;

    public Print(Instruccion expresion, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var resultado = this.expresion.interpretar(arbol, tabla);
        if (resultado instanceof Errores) {
            return resultado;
        }

        if (resultado instanceof Object[]) {
            arbol.Print(Arrays.deepToString((Object[]) resultado));
        } else if (resultado instanceof LinkedList) {
            arbol.Print(resultado.toString());
        } else {
            arbol.Print(resultado.toString());
        }

        return null;
    }
}