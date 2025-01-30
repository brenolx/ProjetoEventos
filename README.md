# ProjetoEventos

-- Criando o banco de dados e selecionando-o
CREATE DATABASE IF NOT EXISTS `gerenciadoreventos`;
USE `gerenciadoreventos`;

-- Criando a tabela de eventos
CREATE TABLE `eventos` (
  `id` int(11) NOT NULL AUTO_INCREMENT, -- ID único do evento
  `titulo` varchar(255) NOT NULL, -- Título do evento
  `descricao` text DEFAULT NULL, -- Descrição opcional
  `data_hora` datetime NOT NULL, -- Data e hora do evento
  `duracao_horas` int(11) NOT NULL, -- Duração do evento em horas
  `local` varchar(255) DEFAULT NULL, -- Local do evento (opcional)
  `capacidade_maxima` int(11) NOT NULL, -- Número máximo de participantes
  `status` enum('FECHADO','ABERTO','ENCERRADO','CANCELADO') DEFAULT 'FECHADO', -- Status do evento
  `categoria` enum('PALESTRA','WORKSHOP','CONFERENCIA') NOT NULL, -- Tipo de evento
  `preco` decimal(10,2) DEFAULT 0.00, -- Preço do evento (padrão: 0)
  `organizador_id` int(11) NOT NULL, -- Referência ao organizador
  PRIMARY KEY (`id`),
  KEY `organizador_id` (`organizador_id`) -- Índice para organizador
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Criando a tabela de inscrições
CREATE TABLE `inscricoes` (
  `id` int(11) NOT NULL AUTO_INCREMENT, -- ID único da inscrição
  `participante_id` int(11) NOT NULL, -- Referência ao participante
  `evento_id` int(11) NOT NULL, -- Referência ao evento
  `data_inscricao` datetime DEFAULT current_timestamp(), -- Data da inscrição (padrão: data atual)
  `status_inscricao` enum('ATIVA','CANCELADA','PENDENTE') DEFAULT 'ATIVA', -- Status da inscrição
  PRIMARY KEY (`id`),
  UNIQUE KEY `participante_evento` (`participante_id`,`evento_id`), -- Garante que um participante só se inscreva uma vez por evento
  KEY `evento_id` (`evento_id`) -- Índice para evento
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Criando a tabela de usuários
CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT, -- ID único do usuário
  `nome_completo` varchar(255) NOT NULL, -- Nome completo do usuário
  `email` varchar(255) NOT NULL UNIQUE, -- Email único para login
  `senha` varchar(255) NOT NULL, -- Senha de acesso (armazenar hash)
  `tipo_usuario` enum('ADMINISTRADOR','PARTICIPANTE') NOT NULL, -- Tipo de usuário
  `cargo` varchar(255) DEFAULT NULL, -- Cargo (opcional, apenas para administradores)
  `data_contratacao` date DEFAULT NULL, -- Data de contratação (opcional)
  `data_nascimento` date DEFAULT NULL, -- Data de nascimento do usuário
  `cpf` varchar(14) DEFAULT NULL, -- CPF do usuário (opcional)
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Criando chaves estrangeiras
ALTER TABLE `eventos`
  ADD CONSTRAINT `eventos_organizador_fk` FOREIGN KEY (`organizador_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE;

ALTER TABLE `inscricoes`
  ADD CONSTRAINT `inscricoes_participante_fk` FOREIGN KEY (`participante_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `inscricoes_evento_fk` FOREIGN KEY (`evento_id`) REFERENCES `eventos` (`id`) ON DELETE CASCADE;

COMMIT;
