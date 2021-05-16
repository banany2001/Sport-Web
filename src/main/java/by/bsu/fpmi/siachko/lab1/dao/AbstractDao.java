package by.bsu.fpmi.siachko.lab1.dao;

import by.bsu.fpmi.siachko.lab1.exception.DAOLayerException;
import by.bsu.fpmi.siachko.lab1.sportevent.SportEvent;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class AbstractDao<T extends SportEvent> implements Dao<T>{

    protected String fileName;

    public AbstractDao(String fileName) {
        this.fileName = fileName;
    }

    public abstract List<T> read() throws DAOLayerException;
    public abstract void write(List<T> list) throws DAOLayerException;


}
