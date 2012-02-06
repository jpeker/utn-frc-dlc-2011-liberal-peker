-- USUARIO
-- =============================================================================
-- La siguiente función obtiene el próximo id de usuario para ser insertado.
CREATE OR REPLACE FUNCTION fn_Get_Id_Word (
) RETURNS INTEGER AS $$

    DECLARE
        var_Id_Word               INTEGER         := NULL;

    BEGIN
        var_Id_Word:= NEXTVAL('sq_Word');
        RETURN var_Id_Word;
    END;
$$ LANGUAGE plpgsql;
-- La siguiente función "guarda" un usuario en la BBDD.
-- Toma como parámetros de entrada los datos del mismo.
-- Entre ellos, el id.
-- Si existe lo actualiza.
-- Si no existe lo crea.
CREATE OR REPLACE FUNCTION fn_Save_Word (
    pin_id_Word                    INTEGER,
    pin_name_Word                  VARCHAR(32),
    pin_nr                     	   INTEGER,
    pin_max_Tf                     INTEGER
   
  
) RETURNS SMALLINT AS $$

    DECLARE
        var_idusuario                INTEGER         := pin_idusuario;
        var_nombre                   VARCHAR         := TRIM(pin_nombre);
        var_apellido                 VARCHAR         := TRIM(pin_apellido);
        var_mail                     VARCHAR         := TRIM(pin_mail);
        var_count                    INTEGER         := 0;
 BEGIN
            -- cuento usuarios
            SELECT COUNT(*)
            INTO var_count
            FROM usuario u
            WHERE u.idUsuario = var_idusuario;
            
                -- veo si existe
            IF (var_count > 0) THEN
            UPDATE usuario u SET -- sí existe ==> update
                nombre = var_nombre,
                apellido = var_apellido,
                mail = var_mail
             
            WHERE u.idUsuario = var_idusuario;
        ELSE -- no existe ==> insert
            IF (var_idusuario IS NULL) THEN
                var_idusuario := fn_getidusuario();
            END IF;
            INSERT INTO usuario(idUsuario, nombre, apellido, mail)
                VALUES (var_idusuario, var_nombre, var_apellido, var_mail);
        END IF;

        RETURN var_idusuario;
    END;
$$ LANGUAGE plpgsql;
-- La siguiente función elimina un usuario de la BBDD.
CREATE OR REPLACE FUNCTION pr_deleteusuario (
    pin_idusuario                    INTEGER
) RETURNS VOID AS $$

    DECLARE   
    var_count                    INTEGER         := 0;
    BEGIN
             -- Cuento amigos del usuario a eliminar
            SELECT COUNT(*)
            INTO var_count
            FROM amigo a
            WHERE a.idAmigo1 = pin_idusuario  OR a.idAmigo2 = pin_idusuario;

            IF (var_count = 0) THEN -- si no tiene amigo lo elimino
            DELETE FROM usuario u
            WHERE u.idUsuario = pin_idusuario;
            END IF;

        RETURN;
    END;
$$ LANGUAGE plpgsql;
-- =============================================================================
-- Amigos
-- =============================================================================
-- La siguiente función "guarda" un usuario en la BBDD.
-- Toma como parámetros de entrada los datos del mismo.
-- Si existe lo actualiza.
-- Si no existe lo crea.
CREATE OR REPLACE FUNCTION fn_saveamigo(
    pin_idamigo1                     INTEGER,
    pin_idamigo2                     INTEGER
) RETURNS SMALLINT AS $$

    DECLARE
        var_idAmigo1                INTEGER         := pin_idamigo1;
        var_idAmigo2                INTEGER         := pin_idamigo2;
    
        var_count                    INTEGER         := 0;
 BEGIN

            SELECT COUNT(*) -- cuento amigos
            INTO var_count
            FROM amigo a
            WHERE a.idAmigo1 = var_idAmigo1 and a.idAmigo2 = var_idAmigo2;

            IF (var_count = 0) THEN -- veo si  existe, si no existe ---> Insert
          
         
            INSERT INTO amigo(idAmigo1, idAmigo2)
                VALUES (var_idAmigo1, var_idAmigo2);
        END IF;

        RETURN var_idAmigo1;
    END;
$$ LANGUAGE plpgsql;
-- La siguiente función elimina un amigo de la BBDD.
CREATE OR REPLACE FUNCTION pr_deleteamigo (
    pin_idamigo1                    INTEGER,
    pin_idamigo2                    INTEGER
) RETURNS VOID AS $$

    DECLARE
       var_count                    INTEGER         := 0;

    BEGIN
           SELECT COUNT(*) -- cuento fotos
            INTO var_count
            FROM foto f
            WHERE f.idAmigo1 = pin_idamigo1 and f.idAmigo2 = pin_idamigo2;
            IF (var_count = 0) THEN --veo que no tenga ninguna foto y elimino      
            DELETE FROM amigo a
            WHERE a.idAmigo1 = pin_idamigo1 and a.idAmigo2 = pin_idamigo2;
            END IF;
        RETURN;
    END;
$$ LANGUAGE plpgsql;
-- =============================================================================
-- FOTOS
-- =============================================================================
-- La siguiente función obtiene el próximo id de la foto para ser insertado.
CREATE OR REPLACE FUNCTION fn_getidfoto (
) RETURNS INTEGER AS $$

    DECLARE
        var_idFoto                INTEGER         := NULL;

    BEGIN
        var_idFoto:= NEXTVAL('sq_Foto');
        RETURN var_idFoto;
    END;
$$ LANGUAGE plpgsql;
-- La siguiente función "guarda" una foto en la BBDD.
-- Toma como parámetros de entrada los datos del mismo.
-- Entre ellos, el id.
-- Si existe lo actualiza.
-- Si no existe lo crea.
CREATE OR REPLACE FUNCTION fn_savefoto (
    pin_idfoto			     INTEGER,
    pin_pathfoto                     VARCHAR,
    pin_idamigo1                     INTEGER,
    pin_idamigo2                     INTEGER,
    pin_fecha                        VARCHAR
   
) RETURNS SMALLINT AS $$

    DECLARE
        var_idfoto		     INTEGER		:= pin_idfoto;
	var_pathfoto                 VARCHAR		:= TRIM(pin_pathfoto);
        var_idAmigo1                 INTEGER		:= pin_idamigo1; 
        var_idAmigo2                 INTEGER		:= pin_idamigo2;
        var_fecha                    TIMESTAMP		:= NULL;

        var_count                    INTEGER		:= 0;
        var_count1                   INTEGER		:= 0;
        var_count2                   INTEGER         	:= 0;
 BEGIN
             -- convierto fechas
            var_fecha := TO_TIMESTAMP(pin_fecha, 'YYYYMMDDHH24MISS');
            SELECT COUNT(*) -- cuento fotos
            INTO var_count
            FROM foto f
            WHERE f.idfoto = var_idfoto;
            SELECT COUNT(*) -- cuento usuarios sin mail
            INTO var_count1
            FROM usuario u
            WHERE (u.idUsuario = var_idAmigo1 OR u.idUsuario = var_idAmigo2) and u.mail is null ;
        IF (var_count > 0) THEN -- veo si existe la foto
            --SELECT COUNT(*) -- Existe la foto, cuento fotos que no tenga comentario
            --INTO var_count2
            --FROM comentario c
            --WHERE c.idfoto = var_idfoto;
	   
	    IF( var_count1 = 0 and var_count2 = 0)THEN -- veo si el usuario a modificar tiene  mail y la foto no tiene comentario
            UPDATE foto f SET -- actualizo la foto
                idAmigo1 = var_idAmigo1,
                idAmigo2 = var_idAmigo2,
                fecha = var_fecha,
                pathfoto = var_pathfoto
                WHERE idfoto = var_idfoto;
            END IF;
        ELSE -- no existe ==> insert
            IF (var_count1 = 0) THEN -- veo si el usuario tiene mail
            INSERT INTO foto(idfoto, pathfoto, idAmigo1, idAmigo2, fecha)
                VALUES (var_idfoto,var_pathfoto, var_idAmigo1, var_idAmigo2, var_fecha);
            END IF;
        END IF;
        RETURN var_idAmigo1;
    END;
$$ LANGUAGE plpgsql;
-- La siguiente función elimina un cliente de la BBDD.
CREATE OR REPLACE FUNCTION pr_deletefoto(
    pin_idfoto                    INTEGER
) RETURNS VOID AS $$
    BEGIN

        DELETE FROM foto f
            WHERE f.idfoto = pin_idfoto;

        RETURN;
    END;
$$ LANGUAGE plpgsql;

-- =============================================================================
-- Comentarios
-- =============================================================================
CREATE OR REPLACE FUNCTION fn_getidcomentario (
) RETURNS INTEGER AS $$

    DECLARE
        var_idcomentario                INTEGER         := NULL;

    BEGIN
        var_idcomentario:= NEXTVAL('sq_comentario');
        RETURN var_idcomentario;
    END;
$$ LANGUAGE plpgsql;

-- La siguiente función "guarda" un usuario en la BBDD.
-- Toma como parámetros de entrada los datos del mismo.
-- Si existe lo actualiza.
-- Si no existe lo crea.

CREATE OR REPLACE FUNCTION fn_savecomentario(
    pin_idcomentario                     INTEGER,
    pin_idusuario                     INTEGER,
    pin_idfoto                          INTEGER,
    pin_comentario                      VARCHAR
) RETURNS SMALLINT AS $$

    DECLARE
        var_idcomentario                   INTEGER         := pin_idcomentario;
        var_idusuario                      INTEGER         := pin_idusuario;
        var_idfoto                      INTEGER            := pin_idfoto;
        var_comentario                     VARCHAR         :=TRIM(pin_comentario);
        var_count                           INTEGER        := 0;
 BEGIN

            SELECT COUNT(*) -- cuento comentarios
            INTO var_count
            FROM comentario c
            WHERE c.idcomentario = var_idcomentario;

            IF (var_count = 0) THEN -- veo si  existe, si no existe ---> Insert
          
         
            INSERT INTO comentario(idcomentario, idusuario,idfoto,comentarios)
                VALUES (var_idcomentario, var_idusuario, var_idfoto, var_comentario);
        END IF;

        RETURN var_idcomentario;
    END;
$$ LANGUAGE plpgsql;
-- La siguiente función elimina un amigo de la BBDD.
CREATE OR REPLACE FUNCTION pr_deletecomentario (
    pin_idcomentario                    INTEGER
    
) RETURNS VOID AS $$

    DECLARE
       var_count                    INTEGER         := 0;

    BEGIN
            
            DELETE FROM comentario c
            WHERE c.idcomentario = pin_idcomentario;
         
        RETURN;
    END;
$$ LANGUAGE plpgsql;