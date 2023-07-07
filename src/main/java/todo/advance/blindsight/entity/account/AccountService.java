package todo.advance.blindsight.entity.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import todo.advance.blindsight.config.PasswordEncrypt;
import todo.advance.blindsight.entity.log.LogService;
import todo.advance.blindsight.entity.mail.EmailDetail;
import todo.advance.blindsight.entity.mail.EmailDetailService;
import todo.advance.blindsight.entity.role.Role;
import todo.advance.blindsight.entity.role.RoleEnum;
import todo.advance.blindsight.entity.role.RoleService;
import todo.advance.blindsight.util.custom.exception.NotFoundException;
import todo.advance.blindsight.util.custom.mail.MaiSenderMessages;
import todo.advance.blindsight.util.generate.code.CodeGenerator;
import todo.advance.blindsight.util.log.CRUDLogger;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final RoleService roleService;
    private final EmailDetailService emailDetailService;
    private final EntityManager entityManager;
    private final LogService logService;
//===================================================================================================================================
    // & CUSTOM SECTION
    /**
     * * send verify message
     * 
     * @param email
     * @param otp
     * @return
     */
    private EmailDetail sendVerifyMessage(String email) {
        EmailDetail newMailMessage = new EmailDetail();
        newMailMessage.setRecipient(email);
        newMailMessage.setSubject("Reset Password");
        newMailMessage.setMsgBody(MaiSenderMessages.verifyEmailMessage());
        // send reset message
        emailDetailService.sendVerifyMail(newMailMessage);
        return newMailMessage;
    }
    
    /**
     * * send reset message
     * 
     * @param email
     * @param otp
     * @return
     */
    private EmailDetail sendResetMessage(String email, String otp) {
        EmailDetail newMailMessage = new EmailDetail();
        newMailMessage.setRecipient(email);
        newMailMessage.setSubject("Reset Password");
        newMailMessage.setMsgBody(MaiSenderMessages.resetPasswordMessage(otp));
        // send reset message
        emailDetailService.sendResetMail(newMailMessage);
        return newMailMessage;
    }
    
    /**
     * * find account by email
     * 
     * @param email
     * @return
     */
    public Account findAccountByEmail(String email) {
        Account existingAccount = accountRepository.findByEmail(email).orElseThrow(() -> {
            log.info(CRUDLogger.SEARCH_ONE_FAIL(email));
            logService.saveErrorLog("account with email does not exist: " + email);
            return new NotFoundException("Not found this account: " + email);
        });
        log.info(CRUDLogger.SEARCH_ONE_SUCCESS(email));
        return existingAccount;
    }
//===================================================================================================================================
    // & MAIN SECTION
    /**
     * * find all accounts (using Criteria)
     * 
     * @return
     */
    public Iterable<Account> findAllAccounts() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> accountRoot = query.from(Account.class);

        query.select(accountRoot);

        List<Account> accounts = entityManager.createQuery(query).getResultList();
        log.info(CRUDLogger.SELECT_ALL_SUCCESS("accounts"));
        logService.saveInfoLog("accounts was found");
        return accounts;
    }

    /**
     * * find account by id (using Criteria Predicates to combine "and" and "or" conditions)
     * 
     * @param id
     * @return
     */
    public Account findAccountByIdWithCriteriaPredicate(String id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> accountRoot = query.from(Account.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(accountRoot.get("accountId"), id));
        predicates.add(cb.or(
                cb.equal(accountRoot.get("accountId"), id),
                cb.equal(accountRoot.get("accountId"), id)
        ));
        query.where(predicates.toArray(new Predicate[0]));

        Account existingAccount = entityManager.createQuery(query).getSingleResult();
        log.info(CRUDLogger.SELECT_ONE_SUCCESS("account"));
        return existingAccount;
    }

    /**
     * * find account by id (using JPA) 
     * 
     * @param id
     * @return
     */
    public Account findAccountById(String id) {
        Account existingAccount = accountRepository.findById(id).orElseThrow(() -> {
            log.error(CRUDLogger.SEARCH_ONE_FAIL(id));
            log.trace("id must be unique & same with one in database");
            logService.saveErrorLog("account with id does not exist: " + id);

            return new NotFoundException("Not found this account: " + id);
        });

        log.info(CRUDLogger.SELECT_ONE_SUCCESS("account"));
        return existingAccount;
    }

    /**
     * * create admin account
     * 
     * @param account
     * @return
     */
    @Transactional
    public Boolean createAdminAccount(Account newAccount) {
        boolean isCreated = false;

        // create account
        newAccount.setPassword(PasswordEncrypt.bCryptpasswordEncoder(newAccount.getPassword()));
        accountRepository.save(newAccount);
        log.info(CRUDLogger.CREATE_SUCCESS("account"));
        logService.saveInfoLog("account was created");

        // assign list roles for account
        Set<Role> roles = roleService.addRoles(newAccount.getAccountId(), RoleEnum.ADMIN);
        newAccount.setRoles(roles);

        if(newAccount.getRoles().size() > 0) {
            // save in db
            accountRepository.save(newAccount);
            log.info(CRUDLogger.UPDATE_SUCCESS("account with role admin"));
            logService.saveInfoLog("assigned role admin for new account");

            // is created
            isCreated = true;
        }
        
        if(isCreated == true) {
            // send verify email
            sendVerifyMessage(newAccount.getEmail()); 
            return isCreated;
        } else {
            return isCreated;
        }
    }

    /**
     * * create user account
     * 
     * @param account
     * @return
     */
    @Transactional
    public Boolean createUserAccount(Account newAccount) {
        boolean isCreated = false;

        // create account
        newAccount.setPassword(PasswordEncrypt.bCryptpasswordEncoder(newAccount.getPassword()));
        accountRepository.save(newAccount);
        log.info(CRUDLogger.CREATE_SUCCESS("account"));
        logService.saveInfoLog("account was created");

        // assign list roles for account
        Set<Role> roles = roleService.addRoles(newAccount.getAccountId(), RoleEnum.GUEST);
        newAccount.setRoles(roles);

        if(newAccount.getRoles().size() > 0) {
            // save in db
            accountRepository.save(newAccount);
            log.info(CRUDLogger.UPDATE_SUCCESS("account with role user"));
            logService.saveInfoLog("assigned role admin for new account");

            // is created
            isCreated = true;
        }
        
        if(isCreated == true) {
            // send verify email
            sendVerifyMessage(newAccount.getEmail()); 
            return isCreated;
        } else {
            return isCreated;
        }
    }

    /**
     * ! error method (check for and insert roles in account)
     * * change user role
     * 
     * @param accountId
     * @param role
     * @return
     */
    @Transactional
    public Boolean changeUserRole(String accountId, String role) {
        boolean isUpdated = false;

        Account existingAccount = findAccountById(accountId);

        for(RoleEnum roleEnum: RoleEnum.values()){
            if(roleEnum.getRole().equalsIgnoreCase(role)){
                log.error("Role name not valid: " + role);
            } else {
                if(roleEnum.getRole().equals("ADMIN") || roleEnum.getRole().equals("OWNER")) {
                    log.warn("You don't have permission to do this");
                    log.info("Contact ADMIN or OWNER to change your role"); 
                    // continue;
                } else {
                    Set<Role> roles = roleService.addRoles(existingAccount.getAccountId(), roleEnum);
                    existingAccount.setRoles(roles);

                    for(Role r: roles){
                        System.out.println(r.getRoleId());
                        System.out.println(r.getRoleName());
                    }
                    
                    isUpdated = true;
                    break;
                }
            }
        }
        

        // for(RoleEnum roleEnum: RoleEnum.values()){
        //     if(roleEnum.getRole().equals(role)){
        //         if(roleEnum.getRole().equals("ADMIN") || roleEnum.getRole().equals("OWNER")) {
        //             log.warn("You don't have permission to do this");
        //             log.info("Contact ADMIN or OWNER to change your role"); 
        //             // continue;
        //         } else {
        //             Set<Role> roles = roleService.addRoles(existingAccount.getAccountId(), roleEnum);
        //             existingAccount.setRoles(roles);
        //             isUpdated = true;
        //             break;
        //         }
        //     } else {
        //         log.error("role name not right " + roleEnum.getRole());
        //         log.warn("Seem to be role it's not exist");
        //         // continue;
        //     }
        // }

        if(isUpdated == true) {
            accountRepository.save(existingAccount);
            log.info(CRUDLogger.UPDATE_SUCCESS("role"));
            logService.saveInfoLog("assigned role " + role + " for account " + existingAccount.getAccountId());
            return true;
        } else {
            log.error(CRUDLogger.UPDATE_FAIL("role"));
            logService.saveErrorLog("Fail to assign role " + role + " for account " + existingAccount.getAccountId());
            return false;
        }
    }


    /**
     * * send reset mail
     * 
     * @param email
     * @return
     */
    @Transactional
    public String sendMailResetPassword(String email) {
        // use method find account by email
        Account existingAccount = findAccountByEmail(email);
        // set code otp for account was found
        existingAccount.setCodeOTP(CodeGenerator.generateOTP());
        // save in db
        accountRepository.save(existingAccount);
        log.info(CRUDLogger.UPDATE_SUCCESS("account with new OTP code"));
        String accountEmail = existingAccount.getEmail();
        // send reset mail
        sendResetMessage(accountEmail, existingAccount.getCodeOTP());
        // return email
        return accountEmail;
    }

    /**
     * * reset password
     * 
     * @param email
     * @param resetPasswordDTO
     * @return
     */
    @Transactional
    public Boolean resetPassword(String email, ResetPasswordDTO resetPasswordDTO) {
        // find account by email
        Account existingAccount = accountRepository.findByEmail(email).orElseThrow(
            () -> new NotFoundException("not found this email: " + email)
        );
        log.info(CRUDLogger.SELECT_ONE_SUCCESS("account"));
        
        // check when user input code otp is match with code otp was sent 
        if(existingAccount.getCodeOTP().equals(resetPasswordDTO.getCodeOTP())) {
            
            // set password and retype password for existing account
            existingAccount.setPassword(resetPasswordDTO.getPassword());
            existingAccount.setRetypePassword((resetPasswordDTO.getPassword()));

            // check when password and retype password is the same
            if(existingAccount.getPassword().equals(existingAccount.getRetypePassword())) {
               
                // set password one time again then encrypt this password
                existingAccount.setPassword(PasswordEncrypt.bCryptpasswordEncoder(existingAccount.getPassword()));
                // save in db
                accountRepository.save(existingAccount);
                log.info(CRUDLogger.UPDATE_SUCCESS("password"));
                return true;
            } else {
                log.error(CRUDLogger.UPDATE_FAIL("password - reason: password and retypePassword not match"));
                return false;
            }
        } else {
            log.error(CRUDLogger.UPDATE_FAIL("password - reason: your OTP code not match"));
            return false;
        }
    }

    /**
     * * delete account
     * 
     * @param accountId
     */
    public void deleteAccount(String accountId) {
        accountRepository.deleteById(accountId);
        log.info(CRUDLogger.DELETE_SUCCESS("account"));
    }
}   
