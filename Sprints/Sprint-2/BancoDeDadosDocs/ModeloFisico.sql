-- Tabela: FUNCIONARIO
-- Descrição: Armazena os dados cadastrais fixos de cada funcionário.
CREATE TABLE FUNCIONARIO (
    Matricula INTEGER NOT NULL,
    Nome VARCHAR(150) NOT NULL,
    CPF VARCHAR(11) NOT NULL,
    Cargo VARCHAR(100) NOT NULL, -- <<-- COLUNA ADICIONADA AQUI
    DataNascimento DATE NULL,
    DataAdmissao DATE NOT NULL,
    SalarioBruto DECIMAL(10, 2) NOT NULL,
    GrauInsalubridade VARCHAR(10) NULL,
    CargaHorariaSemanal INTEGER NOT NULL,
    PossuiPericulosidade INTEGER NOT NULL DEFAULT 0,
    PRIMARY KEY (Matricula),
    CONSTRAINT uk_cpf UNIQUE (CPF)
);

-- Tabela: LOGIN
-- Descrição: Armazena as credenciais de acesso (relação 1:1 com FUNCIONARIO).
CREATE TABLE LOGIN (
    Matricula_Funcionario INTEGER NOT NULL,
    SenhaHash VARCHAR(255) NOT NULL,
    PRIMARY KEY (Matricula_Funcionario),
    CONSTRAINT fk_login_funcionario FOREIGN KEY (Matricula_Funcionario)
        REFERENCES FUNCIONARIO (Matricula)
        ON DELETE CASCADE
);

-- Tabela: DEPENDENTE
-- Descrição: Armazena os dependentes de um funcionário.
CREATE TABLE DEPENDENTE (
    ID_Dependente INTEGER PRIMARY KEY AUTOINCREMENT,
    Matricula_Funcionario INTEGER NOT NULL,
    Nome VARCHAR(150) NOT NULL,
    CPF VARCHAR(11) NULL,
    DataNascimento DATE NOT NULL,
    Parentesco VARCHAR(50) NOT NULL,
    CONSTRAINT uk_dependente_cpf UNIQUE (CPF),
    CONSTRAINT fk_dependente_funcionario FOREIGN KEY (Matricula_Funcionario)
        REFERENCES FUNCIONARIO (Matricula)
        ON DELETE CASCADE
);

-- Tabela: FOLHA_PAGAMENTO
-- Descrição: Armazena os totais de cada folha de pagamento mensal.
CREATE TABLE FOLHA_PAGAMENTO (
    ID_Folha INTEGER PRIMARY KEY AUTOINCREMENT,
    Matricula_Funcionario INTEGER NOT NULL,
    MesReferencia DATE NOT NULL,
    SalarioBrutoCalculo DECIMAL(10, 2) NOT NULL,
    TotalProventos DECIMAL(10, 2) NOT NULL,
    TotalDescontos DECIMAL(10, 2) NOT NULL,
    SalarioLiquido DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_folha_funcionario FOREIGN KEY (Matricula_Funcionario)
        REFERENCES FUNCIONARIO (Matricula)
        ON DELETE RESTRICT
);

-- Tabela: ITEM_FOLHA
-- Descrição: Armazena cada linha de detalhe de uma folha de pagamento.
CREATE TABLE ITEM_FOLHA (
    ID_Item INTEGER PRIMARY KEY AUTOINCREMENT,
    ID_Folha INTEGER NOT NULL,
    Descricao VARCHAR(100) NOT NULL,
    Tipo TEXT NOT NULL CHECK(Tipo IN ('PROVENTO', 'DESCONTO')),
    Valor DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_item_folha FOREIGN KEY (ID_Folha)
        REFERENCES FOLHA_PAGAMENTO (ID_Folha)
        ON DELETE CASCADE
);

-- ===================================================================
-- CRIAÇÃO DE ÍNDICES
-- ===================================================================
CREATE INDEX idx_dependente_funcionario ON DEPENDENTE(Matricula_Funcionario);
CREATE INDEX idx_folha_funcionario ON FOLHA_PAGAMENTO(Matricula_Funcionario);
CREATE INDEX idx_item_folha ON ITEM_FOLHA(ID_Folha);
