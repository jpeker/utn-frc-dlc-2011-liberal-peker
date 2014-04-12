#include <graphics.h>
#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <conio.h>
#include <dos.h>

#define UP       0 + 72
#define DOWN     0 + 80
#define LEFT     0 + 75
#define RIGHT    0 + 77

#define CELDA    20
#define FILAS    15
#define COLUMNAS 20

#define RADIO   9
#define BORRAR  1
#define MOSTRAR 0
#define RADIO_DE_CAPTURA 4

enum Tipos   {NADA, BORDE, PACMAN, FANTASMA, COMIDA};

struct Pacman
{
  int x,y;       // coordenadas de la matriz donde se supone que est 
  int estado;    // indice de la figura que hay que dibujar (0 a 12)
  int vidas;     // cantidad de vidas que le quedan
  int direccion; // direcci¢n en la que se estaba moviendo (ver constantes)
  int conteo;    // cual de las tres caras de avance va???
};

struct Fantasmita
{
  int x,y;        // coordenadas de la matriz tablero donde se supone que est 
  int vivo;       // ¡ndice del vector donde se encuentra la figura
  Tipos anterior; // lo que hab¡a antes en esa casilla
  int direccion;  // direcci¢n en la que se estaba moviendo (ver constantes)
};

struct Casilla
{
  int x, y;        // coordenadas del pixel donde corresponde dibujarla
  Tipos contenido; // un valor del enum Tipos: indica qu‚ dibujar en ella
};

void iniciarGraficos();

void levantarImagenes(void *pac[], int &cantidad);
void liberarFiguras(void *pac[], int cantidad);

void iniciarPacman(Pacman &p);
void iniciarFantasmita(Fantasmita &f, int indice);
void iniciarTodosLosFantasmitas(Fantasmita f[3]);
void mostrarPacman(Pacman &p, Casilla tablero[][COLUMNAS], void *pac[16], int forma);
void mostrarFantasmita(Fantasmita f, Casilla tablero[][COLUMNAS], void *pac[16]);
void mostrarTodosLosFantasmitas(Fantasmita f[3], Casilla t[][COLUMNAS], void *pac[]);
void moverFantasmitas(Fantasmita f[3], Pacman p, Casilla t[][COLUMNAS], void *pac[]);
void proximoPasoFantasmita(Fantasmita f, Pacman p, Casilla t[][COLUMNAS], int &col, int &fil);
int  verSiEstaBloqueado (Fantasmita f, Casilla t[][COLUMNAS]);
void buscarSalidaDeLaJaula (Fantasmita f, Casilla t[][COLUMNAS], int &col, int &fil);
int  acercarFila(Fantasmita f, Pacman p, Casilla t[][COLUMNAS]);
int  acercarColumna(Fantasmita f, Pacman p, Casilla t[][COLUMNAS]);
int  analizarPorDondeSigue(Fantasmita f, Casilla t[][COLUMNAS]);
void moverEnFormaAleatoria (Fantasmita f, Casilla t[][COLUMNAS], int &col, int &fil);
int  pacmanAtrapado(Fantasmita f[3], Pacman p, Casilla t[][COLUMNAS], void *pac);

void dibujarTablero (Casilla t[][COLUMNAS], Pacman p, Fantasmita f[3], void *pac[]);
void iniciarTablero(Casilla t[][COLUMNAS], int xIni, int yIni);
void ponerParedVertical(Casilla t[][COLUMNAS], int f1, int f2, int c);
void ponerParedHorizontal(Casilla t[][COLUMNAS], int f, int c1, int c2);

void dibujarComida(Casilla t[][COLUMNAS], int i, int j);

void main()
{
   iniciarGraficos();

   Casilla tablero[FILAS][COLUMNAS];
   Pacman pacman;
   Fantasmita fantasmita[3];

   /*
	 El vector "pac" contendr  punteros a los 16 gr ficos, repartidos as¡:
	 pac[0] a pac[11]:  Los 12 dibujos del pacman movi‚ndose en cada direcci¢n
	 pac[12]:           El pacman atrapado
	 pac[13] a pac[15]: Los tres fantasmitas
   */
   void *pac[16];

   int inicio = 1, f, c, indFig, cantidad;
   char car;

   levantarImagenes(pac, cantidad);

   iniciarPacman(pacman);
   iniciarTodosLosFantasmitas(fantasmita);
   dibujarTablero(tablero, pacman, fantasmita, pac);

   // y ac  vamos!!!
   car = ' ';
   while (car!=27)
   {
	  delay(200);
	  moverFantasmitas(fantasmita, pacman, tablero, pac);
	  if(pacmanAtrapado(fantasmita, pacman, tablero, pac))
	  {
		 // son¢ el pacman
		 pacman.estado = 12;
		 pacman.vidas--;
		 mostrarPacman(pacman, tablero, pac, MOSTRAR);
		 getch();
		 exit(0);
	  }
	  if (kbhit())
	  {
		   // si alguien toc¢ una tecla, vemos si es tecla especial...
		   car = getch();
		   if(car == 0)
		   {
		     // fue una tecla  especial... veamos cual
		     car = getch();
		   }
	  }
	  if (car == UP || car == DOWN || car == RIGHT || car == LEFT)
	  {
			  /*
				fue una tecla del cursor... borramos el pacman de su posici¢n
				anterior...
			  */
			  indFig = pacman.conteo;
			  if(inicio!=1)
			  {
			    if(indFig == 0) pacman.conteo = 2; else pacman.conteo--;
			  }
			  inicio = 0;
			  mostrarPacman(pacman, tablero, pac, BORRAR);
			  pacman.conteo = indFig;

			  // analizamos el pr¢ximo movimiento
			  f = pacman.y;  // fila actual
			  c = pacman.x;  // columna actual
			  int nf, nc;    // nuevas fila y columna
			  switch(car)
			  {
				/*
				  Por ahora no me preocupo de que el pacman atrape a un
				  fantasmita.. si veo uno, no dejo que el pacman avance!!!
				*/
				case UP:     // subir es restar una fila
							 nf = f - 1;
							 if(nf > -1 && tablero[nf][c].contenido != BORDE && tablero[nf][c].contenido != FANTASMA)
							 {
							   // el movimiento es v lido
							   f = nf;
							 }
							 break;

				case DOWN:   // bajar es sumar una fila
							 nf = f + 1;
							 if(nf < FILAS && tablero[nf][c].contenido != BORDE && tablero[nf][c].contenido != FANTASMA)
							 {
							   // el movimiento es v lido
							   f = nf;
							 }
							 break;

				case LEFT:
							 nc = c - 1;
							 if(nc > -1 && tablero[f][nc].contenido != BORDE && tablero[f][nc].contenido != FANTASMA)
							 {
							   // el movimiento es v lido
							   c = nc;
							 }
							 break;

				case RIGHT:
							 nc = c + 1;
							 if(nc < COLUMNAS && tablero[f][nc].contenido != BORDE && tablero[f][nc].contenido != FANTASMA)
							 {
							   // el movimiento es v lido
							   c = nc;
							 }
							 break;
			  }

			  pacman.y = f;
			  pacman.x = c;
			  mostrarPacman(pacman, tablero, pac, MOSTRAR);
	       /*	  }
		}*/
	  }
   }

   liberarFiguras(pac, cantidad);
   closegraph();
}

/**
   Da valores iniciales al Pacman que entra como par metro
   Arranca como para empezar siempre arriba y a la izquierda del tablero
*/
void iniciarPacman(Pacman &p)
{
   p.x = p.y = 0;  // en la matriz, primera casilla
   p.estado = 0;   // primera figura: mira a la derecha con boca chica
   p.vidas  = 3;   // tiene tres vidas para todo el partido
   p.direccion = RIGHT;
   p.conteo = 0;   // empieza con la primera cara de avance en esa direcci¢n
}

/**
   Da valores iniciales a un Fantasmita que entra como par metro
   Arranca como para empezar en la jaula central del tablero
*/
void iniciarFantasmita(Fantasmita &f, int indice)
{
   int c;
   if (indice == 13) { c = 7; }
   else
   {
	if(indice == 14) { c = 8; }
	else { c = 9; }
   }
   f.x = c;  // una columna de adentro de la jaula
   f.y = 8;  // es la primera fila de adentro de la jaula
   f.vivo = indice;
   f.anterior = COMIDA;
   f.direccion = RIGHT;
}

/**
  Inicializa el vector completo de Fantasmitas. El valor 13 es el ¡ndice
  del vector de figuras donde empiezan los fantasmas
*/
void iniciarTodosLosFantasmitas(Fantasmita f[3])
{
   int i, figura = 13;
   for(i = 0; i<3; i++)
   {
	  iniciarFantasmita(f[i], figura);
	  figura++;
   }
}

/**
  Muestra el pacman en el tablero. La figura que muestra corresponde al
  valor del campo "estado" en el pacman, y sale del vector de figuras
  El par metro "forma" indica si el pacman debe mostrarse o debe borrarse
  de donde estaba (vale 0 si debo mostrar, 1 si debo borrar).
*/
void mostrarPacman(Pacman &p, Casilla tablero[][COLUMNAS], void *pac[16], int forma)
{
  int tipo = p.estado + p.conteo;   // ac  sabemos qu‚ figura mostrar

  int j = p.x, i = p.y; // coordenadas de la matriz donde debo mirar

  int x = tablero[i][j].x;  // columna del pixel donde debo colgar la imagen
  int y = tablero[i][j].y;  // ¡dem fila

  putimage(x, y, pac[tipo], forma);
  if (forma == MOSTRAR)
  {
	tablero[i][j].contenido = PACMAN;
  }
  else
  {
	tablero[i][j].contenido = NADA;
  }

  if(p.conteo == 2) 
  { 
    p.conteo = 0;
  }
  else
  {
    p.conteo++;
  }
}

/**
   Muestra todos los fantasmitas en la jaula central
*/
void mostrarTodosLosFantasmitas(Fantasmita f[3], Casilla t[][COLUMNAS], void *pac[])
{
   int i;
   for(i = 0; i<3; i++)
   {
	  mostrarFantasmita(f[i], t, pac);
   }
}

/**
  Muestra un fantasmita en el tablero.
*/
void mostrarFantasmita(Fantasmita f, Casilla tablero[][COLUMNAS], void *pac[16])
{
  int tipo = f.vivo;    // ac  sabemos qu‚ figura mostrar
  int j = f.x, i = f.y; // coordenadas de la matriz donde debo mirar

  int x = tablero[i][j].x;  // columna del pixel donde debo colgar la imagen
  int y = tablero[i][j].y;  // ¡dem fila

  putimage(x, y, pac[tipo], 0);

  tablero[i][j].contenido = FANTASMA;
}

/**
  Muestra el Tablero, con un Pacman arriba y tres fantasmitas
*/
void dibujarTablero (Casilla t[][COLUMNAS], Pacman p, Fantasmita f[3], void *pac[])
{

   int i, j, x1, y1, x2, y2;
   int xIni = 110, xFin = xIni + COLUMNAS * CELDA;
   int yIni = 60, yFin = yIni + FILAS * CELDA;

   // ponemos el t¡tulo
   setcolor(LIGHTGREEN);
   settextjustify(1,1);
   settextstyle(4,0,2);
   moveto(getmaxx()/2, 10);
   outtext("El Regreso del Pacman");

   // iniciamos el tablero y mostramos el rect ngulo del borde
   iniciarTablero(t, xIni, yIni);
   setcolor(CYAN);
   rectangle(xIni-CELDA/2, yIni-CELDA/2, xFin+CELDA/2, yFin+CELDA/2);

   // mostramos las paredes y la comida
   for (i=0; i<FILAS; i++)
   {
	 for (j=0; j<COLUMNAS; j++)
	 {
		switch(t[i][j].contenido)
		{
		   case COMIDA:
				dibujarComida(t,i,j);
				setcolor(CYAN);
				break;

		   case BORDE:
				int v;
				for(v=i; v<FILAS && t[v][j].contenido==BORDE; v++);
				v--;
				x1 = t[i][j].x + CELDA/2;
				y1 = t[i][j].y + CELDA/2;
				x2 = t[v][j].x + CELDA/2;
				y2 = t[v][j].y + CELDA/2;
				line(x1,y1,x2,y2);

				int h;
				for(h=j; h<COLUMNAS && t[i][h].contenido==BORDE; h++);
				h--;
				x1 = t[i][j].x + CELDA/2;
				y1 = t[i][j].y + CELDA/2;
				x2 = t[i][h].x + CELDA/2;
				y2 = t[i][h].y + CELDA/2;
				//j = h;
				line(x1,y1,x2,y2);

				break;
		}
	 }
   }

   // ahora dibujamos el Pacman
   mostrarPacman(p, t, pac, MOSTRAR);

   // y por £ltimo los fantasmitas
   mostrarTodosLosFantasmitas(f, t, pac);

   // algunos mensajes y ayudas al pie del tablero
   setcolor(BROWN);
   settextjustify(1,1);
   settextstyle(3,0,1);
   moveto(getmaxx()/2, yFin + 2*CELDA);
   outtext("Presione <Esc> para terminar... y que lo disfrute!!");
}

/**
   Da valores iniciales a las Casilas del tablero
*/
void iniciarTablero(Casilla t[][COLUMNAS], int xIni, int yIni)
{
   int i, j;
   int deltaX;
   int deltaY = 0;

   // ajusta todas las coordenadas de pixel, y por defecto pone todo "comida"
   for (i=0; i<FILAS; i++)
   {
	  deltaX = 0;
	  for (j=0; j<COLUMNAS; j++)
	  {
		 t[i][j].x = xIni + deltaX;
		 t[i][j].y = yIni + deltaY;
		 t[i][j].contenido = COMIDA;

		 deltaX += CELDA;
	  }

	  deltaY += CELDA;
   }

   // ahora ponemos las "paredes"
   ponerParedHorizontal(t, 1, 1, 6);
   ponerParedHorizontal(t, 1, 10, 16);
   ponerParedHorizontal(t, 3, 14, 18);
   ponerParedHorizontal(t, 4, 1, 6);
   ponerParedHorizontal(t, 5, 12, 16);
   ponerParedHorizontal(t, 6, 5, 6);
   ponerParedHorizontal(t, 7, 6, 9);
   ponerParedHorizontal(t, 7, 11, 13);
   ponerParedHorizontal(t, 9, 1, 3);
   ponerParedHorizontal(t, 9, 15, 18);
   ponerParedHorizontal(t, 10, 5, 13);

   ponerParedHorizontal(t, 11, 1, 2);
   ponerParedHorizontal(t, 11, 4, 5);

   ponerParedHorizontal(t, 13, 1, 2);

   ponerParedHorizontal(t, 12, 7, 11);
   ponerParedHorizontal(t, 12, 13, 16);
   ponerParedHorizontal(t, 13, 4, 5);

   ponerParedVertical  (t, 1, 4, 1);
   ponerParedVertical  (t, 6, 9, 1);
   ponerParedVertical  (t, 11, 13, 2);
   ponerParedVertical  (t, 6, 7, 3);
   ponerParedVertical  (t, 11, 13, 4);
   ponerParedVertical  (t, 3, 4, 6);
   ponerParedVertical  (t, 6, 10, 6);
   ponerParedVertical  (t, 10, 13, 7);
   ponerParedVertical  (t, 1, 5, 8);
   ponerParedVertical  (t, 1, 5, 10);

   ponerParedVertical  (t, 12, 13, 11);
   ponerParedVertical  (t, 3, 5, 12);
   ponerParedVertical  (t, 7, 10, 13);
   ponerParedVertical  (t, 12, 13, 13);
   ponerParedVertical  (t, 7, 9, 15);
   ponerParedVertical  (t, 11, 13, 16);
   ponerParedVertical  (t, 1, 3, 18);
   ponerParedVertical  (t, 5, 9, 18);
   ponerParedVertical  (t, 11, 13, 18);
}

/**
   Asigna valores para una pared vertical en el tablero
*/
void ponerParedVertical(Casilla t[][COLUMNAS], int f1, int f2, int c)
{
   int i;
   for(i=f1; i<=f2; i++)
   {
	 t[i][c].contenido = BORDE;
   }
}

/**
   Asigna valores para una pared horizontal en el tablero
*/
void ponerParedHorizontal(Casilla t[][COLUMNAS], int f, int c1, int c2)
{
   int j;
   for(j=c1; j<=c2; j++)
   {
	 t[f][j].contenido = BORDE;
   }
}

/**
   Carga desde disco todas las figuras gr ficas. Las coloca en el vector "pac"
*/
void levantarImagenes(void *pac[], int &cantidad)
{
   FILE *m = fopen ("a:\\figuras.pix", "rb");
   if(m)
   {
	  int i;

	  // los tama¤os en bytes de cada una de las im genes anteriores
	  unsigned int size[16];

	  fread(&cantidad, sizeof(int), 1, m);
	  for(i=0; i<cantidad; i++)
	  {
		fread(&size[i], sizeof(unsigned int), 1, m);
		pac[i]=malloc(size[i]);
		fread(pac[i], size[i], 1, m);
	  }
	  fclose(m);
   }
   else
   {
	  outtextxy(10,10,"Error al intentar cargar las figuras... ");
	  getch();
	  closegraph();
	  exit(1);
   }
}

/**
   Libera la memoria ocupada por las figuras gr ficas
*/
void liberarFiguras(void *pac[], int cantidad)
{
  int i;
  for(i=0; i<cantidad; i++)
  {
	free(pac[i]);
  }
}

void iniciarGraficos()
{
   int gdriver = DETECT, gmode, errorcode;
   initgraph(&gdriver, &gmode, "c:\\borlandc\\bgi");
   errorcode = graphresult();
   if (errorcode != grOk)
   {
	  printf("Error en modo gr fico: %s\n", grapherrormsg(errorcode));
	  printf("Presione un tecla para terminar...");
	  getch();
	  exit(1);
   }
   randomize();
}
/**
   Mueve los fantasmitas hacia direcciones aleatorias en el tablero
*/
void moverFantasmitas(Fantasmita f[3], Pacman p, Casilla t[][COLUMNAS], void *pac[])
{
  int i, figura = 13;
  for (i=0; i<3; i++)
  {
	int col, fil;
	proximoPasoFantasmita(f[i], p, t, col, fil);
	if (col != -1)
	{
	   // suficiente con verificar una, pues si esa es -1 la otra tambi‚n!

	   // borro el fantasmita anterior
	   int x = f[i].x;
	   int y = f[i].y;
	   putimage(t[y][x].x, t[y][x].y, pac[figura], 1);
	   if(f[i].anterior == COMIDA)
	   {
		 dibujarComida(t, y, x);
	   }
	   else
	   {
		 t[y][x].contenido = NADA;
	   }
	   int d;
	   if(col == x)
	   {
		 if(fil < y) d = UP;
		 else if (fil > y) d = DOWN;
			  else d = f[i].direccion;
	   }
	   else
	   {
		 if(col > x) d = RIGHT;
		 else d = LEFT;
	   }
	   f[i].direccion = d;

	   f[i].x = col;
	   f[i].y = fil;
	   f[i].anterior = t[fil][col].contenido;
	   mostrarFantasmita(f[i], t, pac);
	}

	/*
	  Si contest¢ que no a la pregunta anterior, pues ese fantasmita
	  estaba bloqueado, y deber  esperar otro turno para moverse!!! Paso
	  al que sigue, y listo.
	*/
	figura++;
  }
}

/**
   Calcula las coordenadas de la matriz donde podr¡a moverse este fantasmita
   y las devuelve en col y fil. Si no hay movimiento posible, ambas vuelven
   valiendo -1.

   Criterios:
	  a.) El fantasmita nunca se queda quieto, a menos que est‚ bloqueado.
		  S¢lo si estuviera bloqueado, col y fil salen valiendo -1. Si no
		  est  bloqueado, col y fil (ambas) vuelven valiendo != -1.

	  b.) Si el fantasmita est  en la jaula central, trato de guiarlo hacia
		  la salida lo m s r pido posible, sin importar donde est‚ el pacman.

	  c.) Fuera de la jaula, tratamos de llevarlo hacia el pacman pero s¢lo
		  si el pacman est  en un radio de 6 casillas a la redonda.

	  d.) Si el pacman est  a m s de tres casillas, el fantasmita trata de
		  seguir la misma direcci¢n que llevaba. Si llega a una bifurcaci¢n,
		  seleccionamos en forma aleatoria por d¢nde sigue.
*/
void proximoPasoFantasmita(Fantasmita f, Pacman p, Casilla t[][COLUMNAS], int &col, int &fil)
{
   int px = p.x, py = p.y;  // coordenadas actuales del pacman
   int i =  f.y, j = f.x;   // coordenadas actuales del fantasmita
   int bloqueado, v;

   /*
	  a.) Ver si est  ya bloqueado, y terminar si as¡ fuera
   */
   col = fil = -1;  // estos son los valores que retorna si no se pudo mover
   bloqueado = verSiEstaBloqueado(f, t);
   if (bloqueado)
   {
	  // no pod¡a moverse para ning£n lado. Lo siento...
	  return;
   }

   /*
	  b.) Si estamos ac , no estaba bloqueado. Veamos si est  en la jaula y
		  lo vamos sacando de ella. Jaula: filas 8 y 9 - columnas 7 a 12 -
		  salida: fila 7 - columna 10
   */
   if(i<=9 && i>=8 && j<=12 && j>=7)
   {
	  // est  en la jaula
	  buscarSalidaDeLaJaula(f, t, col, fil);
	  if (col != -1) { return; }
	  else
	  {
		/*
		   Si llegu‚ ac , estaba en la jaula, y no hab¡a un movimiento
		   que lo acerque a la salida. Lo hacemos ir en forma aleatoria
		   y paramos (recordar que bloqueado no estaba, as¡ que alg£n
		   movimiento tiene que haber...
		*/
		moverEnFormaAleatoria(f, t, col, fil);
		return;
	   }
   }

   /*
	  c.) Si estamos ac , no est  bloqueado ni est  en la jaula. Tenemos
		  que ver si el pacman est  a 6 o menos casillas, para intentar
		  perseguirlo.
   */
   int dy = abs(py - i);
   int dx = abs(px - j);
   if( dy <= RADIO_DE_CAPTURA && dx <= RADIO_DE_CAPTURA )
   {
	  /*
		 Vemos por donde nos podemos acercar
	  */
	  col = acercarColumna(f, p, t);
	  fil = acercarFila(f, p, t);

	  if (col == -1 && fil != -1)
	  {
		 // Podemos acercarnos en la fila, pero no en la columna...
		 col = j;
		 return;
	  }

	  if (col != -1 && fil == -1)
	  {
		 // Podemos en la columna pero no en la fila...
		 fil = i;
		 return;
	  }

	  if (col != -1 && fil != -1)
	  {
		// Podemos en ambas... nos movemos en la m s pr¢xima??
		if(dy <= dx)
		{
		  // la fila est  m s cerca (o es la misma distancia que a la columna)
		  col = j; // dejo la columna que estaba y acepto la fila nueva
		}
		else
		{
		  // la columna est  m s cerca
		  fil = i;  // dejo la fila que estaba y acepto la columna nueva
		}
		return;
	  }

	  /*
		Si estamos ac , es que lo ten¡amos a 3 o menos pero no nos pudimos
		acercar. Lamentablemente, no queda otra que seguir
	  */
   }

   /*
	 d.) Si llegu‚ hasta ac , es que todo lo anterior fall¢, y por lo tanto
		 intentamos un movimiento en la misma direcci¢n que tra¡a el
		 fantasmita. Si justo est  en una bifurcaci¢n, tomamos en forma
		 aleatoria.
   */
   int ruta;
   ruta = analizarPorDondeSigue(f, t);
   switch(ruta)
   {
	  case UP:    col = j;
				  fil = i - 1;
				  break;

	  case DOWN:  col = j;
				  fil = i + 1;
				  break;

	  case RIGHT: fil = i;
				  col = j + 1;
				  break;

	  case LEFT:  fil = i;
				  col = j - 1;
   }

   if (col == -1)
   {
	 /*
	   Y bien: si estamos ac  es que TODO lo otro fall¢. Tiramos la moneda,
	   y hasta la vista baby!!!
	 */
	 moverEnFormaAleatoria(f, t, col, fil);
   }
}


/**
   Devuelve 1 si el fantasmita est  bloqueado por completo, y cero en
   caso contrario.
*/
int  verSiEstaBloqueado (Fantasmita f, Casilla t[][COLUMNAS])
{
   int i =  f.y, j = f.x;   // coordenadas actuales del fantasmita
   int der, izq, bajo, subo, bloqueado;
   der = j + 1;
   izq = j - 1;
   bajo = i + 1;
   subo = i - 1;
   bloqueado = 0; // asumo que no estaba bloqueado
   if( (der == COLUMNAS || t[i][der].contenido == BORDE  || t[i][der].contenido == FANTASMA)  &&
	   (izq == -1       || t[i][izq].contenido == BORDE  || t[i][izq].contenido == FANTASMA)  &&
	   (bajo == FILAS   || t[bajo][j].contenido == BORDE || t[bajo][j].contenido == FANTASMA) &&
	   (izq == -1       || t[subo][j].contenido == BORDE || t[subo][j].contenido == FANTASMA))
   {
	  bloqueado = 1;
   }
   return bloqueado;
}

/**
   Determina si puede moverse hacia la salida de la jaula. Si puede, col y
   fil salen valiendo != -1. Jaula: filas 8 y 9 - columnas 7 a 12 -
   salida: fila 7 - columna 10
*/
void buscarSalidaDeLaJaula (Fantasmita f, Casilla t[][COLUMNAS], int &col, int &fil)
{
  int i = f.y, j = f.x;
  int subo = i - 1;
  int bajo = i + 1;
  int der = j + 1;
  int izq = j - 1;
  if(j == 10)
  {
	 if(t[subo][j].contenido!=FANTASMA)
	 {
	   col = j;
	   fil = subo;
	   return;
	 }
  }

  if(j < 10)
  {
	 if(t[i][der].contenido != FANTASMA)
	 {
	   col = der;
	   fil = i;
	   return;
	 }
  }

  if(j > 10)
  {
	 if(t[i][izq].contenido != FANTASMA)
	 {
	   col = izq;
	   fil = i;
	   return;
	 }
  }

  /*
	 Si todo lo anterior fall¢, entonces pruebo a acercar la fila
  */
  if(i!=8)
  {
	 if(t[subo][j].contenido != FANTASMA)
	 {
		col = j;
		fil = subo;
		return;
	 }
  }

  /*
	 Y si llegu‚ ac , es que no encontr‚ un moviemiento que me acerque a
	 la salida. Quiz s pueda moverse dentro de la jaula en otra direcci¢n,
	 pero no hacia la salida. Dejo col y fil como las recib¡ (-1), y salgo.
  */
}

/**
   Busca acercar el fantasmita una fila hacia el pacman. La nueva fila
   vuelve en fil. Si no pudo cambiar de fila, fil vuelve igual a como
   entr¢
*/
int acercarFila(Fantasmita f, Pacman p, Casilla t[][COLUMNAS])
{
  int i = f.y,  j = f.x;
  int py = p.y;

  if (i < py)
  {
	// el pacman est  en una fila mayor a mi posici¢n
	int bajo = i + 1;
	if(bajo < FILAS && t[bajo][j].contenido !=BORDE && t[bajo][j].contenido != FANTASMA)
	{
	  // abajo puedo... as¡ que ac  paramos
	  return bajo;
	}
  }
  else
  {
	if (i > py)
	{
	  // el pacman est  en una fila menor a mi posici¢n
	  int subo = i - 1;
	  if(subo > -1 && t[subo][j].contenido != BORDE && t[subo][j].contenido != FANTASMA)
	  {
		// arriba puedo... as¡ que ac  paramos
		return subo;
	  }
	}
  }
  // y si no podemos hacer otra cosa, salimos con -1
  return -1;
}

/**
   Busca acercar el fantasmita una columna hacia el pacman. La nueva
   columna vuelve en col. Si no pudo cambiar de columna, col vuelve
   igual a como entr¢
*/
int acercarColumna(Fantasmita f, Pacman p, Casilla t[][COLUMNAS])
{
  int i = f.y,  j = f.x;
  int px = p.x;

  if (j < px)
  {
	// el pacman est  en una columna mayor a mi posici¢n
	int der = j + 1;
	if(der < COLUMNAS && t[i][der].contenido !=BORDE && t[i][der].contenido != FANTASMA)
	{
	  // a la derecha puedo... as¡ que ac  paramos
	  return der;
	}
  }
  else
  {
	if (j > px)
	{
	  // el pacman est  en una columna menor a mi posici¢n
	  int izq = j - 1;
	  if(izq > -1 && t[i][izq].contenido != BORDE && t[i][izq].contenido != FANTASMA)
	  {
		// a la izquierda puedo... as¡ que ac  paramos
		return izq;
	  }
	}
  }
  // y si no podemos hacer otra cosa, salimos con -1
  return -1;
}

/**
   Determina si es posible seguir en la misma direcci¢n que tra¡a el
   fantasmita. Devuelve una constante que indica por donde seguir. Si
   el camino se abr¡a en dos o mas, retorna -1 para provocar una decisi¢n
   aleatoria
*/
int  analizarPorDondeSigue(Fantasmita f, Casilla t[][COLUMNAS])
{
   Tipos c1, c2, c3;
   int p = f.direccion;
   int i = f.y, j = f.x;
   int izq = j - 1, der = j + 1, subo = i - 1, bajo = i + 1;
   int indice = -1;
   switch(p)
   {
	  case UP:    if (subo > -1)
				  {
					c1 = t[subo][j].contenido;
					if (c1 != BORDE && c1 != FANTASMA)
					{
					  if (izq > -1)
					  {
						c2 = t[i][izq].contenido;
						if (c2 != BORDE && c2 != FANTASMA) break;
					  }
					  if (der < COLUMNAS)
					  {
						c3 = t[i][der].contenido;
						if(c3 != BORDE && c3 != FANTASMA) break;
					  }
					  indice = UP;
					}
				  }
				  break;

	  case DOWN:  if (bajo < FILAS)
				  {
					c1 = t[bajo][j].contenido;
					if (c1 != BORDE && c1 != FANTASMA)
					{
					  if (izq > -1)
					  {
						c2 = t[i][izq].contenido;
						if (c2 != BORDE && c2 != FANTASMA) break;
					  }
					  if (der < COLUMNAS)
					  {
						c3 = t[i][der].contenido;
						if(c3 != BORDE && c3 != FANTASMA) break;
					  }
					  indice = DOWN;
					}
				  }
				  break;

	  case LEFT:  if (izq > -1)
				  {
					c1 = t[i][izq].contenido;
					if (c1 != BORDE && c1 != FANTASMA)
					{
					  if (subo > -1)
					  {
						c2 = t[subo][j].contenido;
						if (c2 != BORDE && c2 != FANTASMA) break;
					  }
					  if (bajo < FILAS)
					  {
						c3 = t[bajo][j].contenido;
						if(c3 != BORDE && c3 != FANTASMA) break;
					  }
					  indice = LEFT;
					}
				  }
				  break;


	  case RIGHT: if (der > COLUMNAS)
				  {
					c1 = t[i][der].contenido;
					if (c1 != BORDE && c1 != FANTASMA)
					{
					  if (subo > -1)
					  {
						c2 = t[subo][j].contenido;
						if (c2 != BORDE && c2 != FANTASMA) break;
					  }
					  if (bajo < FILAS)
					  {
						c3 = t[bajo][j].contenido;
						if(c3 != BORDE && c3 != FANTASMA) break;
					  }
					  indice = RIGHT;
					}
				  }
				  break;
   }
   return indice;
}



/**
   Calcula en forma totalmente aleatoria las coordenadas del pr¢ximo
   movimiento del pacman. Retorna esas coordenadas en col y fil. Si no
   hab¡a movimiento posible, ambas quedan como entraron
*/
void moverEnFormaAleatoria (Fantasmita f, Casilla t[][COLUMNAS], int &col, int &fil)
{
   /**
	 Tiro la moneda: si sale 0 intento primero un paso horizontal,
	 si sale 1 intento  un paso vertical. Si el que sali¢ no es posible,
	 intento el otro. Si ninguno es posible, dejo AMBAS coordenadas en -1
	 y salgo;
   */
   int i = f.y, j = f.x;
   int der  =  j + 1, izq  = j - 1;
   int bajo =  i + 1, subo = i - 1;
   int v, hv = random(2);
   for(int vuelta = 1; vuelta <= 2; vuelta++)
   {
		 if(hv == 0)
		 {
			// Hago primero un intento de paso horizontal

			// Tiro de nuevo la moneda:  0 voy a la izquierda, 1 a la derecha
			int id = random(2);
			for(v=1; v<=2; v++)
			{
			  if(id == 1)
			  {
				 // intento ir a la derecha
				 if(der < COLUMNAS && t[i][der].contenido != BORDE && t[i][der].contenido != FANTASMA)
				 {
				   col = der;
				   fil = i;
				   return;
				 }
				 if(v==2)
				 {
					break;
				 }
			  }

			  // Si lo anterior fall¢, entonces pruebo a la izquierda
			  if(izq > -1 && t[i][izq].contenido !=BORDE && t[i][izq].contenido != FANTASMA)
			  {
				col = izq;
				fil = i;
				return;
			  }
			  if(id==0) { id = 1; }
			  else      { break;  }
			}

			if(vuelta == 2)
			{
			   /*
				 Si estoy en la segunda vuelta y sigo vivo, es que no hab¡a
				 movimiento posible... Bye, Bye!!!

				 O sea: si llegu‚ hasta ac  es que el dichoso fantasmita
				 estaba atrapado. En este caso, col y fil quedan valiendo -1,
				 y no hay m s que hacer que volver...
			   */
			   return;
			}

		 }
		 /*
			Si llegu‚ hasta ac , es que no pude (o no deb¡a hacerlo
			primero) moverme en horizontal. Entonces intento un paso
			vertical.
		 */

		 // Tiro la moneda: si es 0 subo en la pantalla, si es 1 bajo
		 int sb = random(2);
		 for(v = 1; v <= 2; v++)
		 {
			if (sb == 0)
			{
			  // subo en la pantalla, o sea: en la matriz resto!!!
			  if(subo > -1 && t[subo][j].contenido != BORDE && t[subo][j].contenido != FANTASMA)
			  {
				col = j;
				fil = subo;
				return;
			  }
			  if(v==2)
			  {
				break;
			  }
			}

			/*
			   Si lo anterior fall¢, o no deb¡a subir primero, entonces
			   pruebo hacia abajo (o sea, sumando!!)
			*/
			if(bajo < FILAS && t[bajo][j].contenido !=BORDE && t[bajo][j].contenido != FANTASMA)
			{
			  col = j;
			  fil = bajo;
			  return;
			}
			if(sb==1) { sb = 0; }
			else      { break;  }
		 }

		 /*
		   Para forzar a que en la siguiente vuelta del ciclo, pase por
		   la primera rama del if...
		 */
		 if (hv == 1) { hv = 0;  }
		 else         { return; }
   }
}


/**
   Dibuja una galleta en la casilla pedida por "i" y "j"
*/
void dibujarComida(Casilla t[][COLUMNAS], int i, int j)
{
	int cx = t[i][j].x + CELDA /2;
	int cy = t[i][i].y + CELDA /2;
	t[i][j].contenido = COMIDA;
	int r  = CELDA /7;
	setcolor(YELLOW);
	circle(cx, cy, r);
	setfillstyle(1,YELLOW);
	floodfill(cx,cy,YELLOW);
}

/**
   Si la posici¢n de alguno de los fantasmitas coincide con la del pacman,
   retorna 1. Si no, retorna 0.
*/
int  pacmanAtrapado(Fantasmita f[3], Pacman p, Casilla t[][COLUMNAS], void *pac)
{
   int i;
   for (i = 0; i < 3; i++)
   {
	  if (f[i].x == p.x && f[i].y == p.y) return 1;
   }
   return 0;
}
