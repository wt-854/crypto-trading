# crypto-trading


## Getting Started
| Command | Description |
| --- | --- |
| `mvn spring-javaformat:apply` | Checkstyle plugin that enforces consistency across codebase |
| `mvn clean install` | Clean target directory and re-build project |
| `mvn spring-boot:run` | Run the application |

- `schema.sql` will populate User data on startup (all users start with 50,000.00 usdtBalance)

---

## Swagger
- `http://localhost:8080/swagger-ui/index.html`

---

## Entities
#### Price
```
id
cryptoPair
bidPrice        // use for SELL order
askPrice        // use for BUY order
timestamp       // for fetching latest
```
###### Description
For price aggregation
- price saved used in buy/sell transactions
- Chose to go with inserting new record instead of upserting, in case historical data is needed

---

#### User
```
id
username
cryptoWallet
```
#### CryptoWallet
```
usdtBalance
btcusdtBalance
ethusdtBalance
```
###### Description
For user balance
- Each user has initial wallet balance of 50,000 USDT (added with `schema.sql` on startup)
- Only support Ethereum - ETHUSDT and Bitcoin - BTCUSDT pairs of crypto trading
- Need to be able to view his/her crypto wallet balance
---

#### Transaction
```
id          // transaction id
userId      // which user transaction belongs to
cryptoPair
amount      // quantity of crypto bought
price       // total price bought/sold
transactionType    // buy/sell
```

###### Description
Represent a user's buy/sell order

---


## APIs
| API Endpoint | HTTP Method | Parameters | Description | Sample Response |
| --- | --- | --- | --- | --- |
| `/prices/{cryptoPair}` | GET  | Path: `cryptoPair` (`BTCUSDT`/`ETHUSDT`) | To retrieve the latest best aggregated price for specified crypto pair | {  "id": 15, "cryptoPair": "BTCUSDT", "bidPrice": 74951.92, "askPrice": 74951.93,  "timestamp": "2024-11-07T20:29:44.992852000" } |
| `/users/{userId}/balance` | GET | Path: `userId` | To retrieve the user’s crypto currencies wallet balance | { "username": "TOM", "cryptoWallet": { "usdtBalance": 50000, "btcusdtBalance": 0, "ethusdtBalance": 0 } } |
| `/transactions` | POST | Request body: { "userId", "cryptoPair", "amount", "transactionType" (`BUY`/`SELL`) } | To allow users to BUY/SELL based on latest best aggregated price | { "id": 1, "userId": 1, "cryptoPair": "ETHUSDT", "amount": 1.1, "price": 2806.8, "transactionType": "BUY", "timestamp": "2024-11-07T20:38:39.836898471" } |
| `/transactions/{userId}` | GET | Path:<br> - `userId` <br> - `startDate` (`YYYY-MM-DDTHH:MM:SS`) <br> - `endDate` (`YYYY-MM-DDTHH:MM:SS`) <br> - `sortOrder` (`desc` default) <br> - `page` (`0` default) <br> - `pageSize` (`10` default) | To retrieve the user's transactions/trading history | PageableObject object <br> { "content": [ { Transaction }, ... ], "pageable": { ... }, ... } |

---
