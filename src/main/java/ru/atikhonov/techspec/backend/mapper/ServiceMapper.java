package ru.atikhonov.techspec.backend.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.atikhonov.techspec.backend.dto.ServiceDto;
import ru.atikhonov.techspec.backend.dto.ServiceInDto;
import ru.atikhonov.techspec.backend.dto.SubscriptionsTopViewDto;
import ru.atikhonov.techspec.backend.model.Services;
import ru.atikhonov.techspec.backend.model.Subscriptions;
import ru.atikhonov.techspec.backend.model.SubscriptionsTopView;
import ru.atikhonov.techspec.backend.repository.ServicesRepository;
import ru.atikhonov.techspec.backend.repository.ServicesRepository;
import ru.atikhonov.techspec.backend.util.CoreUtil;

import java.util.Optional;

/**
 * Маппинг:
 * -  между entity и dto rest сервиса
 */
@Service
@RequiredArgsConstructor
public class ServiceMapper {
    private final ServicesRepository bannerCategoryRepository;
    /**
     * Маппинг из entity в DTO
     *
     * @param entity - строка из entity
     * @return Данные в структуре DTO
     */
    public ServiceDto fromEntityToDto(Object entity) {
        ServiceDto result = new ServiceDto();
        CoreUtil.patch(entity, result);
        return result;
    }

    /**
     * Маппинг из DTO в Entity
     *
     * @param dto - строка из DTO
     * @return данные в структуре Entity
     */
    public Services fromDtoToEntity(ServiceInDto dto) {
        Services result = new Services();
        CoreUtil.patch(dto, result);

        return result;
    }

    /**
     * Маппинг из view в DTO
     *
     * @param view - строка из view
     * @return Данные в структуре DTO
     */
    public SubscriptionsTopViewDto fromViewToDto(SubscriptionsTopView view) {
        SubscriptionsTopViewDto result = new SubscriptionsTopViewDto();
        CoreUtil.patch(view, result);
        return result;
    }

}


