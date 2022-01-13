package ru.softdarom.qrcheck.events.model.base;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.softdarom.qrcheck.events.exception.NotFoundException;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j(topic = "UTIL")
@RequiredArgsConstructor
@Getter
public enum RoleType {

    USER(Ability.USER),
    CHECKMAN(Ability.CHECKMAN),
    PROMOTER(Ability.PROMOTER),
    API_KEY(Ability.API_KEY);

    private static final String PREFIX = "ROLE_";

    private final String role;

    public static RoleType roleOf(String role) {
        return EnumSet.allOf(RoleType.class)
                .stream()
                .filter(it -> Objects.equals(it.role, formatStringRole(role)))
                .findAny()
                .orElseThrow(() -> new NotFoundException("Not found role " + role));
    }

    public static Set<RoleType> rolesOfAuthorities() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(it -> it.contains(PREFIX))
                .map(RoleType::roleOf)
                .collect(Collectors.toSet());
    }

    public static String[] getMobileAbilityRoles() {
        return Stream.of(USER, CHECKMAN, PROMOTER)
                .map(RoleType::getRole)
                .toArray(String[]::new);
    }

    public static String[] getInternalAbilityRoles() {
        return Stream.of(API_KEY)
                .map(RoleType::getRole)
                .toArray(String[]::new);
    }

    private static String formatStringRole(String role) {
        if (Objects.isNull(role)) {
            LOGGER.debug("The passed role is null. Return empty string.");
            return "";
        }

        return role.startsWith(PREFIX) ? role.replace(PREFIX, "") : role;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Ability {

        public static final String USER = "USER";
        public static final String CHECKMAN = "CHECKMAN";
        public static final String PROMOTER = "PROMOTER";
        public static final String API_KEY = "API_KEY";

    }
}
