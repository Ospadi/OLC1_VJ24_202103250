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
public class AccesoVector2D extends Instruccion {
    private String id;
    private Instruccion indice1;
    private Instruccion indice2;

    public AccesoVector2D(String id, Instruccion indice1, Instruccion indice2, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.indice1 = indice1;
        this.indice2 = indice2;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var variable = tabla.getVariable(this.id);
        if (variable == null) {
            return new Errores("SEMANTICO", "Variable no existente", this.linea, this.col);
        }

        var index1 = this.indice1.interpretar(arbol, tabla);
        var index2 = this.indice2.interpretar(arbol, tabla);
        if (index1 instanceof Errores || index2 instanceof Errores) {
            return index1 instanceof Errores ? index1 : index2;
        }

        int idx1 = (int) index1;
        int idx2 = (int) index2;

        Object[][] array = (Object[][]) variable.getValor();
        this.tipo.setTipo(variable.getTipo().getTipo());
        return array[idx1][idx2];
    }
}

