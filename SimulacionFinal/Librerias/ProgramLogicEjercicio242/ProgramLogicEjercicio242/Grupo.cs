using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ProgramLogicEjercicio242
{
    public class Grupo
    {
        private int cantidadDeMiembros;
        //Atributo estatico cuenta la cantidad de grupos
        private  int nroGrupo;
        private bool libre = false;
        public MiembroGrupo[] items;
        public static int cantidadTotalDeGrupos = 0;
        private double arrivalGrupo = 0;
        private double endTimeRecorrido = 0;
        //Si el miembro es false esta libre
        //Si el miembro es true esta ocupado
        private List<MiembroGrupo> miembros = new List<MiembroGrupo>();

        public Grupo(int _cantidadDeMiembros, double _arrivalGrupo) 
        {
            cantidadDeMiembros = _cantidadDeMiembros;
            cantidadTotalDeGrupos++;
            nroGrupo = cantidadTotalDeGrupos;
            bool guia = true;
            for (int i = 0; i < cantidadDeMiembros; i++) 
            {
                miembros.Add(new MiembroGrupo(guia,nroGrupo));
                guia = false;
            }
            arrivalGrupo = _arrivalGrupo;
        }

        public Grupo() { }

        public List<MiembroGrupo> getListaGrupo() {
            return miembros;
        }

        public int getCantidadMiembros() {
            return cantidadDeMiembros;
        }

        public int getCantidadGrupos() {
            return cantidadTotalDeGrupos;
        }

        public double getEndTimeRecorrido() 
        {
            return endTimeRecorrido;
        }

        public void setEndTimeRecorrido(double timeRecorrido) 
        {
            endTimeRecorrido = timeRecorrido;
        }

        public double getArrivalGrupo() 
        {
            return arrivalGrupo;
        }

        public MiembroGrupo GetEnumerator() 
        {
            return new MiembroGrupo(this);
        }
    }
}
