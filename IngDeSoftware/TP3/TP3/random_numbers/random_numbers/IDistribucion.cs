using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace random_numbers
{
    interface IDistribucion
    {
        List<double> getNRandoms(int cant);
    }
}
