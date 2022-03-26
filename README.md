## Gerador de recibos criado com 🍃 Spring Boot

Gerador de recibos em PDF, gerado a partir de dados em CSV e utilizando QR Code para consulta online.

### Exemplo:

Criar diretório "temp" na raiz do projeto.

POST http://localhost:8080/api/csv/upload

#### Body:

Type: form-data

Key: file

Value: file.csv

### Exemplo de csv:

```
data,descricao,valor
26/03/2022,Assistencia,30.99
```
