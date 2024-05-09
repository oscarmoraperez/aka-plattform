package contracts.orderservice

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'POST'
        url '/api/paymentservice/payments'
        body([
                "amount"    : 15,
                "currency"  : "CHF",
                "type"      : "CREDIT_CARD",
                "cardNumber": "1234567890123"
        ])
        headers {
            contentType('application/json')
        }
    }
    response {
        status OK()
        body([
                "id"        : $(positiveInt()),
                "amount"    : 15,
                "currency"  : "CHF",
                "type"      : "CREDIT_CARD",
                "cardNumber": "1234567890123",
                "status"    : "CREATED"
        ])
        headers {
            contentType('application/json')
        }
    }
}