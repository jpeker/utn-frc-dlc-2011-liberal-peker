using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using ArrivalsForQueuesRLiberal;
using RNDGenDistRLiberal;

namespace ProgramLogicEjercicio242
{
    public class ProgramLogic
    {
        #region Atributos

        #region primitivos
        
        private bool second_row = false;
        
        #endregion primitivos

        #region RelojYColas
        //Reloj
        private ProblemColumn reloj =
            new ProblemColumn(new List<object> {"Reloj", 0.0 }, 'd');
        
        //Cola grupos cantidad
        //Indice - Valor de cantidad de personas que llega por grupo

        private List<Grupo> colaGrupos = new List<Grupo>();
        #endregion RelojYColas

        #region distribucionesDeProbabilidad
        // Llegada Turistas
        private Event llegadaTuristas = 
        new Event(new List<object> { "Llegada Turistas", 0, 0, 0, "Libre" });
        private CStrategy_UniformDist 
            rnd_tur = new CStrategy_UniformDist(4, 6);

        // Guia retirar entrada
        private Event retirarEntradas = 
        new Event(new List<object> { "Retirar Entradas", 0, 0, 0, "Libre" });
        private CStrategy_UniformDist 
            rnd_retent = new CStrategy_UniformDist(15, 25);

        // Clientes Recorrer Negocio
        private Event recorrerNegocio =
        new Event(new List<object> { "Recorrer Negocio", 0, 0, 0, "Libre" });
        private CStrategy_UniformDist
            rnd_recneg = new CStrategy_UniformDist(10, 20);

        // Cliente Salida negocio
        private Event salidaNegocio =
        new Event(new List<object> { "Salida Negocio", 0, 0, 0, "Libre" });
        private CStrategy_UniformDist
            rnd_salidaneg = new CStrategy_UniformDist(0.13, 0.20);

        // Cliente compra negocio
        private Event compraNegocio =
        new Event(new List<object> { "Compra Negocio", 0, 0, 0, "Libre" });
        private CStrategy_UniformDist
            rnd_compneg = new CStrategy_UniformDist(0.25, 0.42);

        #endregion distribucionesProbabilidad
        
        #endregion Atributos

        #region MetodosDeEjecucionDeEventos

        // Metodo Llegada Turistas
        
        public void touristsArrival() {
             llegadaTuristas.setNextRow(
             new List<object> { "Llegada Turistas",
                                rnd_tur.implementAleatoryNumberDistribution
                                (new Random()),
                                reloj.getObjectProblemColumn(1)
                                });

            //Añade un grupo entre 16 y 21 personas (incluyendo guia)
            NumberVariable cantidadGrupo = new NumberVariable(new Random());
            colaGrupos.Add(new Grupo(cantidadGrupo.getValue()
                , (double)llegadaTuristas.getObjectListEvent(2)));
            // Cuando es la segunda fila del programa es 
            //la llegada del primer grupo
            if (second_row)
            {
                buscarEntradas();
                recorridaNegocioCli();
            }
        }

        //Metodo Retirar Entradas
        public void buscarEntradas() {
           retirarEntradas.setNextRow(
           new List<object> { "Retirar Entradas",
                                rnd_retent.implementAleatoryNumberDistribution
                                (new Random()),
                                reloj.getObjectProblemColumn(1)
                                });
           // Debo poner en false al guia del grupo n porque se va
           Grupo grupoN = (Grupo) colaGrupos
               [colaGrupos.Count-1];
           // Poner al guia es la pos 0 es constante 
           // asignando que el guia es el primer del grupo
           grupoN.getListaGrupo()[0].setIdle(true);
           grupoN.getListaGrupo()[0].
               setEndTime((double)retirarEntradas.getObjectListEvent(3)); 
        }

        //Metodo Recorrida Negocio Clientes
        public void recorridaNegocioCli()
        {
            recorrerNegocio.setNextRow(
            new List<object> { "Recorrida Negocio",
                                rnd_recneg.implementAleatoryNumberDistribution
                                (new Random()),
                                reloj.getObjectProblemColumn(1)
                                });
            // Debo poner en false al guia del grupo n porque se va
            Grupo grupoN = (Grupo)colaGrupos
                           [colaGrupos.Count - 1];
            grupoN.setEndTimeRecorrido
                ((double)recorrerNegocio.getObjectListEvent(3));
        }

        //Metodo Sale Negocio Cliente
        public void saleCliDeNegocio(Grupo GrupoM)
        {
            foreach (MiembroGrupo miembroM in GrupoM) 
            { 
                salidaNegocio.setNextRow(
                new List<object> { "Sale Negocio",
                                rnd_salidaneg.implementAleatoryNumberDistribution
                                (new Random()), reloj.getObjectProblemColumn(1)
                                });
                miembroM.setEndTime((double)salidaNegocio.getObjectListEvent(3));
            }
        }

        //Metodo Compra Negocio Cliente
        public void comprarCliEnNegocio(Grupo GrupoM)
        {
            int cantidadDeTurCompran = (int) (GrupoM.getListaGrupo().Count * 0.65);
            for( int i = 1; i < cantidadDeTurCompran + 1; i++) 
            {
            compraNegocio.setNextRow(
                new List<object> { "Compra Negocio",
                                rnd_compneg.implementAleatoryNumberDistribution
                                (new Random()), reloj.getObjectProblemColumn(1)
                                });
            GrupoM.getListaGrupo()[i].setEndTime((double)compraNegocio.getObjectListEvent(3));
            GrupoM.getListaGrupo()[i].setIdle(true);
            }
        }

        #endregion MetodosDeEjecucionDeEventos

        #region LogicaDelPrograma

        //Metodo que controla los eventos que se ejecutan
        private void askNextEvent() {
            //Segunda a n fila.
            
            #region ValoresMenoresDeEjecucionDeEventos
            
            //Buscar el menor valor de llegada de grupo
            double minTimeLlegadaGrupoM
                = colaGrupos.Min<Grupo>(x => x.getArrivalGrupo());

            //Buscar menor valor salida de guia
            //Creo un grupo de guias.

            Grupo grupoM = new Grupo();
            for (int i = 0; i < colaGrupos.Count; i++)
            {
                Grupo grupoN = (Grupo)colaGrupos[i];
                MiembroGrupo guiaMenor = grupoN.getListaGrupo()
                    .First(x => x.getIsGuia() == true);
                grupoM.getListaGrupo().Add(guiaMenor);
            }

            //Selecciono el guia con tiempo minimo de salida

            MiembroGrupo guiaGrupoM = grupoM.getListaGrupo()
                .First(x => x.getEndTime() ==
                    grupoM.getListaGrupo().Min(y => y.getEndTime()));
            
            // Busco el menor valor de salida de recorridos
            double minTimeRecorridoGrupoM
                = colaGrupos.Min<Grupo>(x => x.getEndTimeRecorrido());
            
            #endregion ValoresMenoresDeEjecucionDeEventos
            

            #region ChequeoDeValoresMinimosYEjecucionDeEventoPertinente
            
            //Llegada de turistas.
            if (
                 (double)minTimeLlegadaGrupoM <
                 (double)guiaGrupoM.getEndTime() &&
                 (double)minTimeLlegadaGrupoM <
                 (double)minTimeRecorridoGrupoM
                )
            {
                touristsArrival();
                //marcar reloj
            }

            // Guia ya retiro entradas

            if (
                (double)guiaGrupoM.getEndTime() <
                (double)minTimeLlegadaGrupoM &&
                (double)guiaGrupoM.getEndTime() <
                (double)minTimeRecorridoGrupoM
                )
            {
                guiaGrupoM.setIdle(false);
                //marcar reloj
            }

            // Clientes ya recorrieron afueras del local
            // y entran
            
            if (
                (double)minTimeRecorridoGrupoM <
                (double)minTimeLlegadaGrupoM &&
                (double)minTimeRecorridoGrupoM <
                (double)guiaGrupoM.getEndTime()
               )
            {
                //marcar reloj
                comprarCliEnNegocio(grupoM);
                saleCliDeNegocio(grupoM);
            }

            for (int i = 0; i < colaGrupos.Count; i++) 
            {
                Grupo grupoN = colaGrupos[i];
                foreach (MiembroGrupo miembroN in grupoN) {
                    if (!miembroN.getIdle) { 
                    grupoN.
                    }
                
                }
            
            }
            

        }
            #endregion ChequeoDeValoresMinimosYEjecucionDeEventoPertinente

        public void execute_logic(double initTime, double endTime)
        {
            reloj.setObjectProblemColumn(1, initTime);
            //bool first_row = false;
            //Por tiempo
            //while ((double)reloj.getObjectProblemColumn(1) < endTime) {
            //Por cantidad de grupos
            while (Grupo.cantidadTotalDeGrupos < 50)
            {
                if (second_row == false)
                {
                    //Deben configurarse primero la primera de las n tiradas
                    //a mano. Desde la segunda tirada en adelante todo se resuelve
                    //por inferencia.
                    
                    //Primera Fila.
                    touristsArrival();
                    reloj.setObjectProblemColumn(1, llegadaTuristas.getObjectListEvent(2));
                    second_row = true;
                }
                else
                {
                    //Llama a segunda fila en adelante
                    askNextEvent();
                }
            }
        }
        #endregion LogicaDelPrograma
    }
}
