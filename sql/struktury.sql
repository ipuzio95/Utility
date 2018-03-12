DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `wez_samochody_pracownicze`()
BEGIN
 
                CREATE TEMPORARY TABLE sam ENGINE=MEMORY as
                (
                               SELECT p.imie, p.nazwisko, p.stanowisko, s.rejestracja
        FROM pracownicy p
        LEFT JOIN samochody s ON p.samochod_id = s.id
                );
   
    SELECT imie, nazwisko, stanowisko,
    CASE WHEN rejestracja IS NULL THEN 'nie ma samochodu' ELSE rejestracja END as rej
    FROM sam;
   
    DROP TEMPORARY TABLE sam;
 
END$$
DELIMITER ;
