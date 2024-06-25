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
public class FuncionToString extends Instruccion {
    private Instruccion expresion;

    public FuncionToString(Instruccion expresion, int linea, int col) {
        super(new Tipo(tipoDato.CADENA), linea, col);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Object valor = expresion.interpretar(arbol, tabla);
        if (valor instanceof Errores) {
            return valor;
        }

        //if (valor instanceof Integer || valor instanceof Double || valor instanceof Boolean || valor instanceof Character || valor instanceof Struct) {
        if (valor instanceof Integer || valor instanceof Double || valor instanceof Boolean || valor instanceof Character) {
            return valor.toString();
        } else {
            return new Errores("SEMANTICO", "Tipo de dato no soportado para toString", this.linea, this.col);
        }
    }
}
