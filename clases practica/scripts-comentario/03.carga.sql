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
SELECT fn_savefoto(1,'fotito.jpg',1,2,'20100120134510');
-- Cargo foto 2 
SELECT fn_getidfoto();
SELECT fn_savefoto(2,'fin.jpg',1,3,'20110122134510');
-- Cargo foto 3 
SELECT fn_getidfoto();
SELECT fn_savefoto(3,'prueba.jpg',1,2,'20110121134510');
-- Cargo foto 3 
SELECT fn_getidfoto();
SELECT fn_savefoto(4,'tata.jpg',3,4,'20110611134510');

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
SELECT fn_savecomentario(7,1,2,'felicidades loco que empieses bien');
-- Cargo com  7 
SELECT fn_getidcomentario();
SELECT fn_savecomentario(8,3,2,'igualmente nos vemos en la vacas jeje ');
