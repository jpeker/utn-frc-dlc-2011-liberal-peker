-- =============================================================================
-- Comentarios
-- =============================================================================
-- Borro una comentario 1
SELECT pr_deletecomentario(1);
-- Borro una comentario 2
SELECT pr_deletecomentario(2);
-- Borro una comentario 3
SELECT pr_deletecomentario(3);
-- Borro una comentario 4
SELECT pr_deletecomentario(4);
-- Borro una comentario 5
SELECT pr_deletecomentario(5);
-- Borro una comentario 6
SELECT pr_deletecomentario(6);
-- Borro una comentario 5
SELECT pr_deletecomentario(7);
-- Borro una comentario 6
SELECT pr_deletecomentario(8);
-- =============================================================================
-- FOTOS
-- =============================================================================
-- Borro una foto 1
SELECT pr_deletefoto(1);
-- Borro una foto 2
SELECT pr_deletefoto(2);
-- Borro una foto 3
SELECT pr_deletefoto(3);
-- Borro una foto 4
SELECT pr_deletefoto(4);
-- =============================================================================
-- AMIGOS
-- =============================================================================
-- Borro un amigo 1
SELECT pr_deleteamigo(1,2); 
-- Borro un amigo 2
SELECT pr_deleteamigo(1,3); 
-- Borro un amigo 2
SELECT pr_deleteamigo(2,1); 
-- Cargo un amigo 4
SELECT pr_deleteamigo(3,4);
-- Cargo un amigo 5
SELECT pr_deleteamigo(3,1);
-- Cargo un amigo 6
SELECT pr_deleteamigo(4,3);
-- =============================================================================
-- Usuarios
-- =============================================================================
-- Borro un usuario 1
SELECT  pr_deleteusuario (1);
-- Borro un usuario 2
SELECT  pr_deleteusuario (2);
-- Borro un usuario 3
SELECT  pr_deleteusuario (3);
-- Borro un usuario 3
SELECT  pr_deleteusuario (4);