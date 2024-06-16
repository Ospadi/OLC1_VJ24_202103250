/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instrucciones;

import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;
import simbolo.*;

/**
 *
 * @author opadi
 */
public class If extends Instruccion {

    private Instruccion condicion;
    private LinkedList<Instruccion> instruccionesIf;
    private LinkedList<Instruccion> instruccionesElse;
    private If elseif;

    public If(Instruccion condicion, LinkedList<Instruccion> instruccionesIf, LinkedList<Instruccion> instruccionesElse, If elseif, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.condicion = condicion;
        this.instruccionesIf = instruccionesIf;
        this.instruccionesElse = instruccionesElse;
        this.elseif = elseif;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var cond = this.condicion.interpretar(arbol, tabla);
        if (cond instanceof Errores) {
            return cond;
        }

        if (this.condicion.tipo.getTipo() != tipoDato.BOOLEANO) {
            return new Errores("SEMANTICO", "Expresion de condición no es booleana", this.linea, this.col);
        }

        var nuevaTabla = new tablaSimbolos(tabla);

        if ((boolean) cond) {
            for (Instruccion instruccion : this.instruccionesIf) {
                var resultado = instruccion.interpretar(arbol, nuevaTabla);
                if (resultado instanceof Break || resultado instanceof Continue) {
                    return resultado;
                }
            }
        } else if (this.elseif != null) {
            var resultado = this.elseif.interpretar(arbol, nuevaTabla);
            if (resultado instanceof Break || resultado instanceof Continue) {
                return resultado;
            }
        } else if (this.instruccionesElse != null) {
            for (Instruccion instruccion : this.instruccionesElse) {
                var resultado = instruccion.interpretar(arbol, nuevaTabla);
                if (resultado instanceof Break || resultado instanceof Continue) {
                    return resultado;
                }
            }
        }

        return null;
    }
}