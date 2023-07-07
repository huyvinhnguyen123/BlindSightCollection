package todo.advance.blindsight.entity.role;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import todo.advance.blindsight.entity.account.Account;
import todo.advance.blindsight.entity.account.AccountRepository;
import todo.advance.blindsight.entity.log.LogService;
import todo.advance.blindsight.util.custom.exception.NotFoundException;
import todo.advance.blindsight.util.log.CRUDLogger;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final AccountRepository accountRepository;
    private final LogService logService;
//===================================================================================================================================
    // & MAIN SECTION
    /**
     * * add role
     * 
     * @param roleName
     * @return
     */
    @Transactional
    public Role addRole(String accountId, RoleEnum role) {
        // find account
        Account existingAccount = accountRepository.findById(accountId)
            .orElseThrow(() -> {
                log.error(CRUDLogger.SEARCH_ONE_FAIL(accountId));
                log.trace("id must be unique & same with one in database");
                logService.saveErrorLog("account with id does not exist: " + accountId);

                return new NotFoundException("Not found this account: " + accountId);
            });

        log.info(CRUDLogger.SELECT_ONE_SUCCESS("account"));
        
        Role newRole = new Role(); // create admin role
        newRole.setAccount(existingAccount); // assign role for account

        newRole.setRoleName(role.getRole());
        newRole.setRoleCode(role.getCode());

        roleRepository.save(newRole); // save in db

        log.info(CRUDLogger.CREATE_SUCCESS("role"));
        logService.saveInfoLog("role was created for account");

        return newRole;
    }

    /**
     * * add list roles for account
     * 
     * @param accountId
     * @param role
     * @return
     */
    public Set<Role> addRoles(String accountId, RoleEnum role){
        Set<Role> roles = new HashSet<Role>();
        roles.add(addRole(accountId, role));
        return roles;
    }
}
