using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Ejercicio260Logica;

namespace ProgramaSimulacionEj260
{
    public partial class Form1 : Form
    {
        private LogicaEjercicio260 logicaSimEjer = new LogicaEjercicio260();
        public Form1()
        {
            InitializeComponent();
        }

        private void buttonRunSim_Click(object sender, EventArgs e)
        {
            // Añadir columnas a la grilla
            addColumns();
            // Ejecutar simulacion
            logicaSimEjer.execute_logic();
        }
        
        
        private void addColumns() {
            //Col particulares
            dataGridSim.Columns.Add(
                logicaSimEjer.eventoActual.
                getObjectProblemColumn(0).ToString(),
                logicaSimEjer.eventoActual.
                getObjectProblemColumn(0).ToString());
            dataGridSim.Columns.Add(
                logicaSimEjer.reloj.
                getObjectProblemColumn(0).ToString(),
                logicaSimEjer.reloj.
                getObjectProblemColumn(0).ToString());
            //Eventos
            dataGridSim.Columns.Add(
                logicaSimEjer.rnd_llp_val.
                getObjectProblemColumn(0).ToString(),
                logicaSimEjer.rnd_llp_val.
                getObjectProblemColumn(0).ToString());
            dataGridSim.Columns.Add(
                logicaSimEjer.llegadaPaquete.
                getObjectListEvent(0).ToString(),
                logicaSimEjer.llegadaPaquete.
                getObjectListEvent(0).ToString());
            dataGridSim.Columns.Add(
                logicaSimEjer.rnd_prp_val.
                getObjectProblemColumn(0).ToString(),
                logicaSimEjer.rnd_prp_val.
                getObjectProblemColumn(0).ToString());
            dataGridSim.Columns.Add(
                logicaSimEjer.procesamientoPaquete.
                getObjectListEvent(0).ToString(),
                logicaSimEjer.procesamientoPaquete.
                getObjectListEvent(0).ToString()); 
        }
    }
}
