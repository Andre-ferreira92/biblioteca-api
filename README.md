Esta é uma API RESTful completa para a gestão de uma biblioteca, focada em regras de negócio complexas, segurança e integridade de dados. O sistema permite o controlo total de utilizadores, empréstimos e inventário físico.

Status do Projeto: Concluído / Estável ✅
Tecnologias

  -Java 21 / Spring Boot
  -Spring Security: Autenticação e autorização por Roles (USER/ADMIN).
  -Spring Data JPA: Persistência de dados e consultas otimizadas.
  -Flyway: Migrações e versionamento da base de dados.
  -PostgreSQL: Base de dados principal.
  -JUnit 5 & Mockito: Testes unitários abrangentes.
  - Swagger/OpenAPI: Documentação interativa dos endpoints.

Funcionalidades Principais

Gestão de Inventário

  -Catálogo de Livros: CRUD completo com validação de ISBN único e pesquisa avançada.
  -Cópias Físicas: Controlo individual de exemplares com status dinâmico (AVAILABLE, LOANED).
  -Categorias: Organização e validação de nomes duplicados.

Utilizadores e Segurança

  -Controlo de Acesso: Diferenciação entre utilizadores comuns e administradores.
  -Criptografia: Segurança de senhas com BCrypt.
  -Gestão de Bloqueios: Sistema automático para utilizadores com histórico de atrasos.

Fluxo de Empréstimos (Regras de Negócio)

  -Limitação: Máximo de 3 empréstimos simultâneos por utilizador.
  -Cálculo Automático: Data de devolução fixada em 30 dias.
  -Validações: Verificação em tempo real da disponibilidade de cópias.

🛡️ Qualidade de Código e Testes

  -Testes Unitários.
  -Tratamento de Erros: Exceções personalizadas (BusinessRuleException, IsbnAlreadyExistsException) para respostas claras via API.
