/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.*;

/**
 *
 * @author opadi
 */
public class AsignacionVector2D extends Instruccion {
    private String id;
    private Instruccion indice1;
    private Instruccion indice2;
    private Instruccion valor;

    public AsignacionVector2D(String id, Instruccion indice1, Instruccion indice2, Instruccion valor, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.indice1 = indice1;
        this.indice2 = indice2;
        this.valor = valor;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var variable = tabla.getVariable(id);
        if (variable == null) {
            return new Errores("SEMANTICO", "Variable no existente", this.linea, this.col);
        }

        if (variable.esConstante()) {
            return new Errores("SEMANTICO", "No se puede asignar a una constante", this.linea, this.col);
        }

        var index1 = this.indice1.interpretar(arbol, tabla);
        var index2 = this.indice2.interpretar(arbol, tabla);
        if (index1 instanceof Errores || index2 instanceof Errores) {
            return index1 instanceof Errores ? index1 : index2;
        }

        int idx1 = (int) index1;
        int idx2 = (int) index2;

        var newValor = this.valor.interpretar(arbol, tabla);
        if (newValor instanceof Errores) {
            return newValor;
        }

        if (variable.getTipo().getTipo() != this.valor.tipo.getTipo()) {
            return new Errores("SEMANTICO", "Tipos erroneos en asignacion", this.linea, this.col);
        }

        Object[][] array = (Object[][]) variable.getValor();
        array[idx1][idx2] = newValor;
        variable.setValor(array);

        return null;
    }
}
