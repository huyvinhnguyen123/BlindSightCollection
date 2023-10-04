package Blind.Sight.Commnunity.domain.repository;

import Blind.Sight.Commnunity.domain.entity.many.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage, Integer> {
}
