package by.bsu.fpmi.siachko.lab1.servlets;

import by.bsu.fpmi.siachko.lab1.demo.EventsList;
import by.bsu.fpmi.siachko.lab1.exception.ServiceLayerException;
import by.bsu.fpmi.siachko.lab1.service.Service;
import by.bsu.fpmi.siachko.lab1.sportevent.SportEvent;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AttendanceStatisticsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ArrayList<SportEvent> list = new ArrayList<>();
        Service service = new Service();
        try
        {
            ArrayList<SportEvent> list1 = (ArrayList<SportEvent>) service.read("games").getList();
            ArrayList<SportEvent> list2 = (ArrayList<SportEvent>) service.read("matches").getList();
            list.addAll(list1);
            list.addAll(list2);
        }
        catch (ServiceLayerException ex)
        {
            System.out.println("Some fails");
        }

        req.setAttribute("list", list);

        EventsList<SportEvent> list1 = new EventsList<>();
        list1.setList(list);
        req.setAttribute("monday", service.averageAttendanceByDay(list1, "Monday"));
        req.setAttribute("tuesday", service.averageAttendanceByDay(list1, "Tuesday"));
        req.setAttribute("wednesday", service.averageAttendanceByDay(list1, "Wednesday"));
        req.setAttribute("thursday", service.averageAttendanceByDay(list1, "Thursday"));
        req.setAttribute("friday", service.averageAttendanceByDay(list1, "Friday"));
        req.setAttribute("saturday", service.averageAttendanceByDay(list1, "Saturday"));
        req.setAttribute("sunday", service.averageAttendanceByDay(list1, "Sunday"));

        req.getRequestDispatcher("pages/attendance-statistics.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action.equals("sort")){
            ArrayList<SportEvent> list = new ArrayList<>();
            Service service = new Service();
            try
            {
                ArrayList<SportEvent> list1 = (ArrayList<SportEvent>) service.read("games").getList();
                ArrayList<SportEvent> list2 = (ArrayList<SportEvent>) service.read("matches").getList();
                list.addAll(list1);
                list.addAll(list2);
            }
            catch (ServiceLayerException ex)
            {
                System.out.println("Some fails");
            }
            EventsList<SportEvent> list1 = new EventsList<>();
            list1.setList(list);
            if (req.getParameter("radios").equals("18-")){
                service.sortByAge(list1, 17);
            }
            if (req.getParameter("radios").equals("18-30")){
                service.sortByAge(list1, 27);
            }
            if (req.getParameter("radios").equals("30-45")){
                service.sortByAge(list1, 37);
            }
            if (req.getParameter("radios").equals("45-60")){
                service.sortByAge(list1, 47);
            }
            if (req.getParameter("radios").equals("60+")){
                service.sortByAge(list1, 67);
            }

            req.setAttribute("list", list1.getList());

            req.setAttribute("monday", service.averageAttendanceByDay(list1, "Monday"));
            req.setAttribute("tuesday", service.averageAttendanceByDay(list1, "Tuesday"));
            req.setAttribute("wednesday", service.averageAttendanceByDay(list1, "Wednesday"));
            req.setAttribute("thursday", service.averageAttendanceByDay(list1, "Thursday"));
            req.setAttribute("friday", service.averageAttendanceByDay(list1, "Friday"));
            req.setAttribute("saturday", service.averageAttendanceByDay(list1, "Saturday"));
            req.setAttribute("sunday", service.averageAttendanceByDay(list1, "Sunday"));

            req.getRequestDispatcher("pages/attendance-statistics.jsp").forward(req, resp);
        }

    }
}
