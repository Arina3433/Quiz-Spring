package com.example.quiznew.api.services.rabbit;

import com.example.quiznew.api.converters.user.UserConverter;
import com.example.quiznew.api.utils.rabbit.RabbitConfig;
import com.example.quiznew.api.utils.rabbit.dto.UserRegisteredEventDto;
import com.example.quiznew.store.entities.user.User;
import com.example.quiznew.store.repositories.UserRepository;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRegistrationListenerService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @RabbitListener(queues = RabbitConfig.USER_REGISTER_QUEUE, ackMode = "MANUAL")
    public void handleUserRegistered(UserRegisteredEventDto eventDto, Message message, Channel channel) {
        try {
            log.info("Получено событие регистрации пользователя: {}", eventDto.username());

            User user = userConverter.toUser(eventDto);
            userRepository.save(user);

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("Ошибка при обработке сообщения регистрации пользователя", e);
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            } catch (Exception ex) {
                log.error("Ошибка при повторной отправке сообщения в очередь", ex);
            }
        }
    }
}
