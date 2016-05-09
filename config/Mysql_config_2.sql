use wikidb;
alter table revision change rev_id rev_id int(10) unsigned not null;
alter table revision change rev_comment  rev_comment varbinary(767) NOT NULL;
alter table text change old_id old_id int(10) unsigned not null;
alter table page change page_id page_id int(10) unsigned not null;
-- drop primary key
alter table revision drop primary key;
alter table text drop primary key;
alter table page drop primary key;
-- set charset
ALTER TABLE page CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
ALTER TABLE revision CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
DELETE FROM page; DELETE FROM text; DELETE FROM revision;
SHOW COLLATION;
-- add primary key again
alter table page add primary key (page_id);
alter table revision add primary key (rev_id);
alter table text add primary key (old_id);