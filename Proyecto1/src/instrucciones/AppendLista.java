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

public class AppendLista extends Instruccion {
    public String identificador;
    public Instruccion valor;

    public AppendLista(String identificador, Instruccion valor, int linea, int col) {
        super(null, linea, col);
        this.identificador = identificador;
        this.valor = valor;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Object nuevoValor = valor.interpretar(arbol, tabla);
        if (nuevoValor instanceof Errores) {
            return nuevoValor;
        }

        Simbolo s = tabla.getVariable(this.identificador);
        if (s == null) {
            return new Errores("SEMANTICO", "Variable no existente", this.linea, this.col);
        }

        LinkedList<Object> lista = (LinkedList<Object>) s.getValor();

        if (!esTipoCompatible(lista, nuevoValor)) {
            return new Errores("SEMANTICO", "Tipo incompatible al agregar elemento", this.linea, this.col);
        }

        lista.add(nuevoValor);

        return null;
    }

    private boolean esTipoCompatible(LinkedList<Object> lista, Object nuevoValor) {
        if (lista.isEmpty()) {
            return true;
        }

        Object primerElemento = lista.getFirst();

        if (primerElemento == null || nuevoValor == null) {
            return primerElemento == nuevoValor;
        }

        return primerElemento.getClass().isAssignableFrom(nuevoValor.getClass());
    }
}
