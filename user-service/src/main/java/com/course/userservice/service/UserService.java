package com.course.userservice.service;


import com.course.userservice.dto.UserDto;
import com.course.userservice.repository.UserRepository;
import com.course.userservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public Flux<UserDto> all() {
        return this.repository.findAll().map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> getUserById(final int userId) {
        return this.repository.findById(userId)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> save(Mono<UserDto> dto) {
        return dto
                .map(EntityDtoUtil::toEntity)
                .flatMap(this.repository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<UserDto> update(int userId, Mono<UserDto> dto) {
        return  this.repository.findById(userId)
                .flatMap(user -> dto
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(entity -> entity.setId(userId)))
                .flatMap(this.repository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteUser(int userId) {
        return  this.repository.deleteById(userId);
    }


}
