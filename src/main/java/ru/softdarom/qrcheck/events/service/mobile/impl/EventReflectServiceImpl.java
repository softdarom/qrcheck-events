package ru.softdarom.qrcheck.events.service.mobile.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.softdarom.qrcheck.events.model.base.RoleType;
import ru.softdarom.qrcheck.events.model.dto.internal.InternalEventDto;
import ru.softdarom.qrcheck.events.service.mobile.EventReflectorService;
import ru.softdarom.qrcheck.events.service.mobile.RoleReflectService;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "SERVICE")
public class EventReflectServiceImpl implements EventReflectorService {

    private final Set<RoleReflectService> roleReflectServices;

    private Map<RoleType, RoleReflectService> roleToReflectService;

    @Autowired
    EventReflectServiceImpl(Set<RoleReflectService> roleReflectServices) {
        this.roleReflectServices = roleReflectServices;
    }

    @PostConstruct
    void init() {
        roleToReflectService = roleReflectServices
                .stream()
                .collect(Collectors.toMap(RoleReflectService::getType, Function.identity()));
    }

    @Override
    public Page<InternalEventDto> getAllByRole(Pageable pageable) {
        var currentRole =
                RoleType.rolesOfAuthorities()
                        .stream()
                        .findAny()
                        .orElseThrow();

        var reflectService = roleToReflectService.get(currentRole);
        if (Objects.isNull(reflectService)) {
            throw new UnsupportedOperationException("Not found a reflect service for a role: " + currentRole);
        }
        return reflectService.getAll(pageable);
    }
}
