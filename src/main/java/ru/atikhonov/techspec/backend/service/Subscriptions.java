package ru.atikhonov.techspec.backend.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Subscriptions;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import ru.atikhonov.techspec.backend.api.RefApi;
import ru.atikhonov.techspec.backend.api.SubscriptionsApi;
import ru.atikhonov.techspec.backend.api.SubscriptionsApiDelegate;
import ru.atikhonov.techspec.backend.dto.ServiceInDto;
import ru.atikhonov.techspec.backend.dto.ServicesViewDto;
import ru.atikhonov.techspec.backend.dto.FileDto;
import ru.atikhonov.techspec.backend.dto.SubscriptionsInDto;
import ru.atikhonov.techspec.backend.exception.ValidateException;
import ru.atikhonov.techspec.backend.mapper.ServiceMapper;
import ru.atikhonov.techspec.backend.mapper.SubscriptionsMapper;
import ru.atikhonov.techspec.backend.mapper.SubsсriptionMapper;
import ru.atikhonov.techspec.backend.mapper.UserMapper;
import ru.atikhonov.techspec.backend.model.Services;
import ru.atikhonov.techspec.backend.model.SubscriptionsView;
import ru.atikhonov.techspec.backend.model.Users;
import ru.atikhonov.techspec.backend.repository.*;
import ru.atikhonov.techspec.backend.model.Subscriptions;
import ru.atikhonov.techspec.backend.util.CoreUtil;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
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
public class Subscriptions implements SubscriptionsApiDelegate {

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
     * @see RefApi#addUser
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
     * @see RefApi#deleteUser
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
     * @see RefApi#getUsers
     */
    @Override
    public ResponseEntity<List<UserDtos>> getUsers() {
        List<UserDtos> result =
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
     * @see RefApi#getUser
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
     * @see RefApi#modifyUser
     */
    @Override
    public ResponseEntity<Void> modifyUser(Long userId,
                                              UserInDto userInDto) {
        if (usersRepository.findById(userId).isPresent()) {

            Users entity = usersRepository.findById(userId).get();
            Users entityNew = usersMap.fromDtoToEntity(userInDto);
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
     * @see RefApi#addService
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
     * @see RefApi#deleteService
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
     * @see RefApi#getServices
     */
    @Override
    public ResponseEntity<List<ServiceDtos>> getServices() {
        List<ServiceDtos> result =
                servicesRepository
                        .findAll()
                        .stream()
                        .map(v -> serviceMapper.fromEntityToDto(v))
                        .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    /**
     * GET : Выборка типа сервиса
     *
     * @param serviceId ИД элемента справочника (required)
     * @return Данные по элементу справочника (status code 200)
     * @see RefApi#getService
     */
    @Override
    public ResponseEntity<ServiceDto> getService(Long serviceId) {
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
     * @see RefApi#modifyService
     */
    @Override
    public ResponseEntity<Void> modifyService(Long serviceId,
                                                 ServiceInDto serviceInDto) {
        if (servicesRepository.findById(serviceId).isPresent()) {

            Services entity = servicesRepository.findById(serviceId).get();
            Services entityNew = servicesMap.fromDtoToEntity(serviceInDto);
            CoreUtil.patch(entityNew, entity);
            servicesRepository.save(entity);
        }
        return ResponseEntity.noContent().build();

    }

//   Сервисы для подписки
    /**
     * POST: Добавление подписки
     *
     * @param subscriptionsInDto
     * @return Пустой ответ (status code 200)
     */
    @Override
    public ResponseEntity<Void> addSubscriptions(SubscriptionsInDto subscriptionsInDto) {
        //  Нужно проверять уникальность подписки
        Subscriptions subscriptionNew = subscriptionMapper.fromDtoToEntity(subscriptionInDto);
        subscriptionsRepository.save(subscriptionNew);

        return ResponseEntity.noContent().build();

    }

    /**
     * DELETE /demo2000/Subscriptions/{subscriptionId} : Удаление подписки
     *
     * @param subscriptionId ИД подписки (required)
     * @return Пустой ответ (status code 200)
     */
    @Override
    public ResponseEntity<Void> deleteSubscriptions(Long subscriptionId) {
        if (subscriptionsRepository.findById(subscriptionId).isPresent()) {
            Subscriptions subscription = subscriptionsRepository.findById(subscriptionId).get();
        }

        return ResponseEntity.noContent().build();

    }

    /**
     * GET /demo2000/Services/{subscriptionId} : Выборка сервиса
     *
     * @param subscriptionId ИД элемента справочника (required)
     * @return Данные по элементу справочника (status code 200)
     */
    @Override
    public ResponseEntity<SubscriptionViewDto> getService(Long subscriptionId) {
        Optional<SubscriptionsView> subscriptionsView = subscriptionsViewRepository.findById(subscriptionId);
        if (subscriptionsView.isPresent()) {
            SubscriptionViewDto result = subscriptionMapper
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
    public ResponseEntity<List<SubscriptionViewDto>> getServices(String requestType) {
        List<SubscriptionViewDto> result = subscriptionsViewRepository
                            .findAll()
                            .stream()
                            .map(v -> subscriptionMapper.fromViewToDto(v))
                            .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}
