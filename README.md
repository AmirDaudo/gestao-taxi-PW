 CREATE DATABASE gestaorotas;

USE gestaorotas;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(20),
    email varchar(30),
    telefone INT,
    senha VARCHAR(25)
);


CREATE TABLE motoristas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    email VARCHAR(50) NOT NULL,
    senha VARCHAR(64) NOT NULL,  -- SHA-256 hash tem 64 caracteres
    marca_carro VARCHAR(50) NOT NULL,
    modelo_carro VARCHAR(50) NOT NULL,
    matricula VARCHAR(20) NOT NULL,
    disponibilidade VARCHAR(10) NOT NULL,
    foto1 VARCHAR(255),  -- Caminho da foto 1
    foto2 VARCHAR(255),  -- Caminho da foto 2
    foto3 VARCHAR(255),  -- Caminho da foto 3
    foto4 VARCHAR(255),  -- Caminho da foto 4
    status VARCHAR(10) DEFAULT 'offline',
    bloqueado BOOLEAN DEFAULT FALSE
);

CREATE TABLE denuncias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    motorista_id INT NOT NULL,
    mensagem TEXT NOT NULL,
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'Pendente',
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES Usuarios(id),
    CONSTRAINT fk_motorista FOREIGN KEY (motorista_id) REFERENCES Motoristas(id)
);

CREATE TABLE corridas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_nome VARCHAR(255) NOT NULL,
    motorista_id INT NOT NULL,
    pickup VARCHAR(255) NOT NULL,
    destino VARCHAR(255) NOT NULL,
    distancia DOUBLE NOT NULL,
    valor_estimado DOUBLE NOT NULL,
    status VARCHAR(50) NOT NULL,
    data_hora_solicitacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_motorista FOREIGN KEY (motorista_id) REFERENCES motoristas(id)
);



ALTER TABLE usuarios ADD reset_token VARCHAR(255);

ALTER TABLE motoristas ADD COLUMN status VARCHAR(10);

ALTER TABLE motoristas ADD COLUMN bloqueado BOOLEAN DEFAULT FALSE;
