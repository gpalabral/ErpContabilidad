USE ERP;

/**
Creacion de la vista par_active_directory
*/
CREATE VIEW par_active_directory AS
    (
        select 
            codigo,            
            descripcion
        from
            CNF.PAR_VALOR
        where
            contexto = 'ACTIVEDIRECTORY' and fecha_baja is null
    )
INSERT INTO CNF.PAR_VALOR (fecha_alta, fecha_baja, fecha_modificacion, usuario_alta, usuario_baja, usuario_modificacion, codigo, contexto, descripcion, valor) 
VALUES (GETDATE(), NULL, GETDATE(),'test','test','test', 'HOS', 'ACTIVEDIRECTORY', 'HOST','192.168.1.235');
INSERT INTO CNF.PAR_VALOR (fecha_alta, fecha_baja, fecha_modificacion, usuario_alta, usuario_baja, usuario_modificacion, codigo, contexto, descripcion, valor) 
VALUES (GETDATE(), NULL, GETDATE(),'test','test','test', 'DOM', 'ACTIVEDIRECTORY', 'DOMINIO','bap.com');


USE ERP

/*ACTIVE DIRECTORY*********************/
CREATE VIEW CNF.PAR_PARAMETROS_ACTIVE_DIRECTORY AS 
SELECT CODIGO,DESCRIPCION,VALOR FROM CNF.PAR_VALOR
WHERE CONTEXTO='ACTIVE_DIRECTORY' AND fecha_baja is null
GO

INSERT INTO CNF.[PAR_VALOR]
	([CODIGO],[DESCRIPCION],[CONTEXTO],[VALOR],[TIPO_DATO],[FECHA_ALTA],[USUARIO_ALTA])
VALUES
/*TIPO COMPROBANTE*/
('HOST','HOST','ACTIVE_DIRECTORY','192.168.1.235',NULL,GETDATE(),'SIS'),
('DOM','DOMINIO','ACTIVE_DIRECTORY','bap.com',NULL,GETDATE(),'SIS')
GO