using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ProgramLogicEjercicio242
{
    public class MiembroGrupo
    {
        private bool idle = false;
        private bool isGuia = false;
        private double initTime = 0;
        private double endTime = 0;
        private int nroGrupo = 0;
        private int nIndex;
        private Grupo grupoM;

        public MiembroGrupo(bool _isGuia, int _nroGrupo){
            isGuia = _isGuia;
            nroGrupo = _nroGrupo;
        }

        public MiembroGrupo(Grupo _grupoM) {
            grupoM = _grupoM;
            nIndex = -1;
        }

        public bool getIsGuia(){
            return isGuia;
        }

        public bool getIdle(){
            return idle;
        }

        public void setIdle(bool val) {
            idle = val;
        }

        public double getInitTime()
        {
            return initTime;
        }

        public void setInitTime(double val)
        {
            initTime = val;
        }

        public double getEndTime()
        {
            return endTime;
        }

        public void setEndTime(double val)
        {
            endTime = endTime + val;
        }

        public bool MoveNext()
        {
            nIndex++;
            return (nIndex < grupoM.items.GetLength(0));
        }

        public MiembroGrupo Current
        {
            get
            {
                return (grupoM.items[nIndex]);
            }
        }

    }
}
