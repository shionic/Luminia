package luminia.backend.repositories;

import luminia.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("update User u set u.rating = u.rating + ?2 where u.id = ?1")
    @Modifying
    void addRatingToUser(Long userId, double toAdd);
    @Query("update User u set u.rating = u.rating - ?2 where u.id = ?1")
    @Modifying
    void removeRatingFromUser(Long userId, double toDel);
}
