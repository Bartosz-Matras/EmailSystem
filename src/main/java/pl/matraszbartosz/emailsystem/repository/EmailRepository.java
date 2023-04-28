package pl.matraszbartosz.emailsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.matraszbartosz.emailsystem.entity.Email;

import java.util.List;

public interface EmailRepository extends JpaRepository<Email, Long> {
    @Query("select e from Email e where e.user.userId = :id")
    List<Email> findEmailByUserId(@Param("id") long id);
}
