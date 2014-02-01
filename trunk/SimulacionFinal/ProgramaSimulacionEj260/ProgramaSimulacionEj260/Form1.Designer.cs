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
            this.panel1 = new System.Windows.Forms.Panel();
            this.dataGridSim = new System.Windows.Forms.DataGridView();
            this.buttonRunSim = new System.Windows.Forms.Button();
            this.panel2 = new System.Windows.Forms.Panel();
            this.panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridSim)).BeginInit();
            this.panel2.SuspendLayout();
            this.SuspendLayout();
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.dataGridSim);
            this.panel1.Location = new System.Drawing.Point(17, 88);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(770, 141);
            this.panel1.TabIndex = 0;
            // 
            // dataGridSim
            // 
            this.dataGridSim.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridSim.Location = new System.Drawing.Point(13, 14);
            this.dataGridSim.Name = "dataGridSim";
            this.dataGridSim.Size = new System.Drawing.Size(740, 105);
            this.dataGridSim.TabIndex = 0;
            // 
            // buttonRunSim
            // 
            this.buttonRunSim.Location = new System.Drawing.Point(13, 16);
            this.buttonRunSim.Name = "buttonRunSim";
            this.buttonRunSim.Size = new System.Drawing.Size(111, 36);
            this.buttonRunSim.TabIndex = 1;
            this.buttonRunSim.Text = "Correr Simulacion";
            this.buttonRunSim.UseVisualStyleBackColor = true;
            this.buttonRunSim.Click += new System.EventHandler(this.buttonRunSim_Click);
            // 
            // panel2
            // 
            this.panel2.Controls.Add(this.buttonRunSim);
            this.panel2.Location = new System.Drawing.Point(17, 7);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(770, 70);
            this.panel2.TabIndex = 2;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(799, 252);
            this.Controls.Add(this.panel2);
            this.Controls.Add(this.panel1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "Form1";
            this.Text = "Ejercicio 260 Simulacion";
            this.panel1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridSim)).EndInit();
            this.panel2.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.DataGridView dataGridSim;
        private System.Windows.Forms.Button buttonRunSim;
        private System.Windows.Forms.Panel panel2;
    }
}

