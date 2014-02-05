using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace RNDGenDistRLiberal
{
    public class NumberVariable
    {
        private int value;
        public NumberVariable(double rnd) {
            value = 16 + (int)((rnd * 100) / 20);
        }
        public int getValue() { 
            return value;
        }
    }
}
