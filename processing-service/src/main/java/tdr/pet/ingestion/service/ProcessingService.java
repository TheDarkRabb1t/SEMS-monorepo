package tdr.pet.ingestion.service;

import model.LogEvent;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ProcessingService {
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "logs.queue", durable = "true"),
            exchange = @Exchange(value = "logs.exchange", ignoreDeclarationExceptions = "true"),
            key = "logs.key")
    )
    public void process(LogEvent logEvent) {
    }
}
