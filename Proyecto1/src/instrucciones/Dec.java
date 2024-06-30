/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.Arbol;
import simbolo.Simbolo;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

/**
 *
 * @author opadi
 */
public class Dec extends Instruccion {

    public String identificador;
    public Instruccion valor;

    public Dec(String identificador, Instruccion valor, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.identificador = identificador;
        this.valor = valor;
    }

    public Dec(String identificador, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.identificador = identificador;
        this.valor = null;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var valorInterpretado = (this.valor == null) ? this.valoresDefault() : this.valor.interpretar(arbol, tabla);

        if (valorInterpretado instanceof Errores) {
            return valorInterpretado;
        }

        if (this.valor != null) {
            if (this.valor.tipo.getTipo() != this.tipo.getTipo()) {
                return new Errores("SEMANTICO", "Tipos erroneos", this.linea, this.col);
            }
        }

        Simbolo s = new Simbolo(this.tipo, this.identificador, valorInterpretado);

        boolean creacion = tabla.setVariable(s);
        if (!creacion) {
            return new Errores("SEMANTICO", "Variable ya existente", this.linea, this.col);
        }

        return null;
    }

    public Object valoresDefault() {
        return switch (this.tipo.getTipo()) {
            case tipoDato.BOOLEANO ->
                true;
            case tipoDato.CADENA ->
                "";
            case tipoDato.CARACTER ->
                '\u0000';
            case tipoDato.ENTERO ->
                0;
            case tipoDato.DECIMAL ->
                0.0;
            default ->
                null;
        };
    }
}
