using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace random_numbers
{
    class DistribucionUniforme : IDistribucion
    {        
        private RandomNumber rnd;
        private double lInferior;
        private double lSuperior;
        public DistribucionUniforme(RandomNumber rnd,double lInferior,double lSuperior)
        {            
            this.rnd = rnd;
            this.lInferior = lInferior;
            this.lSuperior = lSuperior;
        }
        public List<double> getNRandoms(int cant)
        {
            List<double> lstRandoms = rnd.getNRandoms(cant);
            for (int i = 0; i < lstRandoms.Count; i++)
            {
                lstRandoms[i] = lInferior + lstRandoms[i] * (lSuperior - lInferior);
            }
            return lstRandoms;

        }
    }
}
