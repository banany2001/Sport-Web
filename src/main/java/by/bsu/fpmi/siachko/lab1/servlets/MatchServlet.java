package by.bsu.fpmi.siachko.lab1.servlets;

import by.bsu.fpmi.siachko.lab1.exception.ServiceLayerException;
import by.bsu.fpmi.siachko.lab1.sportevent.EventType;
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

        ArrayList<Match> list = new ArrayList<>();

        try {
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
        }

        req.setAttribute("list", list);

        req.getRequestDispatcher("pages/match.jsp").forward(req, resp);
    }
}
