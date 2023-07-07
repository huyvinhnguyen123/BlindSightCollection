package todo.advance.blindsight.entity.profile;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import todo.advance.blindsight.entity.profile.dto.ProfileWithoutSubcardDTO;

public interface ProfileRepository extends JpaRepository<Profile, String> {
    @Query(value = "SELECT a.account_id, p.profile_id, a.email, p.username, p.avatar, p.bio, p.date_join \r\n " +
    "FROM account a \r\n " +
    "JOIN profile p ON a.account_id = p.account_id", nativeQuery = true)
    List<ProfileWithoutSubcardDTO> retrieveProfilesWithAssociationAccountDetails();
}
