-- =============================================================================
-- USUARIOS
-- =============================================================================
-- Cargo usuario 1
SELECT fn_getidusuario();
SELECT fn_saveusuario(1, 'julian', 'peker', 'julian@hotmail.com');
-- Cargo usuario 2
SELECT fn_getidusuario();
SELECT fn_saveusuario(2, 'rodrigo', 'liberal','rodrigo@hotmail.com');
--Cargo usuario 3
SELECT fn_getidusuario();
SELECT fn_saveusuario(3, 'juan','perez','juanperez@hotmail.com');
--Cargo usuario 4
SELECT fn_getidusuario();
SELECT fn_saveusuario(4, 'gianna','ramos','gianna_cba@hotmail.com');
-- Corrijo Dirección de usuario 1
SELECT fn_saveusuario(1, 'julian', 'peker', 'julianpeker@hotmail.com');

-- =============================================================================
-- AMIGOS
-- =============================================================================
-- Cargo un amigo 1
SELECT fn_saveamigo(1,2);
-- Cargo un amigo 2
SELECT fn_saveamigo(1,3);
-- Cargo un amigo 3
SELECT fn_saveamigo(2,1);
-- Cargo un amigo 4
SELECT fn_saveamigo(3,4);
-- Cargo un amigo 5
SELECT fn_saveamigo(3,1);
-- Cargo un amigo 6
SELECT fn_saveamigo(4,3);
-- =============================================================================
-- FOTO
-- =============================================================================
-- Cargo foto 1
SELECT fn_getidfoto();
SELECT fn_savefoto(1,'img/fotito.jpg',1,2,'20100120134510');
-- Cargo foto 2 
SELECT fn_getidfoto();
SELECT fn_savefoto(2,'img/fin.jpg',1,3,'20110122134510');
-- Cargo foto 3 
SELECT fn_getidfoto();
SELECT fn_savefoto(3,'img/prueba.jpg',1,2,'20110121134510');
-- Cargo foto 4 
SELECT fn_getidfoto();
SELECT fn_savefoto(4,'img/tata.jpg',3,4,'20110611134510');
-- Cargo foto 5 
SELECT fn_getidfoto();
SELECT fn_savefoto(5,'img/infer.jpg',1,2,'20110617134511');
-- Cargo foto 6 
SELECT fn_getidfoto();
SELECT fn_savefoto(6,'img/manzana.jpg',1,3,'20110605134511');
-- Cargo foto 7 
SELECT fn_getidfoto();
SELECT fn_savefoto(7,'img/hermano.jpg',3,4,'20110620134511');

-- Intento ingresar una foto con un usuario sin correo de mail
--SELECT fn_savefoto(1,'C:\\misdocumentos\fotofiesta.jpg',1,2,'20110120134510');

-- =============================================================================
-- COMENTARIO
-- =============================================================================
-- Cargo com 1
SELECT fn_getidcomentario();
SELECT fn_savecomentario(1,1,3,'es loco infer');
-- Cargo com  2 
SELECT fn_getidcomentario();
SELECT fn_savecomentario(2,2,3,' que grosso peker');
-- Cargo com 3
SELECT fn_getidcomentario();
SELECT fn_savecomentario(3,1,1,'que noche se viene');
-- Cargo com  4 
SELECT fn_getidcomentario();
SELECT fn_savecomentario(4,2,1,'puede ser');
-- Cargo com  5 
SELECT fn_getidcomentario();
SELECT fn_savecomentario(5,3,4,'tas hermosa gianna hace mucho que no te veo');
-- Cargo com  6 
SELECT fn_getidcomentario();
SELECT fn_savecomentario(6,4,4,'es verdad hay que organizar juntada');
-- Cargo com  7 
SELECT fn_getidcomentario();
SELECT fn_savecomentario(7,1,2,'felicidades loco que empieses bien');
-- Cargo com  8 
SELECT fn_getidcomentario();
SELECT fn_savecomentario(8,3,2,'igualmente nos vemos en la vacas jeje ');
-- Cargo com  9 
SELECT fn_getidcomentario();
SELECT fn_savecomentario(9,2,5,'muy buena las chicas de santa fe');
-- Cargo com  10 
SELECT fn_getidcomentario();
SELECT fn_savecomentario(10,1,6,'jajaj malisimo ese logo hay que elegir otro');
-- Cargo com  11 
SELECT fn_getidcomentario();
SELECT fn_savecomentario(11,3,7,'como hacia frio ese dia pero tuvo bueno');
-- Cargo com  12 
SELECT fn_getidcomentario();
SELECT fn_savecomentario(12,4,7,'si estaba helado');
-- Cargo com  13 
SELECT fn_getidcomentario();
SELECT fn_savecomentario(13,3,6,'ya me voy a poner  buscar uno');
-- Cargo com  9 
SELECT fn_getidcomentario();
SELECT fn_savecomentario(14,1,5,'si re lindas hay que volver a ir jeje :P');