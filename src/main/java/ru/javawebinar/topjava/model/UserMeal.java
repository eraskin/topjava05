package ru.javawebinar.topjava.model;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
@NamedQueries({
        @NamedQuery(name = UserMeal.DELETE, query = "DELETE FROM UserMeal um WHERE um.id=:id AND um.user.id=:user_id"),
        @NamedQuery(name = UserMeal.GET_ONE_BY_USER_ID, query = "SELECT um FROM UserMeal um WHERE um.id=:id AND um.user.id=:user_id"),
        @NamedQuery(name = UserMeal.GET_ALL_BY_USER_ID, query = "SELECT um FROM UserMeal um WHERE um.user.id=:user_id"),
        @NamedQuery(name = UserMeal.GET_BETWEEN_BY_USER_ID, query = "SELECT um FROM UserMeal um WHERE um.user.id=:user_id AND um.dateTime BETWEEN :startDate AND :endDate ORDER BY um.dateTime")

})
@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "meals_unique_user_datetime_idx")})
public class UserMeal extends BaseEntity {

    public static final String DELETE = "UserMeal.delete";
    public static final String GET_ONE_BY_USER_ID = "UserMeal.getByUserId";
    public static final String GET_ALL_BY_USER_ID = "UserMeal.getAllByUserId";
    public static final String GET_BETWEEN_BY_USER_ID = "UserMeal.getBetweenByUserId";

    @Column(name = "date_time", columnDefinition = "timestamp", nullable = true)
    protected LocalDateTime dateTime;

    @Column(name = "description", nullable = true)
    protected String description;

    @Column(name = "calories", nullable = true)
    protected int calories;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public UserMeal() {
    }

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public Integer getId() {
        return id;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}