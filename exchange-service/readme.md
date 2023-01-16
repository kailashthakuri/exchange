# How to run application locally
## Prerequisites
1. maven
2. java(`>=8`)

## Execute command (from root of the project)
1. mvn clean install (downloads dependencies and does other stuffs)
2. mvn spring-boot:run (runs application at port 8080 if available)

# What application does?
1. It usage the NBP api to initially load exchange rate for different countries against PLN. 
   Currently it saves middle exchange rates from Table A & B (from NBP api) in pln_mid_exchange table in local database
   and bid & ask exchange rates from Table C (from NBP api) in pln_exchange table in local database.
   it stores exchange rates for the last 10 data series.
   it load initial data only if local table is empty. it usage file based H2 database. Database can be found in `data` folder in root of project.
   `TODO (improvement) : loads latest exchanges rate published if available in NBA api.`
2. it exposes two apis
    1. Returns sell exchange rate for provided code and published date for PLN
    ```javascript
     GET => localhost:8080/pln/rate/{code}/{publishedDate}
   
     code: Currency code (ISO 4217 format)
     publishedDate: Date of publish in yyyy-MM-dd format
   
     //////////////////// Example //////////////////////
     Request : GET: localhost:8080/pln/rate/USD/2023-01-13
     Response Body :
     {
         "data": {
         "rate": 4.4034,
             "currency": "dolar amerykański",
             "code": "USD",
             "effectiveDate": "2023-01-12T18:15:00.000+00:00"
         },
         "meta": {}
     }
     Response Body [if it does not finds sell exchange rate for provided parameters]
     {
        "error": {
            "timestamp": 1673845697053,
            "code": "DATA_NOT_FOUND",
            "status": 404,
            "title": "Requested data not found",
            "detail": "Rate not found for provided parameters"
        },
        "meta": {}
    }
   
    ```
    2. calculates the total amount in PLN based on mid exchange rate for given currency and amount.
    ```javascript
     POST => localhost:8080/pln/total-purchasing-cost
   
     //////////////////// Example //////////////////////
     Request : POST : localhost:8080/pln/total-purchasing-cost
     Request Body :
     {
       "currencies":[
           {
             "code":"USD",
             "amount":10
           },
           {
            "code":"AUD",
            "amount":20
           }
         ],
        "publishedDate":"2023-01-13"
     }
     Response Body
     {
       "data": {
           "code": "PLN",
           "total": 103.638
       },
       "meta": {}
     }
    ```

