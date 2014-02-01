using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace RNDGenDistRLiberal
{
    public class CStrategy_UniformDist : Strategy_AleatoryNumberDistribution
    {
        private double maxIntervalValue = 0;
        private double minIntervalValue = 0;
        public CStrategy_UniformDist(double _maxIntervalValue, double _minIntervalValue)
        {
            maxIntervalValue = _maxIntervalValue;
            minIntervalValue = _minIntervalValue;
        }
        public override double implementAleatoryNumberDistribution(Random rnd)
        {
           return rnd.NextDouble() * (maxIntervalValue - minIntervalValue) + maxIntervalValue;
        }
    }
}
