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
public class DeclaracionVector extends Instruccion {
    public String mutabilidad;
    public String identificador;
    public LinkedList<Instruccion> valores;

    public DeclaracionVector(String mutabilidad, String identificador, Tipo tipo, LinkedList<Instruccion> valores, int linea, int col) {
        super(tipo, linea, col);
        this.mutabilidad = mutabilidad;
        this.identificador = identificador;
        this.valores = valores;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Object[] valorInicial = new Object[valores.size()];

        for (int i = 0; i < valores.size(); i++) {
            Object valor = valores.get(i).interpretar(arbol, tabla);
            if (valor instanceof Errores) {
                return valor;
            }
            valorInicial[i] = valor;
        }

        Simbolo s = new Simbolo(this.tipo, this.identificador, valorInicial);

        if (this.mutabilidad.equals("const")) {
            s.setConstante(true);
        }

        boolean creacion = tabla.setVariable(s);
        if (!creacion) {
            return new Errores("SEMANTICO", "Variable ya existente", this.linea, this.col);
        }

        return null;
    }
}