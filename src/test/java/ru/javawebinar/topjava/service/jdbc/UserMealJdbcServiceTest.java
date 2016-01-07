package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserMealServiceTest;

@ActiveProfiles({Profiles.POSTGRES, Profiles.JDBC})
public class UserMealJdbcServiceTest extends UserMealServiceTest {
}