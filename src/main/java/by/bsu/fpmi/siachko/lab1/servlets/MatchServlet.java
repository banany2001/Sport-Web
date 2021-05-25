package by.bsu.fpmi.siachko.lab1.servlets;

import by.bsu.fpmi.siachko.lab1.exception.ServiceLayerException;
import by.bsu.fpmi.siachko.lab1.service.Service;
import by.bsu.fpmi.siachko.lab1.sportevent.EventType;
import by.bsu.fpmi.siachko.lab1.sportevent.SportEvent;
import by.bsu.fpmi.siachko.lab1.sportevent.events.game.Game;
import by.bsu.fpmi.siachko.lab1.sportevent.events.match.Match;
import by.bsu.fpmi.siachko.lab1.sportevent.participant.GameParticipant;
import by.bsu.fpmi.siachko.lab1.sportevent.participant.MatchParticipant;
import by.bsu.fpmi.siachko.lab1.sportevent.property.attendance.Attendance;
import by.bsu.fpmi.siachko.lab1.sportevent.property.date.Date;
import by.bsu.fpmi.siachko.lab1.sportevent.property.place.Place;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class MatchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /*try {
            list.add(new Match(EventType.MATCH, new Date("Monday", 1, 12, 2017),
                    new Place("ABA", "Fond", "Fond", "23017", "Fond, 17"),
                    new Attendance(12, 17, 58, 63, 71),
                    "Tennis", new MatchParticipant("George", true), new MatchParticipant("Jack", false)));
            list.add(new Match(EventType.MATCH, new Date("Monday", 8, 12, 2017),
                    new Place("ABA", "Fond", "Fond", "23017", "Fond, 17"),
                    new Attendance(12, 17, 58, 63, 71),
                    "Tennis", new MatchParticipant("George", false), new MatchParticipant("Jack", true)));
        }
        catch (ServiceLayerException ex)
        {
            throw new IOException();
        }*/

        String action = req.getParameter("action");
        if (action == null || action.equals("submit")){
            action = "info";
        }

        if (action.equals("add")){
            req.getRequestDispatcher("pages/add-match.jsp").forward(req, resp);
        }
        else if (action.equals("edit")){
            Service service = new Service();
            try
            {
                Match match = service.getMatchByUuid(Integer.parseInt(req.getParameter("id")));
                req.setAttribute("item", match);
                if (match.getMatchParticipant1().isResult()){
                    req.setAttribute("result1", "V");
                }
                else {
                    req.setAttribute("result1", "X");
                }
                if (match.getMatchParticipant2().isResult()){
                    req.setAttribute("result2", "V");
                }
                else {
                    req.setAttribute("result2", "X");
                }
                req.getRequestDispatcher("pages/edit-match.jsp").forward(req, resp);
            }
            catch (ServiceLayerException ex)
            {
                System.out.println("Element not found.");
            }
        }
        else if (action.equals("delete")){
            Service service = new Service();
            try
            {
                service.deleteMatch(Integer.parseInt(req.getParameter("id")));
            }
            catch (ServiceLayerException ex)
            {
                System.out.println("Deletion is unsuccessful");
            }
            ArrayList<Match> list1 = new ArrayList<>();
            try
            {
                ArrayList<SportEvent> list = (ArrayList<SportEvent>) service.read("matches").getList();
                for (SportEvent event : list){
                    list1.add((Match) event);
                    //System.out.println(event.getUuid());
                }
            }
            catch (ServiceLayerException ex)
            {
                System.out.println("Error while reading.");
            }
            req.setAttribute("list", list1);
            req.getRequestDispatcher("pages/match.jsp").forward(req, resp);
        }
        else if (action.equals("info")){

            ArrayList<Match> list1 = new ArrayList<>();
            Service service = new Service();
            try
            {
                ArrayList<SportEvent> list = (ArrayList<SportEvent>) service.read("matches").getList();
                for (SportEvent event : list){
                    list1.add((Match) event);
                    //System.out.println(event.getUuid());
                }
            }
            catch (ServiceLayerException ex)
            {
                System.out.println("Error while reading.");
            }
            req.setAttribute("list", list1);
            req.getRequestDispatcher("pages/match.jsp").forward(req, resp);
        }

        req.getRequestDispatcher("pages/match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if (action.equals("submit")){
            Service service = new Service();
            try
            {
                service.addNewMatch(req.getParameter("match_name"), req.getParameter("participant1"),
                        req.getParameter("participant1_result"), req.getParameter("participant2"),
                        req.getParameter("participant2_result"), req.getParameter("day_of_week"),
                        req.getParameter("date"), req.getParameter("place"),
                        req.getParameter("attendance_until_18"), req.getParameter("attendance_until_30"),
                        req.getParameter("attendance_until_45"), req.getParameter("attendance_until_60"),
                        req.getParameter("attendance_after_60"));
            }
            catch (ServiceLayerException ex)
            {
                System.out.println("Error while adding.");
            }
        }
        else if (action.equals("edit")){

            Service service = new Service();
            try
            {
                service.editMatch(Integer.parseInt(req.getParameter("id")), req.getParameter("match_name"), req.getParameter("participant1"),
                        req.getParameter("participant1_result"), req.getParameter("participant2"),
                        req.getParameter("participant2_result"), req.getParameter("day_of_week"),
                        req.getParameter("date"), req.getParameter("place"),
                        req.getParameter("attendance_until_18"), req.getParameter("attendance_until_30"),
                        req.getParameter("attendance_until_45"), req.getParameter("attendance_until_60"),
                        req.getParameter("attendance_after_60"));
            }
            catch (ServiceLayerException ex)
            {
                System.out.println("Error while editing.");
            }

        }

        ArrayList<Match> list1 = new ArrayList<>();
        Service service = new Service();
        try
        {
            ArrayList<SportEvent> list = (ArrayList<SportEvent>) service.read("matches").getList();
            for (SportEvent event : list){
                list1.add((Match) event);
                //System.out.println(event.getUuid());
            }
        }
        catch (ServiceLayerException ex)
        {
            System.out.println("Error while reading.");
        }
        req.setAttribute("list", list1);

        req.getRequestDispatcher("pages/match.jsp").forward(req, resp);
    }
}
