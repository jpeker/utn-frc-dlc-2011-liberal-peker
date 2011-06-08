-- =============================================================================
-- USUARIO
-- =============================================================================
DROP SEQUENCE IF EXISTS sq_usuario CASCADE;
CREATE SEQUENCE sq_usuario;
DROP TABLE IF EXISTS usuario CASCADE;
CREATE TABLE usuario (
  idUsuario         	INTEGER                    	NOT NULL,
  nombre                VARCHAR(32)                     NOT NULL,
  apellido              VARCHAR(32)                     NOT NULL,
  mail                   VARCHAR(32),
  PRIMARY KEY (idUsuario)
);
-- =============================================================================
-- Amigos
-- =============================================================================
DROP SEQUENCE IF EXISTS sq_amigo CASCADE;
CREATE SEQUENCE sq_amigo;
DROP TABLE IF EXISTS amigo CASCADE;
CREATE TABLE amigo (
  idAmigo1            INTEGER                        NOT NULL,
  idAmigo2            INTEGER                        NOT NULL,

 
  PRIMARY KEY (idAmigo1,idAmigo2),
  FOREIGN KEY (idAmigo1)
    REFERENCES usuario(idUsuario),
  FOREIGN KEY (idAmigo2)
    REFERENCES usuario(idUsuario)
);
-- =============================================================================
-- Fotos
-- =============================================================================
DROP SEQUENCE IF EXISTS sq_foto CASCADE;
CREATE SEQUENCE sq_foto;
DROP TABLE IF EXISTS foto CASCADE;
CREATE TABLE foto (
  idfoto	      INTEGER			     NOT NULL,
  pathfoto            VARCHAR(60)                    NOT NULL,                    
  idAmigo1            INTEGER                        NOT NULL,
  idAmigo2            INTEGER                        NOT NULL,
  fecha               TIMESTAMP                      NOT NULL,

 
  PRIMARY KEY (idfoto),
  FOREIGN KEY (idAmigo1,idAmigo2)
    REFERENCES amigo(idAmigo1,idAmigo2)

);
-- =============================================================================
-- Comentarios
-- =============================================================================
DROP SEQUENCE IF EXISTS sq_comentario CASCADE;
CREATE SEQUENCE sq_comentario;
DROP TABLE IF EXISTS comentario CASCADE;
CREATE TABLE comentario (
  idcomentario	      INTEGER			     NOT NULL,
  idUsuario            INTEGER                        NOT NULL,
  idfoto            INTEGER                        NOT NULL,
  comentarios         VARCHAR(255), 
 
  PRIMARY KEY (idcomentario),
  FOREIGN KEY (idUsuario)
    REFERENCES usuario(idUsuario),
  FOREIGN KEY (idfoto)
    REFERENCES foto(idfoto)

);
