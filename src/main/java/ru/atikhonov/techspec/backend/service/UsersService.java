package ru.atikhonov.techspec.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.atikhonov.techspec.backend.api.ApiUtil;
import ru.atikhonov.techspec.backend.api.SubscriptionsApi;
import ru.atikhonov.techspec.backend.api.UsersApiDelegate;
import ru.atikhonov.techspec.backend.dto.*;
import ru.atikhonov.techspec.backend.exception.ValidateException;
import ru.atikhonov.techspec.backend.mapper.ServiceMapper;
import ru.atikhonov.techspec.backend.mapper.SubsсriptionMapper;
import ru.atikhonov.techspec.backend.mapper.UserMapper;
import ru.atikhonov.techspec.backend.model.Services;
import ru.atikhonov.techspec.backend.model.Subscriptions;
import ru.atikhonov.techspec.backend.model.SubscriptionsView;
import ru.atikhonov.techspec.backend.model.Users;
import ru.atikhonov.techspec.backend.repository.ServicesRepository;
import ru.atikhonov.techspec.backend.repository.SubscriptionsRepository;
import ru.atikhonov.techspec.backend.repository.SubscriptionsViewRepository;
import ru.atikhonov.techspec.backend.repository.UsersRepository;
import ru.atikhonov.techspec.backend.util.CoreUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс для выполнения функций rest сервисов (GET, POST, PUT, DELETE)
 *
 * @author Аркадий Тихонов
 */
@Service
@RequiredArgsConstructor
public class UsersService implements UsersApiDelegate {

    private final UsersRepository usersRepository;
    private final UserMapper userMapper;

    private final SubscriptionsRepository subscriptionsRepository;
    private final SubscriptionsViewRepository subscriptionsViewRepository;
    private final SubsсriptionMapper subsсriptionMapper;

    private final ServicesRepository servicesRepository;

//   Сервисы для пользоателя

    /**
     *  Добавление пользователя
     *
     * @param userInDto (optional)
     * @return Пустой ответ (status code 200)
     */
    @Override
    public ResponseEntity<Void> addUser(UserInDto userInDto) {
        Users user = userMapper.fromDtoToEntity(userInDto);
        usersRepository.save(user);

        return ResponseEntity.noContent().build();

    }

    /**
     * Удаление пользователя
     *
     * @param userId ИД элемента справочника (required)
     * @return Пустой ответ (status code 200)
     */
    @Override
    public ResponseEntity<Void> deleteUser(Long userId) {
        if (usersRepository.findById(userId).isPresent()) {
            Users user = usersRepository.findById(userId).get();
            List <Subscriptions> subscriptions = subscriptionsRepository.findByUser(user);
            subscriptionsRepository.deleteAll(subscriptions);
            usersRepository.delete(user);
        }
        return ResponseEntity.noContent().build();

    }

    /**
     *  Выборка списка пользователей
     *
     * @return Список пользователей (status code 200)
     */
    @Override
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> result =
                usersRepository
                        .findAll()
                        .stream()
                        .map(v -> userMapper.fromEntityToDto(v))
                        .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    /**
     * Выборка пользователя
     *
     * @param userId ИД элемента справочника (required)
     * @return Данные по элементу справочника (status code 200)
     */
    @Override
    public ResponseEntity<UserDto> getUser(Long userId) {
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            UserDto result = userMapper
                    .fromEntityToDto(usersRepository.findById(userId).get());
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * Изменение пользователя
     *
     * @param userId    ИД (required)
     * @param userInDto (optional)
     * @return Пустое значение (status code 200)
     */
    @Override
    public ResponseEntity<Void> modifyUser(Long userId,
                                              UserInDto userInDto) {
        if (usersRepository.findById(userId).isPresent()) {

            Users entity = usersRepository.findById(userId).get();
            Users entityNew = userMapper.fromDtoToEntity(userInDto);
            CoreUtil.patch(entityNew, entity);
            usersRepository.save(entity);
        }
        return ResponseEntity.noContent().build();

    }

//   Сервисы для подписки
    /**
     * POST: Добавление подписки для пользователя
     *
     * @return Пустой ответ (status code 200)
     */
    @Override
    public ResponseEntity<Void> addSubscription(Long userId, Long serviceId)  {
        if (subscriptionsRepository.findByUserIdAndServiceId(userId,serviceId).size() > 0) {
            throw ValidateException.exceptionSimple("Заданный сервис для пользователя уже есть");
        }
        Subscriptions subscriptionNew = new Subscriptions();
        if (usersRepository.findById(userId).isPresent()) {
            subscriptionNew.setUser(usersRepository.findById(userId).get());
        } else {
            throw ValidateException.exceptionSimple("Не найден пользователь с данным идентификатором");
        }
        if (servicesRepository.findById(serviceId).isPresent()) {
            subscriptionNew.setService(servicesRepository.findById(serviceId).get());
        } else {
            throw ValidateException.exceptionSimple("Не найден тип сервиса с данным идентификатором");
        }
        subscriptionsRepository.save(subscriptionNew);
        return ResponseEntity.noContent().build();
    }

    /**
     *  Удаление подписки
     *
     * @param userId ИД подписки (required)
     * @param serviceId ИД подписки (required)
     * @return Пустой ответ (status code 200)
     */
    @Override
    public ResponseEntity<Void> deleteSubscription(Long userId, Long serviceId) {
        List <Subscriptions> subscriptions = subscriptionsRepository.findByUserIdAndServiceId(userId,serviceId);
        if (subscriptions.size() > 0) {
            subscriptionsRepository.delete(subscriptions.get(0));
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /demo2000/Services : Выборка списка подписок для пользователя
     *
     * @return Список подписок (status code 200)
     */
    @Override
    public ResponseEntity<List<SubscriptionViewDto>> getSubscriptions(Long userId) {
        List<SubscriptionViewDto> result = subscriptionsViewRepository
                .findAllByUserId(userId)
                .stream()
                .map(v -> subsсriptionMapper.fromViewToDto(v))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

}
