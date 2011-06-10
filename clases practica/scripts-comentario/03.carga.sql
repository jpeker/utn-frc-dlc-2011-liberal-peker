-- =============================================================================
-- USUARIOS
-- =============================================================================
-- Cargo usuario 1
SELECT fn_getidusuario();
SELECT fn_saveusuario(1, 'julian 01', 'peker 01', 'julian@hotmail.com');
-- Cargo usuario 2
SELECT fn_getidusuario();
SELECT fn_saveusuario(2, 'rodrigo 02', 'liberal 02','rodrigo@hotmail.com');
--Cargo usuario 3
SELECT fn_getidusuario();
SELECT fn_saveusuario(3, 'juan 03','perez 03','juanperez@hotmail.com');
-- Corrijo Dirección de usuario 1
SELECT fn_saveusuario(1, 'julian 01', 'peker 01', 'julianpeker@hotmail.com');

-- =============================================================================
-- AMIGOS
-- =============================================================================
-- Cargo un amigo 1
SELECT fn_saveamigo(1,2);
-- Cargo un amigo 2
SELECT fn_saveamigo(1,3);
-- =============================================================================
-- FOTO
-- =============================================================================
-- Cargo foto 1
SELECT fn_getidfoto();
SELECT fn_savefoto(1,'C:\\misdocumentos\fotobariloche.jpg',1,3,'20100120134510');
-- Cargo foto 2 
SELECT fn_getidfoto();
SELECT fn_savefoto(2,'C:\\misdocumentos\fotoasado.jpg',1,3,'20110120134510');
-- Cargo foto 3 
SELECT fn_getidfoto();
SELECT fn_savefoto(3,'prueba.jpg',1,2,'20110120134510');
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