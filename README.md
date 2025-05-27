# MovelControl

Este repositório abriga o código-fonte do projeto MovelControl, uma aplicação desenvolvida utilizando Java e o framework Spring Boot. O sistema se destina ao controle ou gerenciamento de Estoque, Orçamentos, Pedidos e Nota Fiscal de uma marcenaria, através de uma API RESTful. O projeto utiliza tecnologias modernas para fornecer uma base robusta e escalável.

## Visão Geral Técnica

O MovelControl é construído sobre a plataforma Java, especificamente utilizando a versão 17 do JDK, e emprega o ecossistema Spring Boot para simplificar a configuração e o desenvolvimento de aplicações web. A gestão de dependências e o processo de build são gerenciados pelo Maven. Para a camada de persistência de dados, o projeto utiliza Spring Data JPA, o que facilita testes e desenvolvimento inicial. A exposição de funcionalidades é realizada através de uma API REST, documentada interativamente com Swagger (via SpringDoc OpenAPI), permitindo fácil exploração e teste dos endpoints disponíveis.

## Tecnologias Empregadas

O desenvolvimento do MovelControl apoia-se em um conjunto de tecnologias consolidadas no mercado de desenvolvimento Java:

*   **Java 17:** Versão LTS (Long-Term Support) da linguagem Java, oferecendo recursos modernos e performance otimizada.
*   **Spring Boot:** Framework principal que acelera o desenvolvimento de aplicações Java, gerenciando configurações e dependências de forma eficiente.
*   **Spring Web:** Módulo do Spring para a construção de aplicações web, incluindo suporte a APIs RESTful.
*   **Spring Data JPA:** Facilita a implementação da camada de acesso a dados, abstraindo interações com o banco de dados.
*   **H2 Database Engine:** Banco de dados relacional em memória, ideal para ambientes de desenvolvimento e testes.
*   **Maven:** Ferramenta para gerenciamento de dependências e automação de build do projeto.
*   **SpringDoc OpenAPI (Swagger):** Ferramenta para geração automática de documentação interativa para APIs REST, facilitando o entendimento e uso dos endpoints.

## Funcionalidades Principais (Inferidas)

Com base na estrutura do projeto e nas dependências, infere-se que o MovelControl oferece as seguintes funcionalidades:

*   **API RESTful:** Exposição de endpoints para interagir com os recursos gerenciados pela aplicação.
*   **Gerenciamento de Entidades:** Operações CRUD (Create, Read, Update, Delete) para as entidades principais do sistema.
*   **Documentação de API:** Interface Swagger UI para visualização e teste dos endpoints da API.

## Pré-requisitos

Para compilar e executar o projeto MovelControl em seu ambiente local, certifique-se de ter instalado:

*   **JDK 17:** Java Development Kit versão 17 ou superior.
*   **Maven:** Ferramenta de automação de build e gerenciamento de dependências Apache Maven.

## Instalação e Configuração

Siga os passos abaixo para configurar o ambiente de desenvolvimento:

1.  **Clonar o Repositório:** Utilize o Git para clonar este repositório para a sua máquina local:
    ```bash
    git clone https://github.com/MOR4Xx/MovelControl.git
    ```
2.  **Navegar para o Diretório:** Acesse a pasta raiz do projeto clonado:
    ```bash
    cd MovelControl
    ```

## Executando a Aplicação

Após a instalação e configuração, você pode iniciar a aplicação MovelControl utilizando o Maven Wrapper incluído no projeto, que garante o uso da versão correta do Maven sem a necessidade de instalação global.

Execute o seguinte comando no terminal, a partir do diretório raiz do projeto:

```bash
./mvnw spring-boot:run
```

Em sistemas Windows, utilize:

```bash
.\mvnw.cmd spring-boot:run
```

A aplicação será iniciada e, por padrão, estará acessível em `http://localhost:8080`. O banco de dados H2 em memória será inicializado automaticamente.

## Documentação da API (Swagger UI)

Para explorar os endpoints da API RESTful fornecidos pelo MovelControl, acesse a interface do Swagger UI no seu navegador. Após iniciar a aplicação, a documentação estará disponível no seguinte endereço:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Esta interface permite visualizar todos os endpoints disponíveis, seus parâmetros, tipos de retorno e também testá-los diretamente pelo navegador.

## Contribuidores

O desenvolvimento deste projeto contou com a colaboração dos seguintes membros:

*   **Jorge Afonso (MOR4Xx):** [https://github.com/MOR4Xx](https://github.com/MOR4Xx)
*   **Artur Duarte Monteiro (Artur-Duarte17):** [https://github.com/Artur-Duarte17](https://github.com/Artur-Duarte17)
*   **Leandro Rosa da Silva (LDRRosa):** [https://github.com/LDRRosa](https://github.com/LDRRosa)

## Licença

Este repositório não possui um arquivo de licença definido até o momento. Recomenda-se verificar diretamente com os mantenedores do projeto para obter informações sobre os termos de uso e distribuição do código.

