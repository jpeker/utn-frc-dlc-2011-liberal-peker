using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using RNDGenDistRLiberal;
using ArrivalsForQueuesRLiberal;
using System.Diagnostics;

namespace Ejercicio260Logica
{
    public class LogicaEjercicio260
    {
        #region Atributos
        #region relojYColas
        //Reloj
        public ProblemColumn reloj =
            new ProblemColumn(new List<object> { "Reloj", 0.0 }, 'd');
        
        //Columna random llegada paquete
        public ProblemColumn rnd_pck_val =
            new ProblemColumn(new List<object>
            { "Rnd Llegada Paquete", 0.0 }, 'd');

        //Columna random procesamiento paquete
        public ProblemColumn rnd_prp_val = 
            new ProblemColumn(new List<object> 
            { "Rnd Procesamiento Paquete", 0.0 }, 'd');

        //Columna evento actual
        public ProblemColumn eventoActual = 
            new ProblemColumn(new List<object> 
                        {"Evento Actual Ejecutandose", "ninguno"} ,'s');
        //Columna evento siguiente
        public ProblemColumn eventoSiguiente =
            new ProblemColumn(new List<object> { "Evento Actual Siguiente", "ninguno" }, 's');


        //Columna total paquetes informacion a ser procesados
        public ProblemColumn cantidadPaquetesSimulados
            = new ProblemColumn(new 
            List<object> { "Total Paquetes Simulados", 0 }, 'i');

        //Cola de paquetes de paquetes de informacion a ser procesados
        //Todos los paquetes vienen con el valor en falso descartado
        //Es decir no descartado.
        //Si el paquete es descartado se lo setea para contar los descartados
        
        //Cola de paquetes que se van almacenando en la memoria
        //y se van sacando para ser procesados
        public List<PaqueteInformacion> colaPaquetesInformacion =
            new List<PaqueteInformacion>();
       
        //Columna total paquetes informacion a ser procesados
        public List<PaqueteInformacion> colaPaquetesAProcesar
            = new List<PaqueteInformacion>(); 
            

        //Cola de descartados para contar los paquetes descartados
        //y en funcion de eso obtener el nivel de confianza
        public List<PaqueteInformacion> colaPaqueteInformacionDescartados =
           new List<PaqueteInformacion>();

        //Columna total paquetes informacion descartados
        public ProblemColumn cantidadPaquetesDescartados
            = new ProblemColumn(new List<object> 
            { "Total Paquetes Descardados", 0 }, 'i');
  
        //Cola de procesados para contar la cantidad de paquetes procesados
        //y en funcion de eso obtener el nivel de confianza
        public List<PaqueteInformacion> colaPaqueteInformacionProcesados =
           new List<PaqueteInformacion>();

        //Columna total paquetes informacion procesados
        public ProblemColumn cantidadPaquetesProcesados
            = new ProblemColumn(new List<object> 
            { "Total Paquetes Procesados", 0 }, 'i');

        #endregion relojYCola

        #region seteoDeEjecucion

        //Valor seteable del tamaño de cola es evolutivo para probar 
        //Si se descarta o no los paquetes
        public int limiteColaPaquetesInformacion = 0;

        //Segunda fila, de aqui en adelante puede variar la ejecucion
        //de los eventos. En la primera fila es necesario que llegue
        //al menos un paquete a procesar para tener algo en la cola.
        private bool secondRow = false;

        #endregion seteoDeEjecucion

        #region distribucionesDeProbabilidad

        // Llegada paquete
        public Event llegadaPaquete =
        new Event(new List<object> 
                    { "Llegada Paquete", 0.0, 0.0, 0.0, "Libre" });
        private CStrategy_NegExpDist
            rnd_pck = new CStrategy_NegExpDist(0.1);
        // 125 paquetes por minuto lamba = 60/125
        // cada 0.48 segundos llega un paquete
        
        // Procesamiento paquete
        public Event procesamientoPaquete =
        new Event(new List<object> 
                    { "Procesamiento Paquete", 0.0, 0.0, 0.0, "Libre" });
        private CStrategy_NegExpDist
            rnd_prp = new CStrategy_NegExpDist(0.9);
        
        // Cada paquete se procesa cada 0.002 segundos
        #endregion distribucionesDeProbabilidad
        #endregion Atributos

        // Metodo Llegada Paquetes


        public void packageArrival()
        {
            //Seteo valor del siguente evento de llegada
            llegadaPaquete.setNextRow(
            new List<object> { "Llegada Paquete",
                                rnd_pck.implementAleatoryNumberDistribution
                                (new Random()),
                                reloj.getObjectProblemColumn(1)
                                });
            //Seteo random
            rnd_pck_val.setObjectProblemColumn(
                1, llegadaPaquete.getObjectListEvent(1));
        }

        public void packageProcess()
        {
            //Seteo el valior del siguiente evento de procesamiento
            procesamientoPaquete.setNextRow(
            new List<object> { "Procesamiento Paquete",
                                rnd_prp.implementAleatoryNumberDistribution
                                (new Random()),
                                reloj.getObjectProblemColumn(1)
                                });
            //Seteo random
            rnd_prp_val.setObjectProblemColumn(1,
                procesamientoPaquete.getObjectListEvent(1));
        }

        //Metodos de procesamiento

        private void contarTotalPaquetes()
        {
            cantidadPaquetesSimulados.setObjectProblemColumn(1,
                (int)cantidadPaquetesProcesados.getObjectProblemColumn(1)
                + (int)cantidadPaquetesDescartados.getObjectProblemColumn(1));
        }

        private void paqueteEntrante()
        {
            //Seteo nuevo paquete entrante
            PaqueteInformacion paqueteInformacion = new PaqueteInformacion();
            if (colaPaquetesInformacion.Count <= limiteColaPaquetesInformacion)
            {
                //Si la cola no esta llena añado el paquete
                colaPaquetesAProcesar.RemoveAt(colaPaquetesAProcesar.Count - 1);
                colaPaquetesInformacion.Add(paqueteInformacion);
            }
            else
            {
                //Si la cola esta llena descarto el paquete
                colaPaqueteInformacionDescartados.Add(paqueteInformacion);
                //colaPaquetesAProcesar.
                //    setObjectProblemColumn(1, colaPaquetesInformacion.Count);
            }
        }

        private void paqueteProcesado()
        {
            //Añado el paquete procesado
            colaPaqueteInformacionProcesados.Add(colaPaquetesInformacion[0]);
            //Seteo la cantidad de paquetes procesados
            cantidadPaquetesProcesados.setObjectProblemColumn
                (1, colaPaqueteInformacionProcesados.Count);
            //Borro el paquete de la cola
            colaPaquetesInformacion.RemoveAt(0);
            //Seteo la cantidad de paquetes aun a procesar
            //colaPaquetesAProcesar.
            //        setObjectProblemColumn(1, colaPaquetesInformacion.Count);
        }

        //Eventos ejecucion
        private void executeCurrentEvent() { 
          if ((double)llegadaPaquete.getObjectListEvent(2)
              == (double) reloj.getObjectProblemColumn (1))
          {
              //Indico evento a ejecutar 
              eventoActual.setObjectProblemColumn
              (1, llegadaPaquete.getObjectListEvent(0).ToString());  
              paqueteEntrante();  
          }

          if ((double)procesamientoPaquete.getObjectListEvent(2)
              == (double)reloj.getObjectProblemColumn(1)) 
          {
              //Indico evento a ejecutar 
              eventoActual.setObjectProblemColumn
              (1, procesamientoPaquete.getObjectListEvent(0).ToString());
              paqueteProcesado();
          }
        }

        private void askNextEvent() {
            // Si el tiempo de llegada de paquete es menor que tiempo
            // de tiempo de procesamiento disparo evento llegada
            // paquete
            if (eventoActual.getObjectProblemColumn(1).
                ToString().Equals("LLegada Paquete"))
            {
                    //Disparo evento
                    packageArrival();
                    debugPAR();
                    //colaPaquetesAProcesar.
                    //setObjectProblemColumn(1, colaPaquetesInformacion.Count);
                    colaPaquetesAProcesar.Add(new 
                        PaqueteInformacion((double)
                        llegadaPaquete.getObjectListEvent(2)));
            }
            // Si hay paquetes en la cola para procesar y
            // Si el tiempo de procesamiento es que el tiempo de llegada
            // es menor que el tiempo de procesamiento del paquete
            // disparo el procesamiento del paquete
            if (colaPaquetesInformacion.Count > 0)
            {
                {
                    //Disparo evento
                    packageProcess();
                    debugPPR();

                }              
            }

            //ERROR

            //Cual es el evento a ejecutar y seteo con el valor del
            //evento a ejecutar

            //El sig es un llegada
            if ( colaPaquetesAProcesar.Count > 0 ||
                (double)llegadaPaquete.getObjectListEvent(2) <
                (double)procesamientoPaquete.getObjectListEvent(2)
                ) 
            {
                reloj.setObjectProblemColumn
                    (1, llegadaPaquete.getObjectListEvent(2));
               
            }

            //el siguiente es un proc
            if ( colaPaquetesInformacion.Count > 0 &&
                (double)procesamientoPaquete.getObjectListEvent(2)
                < 
                (double)llegadaPaquete.getObjectListEvent(2)
                )
            {    
                reloj.setObjectProblemColumn
                   (1, procesamientoPaquete.getObjectListEvent(2));
            }

        }

        #region LogicaDelPrograma
        public void execute_logic(double initTime, double endTime)
        {
            reloj.setObjectProblemColumn(1, initTime);
            Debug.WriteLine("Inicio");
            while (true)
            {
                limiteColaPaquetesInformacion = 100;
                
                if (secondRow == false)
                {
                    //Primera Fila.
                    Debug.WriteLine("Primera Fila");
                    packageArrival();
                    debugPAR();
                    //Muestro salidas
                    debugOutputs();
                    secondRow = true;
                    //packageArrival();
                    //debugPAR();
                    reloj.setObjectProblemColumn
                      (1, llegadaPaquete.getObjectListEvent(2));
                    //eventoActual.setObjectProblemColumn
                    //(1, llegadaPaquete.getObjectListEvent(0).ToString());
                    //debugOutputs();
                    //Seteo inicio de procesamiento en 0 
                    procesamientoPaquete.setObjectListEvent(2,(double)0);
                    //De ahora en mas todo el bucle se limita a next event
                }
                else
                {
                    //pregunto cual va ser el siguiente evento
                    //o un arrival o procesamiento de paquete
                    Debug.WriteLine("Segunda Fila");
                    executeCurrentEvent();
                    debugOutputs();
                    askNextEvent();
                    debugOutputs();
                }
            }
        }



        //Metodos de debug

        private void debugOutputs() {
            //DEBUG
            Debug.WriteLine(
            "Cantidad de paquetes totales = "
             + cantidadPaquetesSimulados.getObjectProblemColumn(1));
            Debug.WriteLine(
            "Cantidad de paquetes a procesar  = "
            + colaPaquetesAProcesar.Count);
            Debug.WriteLine(
            "Cantidad de paquetes descartados  = "
            + cantidadPaquetesDescartados.getObjectProblemColumn(1));
            Debug.WriteLine(
            "Cantidad de paquetes procesados  = "
            + cantidadPaquetesProcesados.getObjectProblemColumn(1));
            Debug.WriteLine(
            "Valor reloj = "
            + reloj.getObjectProblemColumn(1));
            Debug.WriteLine(
            "Evento actual ejecutandose = " 
            + eventoActual.getObjectProblemColumn(1));
        }
        private void debugPAR() {
            Debug.WriteLine(
                "Evento disparado PAR");
            Debug.WriteLine(
                "PAR Actual " + (double)llegadaPaquete.getObjectListEvent(3));
            Debug.WriteLine(
                "RNDPAR " + rnd_pck_val.getObjectProblemColumn(1));
            Debug.WriteLine(
                "PAR " + (double)llegadaPaquete.getObjectListEvent(2));
        }
        private void debugPPR() {
            Debug.WriteLine(
                "Evento disparado PPR");
            Debug.WriteLine(
                "PPR Actual " 
                + (double)procesamientoPaquete.getObjectListEvent(2));
            Debug.WriteLine(
                "RNDPPR " + rnd_prp_val.getObjectProblemColumn(1));
            Debug.WriteLine(
                "PPR " + (double)procesamientoPaquete.getObjectListEvent(2));
        }

        #endregion LogicaDelPrograma
    }
}
