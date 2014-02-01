using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ArrivalsForQueuesRLiberal
{
    public class Event : AbstractEvent
    {
      public static bool firstTime = true;
      private List<object> serverList = new List<object>(5);
      public Event(List<object> list)
      {
            serverList.Add((string) list[0]);//NameEvent
            serverList.Add((double) list[1]);//RandomValueForNext
            serverList.Add((double) list[2]);//NextValue
            serverList.Add((double) list[3]);//CurrentValue
            serverList.Add((string) list[4]);//State
      }
      public Event() { }
      public List<object> getListEvent() {
          return serverList;
      }

      public object getObjectListEvent(int index)
      {
          return serverList[index];
      }

      public void setObjectListEvent(int index, object value)
      {
          serverList[index] = value;
      }

      public double getCurrentValue() { 
        return (double) serverList[3];
      }

      public override void setNextRow(List<object> list)
        {
            if ((list[0].GetType() == typeof(string)) 
                    && list[0].Equals(serverList[0]))
            {
                //list [0] event name ,list[1] random, list[2] clock
                //serverList2 valor de random generado + clock actual
                serverList[1] = list[1]; 
                //if (firstTime) { serverList[3] = (double)list[1]; 
                //    firstTime = false; }
                //else { serverList[3] = (double)serverList[2]; }
                serverList[2] = (double)list[1] + (double)list[2];               
            }
        }

      public override void setValueChange(int value)
      {
          serverList[3] = (int)serverList[3] + value;
      }

      public override void setStateQueue()
      {
          if ((int)serverList[3] == 0)
          {
              serverList[4] = "Libre";
          }
          else {
              serverList[4] = "Ocupado";
          }
      }
    }
}
