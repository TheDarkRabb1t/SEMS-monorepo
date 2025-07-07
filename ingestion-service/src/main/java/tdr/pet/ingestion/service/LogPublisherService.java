package tdr.pet.ingestion.service;

import lombok.AllArgsConstructor;
import model.dto.LogEventDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import tdr.pet.ingestion.config.RabbitConfig;

@Service
@AllArgsConstructor
public class LogPublisherService {

    private final RabbitTemplate rabbitTemplate;

    public void publishLog(LogEventDto event) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.ROUTING_KEY,
                event
        );
    }
}
