package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM UserMeal u WHERE u.id=:id and u.user.id=:user_id")
    int delete(@Param("id") int id, @Param("user_id") int userId);

    @Override
    @Transactional
    UserMeal save(UserMeal user);

    @Override
    UserMeal findOne(Integer id);

    List<UserMeal> getAllByUserIdOrderByDateTimeDesc(int userId);

    @Override
    List<UserMeal> findAll(Sort sort);

}
