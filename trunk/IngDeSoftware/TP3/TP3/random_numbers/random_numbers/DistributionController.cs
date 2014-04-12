using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;

namespace random_numbers
{
    class DistributionController : IDistribucion
    {
        private IDistribucion distribucion;        
        public DistributionController(int distribucion, RandomNumber rnd, Hashtable parametros)
        {
            
            switch (distribucion)
            {
                case (int) Utils.distribucion.ExponencialNegativo:
                    this.distribucion = new DistribucionExponencialNegativa(double.Parse(parametros["promedio"].ToString()), rnd);
                    break;
                case (int)Utils.distribucion.Uniforme:
                    this.distribucion = new DistribucionUniforme(rnd,double.Parse(parametros["limInf"].ToString()), double.Parse(parametros["limSup"].ToString()));
                    break;
                case (int)Utils.distribucion.Normal:
                    this.distribucion = new DistribucionNormal(rnd, double.Parse(parametros["promedio"].ToString()), double.Parse(parametros["desviacion"].ToString()));
                    break;
                default:
                    Console.WriteLine("Default case");
                    break;
            }                                   
        }

        public List<double> getNRandoms(int cant)
        {
            return distribucion.getNRandoms(cant);
        }
    }
}
