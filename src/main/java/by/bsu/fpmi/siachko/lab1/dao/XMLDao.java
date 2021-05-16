package by.bsu.fpmi.siachko.lab1.dao;

import by.bsu.fpmi.siachko.lab1.demo.EventsList;
import by.bsu.fpmi.siachko.lab1.exception.DAOLayerException;
import by.bsu.fpmi.siachko.lab1.sportevent.SportEvent;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.lang.reflect.Proxy;
import java.util.List;

public class XMLDao<T extends SportEvent> extends AbstractDao<T> {

    private JAXBContext context;
    private Marshaller marshaller;
    Class<T> tClass;

    private XMLDao(String fileName, Class<T> tClass) throws DAOLayerException {
        super(fileName);
        try {
            context = JAXBContext.newInstance(tClass, EventsList.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        }
        catch (JAXBException ex)
        {
            throw new DAOLayerException();
        }
    }

    public static <T> Dao<T> create(String fileName, Class<T> tClass, Class<?> aClass) throws DAOLayerException
    {
        return (Dao<T>) Proxy.newProxyInstance(
                aClass.getClassLoader(),
                new Class[]{Dao.class},
                new LoggingProxyHandler<>(new XMLDao(fileName, tClass))
        );
    }

    @Override
    public void write(List<T> list) throws DAOLayerException {
        try {
            EventsList<T> metaList = new EventsList<>();
            metaList.setList(list);
            marshaller.marshal(metaList, new File(fileName));
        }
        catch (JAXBException ex)
        {
            throw new DAOLayerException();
        }
    }

    @Override
    public List<T> read() throws DAOLayerException
    {
        List<T> list = null;
        try {
            list = ((EventsList<T>) context.createUnmarshaller().unmarshal(new File(fileName))).getList();
        }
        catch (JAXBException ex)
        {
            throw new DAOLayerException();
        }
        return list;
    }

}
