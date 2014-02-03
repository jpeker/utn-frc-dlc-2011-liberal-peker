using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Diagnostics;

namespace ArrivalsForQueuesRLiberal
{
    public class ProblemColumn
    {
      private List<object> pcList = new List<object>();
      public ProblemColumn(List<object> list, char value)
      {
            //Debug.WriteLine(list[0]);
            pcList.Add((string) list[0]);//Name
            switch (value) { //Data type, string s, integer i, double d
                case 's': { pcList.Add( (string)list[1]); break; }
                case 'i': { pcList.Add( (int)list[1]); break; }
                case 'd': { pcList.Add( (double)list[1]); break; }
           }
      }

      public ProblemColumn() { pcList = new List<object>(); }
     
      public List<object> getListProblemColumn()
      {
          return pcList;
      }

      public void clearList() 
      {
          pcList.Clear();
      }

      public void restartProblemColumm(char value) {
          switch (value)
          { 
              //Data type, string s, integer i, double d
              case 's': { pcList[1] = (string)"ninguno"; break; }
              case 'i': { pcList[1] = (int)0; break; }
              case 'd': { pcList[1] = (double)0.0; break; }
          }
      }

      public object getObjectProblemColumn(int index)
      {
          return pcList[index];
      }

      public void setObjectProblemColumn(int index, object value)
      {
          pcList[index] = value;
      }

      
    }
}
