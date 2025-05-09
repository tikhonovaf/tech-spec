package ru.atikhonov.techspec.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.atikhonov.techspec.backend.api.ApiUtil;
import ru.atikhonov.techspec.backend.api.ServicesApiDelegate;
import ru.atikhonov.techspec.backend.api.SubscriptionsApi;
import ru.atikhonov.techspec.backend.api.UsersApiDelegate;
import ru.atikhonov.techspec.backend.dto.*;
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
public class ServiceService implements ServicesApiDelegate {

    private final ServicesRepository servicesRepository;
    private final ServiceMapper serviceMapper;

//   Сервисы для типов сервисов

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


}
