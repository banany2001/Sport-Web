package by.bsu.fpmi.siachko.lab1.dao;

import by.bsu.fpmi.siachko.lab1.exception.DAOLayerException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface Dao <T>{
    public List<T> read() throws DAOLayerException;
    public void write(List<T> list) throws DAOLayerException;
}
