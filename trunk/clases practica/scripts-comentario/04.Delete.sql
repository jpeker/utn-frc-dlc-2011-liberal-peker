-- =============================================================================
-- Comentarios
-- =============================================================================
-- Borro una foto 1
SELECT pr_deletecomentario(1);
-- Borro una foto 2
SELECT pr_deletecomentario(2);

-- =============================================================================
-- FOTOS
-- =============================================================================
-- Borro una foto 1
SELECT pr_deletefoto(1);
-- Borro una foto 2
SELECT pr_deletefoto(2);
-- =============================================================================
-- AMIGOS
-- =============================================================================
-- Borro un amigo 1
SELECT pr_deleteamigo(1,2); 
-- Borro un amigo 2
SELECT pr_deleteamigo(1,3); 
-- =============================================================================
-- Usuarios
-- =============================================================================
-- Borro un usuario 1
SELECT  pr_deleteusuario (1);
-- Borro un usuario 2
SELECT  pr_deleteusuario (2);
-- Borro un usuario 3
SELECT  pr_deleteusuario (3);