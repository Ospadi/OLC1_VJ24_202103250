/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.HashMap;
import java.util.LinkedList;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;

/**
 *
 * @author opadi
 */
public class Metodo extends Instruccion {
    public String id;
    public LinkedList<HashMap> parametros;
    public LinkedList<Instruccion> instrucciones;

    public Metodo(String id, LinkedList<HashMap> parametros, LinkedList<Instruccion> instrucciones, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.id = id;
        this.parametros = parametros;
        this.instrucciones = instrucciones;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        return null;
    }

    public Object ejecutar(Arbol arbol, tablaSimbolos tabla) {
        for (var i : this.instrucciones) {
            var resultado = i.interpretar(arbol, tabla);

            if (resultado instanceof Errores) {
                return resultado;
            }

            if (i instanceof Return) {
                var value = ((Return) i).interpretar(arbol, tabla);
                if (this.tipo.getTipo() == ((Return) i).tipo.getTipo()) {
                    return value;
                } else {
                    return new Errores("SEMANTICO", "El tipo de retorno no coincide", this.linea, this.col);
                }
            }

            if (resultado instanceof Return) {
                var value = ((Return) resultado).interpretar(arbol, tabla);
                if (this.tipo.getTipo() == ((Return) resultado).tipo.getTipo()) {
                    return value;
                } else {
                    return new Errores("SEMANTICO", "El tipo de retorno no coincide", this.linea, this.col);
                }
            }

            if (resultado != null) {
                return resultado;
            }
        }
        return null;
    }
}

