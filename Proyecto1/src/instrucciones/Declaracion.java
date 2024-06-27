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

/**
 *
 * @author opadi
 */
public class Declaracion extends Instruccion {
    public String mutabilidad;
    public String identificador;
    public Instruccion valor;

    public Declaracion(String mutabilidad, String identificador, Instruccion valor, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.mutabilidad = mutabilidad;
        this.identificador = identificador;
        this.valor = valor;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Object valorInicial = null;

        if (valor != null) {
            valorInicial = this.valor.interpretar(arbol, tabla);
            if (valorInicial instanceof Errores) {
                return valorInicial;
            }

            if (this.valor.tipo.getTipo() != this.tipo.getTipo()) {
                return new Errores("SEMANTICO", "Tipos erroneos en la declaración", this.linea, this.col);
            }
        } else {
            switch (this.tipo.getTipo()) {
                case ENTERO:
                    valorInicial = 0;
                    break;
                case DECIMAL:
                    valorInicial = 0.0;
                    break;
                case BOOLEANO:
                    valorInicial = false;
                    break;
                case CARACTER:
                    valorInicial = '\u0000';
                    break;
                case CADENA:
                    valorInicial = "";
                    break;
                default:
                    valorInicial = null;
                    break;
            }
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