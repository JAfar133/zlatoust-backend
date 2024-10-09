package com.zlatoust.services.dto.mapper;

public interface EntityDTOMapper<D, E> {
    E toEntity(D dto);
    D toDto(E entity);
}
