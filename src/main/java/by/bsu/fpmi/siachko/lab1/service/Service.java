package by.bsu.fpmi.siachko.lab1.service;

import by.bsu.fpmi.siachko.lab1.dao.*;
import by.bsu.fpmi.siachko.lab1.demo.EventsList;
import by.bsu.fpmi.siachko.lab1.exception.DAOLayerException;
import by.bsu.fpmi.siachko.lab1.exception.LayerException;
import by.bsu.fpmi.siachko.lab1.exception.ServiceLayerException;
import by.bsu.fpmi.siachko.lab1.sportevent.EventType;
import by.bsu.fpmi.siachko.lab1.sportevent.SportEvent;
import by.bsu.fpmi.siachko.lab1.sportevent.events.game.Game;
import by.bsu.fpmi.siachko.lab1.sportevent.events.match.Match;
import by.bsu.fpmi.siachko.lab1.sportevent.participant.GameParticipant;
import by.bsu.fpmi.siachko.lab1.sportevent.participant.MatchParticipant;
import by.bsu.fpmi.siachko.lab1.sportevent.property.attendance.Attendance;
import by.bsu.fpmi.siachko.lab1.sportevent.property.date.Date;
import by.bsu.fpmi.siachko.lab1.sportevent.property.place.Place;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Service {

    private FileType fileType = FileType.CSV;
    private Dao<SportEvent> dao;

    public Service() throws FileNotFoundException
    {
        new LogFile();
    }

    public List<SportEvent> sortByAge(EventsList<SportEvent> list, int age)
    {
        List<SportEvent> temp_list = list.getList();
        if (age < 18){
            Collections.sort(temp_list, new Comparator<SportEvent>() {
                @Override
                public int compare(SportEvent o1, SportEvent o2) {
                    return Integer.compare(o1.getAttendance().getPeopleUntil18(), o2.getAttendance().getPeopleUntil18());
                }
            });
        }
        else if (age < 30){
            Collections.sort(temp_list, new Comparator<SportEvent>() {
                @Override
                public int compare(SportEvent o1, SportEvent o2) {
                    return Integer.compare(o1.getAttendance().getPeopleUntil30(), o2.getAttendance().getPeopleUntil30());
                }
            });
        }
        else if (age < 45){
            Collections.sort(temp_list, new Comparator<SportEvent>() {
                @Override
                public int compare(SportEvent o1, SportEvent o2) {
                    return Integer.compare(o1.getAttendance().getPeopleUntil45(), o2.getAttendance().getPeopleUntil45());
                }
            });
        }
        else if (age < 60){
            Collections.sort(temp_list, new Comparator<SportEvent>() {
                @Override
                public int compare(SportEvent o1, SportEvent o2) {
                    return Integer.compare(o1.getAttendance().getPeopleUntil60(), o2.getAttendance().getPeopleUntil60());
                }
            });
        }
        else {
            Collections.sort(temp_list, new Comparator<SportEvent>() {
                @Override
                public int compare(SportEvent o1, SportEvent o2) {
                    return Integer.compare(o1.getAttendance().getPeopleAfter60(), o2.getAttendance().getPeopleAfter60());
                }
            });
        }
        return temp_list;
    }

    public int averageAttendanceByDay(EventsList<SportEvent> list, String day)
    {
        int sum = 0;
        int cnt = 0;
        List<SportEvent> temp_list = list.getList();
        day = day.toLowerCase();
        for (SportEvent event : temp_list){
            if (event.getDate().getDayOfWeek().toLowerCase().equals(day)) {
                sum += event.getAttendance().getTotal();
                cnt++;
            }
        }
        return sum / Math.max(1, cnt);
    }

    private void createDao(String name) throws DAOLayerException
    {
        if (fileType == FileType.XML){
            dao = XMLDao.create(name + fileType.toString(), SportEvent.class, Service.class);
        }
        else if (fileType == FileType.JSON){
            dao = JsonDao.create(name + fileType.toString(), Service.class);
        }
        else if (fileType == FileType.CSV){
            dao = CsvDao.create(name + fileType.toString(), Service.class);
        }
        File tmpFile = new File(name + fileType.toString());
        if (!tmpFile.exists()){
            ArrayList<SportEvent> list = new ArrayList<>();
            dao.write(list);
        }
    }

    public EventsList<SportEvent> read(String name) throws ServiceLayerException
    {
        EventsList<SportEvent> list = new EventsList<>();

        try
        {
            createDao(name);
            list.setList(dao.read());
            int maximalUuid = 0;
            if (list.getList() != null){
                for (SportEvent sportEvent : list.getList()){
                    maximalUuid = Math.max(maximalUuid, sportEvent.getUuid());
                }
            }
            else {
                list = new EventsList<>();
            }
            SportEvent.setNextUuid(maximalUuid + 1);
        }
        catch (DAOLayerException ex)
        {
            throw new ServiceLayerException();
        }

        return list;
    }

    private void write(String name, EventsList<SportEvent> list) throws DAOLayerException
    {
        createDao(name);
        dao.write(list.getList());
    }

    public void addNewGame(String gameName, String name1,  String points1, String name2, String points2,
                           String dayOfWeek, String date, String place, String peopleUntil18, String peopleUntil30,
                           String peopleUntil45, String peopleUntil60, String peopleAfter60) throws ServiceLayerException
    {

        EventsList<SportEvent> list = new EventsList<>();
        try {
            list = read("games");
        }
        catch (ServiceLayerException ex)
        {
            System.out.println("No file.");
        }

        Game game = new Game(EventType.GAME, new Date(dayOfWeek, date), new Place(place),
                new Attendance(peopleUntil18, peopleUntil30, peopleUntil45, peopleUntil60, peopleAfter60),
                gameName, new GameParticipant(name1, points1), new GameParticipant(name2, points2));

        list.getList().add(game);

        try
        {
            write("games", list);
        }
        catch (DAOLayerException ex)
        {
            throw new ServiceLayerException();
        }

    }

    public void addNewMatch(String matchName, String name1,  String result1, String name2, String result2,
                            String dayOfWeek, String date, String place, String peopleUntil18, String peopleUntil30,
                            String peopleUntil45, String peopleUntil60, String peopleAfter60) throws ServiceLayerException
    {

        EventsList<SportEvent> list = new EventsList<>();

        try {
            list = read("matches");
        }
        catch (ServiceLayerException ex)
        {
            System.out.println("No file.");
        }

        Match match = new Match(EventType.MATCH, new Date(dayOfWeek, date), new Place(place),
                new Attendance(peopleUntil18, peopleUntil30, peopleUntil45, peopleUntil60, peopleAfter60),
                matchName, new MatchParticipant(name1, result1), new MatchParticipant(name2, result2));

        list.getList().add(match);

        try
        {
            write("matches", list);
        }
        catch (DAOLayerException ex)
        {
            throw new ServiceLayerException();
        }

    }

    public Game getGameByUuid(int uuid) throws ServiceLayerException
    {
        try
        {
            EventsList<SportEvent> list = read("games");

            for (SportEvent event : list.getList()){
                if (event.getUuid() == uuid){
                    return (Game) event;
                }
            }

        }
        catch (ServiceLayerException ex)
        {
            throw new ServiceLayerException();
        }

        return null;
    }

    public Match getMatchByUuid(int uuid) throws ServiceLayerException
    {
        try
        {
            EventsList<SportEvent> list = read("matches");

            for (SportEvent event : list.getList()){
                if (event.getUuid() == uuid){
                    return (Match) event;
                }
            }

        }
        catch (ServiceLayerException ex)
        {
            throw new ServiceLayerException();
        }

        return null;
    }

    public void editGame(int uuid, String gameName, String name1,  String points1, String name2, String points2,
                           String dayOfWeek, String date, String place, String peopleUntil18, String peopleUntil30,
                           String peopleUntil45, String peopleUntil60, String peopleAfter60) throws ServiceLayerException
    {

        try {
            EventsList<SportEvent> list = read("games");

            int index = -1;
            for (int i = 0; i < list.getList().size(); i++){
                if (list.getList().get(i).getUuid() == uuid){
                    index = i;
                    break;
                }
            }

            if (index != -1){
                list.getList().get(index).setDate(new Date(dayOfWeek, date));
                list.getList().get(index).setPlace(new Place(place));
                list.getList().get(index).setAttendance(new Attendance(peopleUntil18, peopleUntil30, peopleUntil45, peopleUntil60, peopleAfter60));
                ((Game)list.getList().get(index)).setGameName(gameName);
                ((Game)list.getList().get(index)).setGameParticipant1(new GameParticipant(name1, points1));
                ((Game)list.getList().get(index)).setGameParticipant2(new GameParticipant(name2, points2));
            }

            write("games", list);
        }
        catch (DAOLayerException ex)
        {
            throw new ServiceLayerException();
        }

    }

    public void editMatch(int uuid, String matchName, String name1,  String result1, String name2, String result2,
                            String dayOfWeek, String date, String place, String peopleUntil18, String peopleUntil30,
                            String peopleUntil45, String peopleUntil60, String peopleAfter60) throws ServiceLayerException
    {

        try {
            EventsList<SportEvent> list = read("matches");

            int index = -1;
            for (int i = 0; i < list.getList().size(); i++){
                if (list.getList().get(i).getUuid() == uuid){
                    index = i;
                    break;
                }
            }

            if (index != -1){
                list.getList().get(index).setDate(new Date(dayOfWeek, date));
                list.getList().get(index).setPlace(new Place(place));
                list.getList().get(index).setAttendance(new Attendance(peopleUntil18, peopleUntil30, peopleUntil45, peopleUntil60, peopleAfter60));
                ((Match)list.getList().get(index)).setMatchName(matchName);
                ((Match)list.getList().get(index)).setMatchParticipant1(new MatchParticipant(name1, result1));
                ((Match)list.getList().get(index)).setMatchParticipant2(new MatchParticipant(name2, result2));
            }

            write("matches", list);
        }
        catch (DAOLayerException ex)
        {
            throw new ServiceLayerException();
        }

    }

    public void deleteGame(int uuid) throws ServiceLayerException
    {
        try
        {
            EventsList<SportEvent> list = read("games");

            int index = -1;
            for (int i = 0; i < list.getList().size(); i++){
                if (list.getList().get(i).getUuid() == uuid){
                    index = i;
                    break;
                }
            }

            if (index != -1){
                list.getList().remove(index);
            }

            dao.write(list.getList());

        }
        catch (LayerException ex)
        {
            throw new ServiceLayerException();
        }

    }

    public void deleteMatch(int uuid) throws ServiceLayerException
    {
        try
        {
            EventsList<SportEvent> list = read("matches");

            int index = -1;
            for (int i = 0; i < list.getList().size(); i++){
                if (list.getList().get(i).getUuid() == uuid){
                    index = i;
                    break;
                }
            }

            if (index != -1){
                list.getList().remove(index);
            }

            dao.write(list.getList());

        }
        catch (LayerException ex)
        {
            throw new ServiceLayerException();
        }

    }

    public void deleteEvent(int index) throws ServiceLayerException
    {
        try {
            EventsList<SportEvent> list = read("events");
            list.getList().remove(index);
            write("events", list);
        }
        catch (DAOLayerException ex)
        {
            throw new ServiceLayerException();
        }
    }

}
