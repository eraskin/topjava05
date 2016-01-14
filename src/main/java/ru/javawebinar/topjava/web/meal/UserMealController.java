package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.LoggerWrapper;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Objects;

@Controller
public class UserMealController {

    private static final LoggerWrapper LOG = LoggerWrapper.get(UserMealController.class);

    @Autowired
    private UserMealService service;

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String mealsList(Model model) {
        model.addAttribute("mealList", UserMealsUtil.getWithExceeded(service.getAll(LoggedUser.id()), LoggedUser.getCaloriesPerDay()));
        return "mealList";
    }

    @RequestMapping(value = "/meals/delete", method = RequestMethod.GET)
    public String delete(HttpServletRequest request) {
        int userId = LoggedUser.id();
        int id = getId(request);
        LOG.info("delete meal {} for User {}", id, userId);
        service.delete(id, userId);
        return "redirect:/meals";
    }

    @RequestMapping(value = "/meals/edit", method = RequestMethod.GET)
    public String openUpdateForm(HttpServletRequest request, Model model) {
        int userId = LoggedUser.id();
        UserMeal meal = service.get(getId(request), userId);
        LOG.info("update {} for User {}", meal, userId);
        model.addAttribute("meal", meal);
        return "mealEdit";
    }

    @RequestMapping(value = "/meals/add", method = RequestMethod.GET)
    public String openAddForm(HttpServletRequest request, Model model) {
        int userId = LoggedUser.id();
        UserMeal meal = new UserMeal(LocalDateTime.now(), "", 1000);
        LOG.info("update {} for User {}", meal, userId);
        model.addAttribute("meal", meal);
        return "mealEdit";
    }

    @RequestMapping(value = "/meals/update", method = RequestMethod.POST)
    public String updateOrCreate(HttpServletRequest request, Model model) {
        int userId = LoggedUser.id();
        String id = request.getParameter("id");
        UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        if (userMeal.isNew()) {
            service.save(userMeal, userId);
        } else {
            service.update(userMeal, userId);
        }
        return "redirect:/meals";
    }

    @RequestMapping(value = "/meals/filter", method = RequestMethod.POST)
    public String getBetween(HttpServletRequest request, Model model) {

        LocalDate startDate = TimeUtil.parseLocalDate(request.getParameter("startDate"), TimeUtil.MIN_DATE);
        LocalDate endDate = TimeUtil.parseLocalDate(request.getParameter("endDate"), TimeUtil.MAX_DATE);
        LocalTime startTime = TimeUtil.parseLocalTime(request.getParameter("startTime"), LocalTime.MIN);
        LocalTime endTime = TimeUtil.parseLocalTime(request.getParameter("endTime"), LocalTime.MAX);

        int userId = LoggedUser.id();
        LOG.info("getBetween dates {} - {} for time {} - {} for User {}", startDate, endDate, startTime, endTime, userId);
        Collection<UserMealWithExceed> list = UserMealsUtil.getFilteredWithExceeded(
                service.getBetweenDates(startDate, endDate, userId), startTime, endTime, LoggedUser.getCaloriesPerDay()
        );

        model.addAttribute("mealList", list);
        return "mealList";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

}
