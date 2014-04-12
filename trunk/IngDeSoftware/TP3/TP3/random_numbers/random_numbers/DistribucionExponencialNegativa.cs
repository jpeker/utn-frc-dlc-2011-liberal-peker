using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace random_numbers
{
    class DistribucionExponencialNegativa : IDistribucion
    {
        private double promedio;
        private RandomNumber rnd;
        public DistribucionExponencialNegativa(double promedio,RandomNumber rnd)
        {
            this.promedio = promedio;
            this.rnd = rnd;
        }
        public List<double> getNRandoms(int cant)
        {
            List<double> lstRandoms=new List<double>();
            List<double> randoms = rnd.getNRandoms(cant);
            for (int i = 0; i < randoms.Count; i++)
            {
                lstRandoms.Add((double)this.promedio * (-1) * Math.Log(1 - randoms[i]));
            }
            return lstRandoms;
        }
    }
}
