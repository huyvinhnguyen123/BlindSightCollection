package todo.advance.blindsight.entity.demoData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import todo.advance.blindsight.config.PasswordEncrypt;
import todo.advance.blindsight.entity.account.Account;
import todo.advance.blindsight.entity.account.AccountRepository;
import todo.advance.blindsight.entity.card.Card;
import todo.advance.blindsight.entity.card.CardRepository;
import todo.advance.blindsight.entity.profile.Profile;
import todo.advance.blindsight.entity.profile.ProfileRepository;
import todo.advance.blindsight.entity.role.Role;
import todo.advance.blindsight.entity.role.RoleEnum;
import todo.advance.blindsight.entity.role.RoleRepository;
import todo.advance.blindsight.entity.subCard.SubCard;
import todo.advance.blindsight.entity.subCard.SubCardRepository;
import todo.advance.blindsight.entity.todo.Todo;
import todo.advance.blindsight.entity.todo.TodoRepository;
import todo.advance.blindsight.util.generate.date.DateGenerator;
import todo.advance.blindsight.util.log.CRUDLogger;

@Service
@Slf4j
@RequiredArgsConstructor
public class DemoDataService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final ProfileRepository profileRepository;
    private final CardRepository cardRepository;
    private final SubCardRepository subCardRepository;
    private final TodoRepository todoRepository;
//===================================================================================================================================
    // & MAIN SECTION
    /**
     * * insert all demo data to database
     */
    @Transactional
    public void insertDemoData() {
        List<Account> accounts = createDemoAccounts();
        accountRepository.saveAll(accounts);
        log.info(CRUDLogger.CREATE_SUCCESS("List account"));

        List<Role> roles = createDemoRoles(RoleEnum.GUEST);
        roleRepository.saveAll(roles);
        log.info(CRUDLogger.CREATE_SUCCESS("List role"));

        List<Account> updateAccounts = updateAccountWithRoles(roles);
        accountRepository.saveAll(updateAccounts);
        log.info(CRUDLogger.UPDATE_SUCCESS("List account"));

        List<Profile> profiles = createDemoProfiles();
        profileRepository.saveAll(profiles);
        log.info(CRUDLogger.CREATE_SUCCESS("List profile"));

        List<Card> cards = createDemoCards();
        cardRepository.saveAll(cards);
        log.info(CRUDLogger.CREATE_SUCCESS("List card"));

        List<SubCard> subCards = createDemoSubCards();
        subCardRepository.saveAll(subCards);
        log.info(CRUDLogger.CREATE_SUCCESS("List subcard"));

        List<Todo> todos = createDemoTodos();
        todoRepository.saveAll(todos);
        log.info(CRUDLogger.CREATE_SUCCESS("List todo"));

        List<SubCard> updateSubCardsWithTodos = updateSubCardWithTodo(todos);
        subCardRepository.saveAll(updateSubCardsWithTodos);
        log.info(CRUDLogger.UPDATE_SUCCESS("List subcards with todos"));

        List<SubCard> updateSubCardsWithProfiles = updateSubCardsWithProfiles(profiles);
        subCardRepository.saveAll(updateSubCardsWithProfiles);
        log.info(CRUDLogger.UPDATE_SUCCESS("List subcards with profiles"));

        log.info("Demo data inserted successfully");
    }

    private List<Account> createDemoAccounts() {
        List<Account> accounts = new ArrayList<>();

        for(int i = 1; i <= 7; i++) {
            Account account = new Account();
            account.setEmail("user" + i + "@example.com");
            account.setPassword(PasswordEncrypt.bCryptpasswordEncoder("password" + i));
            accounts.add(account);
        }

        return accounts;
    }

    private List<Role> createDemoRoles(RoleEnum role) {
        List<Role> roles = new ArrayList<>();

        List<Account> accounts = (List<Account>) accountRepository.findAll();

        for(int i = 0; i < accounts.size(); i++) {
            Role roleAccount = new Role();
            roleAccount.setAccount(accounts.get(i));
            roleAccount.setRoleName(role.getRole());
            roleAccount.setRoleCode(role.getCode());
            roles.add(roleAccount);
        }

        return roles;
    }

    private List<Account> updateAccountWithRoles(List<Role> roles) {
        Set<Role> rolesSet = new HashSet<>(roles);

        List<Account> accounts = (List<Account>) accountRepository.findAll();

        for(int i = 0; i < accounts.size(); i++) {
            Account existingAccount = accounts.get(i);
            existingAccount.setRoles(rolesSet);
        }

        return accounts;
    } 

    private List<Profile> createDemoProfiles() {
        List<Profile> profiles = new ArrayList<>();

        List<Account> accounts = (List<Account>) accountRepository.findAll();

        for(int i = 0; i < accounts.size(); i++) {
            Account existingAccount = accounts.get(i);
            Profile profile = new Profile();
            profile.setAccount(existingAccount);
            profile.setBio("Write your bio");
            profile.setAvatar("https://cdn-icons-png.flaticon.com/512/6596/6596121.png");
            profiles.add(profile);
        }

        return profiles;
    }

    private List<Card> createDemoCards() {
        List<Card> cards = new ArrayList<>();

        for(int i = 1; i <= 7; i++) {
            Card card = new Card();
            card.setCardName("Card " + i);
            card.setCardImage("Card Image");
            cards.add(card);
        }

        return cards;
    }

    private List<SubCard> createDemoSubCards() {
        List<SubCard> subCards = new ArrayList<>();
        List<Card> cards = (List<Card>) cardRepository.findAll();

        for(int i = 0; i < cards.size(); i++) {
            for(int j = 1; j <= 10; j ++ ){
                SubCard subCard = new SubCard();
                subCard.setCard(cards.get(i));
                subCard.setSubCardName("Task " + j);
                subCard.setSubCardDescription("Task " + j + " fix bug, update code and follow customer requirement");
                subCard.setSubCardDueDate(DateGenerator.getCurrentDateWithoutHMS());
                subCards.add(subCard);
            }
        }

        return subCards;
    }

    private List<Todo> createDemoTodos() {
        List<Todo> todos = new ArrayList<>();
        List<SubCard> subCards = (List<SubCard>) subCardRepository.findAll();

        for(int i = 0; i < subCards.size(); i++) {
            for(int j = 1; j <= 10; j++) {
                Integer id = (i * 100) + j + 1;

                Todo todo = new Todo();
                todo.setSubCard(subCards.get(i));
                todo.setTodoId(id.toString());
                todo.setTodoDescription("Task Description " + j);
                todo.setDone(false);
                todos.add(todo);
            }
        }

        return todos;
    }

    private List<SubCard> updateSubCardWithTodo(List<Todo> todos) {
        List<Todo> todoList = todos;
        List<SubCard> subCards = (List<SubCard>) subCardRepository.findAll();

        for(int i = 0; i < subCards.size(); i++) {
            SubCard subCard = subCards.get(i);
            subCard.setTodos(todoList);
        }

        return subCards;
    }

    private List<SubCard> updateSubCardsWithProfiles(List<Profile> profiles) {
        List<Profile> profileList = profiles;
        
        List<SubCard> subCards = (List<SubCard>) subCardRepository.findAll();
        for(int i = 0; i < subCards.size(); i++) {
            SubCard subCard = subCards.get(i);
            subCard.setProfiles(profileList);
        }

        return subCards;
    }
}
