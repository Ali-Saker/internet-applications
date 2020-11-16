package com.internetapplications.security;

import com.google.common.base.CaseFormat;
import com.internetapplications.entity.User;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class PermissionEvaluatorImpl implements PermissionEvaluator {

    PermissionEvaluatorImpl() {
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetType, Object privilege) {
        if (authentication != null && privilege instanceof String) {
            return targetType != null ? this.hasPrivilege(authentication, targetType.toString(), privilege.toString()) : this.hasPermission(authentication, privilege.toString());
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object privilege) {
        if (authentication != null && privilege instanceof String) {
            return targetType != null ? this.hasPrivilege(authentication, targetType, privilege.toString()) : this.hasPermission(authentication, privilege.toString());
        }
        return false;
    }

    private boolean hasPrivilege(Authentication auth, String targetType, String privilege) {
        return this.hasPermission(auth, targetType.toUpperCase().replace("CONTROLLER", "") + "_" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, privilege));
    }

    private boolean hasPermission(Authentication auth, String code) {
        User user = (User)auth.getPrincipal();
        return user.hasPermission(code);
    }
}
