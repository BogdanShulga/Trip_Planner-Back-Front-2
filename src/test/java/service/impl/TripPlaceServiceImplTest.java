package service.impl;

import dao.TripPlaceDao;
import entity.TripPlace;
import exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TripPlaceServiceImplTest {

    @Mock
    TripPlaceDao tripPlaceDao;

    @InjectMocks
    TripPlaceServiceImpl tripPlaceServiceImpl;

    List<TripPlace> testTripPlaceList = List.of(new TripPlace(1L, 1L),
            new TripPlace(2L, 9L),
            new TripPlace(3L, 5L),
            new TripPlace(1L, 9L));

    TripPlace testTripPlace = testTripPlaceList.get(0);

    List<TripPlace> emptyTripPlaceList = new ArrayList<>();

    @Test
    public void getByTwoIdTest() {

        when(tripPlaceDao.getByTwoId(1L, 1L)).thenReturn(Optional.of(testTripPlace));

        TripPlace resultTripPlace = tripPlaceServiceImpl.getByTwoId(1L, 1L);

        assertEquals(testTripPlace, resultTripPlace);

        verify(tripPlaceDao, times(1)).getByTwoId(1L, 1L);
    }

    @Test(expected = NotFoundException.class)
    public void getByTwoIdExceptionTest() {

        when(tripPlaceDao.getByTwoId(1L, 1L)).thenReturn(Optional.empty());

        tripPlaceServiceImpl.getByTwoId(1L, 1L);
    }

    @Test
    public void getByTripIdTest() {

        when(tripPlaceDao.getByTripId(anyLong())).thenReturn(testTripPlaceList);

        List<TripPlace> resultTripPlaceList = tripPlaceServiceImpl.getByTripId(anyLong());

        assertEquals(testTripPlaceList, resultTripPlaceList);

        verify(tripPlaceDao, times(1)).getByTripId(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void getByTripIdExceptionTest() {

        when(tripPlaceDao.getByTripId(anyLong())).thenReturn(emptyTripPlaceList);

        tripPlaceServiceImpl.getByTripId(anyLong());
    }

    @Test
    public void getAllTest() {

        when(tripPlaceDao.getAll()).thenReturn(testTripPlaceList);

        List<TripPlace> resultTripPlaceList = tripPlaceServiceImpl.getAll();

        assertEquals(testTripPlaceList, resultTripPlaceList);

        verify(tripPlaceDao, times(1)).getAll();
    }

    @Test(expected = NotFoundException.class)
    public void getAllExceptionTest() {

        when(tripPlaceDao.getAll()).thenReturn(emptyTripPlaceList);

        tripPlaceServiceImpl.getAll();
    }

    @Test
    public void insertTest() {

        when(tripPlaceDao.insert(testTripPlace)).thenReturn(true);

        boolean result = tripPlaceServiceImpl.insert(testTripPlace);

        assertTrue(result);

        verify(tripPlaceDao, times(1)).insert(testTripPlace);
    }

    @Test(expected = NotFoundException.class)
    public void insertExceptionTest() {

        when(tripPlaceDao.insert(testTripPlace)).thenReturn(false);

        tripPlaceServiceImpl.insert(testTripPlace);
    }

    @Test
    public void updateByFieldTest() {

        when(tripPlaceDao.updateByField(anyLong(), anyLong())).thenReturn(true);

        boolean result = tripPlaceServiceImpl.updateByField(anyLong(), anyLong());

        assertTrue(result);

        verify(tripPlaceDao, times(1)).updateByField(anyLong(), anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void updateByFieldExceptionTest() {

        when(tripPlaceDao.updateByField(anyLong(), anyLong())).thenReturn(false);

        tripPlaceServiceImpl.updateByField(anyLong(), anyLong());
    }

    @Test
    public void deleteByTwoIdTest() {

        when(tripPlaceDao.deleteByTwoId(anyLong(), anyLong())).thenReturn(true);

        boolean result = tripPlaceServiceImpl.deleteByTwoId(anyLong(), anyLong());

        assertTrue(result);

        verify(tripPlaceDao, times(1)).deleteByTwoId(anyLong(), anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void deleteByTwoIdExceptionTest() {

        when(tripPlaceDao.deleteByTwoId(anyLong(), anyLong())).thenReturn(false);

        tripPlaceServiceImpl.deleteByTwoId(anyLong(), anyLong());
    }

    @Mock
    TripPlaceServiceImpl tripPlaceServiceImplMock;

    @Test
    public void isEmptyPlaceListTest() {

        when(tripPlaceServiceImplMock.getByTripId(anyLong())).thenReturn(testTripPlaceList);

        List<TripPlace> resultPlacesReserved = tripPlaceServiceImplMock.getByTripId(anyLong());

        assertFalse(resultPlacesReserved.isEmpty());

        when(tripPlaceServiceImplMock.getByTripId(anyLong())).thenReturn(emptyTripPlaceList);

        List<TripPlace> resultPlacesNotReserved = tripPlaceServiceImplMock.getByTripId(anyLong());

        assertTrue(resultPlacesNotReserved.isEmpty());

        verify(tripPlaceServiceImplMock, times(2)).getByTripId(anyLong());
    }
}
