package luminia.backend.repositories;

import luminia.backend.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("update User u set u.rating = u.rating + ?2 where u.id = ?1")
    @Modifying
    void addRatingToUser(Long userId, double toAdd);
    @Query("update User u set u.rating = u.rating - ?2 where u.id = ?1")
    @Modifying
    void removeRatingFromUser(Long userId, double toDel);

    @Query("select u from User u where (u.fullName ilike ?1 and u.nameProtected = false) or (u.username ilike ?1)")
    Page<User> findByNameLikeWithPrivacy(String search, Pageable pageable);
    @Query("select u from User u where (u.fullName ilike ?1) or (u.username ilike ?1)")
    Page<User> findByNameLike(String search, Pageable pageable);

    Optional<User> findByUsername(String username);
}
