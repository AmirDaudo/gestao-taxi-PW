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
    senha VARCHAR(50) NOT NULL,
    marca_carro VARCHAR(50) NOT NULL,
    modelo_carro VARCHAR(50) NOT NULL,
    matricula VARCHAR(20) NOT NULL,
    disponibilidade VARCHAR(10) NOT NULL,
    foto1 LONGBLOB,
    foto2 LONGBLOB,
    foto3 LONGBLOB,
    foto4 LONGBLOB
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

ALTER TABLE usuarios ADD reset_token VARCHAR(255);

ALTER TABLE motoristas ADD COLUMN status VARCHAR(10);

ALTER TABLE motoristas ADD COLUMN bloqueado BOOLEAN DEFAULT FALSE;
