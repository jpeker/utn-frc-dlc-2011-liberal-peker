using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ArrivalsForQueuesRLiberal
{
    public abstract class AbstractEvent
    {
        public virtual void setNextRow(List<Object> list) { }
        public virtual void setValueChange(int value) { }
        public virtual void setStateQueue() { }
    }

}
