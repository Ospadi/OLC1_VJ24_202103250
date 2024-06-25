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
public class RemoveLista extends Instruccion {
    public String identificador;
    public Instruccion indice;

    public RemoveLista(String identificador, Instruccion indice, int linea, int col) {
        super(null, linea, col);
        this.identificador = identificador;
        this.indice = indice;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Object indiceVal = indice.interpretar(arbol, tabla);
        if (indiceVal instanceof Errores) {
            return indiceVal;
        }

        Simbolo s = tabla.getVariable(this.identificador);
        if (s == null) {
            return new Errores("SEMANTICO", "Variable no existente", this.linea, this.col);
        }

        LinkedList<Object> lista = (LinkedList<Object>) s.getValor();
        int idx = (int) indiceVal;
        if (idx >= lista.size() || idx < 0) {
            return new Errores("SEMANTICO", "Índice fuera de rango", this.linea, this.col);
        }

        return lista.remove(idx);
    }
}

