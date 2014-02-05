using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Ejercicio260Logica;
using System.Globalization;

namespace ProgramaSimulacionEj260
{
    public partial class Form1 : Form
    {
        private LogicaEjercicio260 logicaSimEjer = new LogicaEjercicio260();
        public Form1()
        {
            InitializeComponent();
            lblError.Text = "";
            //addColumnsToDataGridView();
            tabControlResultadosSimulacion.TabPages.RemoveAt(0);
            tabControlResultadosSimulacion.TabPages.RemoveAt(0);
        }

        private void buttonRunSim_Click(object sender, EventArgs e)
        {
            // Añadir columnas a la grilla
            if (validations())
            {
                // Ejecutar simulacion
                btnBorrar.Enabled = false;
                btnCorrerSimPorNro.Enabled = false;
                btnEjecutarSigNroSim.Enabled = false;
                buttonRunSim.Enabled = false;
                lblError.Text = "Datos ingresados correctamente Ejecutandose simulacion";
                // logicaSimEjer.execute_logic();
                int cantidadTabs = Convert.ToInt32(txtNroSims.Text);
                for (int i = 0; i < cantidadTabs; i++)
                {
                    tabControlResultadosSimulacion.TabPages.Add(
                        "Resultados Simulacion " + Convert.ToString(i+1));
                    DataGridView dg = new DataGridView();
                    dg.Height = 443;
                    dg.Width = 1256;
                    dg.Location = new Point(7, 14);
                    dg.ReadOnly = true;
                    dg.MultiSelect = false;
                    dg.AllowUserToAddRows = false;
                    dg.AllowUserToDeleteRows = false;
                    addColumnsToDataGridView(dg);
                    tabControlResultadosSimulacion.TabPages[i].Controls.Add(dg);
                }
            }
            btnBorrar.Enabled = true;
            btnCorrerSimPorNro.Enabled = true;
            btnEjecutarSigNroSim.Enabled = true;
            buttonRunSim.Enabled = true;
        }

        private bool validations()
        {
            //Valido que los campos sean correctos
            string test = txtNroSims.Text;
            bool validate = false;
            try
            {
                if (Convert.ToInt32(test) >= 2 && Convert.ToInt32(test) <= 100)
                {
                    test = txtCantPaquetes.Text;
                    if (Convert.ToInt32(test) >= 100 && Convert.ToInt32(test) <= 1000)
                    {
                        test = txtLimiteCola.Text;
                        if (Convert.ToInt32(test) >= 1 && Convert.ToInt32(test) <= 1000)
                        {
                            test = txtLlegadaPaquete.Text;
                            double value = Convert.ToDouble(test.Replace(",", "."), CultureInfo.InvariantCulture);
                            if (value < (double)1.00)
                            {
                                test = txtProcesamientoPaquete.Text;
                                if (Convert.ToDouble(test.Replace(",", "."), CultureInfo.InvariantCulture) < (double)1.00)
                                {
                                    validate = true;
                                }
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                validate = false;
            }
            if (!validate)
            {
                clearTxt();
                lblError.Text = "Error verifique los datos validos que se encuentran indicados arriba con los rangos validos, solo validos valores numericos";
            }
            return validate;
        }

        private void clearTxt()
        {
            txtCantPaquetes.ResetText();
            txtLimiteCola.ResetText();
            txtLlegadaPaquete.ResetText();
            txtNroSims.ResetText();
            txtProcesamientoPaquete.ResetText();
        }
        private void addColumnsToDataGridView(DataGridView dg)
        {
            //Col particulares
            dg.Columns.Add(
                logicaSimEjer.eventoActual.
                getObjectProblemColumn(0).ToString(),
                logicaSimEjer.eventoActual.
                getObjectProblemColumn(0).ToString());
            dg.Columns.Add(
                logicaSimEjer.reloj.
                getObjectProblemColumn(0).ToString(),
                logicaSimEjer.reloj.
                getObjectProblemColumn(0).ToString());
            //Eventos
            dg.Columns.Add(
                logicaSimEjer.rnd_llp_raw.
                getObjectProblemColumn(0).ToString(),
                logicaSimEjer.rnd_llp_raw.
                getObjectProblemColumn(0).ToString());
            dg.Columns.Add(
                logicaSimEjer.rnd_llp_val.
                getObjectProblemColumn(0).ToString(),
                logicaSimEjer.rnd_llp_val.
                getObjectProblemColumn(0).ToString());
            dg.Columns.Add(
                logicaSimEjer.llegadaPaquete.
                getObjectListEvent(0).ToString(),
                logicaSimEjer.llegadaPaquete.
                getObjectListEvent(0).ToString());
            dg.Columns.Add(
                logicaSimEjer.rnd_prp_raw.
                getObjectProblemColumn(0).ToString(),
                logicaSimEjer.rnd_prp_raw.
                getObjectProblemColumn(0).ToString());
            dg.Columns.Add(
                logicaSimEjer.rnd_prp_val.
                getObjectProblemColumn(0).ToString(),
                logicaSimEjer.rnd_prp_val.
                getObjectProblemColumn(0).ToString());
            dg.Columns.Add(
                logicaSimEjer.procesamientoPaquete.
                getObjectListEvent(0).ToString(),
                logicaSimEjer.procesamientoPaquete.
                getObjectListEvent(0).ToString());
            //Col Particulares
            dg.Columns.Add(
                logicaSimEjer.cantidadPaquetesASerProcesados.
                getObjectProblemColumn(0).ToString(),
                logicaSimEjer.cantidadPaquetesASerProcesados.
                getObjectProblemColumn(0).ToString());
            dg.Columns.Add(
                logicaSimEjer.cantidadPaquetesProcesados.
                getObjectProblemColumn(0).ToString(),
                logicaSimEjer.cantidadPaquetesProcesados.
                getObjectProblemColumn(0).ToString());
            dg.Columns.Add(
                logicaSimEjer.cantidadPaquetesDescartados.
                getObjectProblemColumn(0).ToString(),
                logicaSimEjer.cantidadPaquetesDescartados.
                getObjectProblemColumn(0).ToString());
            dg.Columns.Add(
                logicaSimEjer.cantidadPaquetesSimulados.
                getObjectProblemColumn(0).ToString(),
                logicaSimEjer.cantidadPaquetesSimulados.
                getObjectProblemColumn(0).ToString());
        }

        private void btnBorrar_Click(object sender, EventArgs e)
        {
            clearTxt();
            tabControlResultadosSimulacion.TabPages.Clear();
            lblError.Text = "";
        }


    }
}
