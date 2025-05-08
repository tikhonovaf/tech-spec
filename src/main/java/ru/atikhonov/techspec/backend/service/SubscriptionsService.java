package ru.atikhonov.techspec.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.atikhonov.techspec.backend.api.ApiUtil;
import ru.atikhonov.techspec.backend.api.SubscriptionsApi;
import ru.atikhonov.techspec.backend.api.SubscriptionsApiDelegate;
import ru.atikhonov.techspec.backend.dto.*;
import ru.atikhonov.techspec.backend.mapper.ServiceMapper;
import ru.atikhonov.techspec.backend.mapper.SubsсriptionMapper;
import ru.atikhonov.techspec.backend.mapper.UserMapper;
import ru.atikhonov.techspec.backend.model.Services;
import ru.atikhonov.techspec.backend.model.Subscriptions;
import ru.atikhonov.techspec.backend.model.SubscriptionsView;
import ru.atikhonov.techspec.backend.model.Users;
import ru.atikhonov.techspec.backend.repository.*;
import ru.atikhonov.techspec.backend.util.CoreUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс для выполнения функций rest сервисов (GET, POST, PATCH, DELETE)
 *
 * @author Аркадий Тихонов
 */
@Service
@RequiredArgsConstructor
public class SubscriptionsService implements SubscriptionsApiDelegate {

    private final ServicesRepository servicesRepository;
    private final ServiceMapper serviceMapper;

    private final UsersRepository usersRepository;
    private final UserMapper userMapper;

    private final SubscriptionsRepository subscriptionsRepository;
    private final SubscriptionsViewRepository subscriptionsViewRepository;
    private final SubsсriptionMapper subsсriptionMapper;

//    private final ApplicationContext applicationContext;

//   Сервисы для пользоателя

    /**
     * POST : Добавление пользователя
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
     * DELETE : Удаление пользователя
     *
     * @param userId ИД элемента справочника (required)
     * @return Пустой ответ (status code 200)
     */
    @Override
    public ResponseEntity<Void> deleteUser(Long userId) {
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            usersRepository.delete(userOptional.get());
        }
        return ResponseEntity.noContent().build();

    }


    /**
     * GET : Выборка списка пользователей
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
     * GET : Выборка пользователя
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
     * PATCH /ref/bannerTypes/{userId} : Изменение пользователя
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


//   Сервисы для сервмсов

    /**
     * POST : Добавление типа сервиса
     *
     * @param serviceInDto (optional)
     * @return Пустой ответ (status code 200)
     */
    @Override
    public ResponseEntity<Void> addService(ServiceInDto serviceInDto) {
        Services service = serviceMapper.fromDtoToEntity(serviceInDto);
        servicesRepository.save(service);

        return ResponseEntity.noContent().build();

    }

    /**
     * DELETE : Удаление типа сервиса
     *
     * @param serviceId ИД элемента справочника (required)
     * @return Пустой ответ (status code 200)
     */
    @Override
    public ResponseEntity<Void> deleteService(Long serviceId) {
        Optional<Services> serviceOptional = servicesRepository.findById(serviceId);
        if (serviceOptional.isPresent()) {
            servicesRepository.delete(serviceOptional.get());
        }
        return ResponseEntity.noContent().build();

    }


    /**
     * GET : Выборка списка типов сервисов
     *
     * @return Список типов сервисов (status code 200)
     */
    @Override
    public ResponseEntity<List<ServiceDto>> getServices() {
        List<ServiceDto> result =
                servicesRepository
                        .findAll()
                        .stream()
                        .map(v -> serviceMapper.fromEntityToDto(v))
                        .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }



    /**
     * GET /subscriptions/services/{serviceId} : Выборка сервиса
     *
     * @param serviceId ИД сервиса (required)
     * @return Сервис (status code 200)
     * @see SubscriptionsApi#getService
     */
    public ResponseEntity<ServiceDto> getService(Long serviceId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"name\" : \"Наименование сервиса 1\", \"id\" : 1 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }



    /**
     * GET /subscriptions/services/{serviceId} : Выборка сервиса
     *
     * @param serviceId ИД сервиса (required)
     * @return Сервис (status code 200)
     * @see SubscriptionsApi#getService
     */
    public ResponseEntity<ServiceDto> getService2222(Long serviceId) {

        Optional<Services> serviceOptional = servicesRepository.findById(serviceId);
        if (serviceOptional.isPresent()) {
            ServiceDto result = serviceMapper
                    .fromEntityToDto(servicesRepository.findById(serviceId).get());
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * PATCH /ref/bannerTypes/{serviceId} : Изменение типа сервиса
     *
     * @param serviceId    ИД (required)
     * @param serviceInDto (optional)
     * @return Пустое значение (status code 200)
     */
    @Override
    public ResponseEntity<Void> modifyService(Long serviceId,
                                                 ServiceInDto serviceInDto) {
        if (servicesRepository.findById(serviceId).isPresent()) {

            Services entity = servicesRepository.findById(serviceId).get();
            Services entityNew = serviceMapper.fromDtoToEntity(serviceInDto);
            CoreUtil.patch(entityNew, entity);
            servicesRepository.save(entity);
        }
        return ResponseEntity.noContent().build();

    }

//   Сервисы для подписки
    /**
     * POST: Добавление подписки
     *
     * @return Пустой ответ (status code 200)
     */
    @Override
    public ResponseEntity<Void> addSubscriptions(SubscriptionInDto subscriptionInDto) {
        //  Нужно проверять уникальность подписки
        Subscriptions subscriptionNew = subsсriptionMapper.fromDtoToEntity(subscriptionInDto);
        subscriptionsRepository.save(subscriptionNew);

        return ResponseEntity.noContent().build();

    }

    /**
     *  Удаление подписки
     *
     * @param subscriptionId ИД подписки (required)
     * @return Пустой ответ (status code 200)
     */
    @Override
    public ResponseEntity<Void> deleteSubscription(Long subscriptionId) {
        if (subscriptionsRepository.findById(subscriptionId).isPresent()) {
            Subscriptions subscription = subscriptionsRepository.findById(subscriptionId).get();
        }

        return ResponseEntity.noContent().build();

    }

    /**
     * Выборка подлписки
     *
     * @param subscriptionId ИД элемента справочника (required)
     * @return Данные по элементу справочника (status code 200)
     */
    @Override
    public ResponseEntity<SubscriptionViewDto> getSubscription(Long subscriptionId) {
        Optional<SubscriptionsView> subscriptionsView = subscriptionsViewRepository.findById(subscriptionId);
        if (subscriptionsView.isPresent()) {
            SubscriptionViewDto result = subsсriptionMapper
                    .fromViewToDto(subscriptionsViewRepository.findById(subscriptionId).get());
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    /**
     * GET /demo2000/Services : Выборка списка подписок
     *
     * @return Список подписок (status code 200)
     */
    @Override
    public ResponseEntity<List<SubscriptionViewDto>> getSubscriptions() {
        List<SubscriptionViewDto> result = subscriptionsViewRepository
                            .findAll()
                            .stream()
                            .map(v -> subsсriptionMapper.fromViewToDto(v))
                            .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}
