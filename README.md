# Instruções

para iniciar o serviço 

```bat
mvn spring-boot:run
ou (windows)
.\mvnw spring-boot:run
ou (mac\linux)
./mvnw spring-boot:run
```

está sendo utilizado o banco H2 por ser simples e não precisar de instalação.

Ao iniciar o método inicializaDados() da Classe com.example.caju.demo.InitConfigApp irá dar uma carga inicial de dados dos comerciantes de de saldos em contas.


### comerciantes
vinculado um mcc ao nome do comerciante
- `UBER TRIP                   SAO PAULO BR` mcc: `5411`
- `UBER EATS                   SAO PAULO BR` mcc: `5412`
- `PAG*JoseDaSilva          RIO DE JANEI BR` mcc: `5811`
- `PICPAY*BILHETEUNICO           GOIANIA BR` mcc: `5812`
- `UBER ENVIO                  JOINVILLE BR` mcc: `6011`

### contas
- 123 `MEAL` saldo: 100
- 123 `CASH` saldo: 100
- 456 `FOOD` saldo: 100
- 456 `CASH` saldo: 100

## Transação

POST
http://localhost:8080/authorizer-api/authorize

exemplo do payload:
```json
{
	"account": "123",
	"totalAmount": 100.00,
	"mcc": "5811",
	"merchant": "PADARIA DO ZE               SAO PAULO BR"
}
```

Possíveis respostas:
- `{ "code": "00" }` se a transação é **aprovada**
- `{ "code": "51" }` se a transação é **rejeitada**, porque tão tem saldo suficiente
- `{ "code": "07" }` se acontecer qualquer outro problema que impeça a transação de ser processada


# swagger
http://localhost:8080/autorizador-api/swagger-ui/index.html


