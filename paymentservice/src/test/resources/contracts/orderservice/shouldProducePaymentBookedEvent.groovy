package contracts.orderservice

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name("shouldSendPaymentBookedEvent")

    input {
        triggeredBy("bookPayment()")
    }

    label("triggerPaymentBookedEvent")

    outputMessage {
        sentTo("payments")
        body([
                id    : 33,
                status: "BOOKED"
        ])
    }
}

