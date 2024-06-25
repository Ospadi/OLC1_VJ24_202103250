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
public class Asignacion extends Instruccion {
    private String id;
    private Instruccion index;
    private Instruccion valor;

    public Asignacion(String id, Instruccion index, Instruccion valor, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.index = index;
        this.valor = valor;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Object indiceVal = index.interpretar(arbol, tabla);
        if (indiceVal instanceof Errores) {
            return indiceVal;
        }

        if (!(indiceVal instanceof Integer)) {
            return new Errores("SEMANTICO", "El índice debe ser un entero", this.linea, this.col);
        }

        Object nuevoValor = valor.interpretar(arbol, tabla);
        if (nuevoValor instanceof Errores) {
            return nuevoValor;
        }

        Simbolo s = tabla.getVariable(this.id);
        if (s == null) {
            return new Errores("SEMANTICO", "Variable no existente", this.linea, this.col);
        }

        if (s.getValor() instanceof LinkedList) {
            LinkedList<Object> lista = (LinkedList<Object>) s.getValor();
            int idx = (int) indiceVal;
            if (idx >= lista.size() || idx < 0) {
                return new Errores("SEMANTICO", "Índice fuera de rango", this.linea, this.col);
            }

            lista.set(idx, nuevoValor);
        } else if (s.getValor() instanceof Object[]) {
            Object[] vector = (Object[]) s.getValor();
            int idx = (int) indiceVal;
            if (idx < 0 || idx >= vector.length) {
                return new Errores("SEMANTICO", "Índice fuera de los límites del vector", this.linea, this.col);
            }

            vector[idx] = nuevoValor;
        } else {
            return new Errores("SEMANTICO", "Variable no es una lista o vector", this.linea, this.col);
        }

        return null;
    }
}