# ProjetoEventos
Criação Bando de Dados
-- Criação do Banco de Dados
CREATE DATABASE GerenciadorEventos;
USE GerenciadorEventos;

-- Tabela usuarios
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome_completo VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    tipo_usuario ENUM('ADMINISTRADOR', 'PARTICIPANTE') NOT NULL,
    cargo VARCHAR(255),
    data_contratacao DATE,
    data_nascimento DATE,
    cpf VARCHAR(14)
);

-- Tabela eventos
CREATE TABLE eventos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    data_hora DATETIME NOT NULL,
    duracao_horas INT NOT NULL,
    local VARCHAR(255),
    capacidade_maxima INT NOT NULL,
    status ENUM('FECHADO', 'ABERTO', 'ENCERRADO', 'CANCELADO') DEFAULT 'FECHADO',
    categoria ENUM('PALESTRA', 'WORKSHOP', 'CONFERENCIA') NOT NULL,
    preco DECIMAL(10, 2) DEFAULT 0.00,
    organizador_id INT NOT NULL,
    FOREIGN KEY (organizador_id) REFERENCES usuarios(id)
);

-- Tabela inscricoes
CREATE TABLE inscricoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    participante_id INT NOT NULL,
    evento_id INT NOT NULL,
    data_inscricao DATETIME DEFAULT CURRENT_TIMESTAMP,
    status_inscricao ENUM('ATIVA', 'CANCELADA', 'PENDENTE') DEFAULT 'ATIVA',
    presenca_confirmada BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (participante_id) REFERENCES usuarios(id),
    FOREIGN KEY (evento_id) REFERENCES eventos(id),
    UNIQUE (participante_id, evento_id)
);

-- Tabela relatorios
CREATE TABLE relatorios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo_relatorio ENUM('PARTICIPACAO_EVENTO', 'EVENTOS_POPULARES', 'EVENTOS_ABERTOS', 'DETALHES_EVENTO') NOT NULL,
    usuario_id INT NOT NULL,
    data_geracao DATETIME DEFAULT CURRENT_TIMESTAMP,
    arquivo_path VARCHAR(255),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
