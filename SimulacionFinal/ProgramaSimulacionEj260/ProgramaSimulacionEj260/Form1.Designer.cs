namespace ProgramaSimulacionEj260
{
    partial class Form1
    {
        /// <summary>
        /// Variable del diseñador requerida.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Limpiar los recursos que se estén utilizando.
        /// </summary>
        /// <param name="disposing">true si los recursos administrados se deben eliminar; false en caso contrario, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Código generado por el Diseñador de Windows Forms

        /// <summary>
        /// Método necesario para admitir el Diseñador. No se puede modificar
        /// el contenido del método con el editor de código.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            this.buttonRunSim = new System.Windows.Forms.Button();
            this.panel2 = new System.Windows.Forms.Panel();
            this.btnBorrar = new System.Windows.Forms.Button();
            this.lblError = new System.Windows.Forms.Label();
            this.btnEjecutarSigNroSim = new System.Windows.Forms.Button();
            this.lblNroSimActual = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.btnCorrerSimPorNro = new System.Windows.Forms.Button();
            this.txtLimiteCola = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.txtProcesamientoPaquete = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.txtLlegadaPaquete = new System.Windows.Forms.TextBox();
            this.txtCantPaquetes = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.txtNroSims = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.panel3 = new System.Windows.Forms.Panel();
            this.tabControlResultadosSimulacion = new System.Windows.Forms.TabControl();
            this.tabPage1 = new System.Windows.Forms.TabPage();
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.tabPage2 = new System.Windows.Forms.TabPage();
            this.dataGridView2 = new System.Windows.Forms.DataGridView();
            this.panel2.SuspendLayout();
            this.panel3.SuspendLayout();
            this.tabControlResultadosSimulacion.SuspendLayout();
            this.tabPage1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            this.tabPage2.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).BeginInit();
            this.SuspendLayout();
            // 
            // buttonRunSim
            // 
            this.buttonRunSim.Location = new System.Drawing.Point(922, 10);
            this.buttonRunSim.Name = "buttonRunSim";
            this.buttonRunSim.Size = new System.Drawing.Size(111, 36);
            this.buttonRunSim.TabIndex = 1;
            this.buttonRunSim.Text = "Correr Simulacion Completa";
            this.buttonRunSim.UseVisualStyleBackColor = true;
            this.buttonRunSim.Click += new System.EventHandler(this.buttonRunSim_Click);
            // 
            // panel2
            // 
            this.panel2.Controls.Add(this.btnBorrar);
            this.panel2.Controls.Add(this.lblError);
            this.panel2.Controls.Add(this.btnEjecutarSigNroSim);
            this.panel2.Controls.Add(this.lblNroSimActual);
            this.panel2.Controls.Add(this.label7);
            this.panel2.Controls.Add(this.btnCorrerSimPorNro);
            this.panel2.Controls.Add(this.txtLimiteCola);
            this.panel2.Controls.Add(this.label6);
            this.panel2.Controls.Add(this.label5);
            this.panel2.Controls.Add(this.txtProcesamientoPaquete);
            this.panel2.Controls.Add(this.label4);
            this.panel2.Controls.Add(this.txtLlegadaPaquete);
            this.panel2.Controls.Add(this.txtCantPaquetes);
            this.panel2.Controls.Add(this.label3);
            this.panel2.Controls.Add(this.txtNroSims);
            this.panel2.Controls.Add(this.label2);
            this.panel2.Controls.Add(this.label1);
            this.panel2.Controls.Add(this.buttonRunSim);
            this.panel2.Location = new System.Drawing.Point(17, 7);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(1312, 128);
            this.panel2.TabIndex = 2;
            // 
            // btnBorrar
            // 
            this.btnBorrar.Location = new System.Drawing.Point(922, 56);
            this.btnBorrar.Name = "btnBorrar";
            this.btnBorrar.Size = new System.Drawing.Size(111, 31);
            this.btnBorrar.TabIndex = 18;
            this.btnBorrar.Text = "Borrar Datos";
            this.btnBorrar.UseVisualStyleBackColor = true;
            this.btnBorrar.Click += new System.EventHandler(this.btnBorrar_Click);
            // 
            // lblError
            // 
            this.lblError.AutoSize = true;
            this.lblError.Location = new System.Drawing.Point(338, 103);
            this.lblError.Name = "lblError";
            this.lblError.Size = new System.Drawing.Size(28, 13);
            this.lblError.TabIndex = 17;
            this.lblError.Text = "error";
            // 
            // btnEjecutarSigNroSim
            // 
            this.btnEjecutarSigNroSim.Location = new System.Drawing.Point(1058, 52);
            this.btnEjecutarSigNroSim.Name = "btnEjecutarSigNroSim";
            this.btnEjecutarSigNroSim.Size = new System.Drawing.Size(126, 35);
            this.btnEjecutarSigNroSim.TabIndex = 16;
            this.btnEjecutarSigNroSim.Text = "Ejecutar Siguiente Nro de Simulacion";
            this.btnEjecutarSigNroSim.UseVisualStyleBackColor = true;
            // 
            // lblNroSimActual
            // 
            this.lblNroSimActual.AutoSize = true;
            this.lblNroSimActual.Location = new System.Drawing.Point(86, 70);
            this.lblNroSimActual.Name = "lblNroSimActual";
            this.lblNroSimActual.Size = new System.Drawing.Size(13, 13);
            this.lblNroSimActual.TabIndex = 15;
            this.lblNroSimActual.Text = "0";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(3, 70);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(77, 13);
            this.label7.TabIndex = 14;
            this.label7.Text = "Nro Sim Actual";
            // 
            // btnCorrerSimPorNro
            // 
            this.btnCorrerSimPorNro.Location = new System.Drawing.Point(1058, 8);
            this.btnCorrerSimPorNro.Name = "btnCorrerSimPorNro";
            this.btnCorrerSimPorNro.Size = new System.Drawing.Size(126, 35);
            this.btnCorrerSimPorNro.TabIndex = 13;
            this.btnCorrerSimPorNro.Text = "Correr Simulacion Por Nro De Simulacion";
            this.btnCorrerSimPorNro.UseVisualStyleBackColor = true;
            // 
            // txtLimiteCola
            // 
            this.txtLimiteCola.Location = new System.Drawing.Point(745, 26);
            this.txtLimiteCola.Name = "txtLimiteCola";
            this.txtLimiteCola.Size = new System.Drawing.Size(135, 20);
            this.txtLimiteCola.TabIndex = 12;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(742, 2);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(138, 13);
            this.label6.TabIndex = 11;
            this.label6.Text = "Limite De La Cola (1 - 1000)";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(449, 49);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(220, 13);
            this.label5.TabIndex = 10;
            this.label5.Text = "Procesamiento Paquete (lamba)  (0.00 - 0.99)";
            // 
            // txtProcesamientoPaquete
            // 
            this.txtProcesamientoPaquete.Location = new System.Drawing.Point(452, 67);
            this.txtProcesamientoPaquete.Name = "txtProcesamientoPaquete";
            this.txtProcesamientoPaquete.Size = new System.Drawing.Size(217, 20);
            this.txtProcesamientoPaquete.TabIndex = 9;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(449, 2);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(200, 13);
            this.label4.TabIndex = 8;
            this.label4.Text = "Llegada de Paquete (lamba) (0.00 - 0.99)";
            // 
            // txtLlegadaPaquete
            // 
            this.txtLlegadaPaquete.Location = new System.Drawing.Point(452, 26);
            this.txtLlegadaPaquete.Name = "txtLlegadaPaquete";
            this.txtLlegadaPaquete.Size = new System.Drawing.Size(217, 20);
            this.txtLlegadaPaquete.TabIndex = 7;
            // 
            // txtCantPaquetes
            // 
            this.txtCantPaquetes.Location = new System.Drawing.Point(151, 67);
            this.txtCantPaquetes.Name = "txtCantPaquetes";
            this.txtCantPaquetes.Size = new System.Drawing.Size(215, 20);
            this.txtCantPaquetes.TabIndex = 6;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(148, 51);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(218, 13);
            this.label3.TabIndex = 5;
            this.label3.Text = "Cantidad de Paquetes a Simular (100 - 1000)";
            // 
            // txtNroSims
            // 
            this.txtNroSims.Location = new System.Drawing.Point(151, 23);
            this.txtNroSims.Name = "txtNroSims";
            this.txtNroSims.Size = new System.Drawing.Size(215, 20);
            this.txtNroSims.TabIndex = 4;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(186, 2);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(144, 13);
            this.label2.TabIndex = 3;
            this.label2.Text = "Nro de simulaciones (2 - 100)";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(3, 23);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(87, 13);
            this.label1.TabIndex = 2;
            this.label1.Text = "Confianza = 0.99";
            // 
            // panel3
            // 
            this.panel3.Controls.Add(this.tabControlResultadosSimulacion);
            this.panel3.Location = new System.Drawing.Point(17, 141);
            this.panel3.Name = "panel3";
            this.panel3.Size = new System.Drawing.Size(1312, 519);
            this.panel3.TabIndex = 3;
            // 
            // tabControlResultadosSimulacion
            // 
            this.tabControlResultadosSimulacion.Controls.Add(this.tabPage1);
            this.tabControlResultadosSimulacion.Controls.Add(this.tabPage2);
            this.tabControlResultadosSimulacion.Location = new System.Drawing.Point(7, 14);
            this.tabControlResultadosSimulacion.Name = "tabControlResultadosSimulacion";
            this.tabControlResultadosSimulacion.SelectedIndex = 0;
            this.tabControlResultadosSimulacion.Size = new System.Drawing.Size(1276, 491);
            this.tabControlResultadosSimulacion.TabIndex = 0;
            // 
            // tabPage1
            // 
            this.tabPage1.Controls.Add(this.dataGridView1);
            this.tabPage1.Location = new System.Drawing.Point(4, 22);
            this.tabPage1.Name = "tabPage1";
            this.tabPage1.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage1.Size = new System.Drawing.Size(1268, 465);
            this.tabPage1.TabIndex = 0;
            this.tabPage1.Text = "Resultados Simulacion 1";
            this.tabPage1.UseVisualStyleBackColor = true;
            // 
            // dataGridView1
            // 
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Location = new System.Drawing.Point(7, 14);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.Size = new System.Drawing.Size(1256, 443);
            this.dataGridView1.TabIndex = 0;
            // 
            // tabPage2
            // 
            this.tabPage2.Controls.Add(this.dataGridView2);
            this.tabPage2.Location = new System.Drawing.Point(4, 22);
            this.tabPage2.Name = "tabPage2";
            this.tabPage2.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage2.Size = new System.Drawing.Size(1268, 465);
            this.tabPage2.TabIndex = 1;
            this.tabPage2.Text = "Resultados Simulacion 2";
            this.tabPage2.UseVisualStyleBackColor = true;
            // 
            // dataGridView2
            // 
            this.dataGridView2.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView2.Location = new System.Drawing.Point(7, 14);
            this.dataGridView2.Name = "dataGridView2";
            this.dataGridView2.Size = new System.Drawing.Size(1256, 443);
            this.dataGridView2.TabIndex = 1;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1354, 683);
            this.Controls.Add(this.panel3);
            this.Controls.Add(this.panel2);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "Form1";
            this.Text = "Ejercicio 260 Simulacion";
            this.panel2.ResumeLayout(false);
            this.panel2.PerformLayout();
            this.panel3.ResumeLayout(false);
            this.tabControlResultadosSimulacion.ResumeLayout(false);
            this.tabPage1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            this.tabPage2.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button buttonRunSim;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.TextBox txtCantPaquetes;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox txtNroSims;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox txtLlegadaPaquete;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.TextBox txtProcesamientoPaquete;
        private System.Windows.Forms.TextBox txtLimiteCola;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label lblNroSimActual;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Button btnCorrerSimPorNro;
        private System.Windows.Forms.Button btnEjecutarSigNroSim;
        private System.Windows.Forms.Label lblError;
        private System.Windows.Forms.Button btnBorrar;
        private System.Windows.Forms.Panel panel3;
        private System.Windows.Forms.TabControl tabControlResultadosSimulacion;
        private System.Windows.Forms.TabPage tabPage1;
        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.TabPage tabPage2;
        private System.Windows.Forms.DataGridView dataGridView2;
    }
}

