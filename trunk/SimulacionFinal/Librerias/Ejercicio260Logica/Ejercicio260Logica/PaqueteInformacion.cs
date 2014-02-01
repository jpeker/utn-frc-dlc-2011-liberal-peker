using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Ejercicio260Logica
{
    public class PaqueteInformacion
    {
        private bool isDescartado = false;
        private double tiempo;
        private static int nroPaquete =0;
        public bool getIsDescartado(){
            return isDescartado;
        }
        public PaqueteInformacion() { 
            nroPaquete++;
        }
        public PaqueteInformacion(double _tiempo) {
            nroPaquete++;
            tiempo = _tiempo;
        }

        public void setIsDescartado() {
            isDescartado = true;
        }
    }
}
