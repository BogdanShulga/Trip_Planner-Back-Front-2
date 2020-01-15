package service.impl;

import dao.impl.TransportDaoImpl;
import entity.Transport;
import exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransportServiceImplTest {

    @Mock
    TransportDaoImpl transportDao;

    @InjectMocks
    TransportServiceImpl transportServiceImpl;

    List<Transport> testTransportList = List.of(new Transport(1L, "transportName1"),
            new Transport(2L, "transportName2"),
            new Transport(3L, "transportName3"));

    Transport testTransport = testTransportList.get(0);

    List<Transport> emptyTransportList = new ArrayList<>();

    @Test
    public void getByIdTest() {

        when(transportDao.getById(1L)).thenReturn(Optional.of(testTransport));

        Transport result = transportServiceImpl.getById(1L);

        assertEquals(testTransport, result);

        verify(transportDao, times(1)).getById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void getByIdExceptionTest() {

        when(transportDao.getById(anyLong())).thenReturn(Optional.empty());

        transportServiceImpl.getById(anyLong());
    }

    @Test
    public void getAllTest() {

        when(transportDao.getAll()).thenReturn(testTransportList);

        List<Transport> expectedTransportList = transportServiceImpl.getAll();

        assertEquals(testTransportList, expectedTransportList);

        verify(transportDao, times(1)).getAll();
    }

    @Test(expected = NotFoundException.class)
    public void getAllExceptionTest() {

        when(transportDao.getAll()).thenReturn(emptyTransportList);

        transportServiceImpl.getAll();
    }

    @Test
    public void insertTest() {

        when(transportDao.insert(testTransport)).thenReturn(true);

        boolean result = transportServiceImpl.insert(testTransport);

        assertTrue(result);

        verify(transportDao, times(1)).insert(testTransport);
    }

    @Test(expected = NotFoundException.class)
    public void insertExceptionTest() {

        when(transportDao.insert(testTransport)).thenReturn(false);

        transportServiceImpl.insert(testTransport);
    }

    @Test
    public void updateByEntityTest() {

        when(transportDao.updateByEntity(testTransport)).thenReturn(true);

        boolean result = transportServiceImpl.updateByEntity(testTransport);

        assertTrue(result);

        verify(transportDao, times(1)).updateByEntity(testTransport);
    }

    @Test(expected = NotFoundException.class)
    public void updateByEntityExceptionTest() {

        when(transportDao.updateByEntity(testTransport)).thenReturn(false);

        transportServiceImpl.updateByEntity(testTransport);
    }

    @Test
    public void deleteByIdTest() {

        when(transportDao.deleteById(anyLong())).thenReturn(true);

        boolean result = transportServiceImpl.deleteById(anyLong());

        assertTrue(result);

        verify(transportDao, times(1)).deleteById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void deleteByIdExceptionTest() {

        when(transportDao.deleteById(anyLong())).thenReturn(false);

        transportServiceImpl.deleteById(anyLong());
    }

    @Test
    public void deleteByNameTest() {

        when(transportDao.deleteByFieldName(anyString())).thenReturn(true);

        boolean result = transportServiceImpl.deleteByName(anyString());

        assertTrue(result);

        verify(transportDao, times(1)).deleteByFieldName(anyString());
    }

    @Test(expected = NotFoundException.class)
    public void deleteByNameExceptionTest() {

        when(transportDao.deleteByFieldName(anyString())).thenReturn(false);

        transportServiceImpl.deleteByName(anyString());
    }
}
