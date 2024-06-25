/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.*;
import java.util.LinkedList;

/**
 *
 * @author opadi
 */
public class DeclaracionLista extends Instruccion {
    public String identificador;

    public DeclaracionLista(Tipo tipo, String identificador, int linea, int col) {
        super(tipo, linea, col);
        this.identificador = identificador;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Simbolo s = new Simbolo(this.tipo, this.identificador, new LinkedList<>());

        boolean creacion = tabla.setVariable(s);
        if (!creacion) {
            return new Errores("SEMANTICO", "Variable ya existente", this.linea, this.col);
        }

        return null;
    }
}
