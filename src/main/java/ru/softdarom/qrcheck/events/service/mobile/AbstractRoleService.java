package ru.softdarom.qrcheck.events.service.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.softdarom.qrcheck.events.dao.access.EventAccessService;

@Service
public abstract class AbstractRoleService implements RoleService {

    protected final EventAccessService eventAccessService;

    @Autowired
    protected AbstractRoleService(EventAccessService eventAccessService) {
        this.eventAccessService = eventAccessService;
    }

    protected Long getExternalUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Long) authentication.getPrincipal();
    }

}
