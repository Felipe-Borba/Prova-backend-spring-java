# Prova Backend

A descrição completa do desafio pode ser acessada através do arquivo:
[1-PROVA DE BACKEND JAVA PARA ERP.pdf](https://github.com/Felipe-Borba/Backend-prova-senior/blob/master/1-PROVA%20DE%20BACKEND%20JAVA%20PARA%20ERP.pdf)
na raiz do projeto.

## Instalação

[Instale JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

[Instale postgresql v10.21](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads)

[Instale maven](https://maven.apache.org/download.cgi)

## Rodando localmente

Clone o projeto

```bash
  git clone https://github.com/Felipe-Borba/Backend-prova-senior
```

Entre no diretório do projeto

```bash
  cd Backend-prova-senior
```

Instale as dependências

```bash
  mvn compile
```

Inicie o servidor

```bash
  mvn spring-boot:run
```

## Apêndice

Aconselho a rodar o projeto localmente a partir da
IDE [intellij](https://www.jetbrains.com/pt-br/idea/download/#section=windows)

Caso queira rodar o banco de dados em um docker
```bash
docker run --name my-postgres -p 5432:5432 -e POSTGRES_PASSWORD=root -d postgresi
```

## Documentação da API

A documentação com a descrição das rotas pode ser acessada a partir do link:

[http://localhost:8080/api-reference.html](http://localhost:8080/api-reference.html)
![screenshot of swagger](/swagger-screenshot.PNG "screenshot da documentação das funcionalidades")
`Ps. O servidor precisa estar rodando localmente para acessar essa página`

## Autor

- [@Felipe-Borba](https://github.com/Felipe-Borba)

