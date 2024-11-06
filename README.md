# crypto-trading

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

#### User
```
id
username
usdtBalance     // start with 50,000
btcusdtBalance
ethusdtBalance
```
###### Description
For user balance
- Each user has initial wallet balance of 50,000 USDT
- Only support Ethereum - ETHUSDT and Bitcoin - BTCUSDT pairs of crypto trading
- Need to be able to view his/her crypto wallet balance

#### Transaction
```
id          // transaction id
userId      // which user transaction belongs to
cryptoPair
amount      // quantity of crypto bought
price       // total price bought/sold
type        // buy/sell
```

## APIs
| API Endpoint | HTTP Method | Parameters | Description |
| --- | --- | --- | --- |
| `/prices/{cryptoPair}` | GET  | Path: `cryptoPair` (`BTCUSDT`/`ETHUSDT`) | To retrieve the latest best aggregated price for specified crypto pair |
| `/users/{userId}/balance` | GET | Path: `userId` | To retrieve the user’s crypto currencies wallet balance |
| `/transactions` | POST | Request body: { userId, cryptoPair, amount, type (`BUY`/`SELL`) } | To allow users to BUY/SELL based on latest best aggregated price |
| `/transactions/{userId}` | GET | Path: `userId` | To retrieve the user's transactions/trading history |
