package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class UserMealServiceImpl implements UserMealService {

    @Autowired
    private UserMealRepository repository;

    @Autowired
    private UserRepository userRepo;

    @Cacheable("meals")
    @Override
    public UserMeal get(int id, int userId) {
        return ExceptionUtil.check(repository.get(id, userId), id);
    }

    @CacheEvict(value = "meals", allEntries = true)
    @Override
    public void delete(int id, int userId) {
        ExceptionUtil.check(repository.delete(id, userId), id);
    }

    @Override
    public Collection<UserMeal> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return repository.getBetween(startDateTime, endDateTime, userId);
    }

    @Cacheable("meals")
    @Override
    public Collection<UserMeal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @CacheEvict(value = "meals", allEntries = true)
    @Override
    public UserMeal update(UserMeal meal, int userId) {
        return ExceptionUtil.check(repository.save(meal, userId), meal.getId());
    }

    @CacheEvict(value = "meals", allEntries = true)
    @Override
    public UserMeal save(UserMeal meal, int userId) {
        return repository.save(meal, userId);
    }

    @CacheEvict(value = "meals", allEntries = true)
    @Override
    public void evictCache() {
    }

    @Transactional
    public UserMeal getWithUser(Integer id, Integer userId) {
        UserMeal meal = get(id, userId);
        meal.setUser(userRepo.get(userId));
        return meal;
    }
}
