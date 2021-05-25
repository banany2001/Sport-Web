package by.bsu.fpmi.siachko.lab1.servlets;

import by.bsu.fpmi.siachko.lab1.dao.Dao;
import by.bsu.fpmi.siachko.lab1.dao.XMLDao;
import by.bsu.fpmi.siachko.lab1.exception.DAOLayerException;
import by.bsu.fpmi.siachko.lab1.exception.ServiceLayerException;
import by.bsu.fpmi.siachko.lab1.service.Service;
import by.bsu.fpmi.siachko.lab1.sportevent.EventType;
import by.bsu.fpmi.siachko.lab1.sportevent.SportEvent;
import by.bsu.fpmi.siachko.lab1.sportevent.events.game.Game;
import by.bsu.fpmi.siachko.lab1.sportevent.participant.GameParticipant;
import by.bsu.fpmi.siachko.lab1.sportevent.property.attendance.Attendance;
import by.bsu.fpmi.siachko.lab1.sportevent.property.date.Date;
import by.bsu.fpmi.siachko.lab1.sportevent.property.place.Place;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class GameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /*list.add(new Game(EventType.GAME, new Date("Monday", 1, 12, 2017),
                new Place("ABA", "Fond", "Fond", "23017", "Fond, 17"),
                new Attendance(12, 17, 58, 63, 71),
                "Football", new GameParticipant("George", 12), new GameParticipant("Jack", 13)));
        list.add(new Game(EventType.GAME, new Date("Monday", 8, 12, 2017),
                new Place("ABA", "Fond", "Fond", "23017", "Fond, 17"),
                new Attendance(12, 17, 58, 63, 71),
                "Football", new GameParticipant("George", 12), new GameParticipant("Jack", 13)));*/

        String action = req.getParameter("action");
        if (action == null || action.equals("submit")){
            action = "info";
        }

        if (action.equals("add")){
            req.getRequestDispatcher("pages/add-game.jsp").forward(req, resp);
        }
        else if (action.equals("edit")){
            Service service = new Service();
            try
            {
                Game game = service.getGameByUuid(Integer.parseInt(req.getParameter("id")));
                req.setAttribute("item", game);
                req.getRequestDispatcher("pages/edit-game.jsp").forward(req, resp);
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
                service.deleteGame(Integer.parseInt(req.getParameter("id")));
            }
            catch (ServiceLayerException ex)
            {
                System.out.println("Deletion is unsuccessful");
            }
            ArrayList<Game> list1 = new ArrayList<>();
            try
            {
                ArrayList<SportEvent> list = (ArrayList<SportEvent>) service.read("games").getList();
                for (SportEvent event : list){
                    list1.add((Game) event);
                    //System.out.println(event.getUuid());
                }
            }
            catch (ServiceLayerException ex)
            {
                System.out.println("Error while reading.");
            }
            req.setAttribute("list", list1);
            req.getRequestDispatcher("pages/game.jsp").forward(req, resp);
        }
        else if (action.equals("info")){

            ArrayList<Game> list1 = new ArrayList<>();
            Service service = new Service();
            try
            {
                ArrayList<SportEvent> list = (ArrayList<SportEvent>) service.read("games").getList();
                for (SportEvent event : list){
                    list1.add((Game) event);
                    //System.out.println(event.getUuid());
                }
            }
            catch (ServiceLayerException ex)
            {
                System.out.println("Error while reading.");
            }
            req.setAttribute("list", list1);
            req.getRequestDispatcher("pages/game.jsp").forward(req, resp);
        }

        req.getRequestDispatcher("pages/game.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if (action.equals("submit")){
            Service service = new Service();
            try
            {
                service.addNewGame(req.getParameter("games_name"), req.getParameter("participant1"),
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
                service.editGame(Integer.parseInt(req.getParameter("id")), req.getParameter("games_name"), req.getParameter("participant1"),
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

        ArrayList<Game> list1 = new ArrayList<>();
        Service service = new Service();
        try
        {
            ArrayList<SportEvent> list = (ArrayList<SportEvent>) service.read("games").getList();
            for (SportEvent event : list){
                list1.add((Game) event);
                //System.out.println(event.getUuid());
            }
        }
        catch (ServiceLayerException ex)
        {
            System.out.println("Error while reading.");
        }
        req.setAttribute("list", list1);

        req.getRequestDispatcher("pages/game.jsp").forward(req, resp);
    }
}
