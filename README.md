# Api Digital Security(istema de Gerenciamento de Segurança Digital)

Este projeto é um sistema de gerenciamento de segurança digital que permite a autenticação de usuários, o gerenciamento de dispositivos e a identificação de vulnerabilidades associadas a esses dispositivos. O sistema utiliza Spring Boot para a construção da API e JWT (JSON Web Token) para autenticação e autorização.

## Funcionalidades Principais

### Autenticação e Autorização
- **Login de Usuários**: Os usuários podem fazer login fornecendo um nome de usuário e senha. Após a autenticação bem-sucedida, um token JWT é gerado e retornado para o cliente.
- **Validação de Token**: O sistema valida o token JWT em cada requisição para garantir que o usuário está autenticado e tem as permissões necessárias.

### Gerenciamento de Dispositivos
- **Criação de Dispositivos**: Os administradores podem criar novos dispositivos, fornecendo informações como nome, endereço IP e localização.
- **Listagem de Dispositivos**: Todos os dispositivos cadastrados podem ser listados.
- **Atualização de Dispositivos**: Os administradores podem atualizar as informações de um dispositivo existente.
- **Exclusão de Dispositivos**: Dispositivos podem ser excluídos, desde que não tenham vulnerabilidades associadas.

### Gerenciamento de Vulnerabilidades
- **Criação de Vulnerabilidades**: Vulnerabilidades podem ser associadas a dispositivos específicos.
- **Listagem de Vulnerabilidades**: Todas as vulnerabilidades cadastradas podem ser listadas.
- **Busca de Vulnerabilidades por Dispositivo**: É possível listar as vulnerabilidades associadas a um dispositivo específico.
- **Exclusão de Vulnerabilidades**: Vulnerabilidades podem ser excluídas por um administrador.

## Tecnologias Utilizadas

- **Spring Boot**: Framework Java para construção de aplicações web.
- **JWT (JSON Web Token)**: Para autenticação e autorização.
- **PostgreSQL**: Banco de dados relacional para armazenamento de dados.
- **Flyway**: Para gerenciamento de migrações de banco de dados.
- **Spring Security**: Para segurança da aplicação, incluindo autenticação e autorização.

## Configuração do Projeto

### Banco de Dados
O projeto utiliza o PostgreSQL como banco de dados. As configurações de conexão estão no arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/digital_security
spring.datasource.username=postgres
spring.datasource.password=p2c77566
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```
### JWT
As configurações relacionadas ao JWT também estão no arquivo application.properties:

```properties

jwt.secret=AYMGJRxTk+ekEgH38Ljlmyu0kvPus5nxb06aDGWjwGE3+BZcLZRCpcfpZSGLz6eGk3DScEENSKGlU6fpMOkgKA==
jwt.expiration=3600000
Logging
O nível de log para o filtro de autenticação JWT está configurado como INFO:

properties
logging.level.com.api.digital.security.filter.JWTAuthenticationFilter=INFO
```
## Como Executar o Projeto
Clone o repositório:

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
Configure o banco de dados:
```

Certifique-se de que o PostgreSQL está instalado e rodando.

Crie um banco de dados chamado digital_security.

Execute o projeto:

No terminal, navegue até a pasta do projeto e execute:

```bash
./mvnw spring-boot:run
Acesse a API:

A API estará disponível em http://localhost:8080.
```

## Endpoints da API

### Autenticação
POST /auth/login: Realiza o login e retorna um token JWT.

### Dispositivos
POST /devices/Criar: Cria um novo dispositivo.

GET /devices/Listar: Lista todos os dispositivos.

GET /devices/Buscar/{id}: Busca um dispositivo pelo ID.

PUT /devices/Update/{id}: Atualiza um dispositivo existente.

DELETE /devices/delete/{id}: Exclui um dispositivo.

### Vulnerabilidades
POST /vulnerabilities/Criar: Cria uma nova vulnerabilidade.

GET /vulnerabilities/Listar: Lista todas as vulnerabilidades.

GET /vulnerabilities/Buscar/device/{deviceId}/vulnerabilities: Lista as vulnerabilidades de um dispositivo específico.

GET /vulnerabilities/Buscar/{id}: Busca uma vulnerabilidade pelo ID.

DELETE /vulnerabilities/Delete/{id}: Exclui uma vulnerabilidade.

## Exceções Personalizadas
O projeto inclui exceções personalizadas para lidar com situações específicas, como:

IdNotFoudException: Quando um ID não é encontrado.

DeviceDependencyException: Quando um dispositivo não pode ser excluído devido a vulnerabilidades associadas.

## Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.
