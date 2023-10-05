-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           PostgreSQL 16rc1, compiled by Visual C++ build 1936, 64-bit
-- OS do Servidor:               
-- HeidiSQL Versão:              12.5.0.6677
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES  */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Copiando estrutura do banco de dados para public
CREATE DATABASE IF NOT EXISTS "public";
SET search_path TO "public";

-- Copiando estrutura para tabela public.contato
CREATE TABLE IF NOT EXISTS "contato" (
	"id" BIGINT NOT NULL DEFAULT 'nextval(''contato_id_seq''::regclass)',
	"cpf" VARCHAR(11) NOT NULL,
	"email" VARCHAR(80) NOT NULL,
	"nome" VARCHAR(25) NOT NULL,
	"nome_meio" VARCHAR(40) NULL DEFAULT NULL,
	"sobrenome" VARCHAR(25) NOT NULL,
	"telefone" VARCHAR(11) NOT NULL,
	"pessoa_id" BIGINT NULL DEFAULT NULL,
	PRIMARY KEY ("id"),
	UNIQUE INDEX "uk_v6ynadwmlgiar3tqrydve73q" ("cpf"),
	UNIQUE INDEX "uk_4ds5brlm0e7etaij4k5twocp4" ("email"),
	UNIQUE INDEX "uk_f829ydnj34uigkh6qie7mr2wp" ("telefone"),
	CONSTRAINT "fkqcaicb387g3yd3xfy44qravfh" FOREIGN KEY ("pessoa_id") REFERENCES "pessoa" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Copiando dados para a tabela public.contato: 0 rows
/*!40000 ALTER TABLE "contato" DISABLE KEYS */;
INSERT INTO "contato" ("id", "cpf", "email", "nome", "nome_meio", "sobrenome", "telefone", "pessoa_id") VALUES
	(1, '57186863000', 'nicolassantos@example.com', 'Nicolas', 'Bezerra dos Santos', 'Oliveira', '18514287327', 1),
	(3, '03027790005', 'charlesleclerc@example.com', 'Chalres', 'Perceval', 'LeClerc', '21954020014', 1),
	(2, '16652050080', 'arthuraguiar@example.com', 'Arthur', 'de Melo', 'Aguiar', '11984752136', 2),
	(5, '42538665060', 'vitorschiavon@example.com', 'Vitor', 'Carneiro', 'Schiavon', '11985254176', 2),
	(4, '02209182018', 'neymarjunior@example.com', 'Neymar', 'dos Santos', 'Junior', '34985654172', 3);
/*!40000 ALTER TABLE "contato" ENABLE KEYS */;

-- Copiando estrutura para tabela public.pessoa
CREATE TABLE IF NOT EXISTS "pessoa" (
	"id" BIGINT NOT NULL DEFAULT 'nextval(''pessoa_id_seq''::regclass)',
	"cpf" VARCHAR(11) NOT NULL,
	"data_nascimento" DATE NOT NULL,
	"nome" VARCHAR(20) NOT NULL,
	"nome_meio" VARCHAR(35) NULL DEFAULT NULL,
	"sobrenome" VARCHAR(35) NOT NULL,
	PRIMARY KEY ("id"),
	UNIQUE INDEX "uk_nlwiu48rutiltbnjle59krljo" ("cpf")
);

-- Copiando dados para a tabela public.pessoa: 0 rows
/*!40000 ALTER TABLE "pessoa" DISABLE KEYS */;
INSERT INTO "pessoa" ("id", "cpf", "data_nascimento", "nome", "nome_meio", "sobrenome") VALUES
	(1, '33403864049', '2004-11-25', 'Sergio', '', 'Perez'),
	(2, '09958327066', '1998-05-14', 'Pedro', 'Cabron', 'Almeida'),
	(3, '42481465091', '1992-03-25', 'David', '', 'Beckham');
/*!40000 ALTER TABLE "pessoa" ENABLE KEYS */;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
