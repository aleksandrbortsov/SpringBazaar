package com.springbazaar.web.security;

import com.springbazaar.domain.Permission;
import com.springbazaar.domain.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BasicPolicyEnforcement implements PolicyEnforcement {
    private static final Logger logger = LoggerFactory.getLogger(BasicPolicyEnforcement.class);

    @Override
    public boolean check(Object subject, Object resource, Object action, Object environment) {
        //Get all user permissions
        Collection<? extends GrantedAuthority> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Set<Permission> allPermissions = new HashSet<>();

        for (GrantedAuthority authority : authorities) {
            Role role = (Role) authority;
            Set<Permission> permissions = role.getPermissions();
            allPermissions.addAll(permissions);
        }
        return checkPermissions(allPermissions, String.valueOf(action));
    }

    private boolean checkPermissions(Set<Permission> permissions, String action) {
        for (Permission permission : permissions) {
            if (permission.getName().equals(action)) {
                return true;
            }
        }
        return false;
    }

//    private List<PolicyRule> filterRules(List<PolicyRule> allRules, SecurityAccessContext cxt) {
//        List<PolicyRule> matchedRules = new ArrayList<>();
//        for (PolicyRule rule : allRules) {
//            try {
//                if (rule.getTarget().getValue(cxt, Boolean.class)) {
//                    matchedRules.add(rule);
//                }
//            } catch (EvaluationException exception) {
//                logger.info("An error occurred while evaluating PolicyRule.", exception);
//            }
//        }
//        return matchedRules;
//    }
//
//    private boolean checkRules(List<PolicyRule> matchedRules, SecurityAccessContext cxt) {
//        for (PolicyRule rule : matchedRules) {
//            try {
//                if (rule.getCondition().getValue(cxt, Boolean.class)) {
//                    return true;
//                }
//            } catch (EvaluationException exception) {
//                logger.info("An error occurred while evaluating PolicyRule.", exception);
//            }
//        }
//        return false;
//    }
}
