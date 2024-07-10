package tec.buildrun.btgpactual.orderms.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import tec.buildrun.btgpactual.orderms.config.RabbitMqConfig;
import tec.buildrun.btgpactual.orderms.listener.dto.OrderCreatedEvent;
import tec.buildrun.btgpactual.orderms.service.OrderService;

@Component
public class OrderCreatedListener {
    private final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);

    private final OrderService orderService;

    public OrderCreatedListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = RabbitMqConfig.ORDER_CREATED_QUEUE)
    public void listen(Message<OrderCreatedEvent> message) {
        this.orderService.save(message.getPayload());
        logger.info("Mensagem persistida no BD");
    }
}
