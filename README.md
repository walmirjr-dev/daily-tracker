# 🎯 DailyTracker API

![Java](https://img.shields.io/badge/Java-21-orange?style=flat&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2-green?style=flat&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?style=flat&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Orchestration-2496ED?style=flat&logo=docker)

O **DailyTracker** é uma aplicação Fullstack para gerenciamento de hábitos e desafios pessoais. O sistema permite criar desafios (ex: "30 dias de Leitura") e registrar o progresso, oferecendo flexibilidade para recuperar dias perdidos e visualização clara da consistência.

Este repositório contém o **Backend (API REST)** e os arquivos de orquestração **Docker** para subir o sistema completo (Backend + Frontend + Banco).

🔗 **Repositório do Frontend:** [https://github.com/walmirjr-dev/daily-tracker-frontend]

---

## 🚀 Funcionalidades

- **Gerenciamento de Desafios:** Criação, edição e visualização detalhada.
- **Sistema de Check-in Flexível:** Permite registrar progresso acumulado (caso tenha esquecido de marcar no dia anterior).
- **Registro de último Check-in:** O sistema marca a data do último checkIn realizado, para ajudar o usuário a manter o ritmo e se lembrar do seu último registro.
- **Controle de "Undo":** Possibilidade de desfazer o último check-in realizado.
- **Dashboard de Progresso:** Cálculo automático de porcentagem e dias restantes.
- **Grid de Consistência:** Histórico visual dos check-ins realizados (estilo GitHub).

---

## 🧠 Regras de Negócio

Para garantir a integridade dos desafios e uma boa experiência de usuário, o sistema segue estas regras:

1.  **Registro Retroativo (Backlog Permitido):**
    O sistema **permite** realizar múltiplos check-ins no mesmo dia. Isso serve para que o usuário possa "tirar o atraso" caso tenha cumprido a meta em dias anteriores mas esqueceu de registrar no aplicativo.

2.  **Limite Temporal (Anti-Cheat):**
    O número total de check-ins realizados **nunca pode exceder** o número de dias corridos desde a data de início do desafio.

      - **Exemplo:** Se um desafio começou dia 01/02 e hoje é 06/02, passaram-se 6 dias. O usuário pode ter no máximo 6 check-ins. Se tentar fazer o 7º, o sistema bloqueia o botão.

3.  **Bloqueio de Futuro:**
    Não é possível "adiantar" o desafio. O sistema impede que o progresso ultrapasse a linha do tempo real (dias decorridos).

4.  **Lógica de Desfazer:**
    A funcionalidade de "Undo" remove estritamente o check-in mais recente do banco de dados, recalculando o progresso imediatamente.

---

## 🛠️ Tecnologias Utilizadas

- **Java 21** (LTS)
- **Spring Boot 3** (Web, JPA, Validation)
- **PostgreSQL** (Banco de Dados Relacional)
- **Docker & Docker Compose** (Containerização e Orquestração)
- **Maven** (Gerenciador de Dependências)

---

## 🐳 Como Rodar (Modo Docker Fullstack)

Este repositório atua como o **Orquestrador** do projeto. O arquivo `docker-compose.yml` está configurado para subir o Backend, o Banco de Dados e buscar o Frontend na pasta vizinha.

### ⚠️ Pré-requisitos de Pastas
Para que o Docker encontre os arquivos, seus diretórios devem estar organizados assim no seu computador:

```text
PROJETOS/ (Pasta Mãe)
├── DailyTracker/              <-- (Este repositório - Backend)
│   ├── docker-compose.yml
│   └── ...
└── daily-tracker-frontend/    <-- (Repositório do Frontend clonado)
    ├── Dockerfile
    └── ...
```

Passo a passo.

Garanta que o repositório do Frontend esteja clonado ao lado deste, com o nome exato daily-tracker-frontend.
Abra o terminal na raiz deste projeto (DailyTracker).
Execute o comando:
```Bash

docker-compose up --build
```
O sistema estará disponível em:

Frontend (Aplicação): http://localhost:3000
Backend (API): http://localhost:8080
Banco de Dados: Porta 5432
