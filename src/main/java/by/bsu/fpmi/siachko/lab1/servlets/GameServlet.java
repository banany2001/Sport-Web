package by.bsu.fpmi.siachko.lab1.servlets;

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

        ArrayList<Game> list = new ArrayList<>();

        list.add(new Game(EventType.GAME, new Date("Monday", 1, 12, 2017),
                new Place("ABA", "Fond", "Fond", "23017", "Fond, 17"),
                new Attendance(12, 17, 58, 63, 71),
                "Football", new GameParticipant("George", 12), new GameParticipant("Jack", 13)));
        list.add(new Game(EventType.GAME, new Date("Monday", 8, 12, 2017),
                new Place("ABA", "Fond", "Fond", "23017", "Fond, 17"),
                new Attendance(12, 17, 58, 63, 71),
                "Football", new GameParticipant("George", 12), new GameParticipant("Jack", 13)));

        req.setAttribute("list", list);

        req.getRequestDispatcher("pages/game.jsp").forward(req, resp);
    }
}
