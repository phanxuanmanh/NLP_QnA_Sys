use wikidb;
set global max_connections = 500;
SHOW GLOBAL VARIABLES LIKE '%innodb_write_io_threads%';
-- set in C:\ProgramData\MySQL\MySQL Server 5.7\my.ini
-- SET GLOBAL innodb_buffer_pool_size=8M;
-- SET GLOBAL innodb_log_buffer_size=256M
-- SET GLOBAL innodb_log_file_size=1G
-- SET  innodb_write_io_threads = 16;
SET GLOBAL innodb_flush_log_at_trx_commit = 0;
SET GLOBAL innodb_fast_shutdown = 0;
SHOW VARIABLES WHERE Variable_name LIKE 'character\_set\_%' OR Variable_name LIKE 'collation%';
/*
add to  C:\ProgramData\MySQL\MySQL Server 5.7\my.ini
no-beep
default-character-set = utf8mb4
[mysql]
default-character-set = utf8mb4
[mysqld]
character-set-client-handshake = FALSE
character-set-server = utf8mb4
collation-server = utf8mb4_unicode_ci
*/
SET GLOBAL character_set_client =utf8mb4;
SET GLOBAL character_set_connection =utf8mb4;
SET GLOBAL  character_set_results=utf8mb4;
SET NAMES utf8mb4; 
ALTER DATABASE wikidb CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci;
ALTER TABLE page CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;