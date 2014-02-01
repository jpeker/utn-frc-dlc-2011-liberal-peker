using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ArrivalsForQueuesRLiberal
{
    public class QueueEvent : Event
    {
        private List<object> serverList = new List<object>();
        private int maxCapacity = 0;
        public QueueEvent(List<object> list, int _maxCapacity)
        {
            serverList.Add((string) list[0]); //Name
            serverList.Add((double)list[1]); //Init Time
            serverList.Add((int) list[2]); //Queue
            serverList.Add((int) list[3]); //Capacity
            serverList.Add((string) list[4]); //State
            maxCapacity = _maxCapacity;
            //for (int i = 5; i < i + (int)serverList[1]; i++)
        }
        public QueueEvent() {}

        public override void setNextRow(List<object> list)
        {
            if ((list[0].GetType() == typeof(string)) 
                && list[0].Equals(serverList[0]))
            {
                serverList[1] = list[1] ;
                //if ((int)serverList[3] < maxCapacity) {
                serverList.Add((double)list[1] + (double)list[2]);
                //}
                //if ((int)serverList[3] > maxCapacity)
                //{
                   // serverList[2] = (int)serverList[2] + 1;
                //    serverList.Add((double)list[1] + (double)list[2]);
                //}
            }
        }

        public void setAddQueueChange() {

        }

        public void setDifQueueChange()
        {
           // if ((int)serverList[3] < maxCapacity)
          //  {
          //      serverList[2] = (int)serverList[2] - 1;
           // }
        }

        public void setValuesChange(int value, int removeindex)
        {
            //Suma valor a la capacidad
            base.setValueChange(value);
            //Si el valor es menor a 0 quito elemento (es decir llega un -1)
            if (value < 0) {
                serverList.RemoveAt(removeindex);
            }
            //Si la capacidad supera a la capacidad maxima 
            //Incremento la cola
            if ((int)serverList[3] >= maxCapacity )
            {
                serverList[2] = (int)serverList[2] + value;
            }
        }

        public override void setStateQueue()
        {
            base.setStateQueue();
        }

        public int getMaxCapacity() {
            return maxCapacity;
        }
    }
}
