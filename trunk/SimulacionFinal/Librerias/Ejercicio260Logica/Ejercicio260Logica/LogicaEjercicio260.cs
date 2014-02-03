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
        public ProblemColumn reloj_anterior =
            new ProblemColumn(new List<object> { "Reloj", 0.0 }, 'd');
        //Columna random llegada paquete
        public ProblemColumn rnd_llp_val =
            new ProblemColumn(new 
            List<object> { "Rnd Llegada Paquete", 0.0 }, 'd');

        //Columna random procesamiento paquete
        public ProblemColumn rnd_prp_val =
            new ProblemColumn(new 
            List<object> { "Rnd Procesamiento Paquete", 0.0 }, 'd');

        //Columna evento actual
        public ProblemColumn eventoActual =
            new ProblemColumn(new 
            List<object> { "Evento Actual Ejecutandose", "ninguno" }, 's');

        //Columna evento siguiente
        public ProblemColumn estadoServidor =
             new ProblemColumn(new 
             List<object> { "Estado Selector", "Libre" }, 's');
        
        //Columna total paquetes simulados
        public ProblemColumn cantidadPaquetesSimulados
            = new ProblemColumn(new
            List<object> { "Total Paquetes Simulados", 0 }, 'i');

        //Cola de paquetes de paquetes de informacion a ser procesados
        //Todos los paquetes vienen con el valor en falso descartado
        //Es decir no descartado.
        //Si el paquete es descartado se lo setea para contar los descartados

        //Cola de paquetes que se van almacenando en la memoria
        //y se van sacando para ser procesados
        public List<PaqueteInformacion> colaPaquetesASerProcesados =
            new List<PaqueteInformacion>();

        //Cola de descartados para contar los paquetes descartados
        //y en funcion de eso obtener el nivel de confianza
        public List<PaqueteInformacion> colaPaqueteInformacionDescartados =
           new List<PaqueteInformacion>();

        //Columna total paquetes informacion descartados
        public ProblemColumn cantidadPaquetesDescartados
            = new ProblemColumn(new 
            List<object> { "Total Paquetes Descardados", 0 }, 'i');

        //Cola de procesados para contar la cantidad de paquetes procesados
        //y en funcion de eso obtener el nivel de confianza
        public List<PaqueteInformacion> colaPaqueteInformacionProcesados =
           new List<PaqueteInformacion>();

        //Columna total paquetes informacion procesados
        public ProblemColumn cantidadPaquetesProcesados
            = new ProblemColumn(new 
            List<object> { "Total Paquetes Procesados", 0 }, 'i');

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
        new Event(new 
            List<object> { "Llegada Paquete", 0.0, 0.0, 0.0, "Libre" });
        private CStrategy_NegExpDist
            rnd_llp = new CStrategy_NegExpDist(0.48);
        // 125 paquetes por minuto lamba = 60/125
        // cada 0.48 segundos llega un paquete

        // Procesamiento paquete
        public Event procesamientoPaquete =
        new Event(new 
            List<object> { "Procesamiento Paquete", 0.0, 0.0, 0.0, "Libre" });
        private CStrategy_NegExpDist
            rnd_prp = new CStrategy_NegExpDist(0.38);

        // Cada paquete se procesa cada 0.002 segundos
        #endregion distribucionesDeProbabilidad
        #endregion Atributos

        #region Metodos

        #region DisparadoresDeSiguienteEvento

        public void dispararLlegadaDePaquete()
        {
            //Seteo valor del siguente evento de llegada
            llegadaPaquete.setNextRow(
            new List<object> { "Llegada Paquete",
                                rnd_llp.implementAleatoryNumberDistribution
                                (new Random()),
                                reloj.getObjectProblemColumn(1)
                                });
            //Seteo random de procesamiento de paquete
            rnd_llp_val.setObjectProblemColumn(
                1, llegadaPaquete.getObjectListEvent(1));
        }

        public void dispararProcesamientoDePaquete()
        {
            //Seteo el valor del siguiente evento de procesamiento
            procesamientoPaquete.setNextRow(
            new List<object> { "Procesamiento Paquete",
                                rnd_prp.implementAleatoryNumberDistribution
                                (new Random()),
                                reloj.getObjectProblemColumn(1)
                                });
            //Seteo random de procesamiento de paquete
            rnd_prp_val.setObjectProblemColumn(1,
                procesamientoPaquete.getObjectListEvent(1));
        }

        //Pregunto cual es el siguiente evento a ejecutarse
        private void siguienteEvento()
        {
            if (
                (double)llegadaPaquete.getObjectListEvent(2) <
                (double)procesamientoPaquete.getObjectListEvent(2)
                || 
                estadoServidor.
                getObjectProblemColumn(1).ToString().Equals("Libre")
                )
            {
                //Ejecuto llegada de paquete como siguiente evento
                reloj_anterior.setObjectProblemColumn(1,
                        (double)reloj.getObjectProblemColumn(1));
                reloj.
                    setObjectProblemColumn
                    (1, (double)llegadaPaquete.getObjectListEvent(2));
            }
            else 
            {
                //Ejecuto procesamiento de paquete como proximo evento
                reloj_anterior.setObjectProblemColumn(1,
                        (double)reloj.getObjectProblemColumn(1));
                reloj.
                    setObjectProblemColumn
                    (1, (double)procesamientoPaquete.getObjectListEvent(2));
            }
        }

        #endregion DisparadoresDeSiguienteEvento

        #region ProcesamientoDeEventoActual

        private void contarTotalPaquetes()
        {
            cantidadPaquetesSimulados.setObjectProblemColumn(1,
                colaPaqueteInformacionProcesados.Count 
                + colaPaqueteInformacionDescartados.Count);
        }

        private void paqueteEntrante()
        {
            //Seteo nuevo paquete entrante
            //agrego un paquete a la cola paqueta a procesar
            PaqueteInformacion paqueteInformacion = new PaqueteInformacion();
            colaPaquetesASerProcesados.Add(paqueteInformacion);
            //si la cola es cero, 
            //proceso el paquete y calculo 
            //cuando finaliza el procesamiento del paquete.
            if (estadoServidor.getObjectProblemColumn(1).Equals("Libre"))
            {
                //if (colaPaquetesASerProcesados.Count > 0) 
                //{
                //    colaPaquetesASerProcesados.RemoveAt(0);
                //}
                // proceso el unico paquete
                dispararProcesamientoDePaquete();
                estadoServidor.setObjectProblemColumn(1, "Ocupado");
                //return;
            }
            if (colaPaquetesASerProcesados.Count <=
                limiteColaPaquetesInformacion)
            {
                //Si la cola no esta llena añado el paquete
               // colaPaquetesASerProcesados.Add(paqueteInformacion);
                // hacer validacion cuando se llena la cola
            }
            else
            {
                //Si la cola esta llena descarto el paquete
                colaPaqueteInformacionDescartados.Add(paqueteInformacion);
                colaPaquetesASerProcesados
                    .RemoveAt(colaPaquetesASerProcesados.Count - 1);
            }
        }

        private void paqueteProcesado()
        {
            //Añado el paquete procesado
            colaPaqueteInformacionProcesados.Add(new PaqueteInformacion());
            colaPaquetesASerProcesados.RemoveAt(0);
        }

        //Evento en ejecucion
        private void executeCurrentEvent()
        {
            if ((double)llegadaPaquete.getObjectListEvent(2)
                == (double)reloj.getObjectProblemColumn(1))
            {
                //Indico el nombre del evento que se esta ejecutando 
                eventoActual.setObjectProblemColumn
                (1, llegadaPaquete.getObjectListEvent(0).ToString());
                //1- calcular llegar del proximo paquete.
                dispararLlegadaDePaquete();
                //2- mover paquete para ser procesado.
                paqueteEntrante();
                return;
            }

            if ((double)procesamientoPaquete.getObjectListEvent(2)
                == (double)reloj.getObjectProblemColumn(1))
            {
                //Indico evento a ejecutar 
                eventoActual.setObjectProblemColumn
                (1, procesamientoPaquete.getObjectListEvent(0).ToString());
                paqueteProcesado();
                //hay algun paquete esperando en la cola a ser procesado
                if (colaPaquetesASerProcesados.Count > 0)
                {
                    //colaPaquetesASerProcesados.RemoveAt(0);
                    dispararProcesamientoDePaquete();
                }
                else 
                {
                    estadoServidor.setObjectProblemColumn(1, "Libre");
                    //Nuevalinea
                    //dispararLlegadaDePaquete();
                }
                
                return;
            }
        }
        #endregion ProcesamientoDeEventoActual

        #region BucleDeEjecucionDeSimulacion
        public void execute_logic(double initTime = 0.0, double endTime = 0.0, int tamaniodeCola = 0)
        {
            reloj.setObjectProblemColumn(1, initTime);
            Debug.WriteLine("Inicio");
            //Seteo el limite de la cola
            limiteColaPaquetesInformacion = 1;
            while ( (int) cantidadPaquetesSimulados
                    .getObjectProblemColumn(1) < 1000
                    )
            {
                if (secondRow == false)
                {
                    //Primera Fila.
                    Debug.WriteLine("Primera Fila");
                    //Disparo llegada de paquete
                    dispararLlegadaDePaquete();
                    //Muestro salidas
                    //debugOutputs();
                    //Seteo inicio de procesamiento en 0 
                    procesamientoPaquete.setObjectListEvent(2, (double)0);
                    //Seteo el reloj
                    reloj_anterior.setObjectProblemColumn(1, 
                        (double) reloj.getObjectProblemColumn(1));
                    reloj.setObjectProblemColumn
                      (1, llegadaPaquete.getObjectListEvent(2));
                    //De ahora en mas todo el bucle se limita a next event
                    secondRow = true;
                }
                else
                {
                    Debug.WriteLine("Segunda Fila");
                    // evento a ejecutar ahora
                    executeCurrentEvent();
                    // Averiguar proximo evento
                    siguienteEvento();
                }
                //Cuento la cantidad de paquetes (descartados + procesados)
                contarTotalPaquetes();
                debugOutputs();
            }
        }
        #endregion BucleDeEjecucionDeSimulacion

        #region MetodosDeDebug
        private void debugOutputs()
        {
            Debug.WriteLine(
            "Cantidad de paquetes totales = "
             + cantidadPaquetesSimulados.getObjectProblemColumn(1));
            Debug.WriteLine(
            "Cantidad de paquetes descartados  = "
            + colaPaqueteInformacionDescartados.Count);
            Debug.WriteLine(
            "Cantidad de paquetes procesados  = "
            + colaPaqueteInformacionProcesados.Count);
            Debug.WriteLine(
            "Actual Valor reloj = "
            + reloj_anterior.getObjectProblemColumn(1));
            Debug.WriteLine(
            "Evento actual ejecutandose = "
            + eventoActual.getObjectProblemColumn(1));
            Debug.WriteLine(
            "Cantidad de elementos en cola "
            + colaPaquetesASerProcesados.Count);
            Debug.WriteLine(
            "Estado servidor "
            + estadoServidor.getObjectProblemColumn(1).ToString());
            Debug.WriteLine(
            " ");
        }
        //Debugs no usados
        private void debugPAR()
        {
            Debug.WriteLine(
                "Evento disparado PAR");
            Debug.WriteLine(
                "PAR Actual " + (double)llegadaPaquete.getObjectListEvent(3));
            Debug.WriteLine(
                "RNDPAR " + rnd_llp_val.getObjectProblemColumn(1));
            Debug.WriteLine(
                "PAR " + (double)llegadaPaquete.getObjectListEvent(2));
        }
        private void debugPPR()
        {
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
        #endregion MetodosDeDebug

        #endregion Metodos
    }
}