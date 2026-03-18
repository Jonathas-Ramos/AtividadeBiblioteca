CREATE DATABASE biblioteca_db;
USE biblioteca_db;

-- 1. Tabela de Livros
CREATE TABLE Livro (
    id VARCHAR(50) PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    ano_publicacao INT,
    quantidade_disponivel INT,
    categoria VARCHAR(100)
);

-- 2. Tabela de Usuários (Guarda tanto Alunos quanto Professores)
CREATE TABLE Usuario (
    matricula VARCHAR(50) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    endereco VARCHAR(255),
    tipo_usuario VARCHAR(20) NOT NULL, -- Vai guardar 'ALUNO' ou 'PROFESSOR'
    curso VARCHAR(100),       -- Fica nulo se for professor
    departamento VARCHAR(100) -- Fica nulo se for aluno
);

-- 3. Tabela de Empréstimos (Faz a ligação entre quem pegou e o que pegou)
CREATE TABLE Emprestimo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    matricula_usuario VARCHAR(50),
    id_livro VARCHAR(50),
    data_retirada DATE NOT NULL,
    data_prevista_devolucao DATE NOT NULL,
    data_devolucao_real DATE,
    situacao VARCHAR(50),
    FOREIGN KEY (matricula_usuario) REFERENCES Usuario(matricula),
    FOREIGN KEY (id_livro) REFERENCES Livro(id)
);

-- 4. Tabela de Reservas
CREATE TABLE Reserva (
    id INT AUTO_INCREMENT PRIMARY KEY,
    matricula_usuario VARCHAR(50),
    id_livro VARCHAR(50),
    data_solicitacao DATE NOT NULL,
    data_expiracao DATE NOT NULL,
    ativa BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (matricula_usuario) REFERENCES Usuario(matricula),
    FOREIGN KEY (id_livro) REFERENCES Livro(id)
);

-- 5. Tabela de Multas (Ligada diretamente ao Empréstimo que gerou o atraso)
CREATE TABLE Multa (
    id_emprestimo INT PRIMARY KEY,
    valor DECIMAL(10, 2) NOT NULL,
    dias_atraso INT NOT NULL,
    FOREIGN KEY (id_emprestimo) REFERENCES Emprestimo(id)
);