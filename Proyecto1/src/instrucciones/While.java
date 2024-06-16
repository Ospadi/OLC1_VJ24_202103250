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
public class While extends Instruccion {

    private Instruccion condicion;
    private LinkedList<Instruccion> instrucciones;

    public While(Instruccion condicion, LinkedList<Instruccion> instrucciones, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.condicion = condicion;
        this.instrucciones = instrucciones;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var newTabla = new tablaSimbolos(tabla);

        while (true) {
            var cond = this.condicion.interpretar(arbol, newTabla);
            if (cond instanceof Errores) {
                return cond;
            }

            if (this.condicion.tipo.getTipo() != tipoDato.BOOLEANO) {
                return new Errores("SEMANTICO", "La condicion debe ser bool",
                        this.linea, this.col);
            }

            if (!(boolean) cond) {
                break;
            }

            var newTabla2 = new tablaSimbolos(newTabla);

            for (var i : this.instrucciones) {
                if (i instanceof Break) {
                    return null;
                }
                var resIns = i.interpretar(arbol, newTabla2);
                if (resIns instanceof Break) {
                    return null;
                }
            }
        }
        return null;
    }
}
