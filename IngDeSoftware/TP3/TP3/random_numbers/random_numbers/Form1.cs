using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Windows.Forms.DataVisualization.Charting;
using System.Windows.Forms.DataVisualization.Charting.Utilities;
using System.Collections;

namespace random_numbers
{
    public partial class Form1 : Form
    {

        //ATRIBUTOS 
        private RandomNumber rnd; //necesito declararlo acá para poder seguir generando números
        private List<double> lstRandoms; //la lista de números aleatorios generada, la utilizo para cargar la grilla
        private List<decimal> lstIntervals; //la lista de los intervalos que desean luego representar graficamente
        private List<int> lstFrecuency; //la lista de las frecuencias que desean luego representar graficamente
        private Boolean grafico = false;
        private Hashtable parametros;
        private decimal mayor;
        
        //CONSTRUCTOR
        public Form1()
        {
            InitializeComponent();
            inicializar();
            
        }
        private void inicializar()
        {
            //datos hardcodeados para testear + rápido
            txtSemilla.Text = "47";
            txta.Text = "13";
            txtc.Text = "443";
            txtm.Text = "1000";
            //chart_InFrec.Enabled = false;
            //chart_InFrec.Text = "Vacio Presione Generar Grafico";
        }

        //EVENTOS
       
        
        private void txt_Validating(object sender, CancelEventArgs e)
        {
            //valido si es o no un entero positvo
            string error = validarNroEnteroPositivo(((System.Windows.Forms.TextBox)sender).Text) ? "" : "error tipo de dato";
            errorProvider1.SetError((System.Windows.Forms.TextBox)sender, error);
        }


        //METODOS

        private void cargarGrilla()
        {
            dataGridView1.RowCount = lstRandoms.Count;

            for (int i = 0; i < lstRandoms.Count; i++)
            {
                dataGridView1.Rows[i].Cells["Orden"].Value = i.ToString();
                dataGridView1.Rows[i].Cells["nr"].Value = lstRandoms[i].ToString();
            }
        }

        private void cargarGrilla2()
        {
            dataGridView2.RowCount = lstIntervals.Count;

            for (int i = 0; i < lstIntervals.Count; i++)
            {
                if (i==0)
                    dataGridView2.Rows[i].Cells["col1_Rango"].Value = "0 - " + lstIntervals[i].ToString();
                else
                    dataGridView2.Rows[i].Cells["col1_Rango"].Value = lstIntervals[i-1].ToString() + " - " + lstIntervals[i].ToString();                                
                dataGridView2.Rows[i].Cells["col2_Frec"].Value = lstFrecuency[i].ToString();
            }
        }
                
        //Metodo para validar que un string sea un nro entero positovo
        //usado en la validación de los parámetros
        private bool validarNroEnteroPositivo(string text)
        {
            double nr;
            if (double.TryParse(text, out nr))
            {
                if (nr > 0)
                {
                    return true;
                }
                return false;
            }
            return false;
        }

        private bool validarParametros()
        {
            bool return_value = true;
            string error_entero_positivo= "Debe ser entero positivo";
            if (!validarNroEnteroPositivo(txtCant.Text))
            {
                errorProvider1.SetError(txtCant, error_entero_positivo);
                return_value = false;
            }

            if (!validarNroEnteroPositivo(txtSemilla.Text))
            {
                errorProvider1.SetError(txtSemilla, error_entero_positivo);
                return_value = false;
            }

            if (!validarNroEnteroPositivo(txta.Text))
            {
                errorProvider1.SetError(txta, error_entero_positivo);
                return_value = false;
            }
            if (cmbMetodo.SelectedIndex == (int) Utils.metodo.Mixto)
            {
                if (!validarNroEnteroPositivo(txtc.Text))
                {
                    errorProvider1.SetError(txtc, error_entero_positivo);
                    return_value = false;
                }
            }

            if (!validarNroEnteroPositivo(txtm.Text))
            {
                errorProvider1.SetError(txtm, error_entero_positivo);
                return_value = false;
            }
            return return_value;
        }

        private void habilitarTodo()
        {
            txtSemilla.Enabled = true;
            txta.Enabled = true;
            txtc.Enabled = true;
            txtm.Enabled = true;
            txtCant.Enabled = true;
            btnGenerar.Enabled = true;
        }

        private void cmbMetodo_SelectedIndexChanged(object sender, EventArgs e)
        {
            string multiplicativo = "Multiplicativo";
            string congruencial = "Congruencial Mixto";
            string nativo = "Nativo del Lenguaje";
            if (cmbMetodo.SelectedIndex!=-1 && cmbMetodo.SelectedItem.ToString().CompareTo(multiplicativo) == 0)
            {
                //Elimino seleccione Método del combo
                //cmbMetodo.Items.Remove("<Seleccione Método>");
                //Muestro la Fórmula
                lblFormula.Text = "X(i+1) = [a*Xi] (mod m)";
                habilitarTodo();
                txtc.Text = "";
                txtc.Enabled = false;
            }

            if (cmbMetodo.SelectedIndex != -1 && cmbMetodo.SelectedItem.ToString().CompareTo(congruencial) == 0)
            {
                //Elimino seleccione Método del combo
                //cmbMetodo.Items.Remove("<Seleccione Método>");
                //Muestro la Fórmula
                lblFormula.Text = "X(i+1) = [a*Xi+C] (mod m)";
                habilitarTodo();
            }
            if (cmbMetodo.SelectedIndex != -1 && cmbMetodo.SelectedItem.ToString().CompareTo(nativo) == 0)
            {
                //Elimino seleccione Método del combo
                //cmbMetodo.Items.Remove("<Seleccione Método>");
                //Muestro la Fórmula
                lblFormula.Text = "";
                txtSemilla.Enabled = false;
                txta.Enabled = false;
                txtc.Enabled = false;
                txtm.Enabled = false;
                txtCant.Enabled = true;
                btnGenerar.Enabled = true;
            }
        }

        private void btnGenerar_Click_1(object sender, EventArgs e)
        {
            if (validarParametros())
            {

                //aplicar la fórmula recursivamente                                
                long c;
                long.TryParse(txtc.Text, out c);
                rnd = new RandomNumber(long.Parse(txtSemilla.Text), long.Parse(txta.Text), c, long.Parse(txtm.Text), cmbMetodo.SelectedIndex);                
                parametros=new Hashtable();
                //Estos parametros deberian ser tomados por pantalla pero ahora da fiaca jeje
                parametros.Add("promedio",10.5);
                parametros.Add("limInf", 5);
                parametros.Add("limSup", 15);
                parametros.Add("desviacion", 2);
                DistributionController dist = new DistributionController(cmbDistribucion.SelectedIndex, rnd, parametros);
                lstRandoms = dist.getNRandoms(int.Parse(txtCant.Text));                
                cargarGrilla();
                btnProximo.Enabled = true;
                txt_CantInt.Enabled = true;
                btn_GenInterv.Enabled = true;
            }
        }

        private void btnProximo_Click(object sender, EventArgs e)
        {
            if (validarParametros())
            {
                if (rnd == null)
                {
                    long c;
                    long.TryParse(txtc.Text, out c);
                    rnd = new RandomNumber(long.Parse(txtSemilla.Text), long.Parse(txta.Text), c, long.Parse(txtm.Text), cmbMetodo.SelectedIndex);
                    if (lstRandoms == null)
                    {
                        lstRandoms = new List<double>();
                    }
                }

                double nr = rnd.NextRandom();
                lstRandoms.Add(nr);
                cargarGrilla();
                txtCant.Text = ""+(Int32.Parse(txtCant.Text) + 1);
            }
        }

        private void btn_GenInterv_Click_1(object sender, EventArgs e)
        {
            if (validarNroEnteroPositivo(txt_CantInt.Text))
            {
                lstIntervals = new List<decimal>();
                lstFrecuency = new List<int>();
                decimal intervalos;
                decimal.TryParse(txt_CantInt.Text, out intervalos);
                //Busco mayor para dividir el intervalo
                mayor=0;
                for (int i = 0; i < lstRandoms.Count; i++)
                    if ((decimal)lstRandoms[i] > mayor )
                        mayor = (decimal)lstRandoms[i]; 
                decimal intervalo = (decimal) mayor / intervalos;
                //Definición de intervalos                

                for (int i = 0; i < intervalos; i++)
                {
                    if (i == 0)
                        lstIntervals.Add(intervalo);
                    else
                        lstIntervals.Add((decimal)lstIntervals[i - 1] + (decimal)intervalo);
                    lstFrecuency.Add(0);
                }
                int posicion;

                for (int j = 0; j < lstRandoms.Count; j++)
                {
                    posicion = 0;
                    
                    for (int k = 0; k < lstIntervals.Count; k++)
                    {
                        if ((decimal)lstRandoms[j] > lstIntervals[k])
                            posicion++;
                    }
                    lstFrecuency[posicion] = lstFrecuency[posicion]+1;
                }
                cargarGrilla2();
                btn_GenGraf.Enabled = true;
            }
            else if (lstRandoms.Count == 0)
            {
                string error_entero_positivo = "No hay números aleatorio generados";
                errorProvider1.SetError(txt_CantInt, error_entero_positivo);
            }
            else
            {
                string error_entero_positivo = "Debe ser entero positivo";
                errorProvider1.SetError(txt_CantInt, error_entero_positivo);
            }
        }

        private void btn_GenGraf_Click_1(object sender, EventArgs e)
        {
            // No borrar la linea
            //Nuevo gráfico Histograma (tab3)

            //Load data

            chart1.Series["RawData"].Points.DataBindY(lstRandoms);
            foreach (double item in lstRandoms)
            {
                chart1.Series["DataDistribution"].Points.AddXY(item, 1);
            }

            // Create a histogram series
            HistogramChartHelper histogramHelper = new HistogramChartHelper();
            histogramHelper.SegmentIntervalNumber = int.Parse(txt_CantInt.Text);
            histogramHelper.ShowPercentOnSecondaryYAxis = false;
            // NOTE: Interval width may be specified instead of interval number
            histogramHelper.SegmentIntervalWidth = (double)mayor/ Int32.Parse(txt_CantInt.Text);
            histogramHelper.CreateHistogram(chart1, "RawData", "Histogram");            

            // Set same X axis scale and interval in the single axis data distribution 
            // chart area as in the histogram chart area.
            chart1.ChartAreas["Default"].AxisX.Minimum = 0;
            chart1.ChartAreas["Default"].AxisX.Maximum = (double)mayor;
            chart1.ChartAreas["Default"].AxisX.Interval = chart1.ChartAreas["HistogramArea"].AxisX.Interval;
            chart1.ChartAreas["Default"].AxisX.Title = "Distribucion de frecuencia de los numeros aleatorios generados";

            chart1.ChartAreas[1].AxisX.Title = "Intervalos";
            chart1.ChartAreas[1].AxisY.Title = "Frecuencia";            
            


            //Grafico viejo (tab2)
            //chart_InFrec.ChartAreas[0].AxisX.Title = "Intervalos";
            //chart_InFrec.ChartAreas[0].AxisY.Title = "Frecuencia";
            //chart_InFrec.Series[0].Points.DataBindXY(lstIntervals, lstFrecuency);
            //chart_InFrec.Series[0].Label.Insert(0, "bolo");
            List<double> lstEsperados = new List<double>();
            double esperado =  double.Parse(txtCant.Text) / double.Parse(txt_CantInt.Text);            
            List<decimal> lstIntervalsWithZero = lstIntervals.ToList();
            lstIntervalsWithZero.Insert(0, 0);
            for (int i = 0; i <= lstFrecuency.Count; i++)
            {
                lstEsperados.Add(esperado); 
            }
            chart1.Series["esperado"].Points.Clear();
            chart1.Series["esperado"].Points.AddXY(0, esperado);
            chart1.Series["esperado"].Points.AddXY(1, esperado);            

            grafico = true;
            lbl_GSem.Text=txtSemilla.Text;
            lbl_Ga.Text = txta.Text;
            lbl_GC.Text = txtc.Text;
            lbl_Gm.Text = txtm.Text;
            lbl_GCantNros.Text = txtCant.Text;
            lbl_GCantInt.Text = txt_CantInt.Text;
            txt_esperado.Text = Math.Round(esperado, 3).ToString();
            if (cmbMetodo.Text == "Multiplicativo")
            {
                lbl_showC.Visible=false;
            }
            else { lbl_showC.Visible = true; }
         //   chart_InFrec.Visible = true;
        }


        private void tabCtrl_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (grafico == false && tabCtrl.SelectedIndex == 1)
            {
                tabCtrl.SelectedIndex = 0;
                MessageBox.Show("Error Primero Debe Generar el Grafico",
                "Error");
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            txt_CantInt.ResetText();
            txta.ResetText();
            txtc.ResetText();
            txtCant.ResetText();
            txtm.ResetText();
            txtSemilla.ResetText();
            txt_CantInt.Enabled=false;
            txta.Enabled=false;
            txtc.Enabled = false;
            txtCant.Enabled = false;
            txtm.Enabled = false;
            txtSemilla.Enabled = false;
            cmbMetodo.SelectedIndex = -1;
            dataGridView1.Rows.Clear();
            dataGridView2.Rows.Clear();
            tabCtrl.SelectedIndex = 0;
            grafico = false;
        //    chart_InFrec.Visible = false;
            btn_GenGraf.Enabled = false;
            btn_GenInterv.Enabled = false;            
            btnProximo.Enabled = false;
            btnGenerar.Enabled = false;
            lblFormula.Text = "Seleccione Un Metodo Para Ver la Formula";
            inicializar();
        }      




    }
}
