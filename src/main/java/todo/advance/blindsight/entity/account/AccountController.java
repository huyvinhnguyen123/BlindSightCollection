package todo.advance.blindsight.entity.account;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import todo.advance.blindsight.util.log.CRUDLogger;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;
    private static final String SECRET_KEY = "ipwaphveiwnjcajunzqidbnhmbyqvomt";
//===================================================================================================================================
    // & ADMIN SECTION
    /**
     * * request create admin account
     * 
     * @param account
     * @return
     */
    @PostMapping("/create/" + SECRET_KEY + "-admin" )
    public ResponseEntity<Boolean> createAdminAccount(@RequestBody Account account) {
        log.info(CRUDLogger.REQUEST_CREATE("account"));
        boolean isCreated = accountService.createAdminAccount(account);
        return new ResponseEntity<>(isCreated, HttpStatus.CREATED);
    }

    /**
     * * request find all accounts
     * 
     * @return
     */
    @GetMapping("")
    public ResponseEntity<Iterable<Account>> findAllAccounts() {
        log.info(CRUDLogger.REQUEST_SELECT("accounts"));
        Iterable<Account> findAllAccounts = accountService.findAllAccounts();
        if(findAllAccounts.iterator().hasNext()) {
            return new ResponseEntity<>(findAllAccounts, HttpStatus.FOUND);
        } else {
            log.info("No account found");
            return new ResponseEntity<>(findAllAccounts, HttpStatus.OK);
        }
    }

    /**
     * * request find account by id
     * 
     * @param accountId
     * @return
     */
    @GetMapping("/{accountId}/account")
    public ResponseEntity<Account> findAccountById(@PathVariable String accountId) {
        log.info(CRUDLogger.REQUEST_SELECT("account"));
        Account findAccountById = accountService.findAccountById(accountId);
        return new ResponseEntity<>(findAccountById, HttpStatus.FOUND);
    }

    /**
     * * request update role
     * 
     * @param accountId
     * @param role
     * @return
     */
    @PutMapping("/update-role/{accountId}/role")
    public ResponseEntity<Boolean> UpdateAccountRole(@PathVariable String accountId, @RequestBody String role) {
        log.info(CRUDLogger.REQUEST_UPDATE("role for account"));
        if(accountService.changeUserRole(accountId, role).equals(false)){
            return new ResponseEntity<>(accountService.changeUserRole(accountId, role), HttpStatus.EXPECTATION_FAILED);
        } else {
            return new ResponseEntity<>(accountService.changeUserRole(accountId, role), HttpStatus.CREATED);
        }
    }
//===================================================================================================================================
    // & USER SECTION
    /**
     * * request create user account
     * 
     * @param account
     * @return
     */
    @PostMapping("/create" )
    public ResponseEntity<Boolean> createUserAccount(@RequestBody Account account) {
        log.info(CRUDLogger.REQUEST_CREATE("account"));
        boolean isCreated = accountService.createUserAccount(account);
        return new ResponseEntity<>(isCreated, HttpStatus.CREATED);
    }

    /**
     * * request send reset mail
     * 
     * @param email
     * @return
     */
    @GetMapping("/send-message")
    public ResponseEntity<String> sendMailResetPassword(@RequestParam String email) {
        log.info(CRUDLogger.REQUEST_SEARCH(email));
        String sendMail = accountService.sendMailResetPassword(email);
        return new ResponseEntity<>(sendMail, HttpStatus.FOUND);
    }

    /**
     * * request reset password
     * 
     * @param email
     * @return
     */
    @PutMapping("/reset-password")
    public ResponseEntity<Boolean> resetPassword(@RequestParam String email, @RequestBody ResetPasswordDTO resetPasswordDTO){
        log.info(CRUDLogger.REQUEST_UPDATE("password"));
        boolean isReset  = accountService.resetPassword(email, resetPasswordDTO);
        return new ResponseEntity<>(isReset, HttpStatus.CREATED);
    }
}
