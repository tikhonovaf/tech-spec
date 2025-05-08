package ru.atikhonov.techspec.backend.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;
import ru.atikhonov.techspec.backend.dto.UserInDto;
import ru.atikhonov.techspec.backend.dto.UserDto;
import ru.atikhonov.techspec.backend.model.Subscriptions;
import ru.atikhonov.techspec.backend.model.Users;
import ru.atikhonov.techspec.backend.repository.UsersRepository;
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
    public UserDto fromEntityToDto(Users entity) {
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
    public Users fromDtoToEntity(UserInDto dto) {
        Users result = new Users();
        CoreUtil.patch(dto, result);

        return result;
    }

}


