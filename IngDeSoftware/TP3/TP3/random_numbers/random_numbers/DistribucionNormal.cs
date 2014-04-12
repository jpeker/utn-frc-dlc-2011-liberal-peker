using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace random_numbers
{
    class DistribucionNormal : IDistribucion
    {
        private RandomNumber rnd;
        private double promedio;
        private double desviacion;
        public DistribucionNormal(RandomNumber rnd, double promedio, double desviacion)
        {            
            this.rnd = rnd;
            this.promedio= promedio;
            this.desviacion = desviacion;
        }

        public List<double> getNRandoms(int cant)
        {
            List<double> lstRandoms = new List<double>();
            List<double> r = rnd.getNRandoms(cant * 12);
            double suma;
            for(int i=0;i<r.Count;i++)
            {
                suma=0;
                for (int j = 0; j < 12 && i<r.Count;j++)
                {
                    suma += r[i];
                    i++;
                }
                lstRandoms.Add((double)(suma-6)*desviacion+promedio);
            }
            return lstRandoms;
        }
    }
}
