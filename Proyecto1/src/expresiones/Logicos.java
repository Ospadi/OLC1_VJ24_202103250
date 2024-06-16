/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expresiones;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.*;

/**
 *
 * @author opadi
 */
public class Logicos extends Instruccion {

    private Instruccion operando1;
    private Instruccion operando2;
    private OperadoresLogicos operacion;

    public Logicos(Instruccion operando1, Instruccion operando2, OperadoresLogicos operacion, int linea, int col) {
        super(new Tipo(tipoDato.BOOLEANO), linea, col);
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.operacion = operacion;
    }

    // Constructor para operador NOT
    public Logicos(Instruccion operando1, OperadoresLogicos operacion, int linea, int col) {
        super(new Tipo(tipoDato.BOOLEANO), linea, col);
        this.operando1 = operando1;
        this.operacion = operacion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Object opIzq = this.operando1.interpretar(arbol, tabla);
        if (opIzq instanceof Errores) {
            return opIzq;
        }

        if (operacion == OperadoresLogicos.NOT) {
            return !((Boolean) opIzq);
        }

        Object opDer = this.operando2.interpretar(arbol, tabla);
        if (opDer instanceof Errores) {
            return opDer;
        }

        switch (operacion) {
            case AND:
                return (Boolean) opIzq && (Boolean) opDer;
            case OR:
                return (Boolean) opIzq || (Boolean) opDer;
            case XOR:
                return (Boolean) opIzq ^ (Boolean) opDer;
            default:
                return new Errores("SEMANTICO", "Operador lógico inválido", this.linea, this.col);
        }
    }
}