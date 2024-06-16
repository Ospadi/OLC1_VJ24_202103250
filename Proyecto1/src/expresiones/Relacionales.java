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
public class Relacionales extends Instruccion {

    private Instruccion operando1;
    private Instruccion operando2;
    private OperadoresRelacionales operacion;

    public Relacionales(Instruccion operando1, Instruccion operando2, OperadoresRelacionales operacion, int linea, int col) {
        super(new Tipo(tipoDato.BOOLEANO), linea, col);
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.operacion = operacion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        if (this.operando1 == null || this.operando2 == null) {
            return new Errores("SEMANTICO", "Operando nulo en la operación relacional", this.linea, this.col);
        }

        Object opIzq = this.operando1.interpretar(arbol, tabla);
        if (opIzq instanceof Errores) {
            return opIzq;
        }
        Object opDer = this.operando2.interpretar(arbol, tabla);
        if (opDer instanceof Errores) {
            return opDer;
        }

        return switch (operacion) {
            case IGUAL ->
                this.igual(opIzq, opDer);
            case DIFERENTE ->
                this.diferente(opIzq, opDer);
            case MENOR ->
                this.menor(opIzq, opDer);
            case MENORQUE ->
                this.menorQue(opIzq, opDer);
            case MAYOR ->
                this.mayor(opIzq, opDer);
            case MAYORQUE ->
                this.mayorQue(opIzq, opDer);
            default ->
                new Errores("SEMANTICO", "Operador invalido", this.linea, this.col);
        };
    }

    private Object igual(Object op1, Object op2) {
        if (op1 instanceof String && op2 instanceof String) {
            return ((String) op1).equalsIgnoreCase((String) op2);
        }
        return op1.equals(op2);
    }

    private Object diferente(Object op1, Object op2) {
        if (op1 instanceof String && op2 instanceof String) {
            return !((String) op1).equalsIgnoreCase((String) op2);
        }
        return !op1.equals(op2);
    }

    private Object menor(Object op1, Object op2) {
        if (op1 instanceof Comparable && op2 instanceof Comparable) {
            return ((Comparable) op1).compareTo(op2) < 0;
        }
        return new Errores("SEMANTICO", "Tipos no comparables", this.linea, this.col);
    }

    private Object menorQue(Object op1, Object op2) {
        if (op1 instanceof Comparable && op2 instanceof Comparable) {
            return ((Comparable) op1).compareTo(op2) <= 0;
        }
        return new Errores("SEMANTICO", "Tipos no comparables", this.linea, this.col);
    }

    private Object mayor(Object op1, Object op2) {
        if (op1 instanceof Comparable && op2 instanceof Comparable) {
            return ((Comparable) op1).compareTo(op2) > 0;
        }
        return new Errores("SEMANTICO", "Tipos no comparables", this.linea, this.col);
    }

    private Object mayorQue(Object op1, Object op2) {
        if (op1 instanceof Comparable && op2 instanceof Comparable) {
            return ((Comparable) op1).compareTo(op2) >= 0;
        }
        return new Errores("SEMANTICO", "Tipos no comparables", this.linea, this.col);
    }
}