package todo.advance.blindsight.entity.profile;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import todo.advance.blindsight.entity.profile.dto.ProfileDTO;
import todo.advance.blindsight.entity.profile.dto.ProfileWithoutSubcardDTO;
import todo.advance.blindsight.entity.profile.dto.UpdatePasswordDTO;
import todo.advance.blindsight.util.log.CRUDLogger;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class ProfileController {
    private final ProfileService profileService;
//===================================================================================================================================
    // & OPTIONAL SECTION
    /**
     * * Request retrieve profiles along with their associated account details
     * 
     * @return
     */
    @GetMapping("/retrieve-profiles")
    public ResponseEntity<Iterable<ProfileWithoutSubcardDTO>> retrieveProfilesWithAssociationAccountDetails () {
        log.info(CRUDLogger.REQUEST_SEARCH("profiles with association account details"));
        return new ResponseEntity<>(profileService.retrieveProfilesWithAssociationAccountDetails(), HttpStatus.FOUND);
    }
//===================================================================================================================================
    // & USER SECTION
    /**
     * * request create profile
     * 
     * @param accountId
     * @param profile
     * @return
     */
    @PostMapping("/profile/create")
    public ResponseEntity<Profile> createProfile(@RequestParam String accountId, @RequestBody Profile profile) {
        log.info(CRUDLogger.REQUEST_CREATE("profile"));
        Profile createProfile = profileService.createProfile(accountId, profile);
        return new ResponseEntity<>(createProfile, HttpStatus.CREATED);
    }

    /**
     * * request find profile by id
     * 
     * @param accountId
     * @param profile
     * @return
     */
    @GetMapping("{accountId}/profile")
    public ResponseEntity<ProfileDTO> findProfileById(@PathVariable String accountId, @RequestParam String profileId) {
        log.info(CRUDLogger.REQUEST_SELECT("profile"));
        ProfileDTO findProfileById = profileService.findProfileById(accountId, profileId);
        return new ResponseEntity<>(findProfileById, HttpStatus.CREATED);
    }

    /**
     * * request update profile 
     * 
     * @param accountId
     * @param profileDTO
     * @return
     */
    @PutMapping("{profileId}/profile/update")
    public ResponseEntity<Boolean> uploadImage(@PathVariable String profileId,
                                                    @RequestParam("file") MultipartFile file, 
                                                    @RequestParam("folder") String folder) {
        log.info(CRUDLogger.REQUEST_UPDATE("profile"));
        Boolean isUpdate = profileService.uploadImage(profileId, file, folder);
        return new ResponseEntity<>(isUpdate, HttpStatus.CREATED);
    }

    /**
     * * request change password
     * 
     * @param accountId
     * @param profileId
     * @param passwordDTO
     * @return
     */
    @PutMapping("{accountId}/profile/change-password")
    public ResponseEntity<Boolean> changePassword(@PathVariable String accountId, @RequestParam String profileId, @RequestBody UpdatePasswordDTO passwordDTO) {
        log.info(CRUDLogger.REQUEST_UPDATE("profile"));
        Boolean isUpdate = profileService.changePassword(accountId, profileId, passwordDTO);
        return new ResponseEntity<>(isUpdate, HttpStatus.CREATED);
    }

    /**
     * * request delete profile
     * 
     * @param profileId
     * @return
     */
    @DeleteMapping("{accountId}/profile/delete")
    public ResponseEntity<Boolean> deleteProfile(@PathVariable String accountId, @RequestParam String profileId) {
        log.info(CRUDLogger.REQUEST_DELETE("profile"));
        Boolean isDelete = profileService.deleteProfile(accountId, profileId);
        return new ResponseEntity<>(isDelete, HttpStatus.NO_CONTENT);
    }
}
    