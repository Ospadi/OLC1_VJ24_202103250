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
public class AsignacionVar extends Instruccion {

    private String id;
    private Instruccion exp;

    public AsignacionVar(String id, Instruccion exp, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.exp = exp;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var variable = tabla.getVariable(id);
        if (variable == null) {
            return new Errores("SEMANTICO", "Variable no existente",
                    this.linea, this.col);
        }
        
        if (variable.esConstante()) {
            return new Errores("SEMANTICO", "No se puede asignar a una constante", this.linea, this.col);
        }

        var newValor = this.exp.interpretar(arbol, tabla);
        if (newValor instanceof Errores) {
            return newValor;
        }

        if (variable.getTipo().getTipo() != this.exp.tipo.getTipo()) {
            return new Errores("SEMANTICO", "Tipos erroneos en asignacion",
                    this.linea, this.col);
        }
        variable.setValor(newValor);
        return null;
    }

}
