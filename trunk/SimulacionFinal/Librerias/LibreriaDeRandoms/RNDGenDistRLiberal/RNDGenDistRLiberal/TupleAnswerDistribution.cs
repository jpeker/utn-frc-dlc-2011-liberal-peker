using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace RNDGenDistRLiberal
{
    public class TupleAnswerDistribution
    {
        private List<Tuple<string, double>> tuple_value_str_dbl_AnswerList;
        //private Double d;
        public TupleAnswerDistribution(List<Tuple<string, double>>
            _incoming_tuple_List, int _rounded_digits) {
                if (valid_tuple_AnswerList
                    (_incoming_tuple_List, _rounded_digits))
                {
                    tuple_value_str_dbl_AnswerList = _incoming_tuple_List;
                }
        }

        private bool valid_tuple_AnswerList(List<Tuple<string, double>> 
            incoming_tuple_List, int rounded_digits) {
            bool isValid = false;
            double accumulateProb = 0;
            foreach (Tuple<string, double> t in incoming_tuple_List)
            {
                if (!(t.Item2 >= 0 && t.Item2 < 1))
                {
                    break;
                }
                accumulateProb = accumulateProb + t.Item2;
            }
            if (Math.Round(accumulateProb, rounded_digits) == 1)
            {
                isValid = true;
            } 
            return isValid;
        }

        public Tuple<string, double> valueAsigned(Random rnd) { 
            Tuple<string,double> valuedTuple = 
                new Tuple<string,double>("__nothing__",-1);
            foreach (Tuple<string, double> t in tuple_value_str_dbl_AnswerList)
            {
                if (rnd.NextDouble() < t.Item2)
                {
                    valuedTuple = t;
                    break;
                }
            }
            return valuedTuple;
        }
    }
}