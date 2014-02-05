using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using RNDGenDistRLiberal;
using ArrivalsForQueuesRLiberal;
using System.Diagnostics;

namespace Ejercicio260Logica
{
    public class LogicaEjercicio260
    {
        #region Atributos
        #region relojYColas
        //RNDS
        Random r1 = new Random();
        Random r2 = new Random();
        
        //Reloj
        public ProblemColumn reloj =
            new ProblemColumn(new List<object> { "Reloj", 0.0 }, 'd');
        public ProblemColumn reloj_anterior =
            new ProblemColumn(new List<object> { "Reloj", 0.0 }, 'd');

        //Columna random raw llegada paquete
        public ProblemColumn rnd_llp_raw =
            new ProblemColumn(new
            List<object> { "Rnd Generado Llegada Paquete", 0.0 }, 'd');

        //Columna random llegada paquete
        public ProblemColumn rnd_llp_val =
            new ProblemColumn(new
            List<object> { "Rnd Llegada Paquete", 0.0 }, 'd');

        //Columna random raw procesamiento paquete
        public ProblemColumn rnd_prp_raw =
            new ProblemColumn(new
            List<object> { "Rnd Generado Procesamiento Paquete", 0.0 }, 'd');

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
             List<object> { "Estado Selector", "ninguno" }, 's');

        //Columna total paquetes simulados
        public ProblemColumn cantidadPaquetesSimulados
            = new ProblemColumn(new
            List<object> { "Total Paquetes Simulados", 0 }, 'i');

        //Cola de paquetes de paquetes de informacion a ser procesados
        //Todos los paquetes vienen con el valor en falso descartado
        //Es decir no descartado.
        //Si el paquete es descartado se lo setea para contar los descartados

        public ProblemColumn cantidadPaquetesASerProcesados
           = new ProblemColumn(new
           List<object> { "Total Paquetes A Ser Procesados", 0 }, 'i');

        //Columna total paquetes informacion procesados
        public ProblemColumn cantidadPaquetesProcesados
            = new ProblemColumn(new
            List<object> { "Total Paquetes Procesados", 0 }, 'i');

        //Columna total paquetes informacion descartados
        public ProblemColumn cantidadPaquetesDescartados
            = new ProblemColumn(new
            List<object> { "Total Paquetes Descardados", 0 }, 'i');

        //Cola de paquetes que se van almacenando en la memoria
        //y se van sacando para ser procesados
        public List<PaqueteInformacion> colaPaquetesASerProcesados =
            new List<PaqueteInformacion>();

        //Cola de descartados para contar los paquetes descartados
        //y en funcion de eso obtener el nivel de confianza
        public List<PaqueteInformacion> colaPaqueteInformacionDescartados =
           new List<PaqueteInformacion>();

        //Cola de procesados para contar la cantidad de paquetes procesados
        //y en funcion de eso obtener el nivel de confianza
        public List<PaqueteInformacion> colaPaqueteInformacionProcesados =
           new List<PaqueteInformacion>();

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
            rnd_llp = new CStrategy_NegExpDist(0.00);
        // 125 paquetes por minuto lamba = 60/125
        // cada 0.48 segundos llega un paquete

        // Procesamiento paquete
        public Event procesamientoPaquete =
        new Event(new
            List<object> { "Procesamiento Paquete", 0.0, 0.0, 0.0, "Libre" });
        private CStrategy_NegExpDist
            rnd_prp = new CStrategy_NegExpDist(0.00);
        // Se procesa un paquete cada 0.002
        // Cada paquete se procesa cada 0.002 segundos
        #endregion distribucionesDeProbabilidad
        #region Estadisticas
        public List<double> listaDeMediasPorSimulacion = new List<double>();
        public double valorMediaTotal = 0;
        public double valorVarianzaTotal = 0;
        //public int cantidadPaquetesASimularPorSimulacion = 0;
        public int nroDeSimulacionesARealizar = 0;
        public int gradosDeLibertad = 0;

        #endregion Estadisticas
        #endregion Atributos

        #region Metodos

        #region DisparadoresDeSiguienteEvento

        public void dispararLlegadaDePaquete()
        {
            //Seteo valor del siguente evento de llegada
            
            double valor = r1.NextDouble();
            rnd_llp_raw.setObjectProblemColumn(1, valor);
            llegadaPaquete.setNextRow(
            new List<object> { "Llegada Paquete",
                                rnd_llp.implementAleatoryNumberDistribution
                                (valor),
                                reloj.getObjectProblemColumn(1)
                                });
            //Seteo random de procesamiento de paquete
            rnd_llp_val.setObjectProblemColumn(
                1, llegadaPaquete.getObjectListEvent(1));
        }

        public void dispararProcesamientoDePaquete()
        {
            //Seteo el valor del siguiente evento de procesamiento
            
            double valor = r2.NextDouble();
            rnd_prp_raw.setObjectProblemColumn(1, valor);
            procesamientoPaquete.setNextRow(
            new List<object> { "Procesamiento Paquete",
                                rnd_prp.implementAleatoryNumberDistribution
                                (valor),
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
                dispararProcesamientoDePaquete();
                estadoServidor.setObjectProblemColumn(1, "Ocupado");
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
                }
                return;
            }
        }
        #endregion ProcesamientoDeEventoActual

        #region BucleDeEjecucionDeSimulacion
        public void ejecutarSimulacion(double lamdallp, double lamdaprp,int cantidadDePaquetesASimularPorSimulacion
            , int tamanioDeCola, ref DataGridView dg)
        {
            dg = addColumnsToDataGridView(dg);
            rnd_llp.setMedVal(lamdallp);
            rnd_prp.setMedVal(lamdaprp);
            limiteColaPaquetesInformacion = tamanioDeCola;
            iniciarNuevaSimulacion();
            estadoServidor.setObjectProblemColumn(1, "Libre");
            secondRow = false;
            
            while ((int)cantidadPaquetesSimulados.getObjectProblemColumn(1)
                    < cantidadDePaquetesASimularPorSimulacion)
            {
                if (secondRow == false)
                {
                    // Primera Fila.
                    //Debug.WriteLine("Primera Fila");
                    // Disparo llegada de paquete
                    dispararLlegadaDePaquete();
                    // Seteo inicio de procesamiento en 0 
                    procesamientoPaquete.setObjectListEvent(2, (double)0);
                    // Seteo el reloj
                    reloj_anterior.setObjectProblemColumn(1,
                        (double)reloj.getObjectProblemColumn(1));
                    reloj.setObjectProblemColumn
                      (1, llegadaPaquete.getObjectListEvent(2));
                    // De ahora en mas todo el bucle se limita al else
                    secondRow = true;
                }
                else
                {
                    //Debug.WriteLine("Segunda Fila");
                    // Evento a ejecutar ahora
                    executeCurrentEvent();
                    // Averiguar proximo evento
                    siguienteEvento();
                }
                // Cuento la cantidad de paquetes (descartados + procesados)
                contarTotalPaquetes();
                //Pasar datos gui
                dg.Rows.Add(new object[]
                           {eventoActual.getObjectProblemColumn(1), 
                            reloj_anterior.getObjectProblemColumn(1),
                            rnd_llp_raw.getObjectProblemColumn(1),
                            rnd_llp_val.getObjectProblemColumn(1),
                            llegadaPaquete.getObjectListEvent(2),
                            rnd_llp_raw.getObjectProblemColumn(1),
                            rnd_llp_val.getObjectProblemColumn(1),
                            procesamientoPaquete.getObjectListEvent(2),
                            estadoServidor.getObjectProblemColumn(1),
                            colaPaquetesASerProcesados.Count,
                            colaPaqueteInformacionProcesados.Count,
                            colaPaqueteInformacionDescartados.Count,
                            cantidadPaquetesSimulados.getObjectProblemColumn(1)
                           });
                //debugOutputs();
            }
            listaDeMediasPorSimulacion.Add(
            (double)((double)colaPaqueteInformacionDescartados.Count /
            (double)((int)cantidadPaquetesSimulados.getObjectProblemColumn(1))));
        }

        
        #endregion BucleDeEjecucionDeSimulacion

        #region BorrarListas
        private void iniciarNuevaSimulacion()
        {
            colaPaquetesASerProcesados.Clear();
            colaPaqueteInformacionDescartados.Clear();
            colaPaqueteInformacionProcesados.Clear();
            cantidadPaquetesSimulados.restartProblemColumm('i');
            cantidadPaquetesProcesados.restartProblemColumm('i');
            cantidadPaquetesDescartados.restartProblemColumm('i');
            reloj.restartProblemColumm('d');
            reloj_anterior.restartProblemColumm('d');
            rnd_llp_val.restartProblemColumm('d');
            rnd_prp_val.restartProblemColumm('d');
            rnd_llp_raw.restartProblemColumm('d');
            rnd_prp_raw.restartProblemColumm('d');
            eventoActual.restartProblemColumm('s');
            estadoServidor.restartProblemColumm('s');
            llegadaPaquete.restartEvent(new
            List<object> { 0.0, 0.0, 0.0 });
            procesamientoPaquete.restartEvent(new
            List<object> { 0.0, 0.0, 0.0 });
        }

        #endregion BorrarListas

        #region MetodosEstadisticos
        private double calcularVarianzaTotal()
        {
            double _valorVarianzaTotal = 0;
            //Realizo el calculo de la varianza total en funcion de las medias
            //muestrales obtenidas
            foreach (double media in listaDeMediasPorSimulacion)
            {
                _valorVarianzaTotal = _valorVarianzaTotal +
                Math.Pow((media -
                listaDeMediasPorSimulacion.Average()), 2);
            }
            _valorVarianzaTotal = _valorVarianzaTotal / (listaDeMediasPorSimulacion.Count - 1);
            return _valorVarianzaTotal;
        }
        public void aniadirEstadisticas(ref DataGridView dg)
        {
            valorMediaTotal = listaDeMediasPorSimulacion.Average();
            valorVarianzaTotal = calcularVarianzaTotal();
            double confIzq = valorMediaTotal + 2.33*
                Math.Sqrt(Math.Pow(valorVarianzaTotal,2)/listaDeMediasPorSimulacion.Count);
            double confDer = valorMediaTotal - 2.33 *
                Math.Sqrt(Math.Pow(valorVarianzaTotal, 2) / listaDeMediasPorSimulacion.Count);
            dg = addColumsToStatsDataGridView(dg);
            dg.Rows.Add(new object[]
                           {"-", 
                            valorMediaTotal,
                            valorVarianzaTotal,
                            confIzq,
                            confDer,
                           });
            foreach (double d in listaDeMediasPorSimulacion)
            {
                dg.Rows.Add(new object[]
                           {d, 
                            "-",
                            "-",
                            "-",
                            "-"
                           });
            }
            listaDeMediasPorSimulacion.Clear();
        }
        #endregion MetodosEstadisticos

        #region MetodosDataGridView
        private DataGridView addColumsToStatsDataGridView(DataGridView dg)
        {
            dg.Columns.Add(
                "Medias Simuladas",
                "Medias Simuladas");
            dg.Columns.Add(
                "Medias Total",
                "Medias Total");
            dg.Columns.Add(
                "Varianza Total",
                "Varianza Total");
            dg.Columns.Add(
                "Extremo Izq Intervarlo",
                "Extremo Izq Intervarlo");
            dg.Columns.Add(
                "Extremo Der Intervarlo",
                "Extremo Der Intervarlo");
            return dg;
        }

        private DataGridView addColumnsToDataGridView(DataGridView dg)
        {
            //Col particulares
            dg.Columns.Add(
                eventoActual.
                getObjectProblemColumn(0).ToString(),
                eventoActual.
                getObjectProblemColumn(0).ToString());
            dg.Columns.Add(
                reloj.
                getObjectProblemColumn(0).ToString(),
                reloj.
                getObjectProblemColumn(0).ToString());
            //Eventos
            dg.Columns.Add(
                rnd_llp_raw.
                getObjectProblemColumn(0).ToString(),
                rnd_llp_raw.
                getObjectProblemColumn(0).ToString());
            dg.Columns.Add(
                rnd_llp_val.
                getObjectProblemColumn(0).ToString(),
                rnd_llp_val.
                getObjectProblemColumn(0).ToString());
            dg.Columns.Add(
                llegadaPaquete.
                getObjectListEvent(0).ToString(),
                llegadaPaquete.
                getObjectListEvent(0).ToString());
            dg.Columns.Add(
                rnd_prp_raw.
                getObjectProblemColumn(0).ToString(),
                rnd_prp_raw.
                getObjectProblemColumn(0).ToString());
            dg.Columns.Add(
               rnd_prp_val.
                getObjectProblemColumn(0).ToString(),
                rnd_prp_val.
                getObjectProblemColumn(0).ToString());
            dg.Columns.Add(
                procesamientoPaquete.
                getObjectListEvent(0).ToString(),
                procesamientoPaquete.
                getObjectListEvent(0).ToString());
            //Col Particulares
            dg.Columns.Add(
                 estadoServidor.
                 getObjectProblemColumn(0).ToString(),
                 estadoServidor.
                 getObjectProblemColumn(0).ToString());
            dg.Columns.Add(
                cantidadPaquetesASerProcesados.
                getObjectProblemColumn(0).ToString(),
                cantidadPaquetesASerProcesados.
                getObjectProblemColumn(0).ToString());
            dg.Columns.Add(
                cantidadPaquetesProcesados.
                getObjectProblemColumn(0).ToString(),
                cantidadPaquetesProcesados.
                getObjectProblemColumn(0).ToString());
            dg.Columns.Add(
                cantidadPaquetesDescartados.
                getObjectProblemColumn(0).ToString(),
                cantidadPaquetesDescartados.
                getObjectProblemColumn(0).ToString());
            dg.Columns.Add(
                cantidadPaquetesSimulados.
                getObjectProblemColumn(0).ToString(),
                cantidadPaquetesSimulados.
                getObjectProblemColumn(0).ToString());
            return dg;
        }
        #endregion MetodosDataGridView
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