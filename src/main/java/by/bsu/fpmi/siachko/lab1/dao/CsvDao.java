package by.bsu.fpmi.siachko.lab1.dao;

import by.bsu.fpmi.siachko.lab1.exception.DAOLayerException;
import by.bsu.fpmi.siachko.lab1.sportevent.SportEvent;
import by.bsu.fpmi.siachko.lab1.sportevent.participant.RaceParticipant;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvDao<T extends SportEvent> extends AbstractDao<T> {

    private Scanner scanner;
    private Scanner lineScanner;
    private PrintWriter printWriter;

    private CsvDao(String fileName) {
        super(fileName);
    }

    public static <T> Dao<T> create(String fileName, Class<?> aClass)
    {
        return (Dao<T>) Proxy.newProxyInstance(
                aClass.getClassLoader(),
                new Class[]{Dao.class},
                new LoggingProxyHandler<>(new CsvDao(fileName))
        );
    }

    private void writeNativeObject(Object object, Class<?> tClass)
    {
        printWriter.print(tClass.getCanonicalName());
        printWriter.print(";");
        printWriter.print(object);
    }

    private void writeObject(Object object, Class<?> tClass) throws DAOLayerException
    {
        try
        {
            if (tClass.getPackage().equals(Integer.class.getPackage())){
                writeNativeObject(object, tClass);
                return;
            }
            printWriter.print(tClass.getCanonicalName());
            if (!tClass.getSuperclass().equals(Object.class)){
                printWriter.print(";");
                writeObject(object, tClass.getSuperclass());
            }
            Field[] fields = tClass.getDeclaredFields();
            for (Field field : fields){

                Annotation[] annotations = field.getDeclaredAnnotations();
                boolean ignoreFound = false;
                for (Annotation annotation : annotations){
                    if (annotation.toString().equals("@by.bsu.fpmi.siachko.lab1.reading.CsvIgnore()")){
                        ignoreFound = true;
                    }
                }

                if (ignoreFound){
                    continue;
                }

                String fieldName = field.getName();
                //System.out.println(fieldName);
            /*if (fieldName.equals("total")){
                continue;
            }*/
                printWriter.print(";");
                StringBuilder getterName = new StringBuilder();
                if (field.getType().equals(Boolean.class) || field.getType().getName().equals("boolean")){
                    getterName.append("is");
                }
                else {
                    getterName.append("get");
                }
                getterName.append(Character.toUpperCase(fieldName.charAt(0))).append(fieldName.substring(1));
                Method getter = tClass.getMethod(getterName.toString());
                Object getterResult = getter.invoke(object);

                if (!fieldName.equals("raceParticipants")){
                    writeObject(getterResult, getterResult.getClass());
                    continue;
                }

                printWriter.print("ArrayList;");
                ArrayList<RaceParticipant> raceParticipants = (ArrayList<RaceParticipant>) getterResult;
                printWriter.print(raceParticipants.size());
                for (RaceParticipant participant : raceParticipants){
                    printWriter.print(";");
                    writeObject(participant, participant.getClass());
                }
            }
        }
        catch (Exception ex)
        {
            throw new DAOLayerException();
        }
    }

    private Object readNativeObject(Class<?> tClass) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        return tClass.getConstructor(String.class).newInstance(lineScanner.next());
    }

    private Object readObject(Object object) throws DAOLayerException
    {

        try{
            Class<?> tClass = Class.forName(lineScanner.next());
            if (tClass.getPackage().equals(Integer.class.getPackage())){
                return readNativeObject(tClass);
            }
            if (object == null){
                object = tClass.getConstructor().newInstance();
            }
            if (!tClass.getSuperclass().equals(Object.class)){
                readObject(object);
            }
            Field[] fields = tClass.getDeclaredFields();
            for (Field field : fields){

                Annotation[] annotations = field.getDeclaredAnnotations();
                boolean ignoreFound = false;
                for (Annotation annotation : annotations){
                    //System.out.println(annotation.toString());
                    if (annotation.toString().equals("@by.bsu.fpmi.siachko.lab1.reading.CsvIgnore()")){
                        ignoreFound = true;
                    }
                }

                if (ignoreFound){
                    continue;
                }

                String fieldName = field.getName();
            /*if (fieldName.equals("total")){
                continue;
            }*/
                //System.out.println(fieldName);
                StringBuilder setterName = new StringBuilder().append("set")
                        .append(Character.toUpperCase(fieldName.charAt(0)))
                        .append(fieldName.substring(1));
                Method setter = tClass.getMethod(setterName.toString(), field.getType());

                if (!fieldName.equals("raceParticipants")){
                    setter.invoke(object, readObject(null));
                    continue;
                }

                String className = lineScanner.next();
                int n;
                n = Integer.parseInt(lineScanner.next());
                ArrayList<RaceParticipant> raceParticipants = new ArrayList<>();
                while (n != 0){
                    raceParticipants.add((RaceParticipant) readObject(null));
                    n--;
                }

                setter.invoke(object, raceParticipants);

            }
        }
        catch (Exception ex)
        {
            throw new DAOLayerException();
        }

        return object;
    }

    @Override
    public List<T> read() throws DAOLayerException
    {
        List<T> list = new ArrayList<>();
        try
        {
            scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()){
                lineScanner = new Scanner(scanner.nextLine()).useDelimiter(";");
                list.add((T) readObject(null));
            }
        }
        catch (IOException | DAOLayerException ex)
        {
            throw new DAOLayerException();
        }

        return list;
    }

    @Override
    public void write(List<T> list) throws DAOLayerException
    {
        try {
            printWriter = new PrintWriter(new File(fileName));
            for (T object : list)
            {
                writeObject(object, object.getClass());
                printWriter.println();
            }
            printWriter.close();
        }
        catch (IOException | DAOLayerException ex)
        {
            throw new DAOLayerException();
        }
    }

}
