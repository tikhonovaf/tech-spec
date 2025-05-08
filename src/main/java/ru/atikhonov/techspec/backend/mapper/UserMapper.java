package ru.atikhonov.techspec.backend.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.User;
import ru.atikhonov.techspec.backend.dto.UserInDto;
import ru.atikhonov.techspec.backend.dto.UserViewDto;
import ru.atikhonov.techspec.backend.model.Subscriptions;
import ru.atikhonov.techspec.backend.repository.UserRepository;
import ru.atikhonov.techspec.backend.repository.UsersRepository;
import ru.atikhonov.techspec.backend.repository.UsersRepository;
import ru.atikhonov.techspec.backend.util.CoreUtil;

import java.util.Optional;

/**
 * Маппинг:
 * -  между entity и dto rest сервиса
 */
@Service
@RequiredArgsConstructor
public class UserMapper {
    private final UsersRepository projectRepository;
    /**
     * Маппинг из entity в DTO
     *
     * @param entity - строка из entity
     * @return Данные в структуре DTO
     */
    public UserDto fromEntityToDto(Object entity) {
        UserDto result = new UserDto();
        CoreUtil.patch(entity, result);
        return result;
    }

    /**
     * Маппингиз DTO в Entity
     *
     * @param dto - строка из DTO
     * @return данные в структуре Entity
     */
    public Subscriptions fromDtoToEntity(UserInDto dto) {
        Subscriptions result = new Subscriptions();
        CoreUtil.patch(dto, result);

        return result;
    }

}


