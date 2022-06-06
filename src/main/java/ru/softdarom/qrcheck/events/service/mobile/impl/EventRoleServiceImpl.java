package ru.softdarom.qrcheck.events.service.mobile.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.softdarom.qrcheck.events.model.base.RoleType;
import ru.softdarom.qrcheck.events.model.dto.internal.InternalEventDto;
import ru.softdarom.qrcheck.events.service.mobile.EventRoleService;
import ru.softdarom.qrcheck.events.service.mobile.RoleService;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "SERVICE")
public class EventRoleServiceImpl implements EventRoleService {

    private final Set<RoleService> roleServices;
    private Map<RoleType, RoleService> roleToRoleService;

    @Autowired
    EventRoleServiceImpl(Set<RoleService> roleServices) {
        this.roleServices = roleServices;
    }

    @PostConstruct
    void init() {
        roleToRoleService = roleServices
                .stream()
                .collect(Collectors.toMap(RoleService::getType, Function.identity()));
    }

    @Override
    public Page<InternalEventDto> getAllByRole(Pageable pageable) {
        var currentRole =
                RoleType.rolesOfAuthorities()
                        .stream()
                        .findAny()
                        .orElseThrow();

        var roleService = roleToRoleService.get(currentRole);
        if (Objects.isNull(roleService)) {
            throw new UnsupportedOperationException("Not found a role service for a role: " + currentRole);
        }
        return roleService.getAll(pageable);
    }
}
