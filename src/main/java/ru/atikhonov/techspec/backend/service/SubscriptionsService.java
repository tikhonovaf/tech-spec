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
import ru.atikhonov.techspec.backend.model.Subscriptions;
import ru.atikhonov.techspec.backend.model.SubscriptionsTopView;
import ru.atikhonov.techspec.backend.model.SubscriptionsView;
import ru.atikhonov.techspec.backend.repository.*;

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
public class SubscriptionsService implements SubscriptionsApiDelegate {

    private final SubscriptionsRepository subscriptionsRepository;
    private final SubscriptionsViewRepository subscriptionsViewRepository;
    private final SubsсriptionMapper subsсriptionMapper;
    private final SubscriptionsTopViewRepository subscriptionsTopViewRepository;
    private final ServiceMapper serviceMapper;


    /**
     * получить ТОП-3 популярных подписок
     *
     * @return Список подписок (status code 200)
     */
    @Override
    public ResponseEntity<List <SubscriptionsTopViewDto> > getTopServices() {
        List<SubscriptionsTopViewDto> result =
                subscriptionsTopViewRepository
                        .findAll()
                        .stream()
                        .map(v -> serviceMapper.fromViewToDto(v))
                        .collect(Collectors.toList());
        return ResponseEntity.ok(result);

    }

}
