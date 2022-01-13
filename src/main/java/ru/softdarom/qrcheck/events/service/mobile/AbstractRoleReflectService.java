package ru.softdarom.qrcheck.events.service.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.softdarom.qrcheck.events.dao.access.EventAccessService;

@Service
public abstract class AbstractRoleReflectService implements RoleReflectService {

    protected final EventAccessService eventAccessService;

    @Autowired
    protected AbstractRoleReflectService(EventAccessService eventAccessService) {
        this.eventAccessService = eventAccessService;
    }

    protected Long getExternalUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Long) authentication.getPrincipal();
    }

}
