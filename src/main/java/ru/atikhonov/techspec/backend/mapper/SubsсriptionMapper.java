package ru.atikhonov.techspec.backend.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Subsription;
import ru.atikhonov.techspec.backend.dto.SubsriptionInDto;
import ru.atikhonov.techspec.backend.dto.SubsriptionViewDto;
import ru.atikhonov.techspec.backend.model.Services;
import ru.atikhonov.techspec.backend.model.Subscriptions;
import ru.atikhonov.techspec.backend.model.Users;
import ru.atikhonov.techspec.backend.repository.ServicesRepository;
import ru.atikhonov.techspec.backend.repository.SubsriptionRepository;
import ru.atikhonov.techspec.backend.repository.SubsriptionsRepository;
import ru.atikhonov.techspec.backend.repository.SubscriptionsRepository;
import ru.atikhonov.techspec.backend.repository.UsersRepository;
import ru.atikhonov.techspec.backend.util.CoreUtil;

import java.util.Optional;

/**
 * Маппинг:
 * -  между entity и dto rest сервиса
 */
@Service
@RequiredArgsConstructor
public class SubsсriptionMapper {
    private final SubscriptionsRepository subscriptionsRepository;
    private final SubscriptionsViewRepository subscriptionsViewRepository;
    private final UsersRepository usersRepository;
    private final ServicesRepository servicesRepository;


    /**
     * Маппинг из view в DTO
     *
     * @param view - строка из entity
     * @return Данные в структуре DTO
     */
    public SubsriptionViewDto fromViewToDto(Object view) {
        SubsriptionViewDto result = new SubsriptionViewDto();
        CoreUtil.patch(view, result);
        return result;
    }

    /**
     * Маппингиз DTO в Entity
     *
     * @param dto - строка из DTO
     * @return данные в структуре Entity
     */
    public Subscriptions fromDtoToEntity(SubsriptionInDto dto) {
        Subscriptions result = new Subscriptions();
        CoreUtil.patch(dto, result);

        if (dto.getUserId() != null) {
            Optional<Users> usersOptional = usersRepository.findById(dto.getUserId());
            if (usersOptional.isPresent()) {
                result.setUser(usersOptional.get());
            }
        } else {
            result.setUser(null);
        }

        if (dto.getServiceId() != null) {
            Optional<Services> servicesOptional = servicesRepository.findById(dto.getServiceId());
            if (servicesOptional.isPresent()) {
                result.setUser(servicesOptional.get());
            }
        } else {
            result.setUser(null);
        }

        return result;
    }
}


