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
public class DeclaracionVector2D extends Instruccion {
    public String identificador;
    public LinkedList<LinkedList<Instruccion>> valores;
    public boolean esConstante;

    public DeclaracionVector2D(String identificador, Tipo tipo, LinkedList<LinkedList<Instruccion>> valores, int linea, int col) {
        super(tipo, linea, col);
        this.identificador = identificador;
        this.valores = valores;
        this.esConstante = false;
    }

    public DeclaracionVector2D(String identificador, Tipo tipo, LinkedList<LinkedList<Instruccion>> valores, boolean esConstante, int linea, int col) {
        super(tipo, linea, col);
        this.identificador = identificador;
        this.valores = valores;
        this.esConstante = esConstante;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Object[][] valorInicial = new Object[valores.size()][];
        for (int i = 0; i < valores.size(); i++) {
            LinkedList<Instruccion> fila = valores.get(i);
            valorInicial[i] = new Object[fila.size()];
            for (int j = 0; j < fila.size(); j++) {
                Object valor = fila.get(j).interpretar(arbol, tabla);
                if (valor instanceof Errores) {
                    return valor;
                }
                valorInicial[i][j] = valor;
            }
        }

        Simbolo s = new Simbolo(this.tipo, this.identificador, valorInicial);
        if (this.esConstante) {
            s.setConstante(true);
        }

        boolean creacion = tabla.setVariable(s);
        if (!creacion) {
            return new Errores("SEMANTICO", "Variable ya existente", this.linea, this.col);
        }

        return null;
    }
}
