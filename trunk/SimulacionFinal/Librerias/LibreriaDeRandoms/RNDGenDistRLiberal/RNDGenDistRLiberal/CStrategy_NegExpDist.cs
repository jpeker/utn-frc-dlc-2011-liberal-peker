using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;

namespace RNDGenDistRLiberal
{
    public class CStrategy_NegExpDist : Strategy_AleatoryNumberDistribution
    {
        private double medval = 0;
        public CStrategy_NegExpDist(double _medval) {
            medval = _medval;
        } 
        public override double implementAleatoryNumberDistribution(double rnd)
        {
            return -1 * medval * Math.Log(1-rnd);
        }

        public void setMedVal(double _medval) {
            medval = _medval;
        }
    }
}
