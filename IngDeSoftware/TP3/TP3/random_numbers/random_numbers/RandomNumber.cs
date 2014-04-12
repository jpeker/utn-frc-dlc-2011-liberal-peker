using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;

namespace random_numbers
{
    public class RandomNumber
    {
        private Random r = null;
        public long X { get; set; }
        public long A { get; set; }
        public long C { get; set; }
        public long M { get; set; }
        public int Metodo { get; set; }
        
        
        public RandomNumber()
        {
             
            

        }

        public RandomNumber(long seed, long a, long c, long m, int metodo)
        {
            X = seed;
            A = a;
            C = c;
            M = m;
            Metodo = metodo;

        }

        public double NextRandom()
        {
            if (Metodo == (int) Utils.metodo.Mixto)
            {
                X = (((A * X) + C) % M);
                return (double)X / (double)(M - 1);
            }
            else if (Metodo == (int)Utils.metodo.Multiplicativo)
            {
                X = ((A * X) % M);
                return (double)X / (double)(M - 1);
            }
            else if (Metodo == (int)Utils.metodo.Nativo)
            {
                double siguiente = -1;
                if (r != null)
                    siguiente = r.NextDouble();
                return siguiente;
            }
            else
            {
                return -1;
            }
            
        }

        public List<double> getNRandoms(int cant)
        {
            if (Metodo == (int) Utils.metodo.Mixto)
            {
                List<double> lstRandoms = new List<double>();
                if (X > 0 && A > 0 && M > X)
                {
                    for (int i = 0; i < cant; i++)
                    {
                        X = (((A * X) + C) % M);
                        lstRandoms.Add((double)X / (double)(M - 1));
                    }
                }
                return lstRandoms;
            }
            else if (Metodo == (int)Utils.metodo.Multiplicativo)
            {
                List<double> lstRandoms = new List<double>();
                if (X > 0 && A > 0 && M > X)
                {
                    for (int i = 0; i < cant; i++)
                    {
                        X = (((A * X)) % M);
                        lstRandoms.Add((double)X / (double)(M - 1));
                    }
                }
                return lstRandoms;
            }
            else
            {
                List<double> lstRandoms = new List<double>();
                r = new Random(DateTime.Now.Millisecond);
                for (int i = 0; i < cant; i++)
                {
                    lstRandoms.Add(r.NextDouble());
                }
                return lstRandoms;            
            }
        }
    }
}
