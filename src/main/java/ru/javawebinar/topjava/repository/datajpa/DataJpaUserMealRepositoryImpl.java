package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
public class DataJpaUserMealRepositoryImpl implements UserMealRepository{

    @Autowired
    private ProxyUserMealRepository proxy;

    @Autowired
    private ProxyUserRepository userProxy;

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        User user = userProxy.getOne(userId);
        userMeal.setUser(user);

        if(!userMeal.isNew() && get(userMeal.getId(), userId) == null) {
            return null;
        } else {
            return proxy.save(userMeal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        if(get(id, userId) != null) {
            proxy.delete(id);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public UserMeal get(int id, int userId) {
        return proxy.findByIdAndUserId(id, userId);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return proxy.getAllByUserIdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return proxy.findByDateTimeBetweenAndUserIdOrderByDateTimeDesc(startDate, endDate, userId);
    }
}
