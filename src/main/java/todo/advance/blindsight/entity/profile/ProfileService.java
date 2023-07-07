package todo.advance.blindsight.entity.profile;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import todo.advance.blindsight.config.PasswordEncrypt;
import todo.advance.blindsight.entity.account.Account;
import todo.advance.blindsight.entity.account.AccountRepository;
import todo.advance.blindsight.entity.custom.UploadFile;
import todo.advance.blindsight.entity.log.LogService;
import todo.advance.blindsight.entity.profile.dto.ProfileDTO;
import todo.advance.blindsight.entity.profile.dto.ProfileWithoutSubcardDTO;
import todo.advance.blindsight.entity.profile.dto.UpdatePasswordDTO;
import todo.advance.blindsight.util.custom.exception.NotFoundException;
import todo.advance.blindsight.util.log.CRUDLogger;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final AccountRepository accountRepository;
    private final LogService logService;
    private final UploadFile uploadFile;

    private static final String BIO = "Write your bio";
    private static final String AVATAR = "https://cdn-icons-png.flaticon.com/512/6596/6596121.png";
//===================================================================================================================================
    // & OPTIONAL SECTION
    /**
     * * Retrieve profiles along with their associated account details
     * 
     * @return List profiles
     */
    public Iterable<ProfileWithoutSubcardDTO> retrieveProfilesWithAssociationAccountDetails() {
        // List<ProfileWithoutSubcardDTO> profileWithoutSubcards = new ArrayList<>();
        // Iterable<Profile> profiles = profileRepository.retrieveProfilesWithAssociationAccountDetails();
        // for(Profile p: profiles) {
        //     ProfileWithoutSubcardDTO profileWithoutSubcard = new ProfileWithoutSubcardDTO();
        //     profileWithoutSubcard.setAccountId(p.getAccount().getAccountId());
        //     profileWithoutSubcard.setProfileId(p.getProfileId());
        //     profileWithoutSubcard.setEmail(p.getAccount().getEmail());
        //     profileWithoutSubcard.setUsername(p.getUsername());
        //     profileWithoutSubcard.setAvatar(p.getAvatar());
        //     profileWithoutSubcard.setBio(p.getBio());
        //     profileWithoutSubcard.setDateJoin(p.getDateJoin());

        //     profileWithoutSubcards.add(profileWithoutSubcard);
        // }
        List<ProfileWithoutSubcardDTO> profileWithoutSubcards = profileRepository.retrieveProfilesWithAssociationAccountDetails();
        log.info(CRUDLogger.SEARCH_ALL_SUCCESS("users"));
        return profileWithoutSubcards;
    }

//===================================================================================================================================
    // & MAIN SECTION
    /**
     * * create profile
     * 
     * @param accountId
     * @param newProfile
     * @return
     */
    @Transactional
    public Profile createProfile(String accountId, Profile newProfile) {
        // find account by id
        Account existingAccount = accountRepository.findById(accountId).orElseThrow(() -> {
            log.error(CRUDLogger.SEARCH_ONE_FAIL(accountId));
            log.trace("id must be unique & same with one in database");
            logService.saveErrorLog("account with id does not exist: " + accountId);

            return new NotFoundException("Not found this account: " + accountId);
        });
        log.info(CRUDLogger.SELECT_ONE_SUCCESS("account"));

        // set new properties for profile
        newProfile.setAccount(existingAccount);
        newProfile.setBio(BIO);
        newProfile.setAvatar(AVATAR);
        profileRepository.save(newProfile); 
        log.info(CRUDLogger.CREATE_SUCCESS("profile"));
        
        // return profile
        return newProfile;
    }

    /**
     * * find profile by id
     * 
     * @param accountId
     * @param profileId
     * @return
     */
    public ProfileDTO findProfileById(String accountId, String profileId) {
        // find account by id
        Account existingAccount = accountRepository.findById(accountId).orElseThrow(() -> {
            log.error(CRUDLogger.SEARCH_ONE_FAIL(accountId));
            log.trace("id must be unique & same with one in database");
            logService.saveErrorLog("account with id does not exist: " + accountId);

            return new NotFoundException("Not found this account: " + accountId);
        });
        log.info(CRUDLogger.SELECT_ONE_SUCCESS("account"));

        // find profile by id
        Profile existingProfile = profileRepository.findById(profileId).orElseThrow(() -> {
            log.error(CRUDLogger.SEARCH_ONE_FAIL(profileId));
            log.trace("id must be unique & same with one in database");
            logService.saveErrorLog("profile with id does not exist: " + profileId);

            return new NotFoundException("Not found this profile: " + profileId);
        });
        log.info(CRUDLogger.SELECT_ONE_SUCCESS("profile"));

        // set properties for profileDTO
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setAccountId(existingAccount.getAccountId());
        profileDTO.setProfileId(existingProfile.getProfileId());
        profileDTO.setEmail(existingAccount.getEmail());
        profileDTO.setUsername(existingProfile.getUsername());
        profileDTO.setAvatar(existingProfile.getAvatar());
        profileDTO.setBio(existingProfile.getBio());
        profileDTO.setDateJoin(existingProfile.getDateJoin());
        profileDTO.setSubcards(existingProfile.getSubcards());

        // display profileDTO
        return profileDTO;
    }

    /**
     * ! The code is running but is not commit excute to database 
     * * upload image
     * 
     * @param accountId
     * @param profileId
     * @param multipartFile
     * @param folder
     * @return
     */
    @Transactional
    public Boolean uploadImage(String profileId, MultipartFile multipartFile, String folder) {
        // find profile by id
        Profile existingProfile = profileRepository.findById(profileId).orElseThrow(() -> {
            log.error(CRUDLogger.SEARCH_ONE_FAIL(profileId));
            log.trace("id must be unique & same with one in database");
            logService.saveErrorLog("profile with id does not exist: " + profileId);

            return new NotFoundException("Not found this profile: " + profileId);
        });
        log.info(CRUDLogger.SELECT_ONE_SUCCESS("profile"));

        // set properties for profile
        existingProfile.setLastModifiedDate(Instant.now().atZone(ZoneId.systemDefault()));

        String cloudURL = uploadFile.uploadFile(multipartFile, folder);
        existingProfile.setAvatar(cloudURL);

        System.out.println(cloudURL);

        // save in db
        profileRepository.save(existingProfile);
        log.info(CRUDLogger.UPDATE_SUCCESS("profile"));

        System.out.println(cloudURL);

        return true;
    }

    /**
     * * update profile
     * 
     * @param accountId
     * @param profileId
     * @param inputProfile
     * @return
     */
    public Boolean updateProfile(String accountId, Profile inputProfile) {
        // find account by id
        Account existingAccount = accountRepository.findById(accountId).orElseThrow(() -> {
            log.error(CRUDLogger.SEARCH_ONE_FAIL(accountId));
            log.trace("id must be unique & same with one in database");
            logService.saveErrorLog("account with id does not exist: " + accountId);

            return new NotFoundException("Not found this account: " + accountId);
        });
        log.info(CRUDLogger.SELECT_ONE_SUCCESS("account"));

        // find profile by id
        Profile existingProfile = profileRepository.findById(inputProfile.getProfileId()).orElseThrow(() -> {
            log.error(CRUDLogger.SEARCH_ONE_FAIL(inputProfile.getProfileId()));
            log.trace("id must be unique & same with one in database");
            logService.saveErrorLog("profile with id does not exist: " + inputProfile.getProfileId());

            return new NotFoundException("Not found this profile: " + inputProfile.getProfileId());
        });
        log.info(CRUDLogger.SELECT_ONE_SUCCESS("profile"));

        // set properties for profile
        Profile updateProfile = new Profile();
        updateProfile.setAccount(existingAccount);
        updateProfile.setProfileId(existingProfile.getProfileId());
        updateProfile.setUsername(inputProfile.getUsername());
        updateProfile.setBio(inputProfile.getBio());

        // save in db
        existingProfile.setLastModifiedDate(Instant.now().atZone(ZoneId.systemDefault()));
        profileRepository.save(existingProfile);
        log.info(CRUDLogger.UPDATE_SUCCESS("profile"));
        return true;
    }

    /**
     * * update/change password
     * 
     * @param accountId
     * @param profileId
     * @param inputUpdatePassword
     * @return
     */
    public Boolean changePassword(String accountId, String profileId, UpdatePasswordDTO inputUpdatePassword) {
        // use method find profile by id
        ProfileDTO existingProfile = findProfileById(accountId, profileId);
        // set profileId for updatePasswordDTO
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO();
        updatePasswordDTO.setProfileId(existingProfile.getProfileId());
       
        // compare between password in db and password user was inputed
        if(existingProfile.getPassword().equals(inputUpdatePassword.getPassword())) {
            // set properties for updatePasswordDTO
            updatePasswordDTO.setPassword(existingProfile.getPassword());
            updatePasswordDTO.setUpdatePassword(inputUpdatePassword.getUpdatePassword());
            updatePasswordDTO.setRetypePassword(inputUpdatePassword.getRetypePassword());
            
            // compare between update password and retype password
            if(updatePasswordDTO.getUpdatePassword().equals(updatePasswordDTO.getRetypePassword())){
                // set new password for account
                Account updateAccount = new Account();
                updateAccount.setPassword(PasswordEncrypt.bCryptpasswordEncoder(updatePasswordDTO.getUpdatePassword()));
                
                // save in db
                accountRepository.save(updateAccount);
                log.info(CRUDLogger.UPDATE_SUCCESS("password"));
                return true;
            } else {
                log.error(CRUDLogger.UPDATE_FAIL("password"));
                return false;
            }
        } else {
            log.error(CRUDLogger.UPDATE_FAIL("password"));
            return false;
        }
    }

    /**
     * * delete profile
     * 
     * @param profileId
     */
    public boolean deleteProfile(String accountId, String profileId) {
        ProfileDTO existingProfile = findProfileById(accountId, profileId);
        profileRepository.deleteById(existingProfile.getProfileId());
        log.info(CRUDLogger.DELETE_SUCCESS("profile"));
        return true;
    }
}
