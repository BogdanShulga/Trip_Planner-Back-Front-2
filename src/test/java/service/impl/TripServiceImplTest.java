package service.impl;

import dao.impl.TripDaoImpl;
import dto.TripDto;
import entity.Trip;
import exception.NotFoundException;
import mapper.TripMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import service.TransportService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceImplTest {

    @Mock
    TripDaoImpl tripDao;

    @Mock
    TransportService transportService;

    @InjectMocks
    TripServiceImpl tripServiceImpl;

    List<Trip> testTripList = List.of(new Trip(1L, LocalDate.of(2020, 1, 5), LocalDate.of(2020, 1, 4), 1, 123456789L, 1L),
            new Trip(2L, LocalDate.of(2020, 1, 5), LocalDate.of(2020, 1, 4), 2, 123456789L, 2L),
            new Trip(3L, LocalDate.of(2020, 1, 5), LocalDate.of(2020, 1, 4), 3, 123456789L, 3L));

    List<TripDto> testTripDtoList = testTripList.stream().map(TripMapper::getTripDto).collect(Collectors.toList());

    Trip testTrip = testTripList.get(0);

    TripDto testTripDto = testTripDtoList.get(0);

    List<Trip> emptyTripList = new ArrayList<>();

    @Test
    public void getByIdTest() {

        when(tripDao.getById(1L)).thenReturn(Optional.of(testTrip));

        TripDto expectedTripDto = tripServiceImpl.getById(1L);

        assertEquals(testTripDto, expectedTripDto);

        verify(tripDao, times(1)).getById(1L);
    }

    @Test(expected = NotFoundException.class)
    public void getByIdExceptionTest() {

        when(tripDao.getById(anyLong())).thenReturn(Optional.empty());

        tripServiceImpl.getById(anyLong());
    }

    @Test
    public void getByFieldTest() {

        when(tripDao.getByField("country1")).thenReturn(Optional.of(testTrip));

        TripDto resultTripDto = tripServiceImpl.getByField("country1");

        assertEquals(testTripDto, resultTripDto);

        verify(tripDao, times(1)).getByField(anyString());
    }

    @Test(expected = NotFoundException.class)
    public void getByFieldExceptionTest() {

        when(tripDao.getByField(anyString())).thenReturn(Optional.empty());

        tripServiceImpl.getByField(anyString());
    }

    @Test
    public void getAllTest() {

        when(tripDao.getAll()).thenReturn(testTripList);

        List<TripDto> expectedUserDtoList = tripServiceImpl.getAll();

        assertEquals(testTripDtoList, expectedUserDtoList);

        verify(tripDao, times(1)).getAll();
    }

    @Test(expected = NotFoundException.class)
    public void getAllExceptionTest() {

        when(tripDao.getAll()).thenReturn(emptyTripList);

        tripServiceImpl.getAll();
    }

    @Test
    public void getAllLimitTest() {

        when(tripDao.getAllLimit()).thenReturn(testTripList);

        List<TripDto> expectedUserDtoList = tripServiceImpl.getAllLimit();

        assertEquals(testTripDtoList, expectedUserDtoList);

        verify(tripDao, times(1)).getAllLimit();
    }

    @Test(expected = NotFoundException.class)
    public void getAllLimitExceptionTest() {

        when(tripDao.getAllLimit()).thenReturn(emptyTripList);

        tripServiceImpl.getAllLimit();
    }

    @Test
    public void getByUserIdTest() {

        when(tripDao.getByUserId(anyLong())).thenReturn(testTripList);

        List<TripDto> expectedUserDtoList = tripServiceImpl.getByUserId(anyLong());

        assertEquals(testTripDtoList, expectedUserDtoList);

        verify(tripDao, times(1)).getByUserId(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void getByUserIdExceptionTest() {

        when(tripDao.getByUserId(anyLong())).thenReturn(emptyTripList);

        tripServiceImpl.getByUserId(anyLong());
    }

    @Test
    public void insertTest() {

        when(tripDao.insert(testTrip)).thenReturn(true);

        boolean result = tripServiceImpl.insert(testTrip);

        assertTrue(result);

        verify(tripDao, times(1)).insert(testTrip);
    }

    @Test(expected = NotFoundException.class)
    public void insertExceptionTest() {

        when(tripDao.insert(testTrip)).thenReturn(false);

        tripServiceImpl.insert(testTrip);
    }

    @Test
    public void updateByEntityTest() {

        when(tripDao.updateByEntity(testTrip)).thenReturn(true);

        boolean result = tripServiceImpl.updateByEntity(testTrip);

        assertTrue(result);

        verify(tripDao, times(1)).updateByEntity(testTrip);
    }

    @Test(expected = NotFoundException.class)
    public void updateByEntityExceptionTest() {

        when(tripDao.updateByEntity(testTrip)).thenReturn(false);

        tripServiceImpl.updateByEntity(testTrip);
    }

    @Test
    public void updateByFieldTest() {

        when(tripDao.updateByField(anyString(), anyString())).thenReturn(true);

        boolean result = tripServiceImpl.updateByField(anyString(), anyString());

        assertTrue(result);

        verify(tripDao, times(1)).updateByField(anyString(), anyString());
    }

    @Test(expected = NotFoundException.class)
    public void updateByFieldExceptionTest() {

        when(tripDao.updateByField(anyString(), anyString())).thenReturn(false);

        tripServiceImpl.updateByField(anyString(), anyString());
    }

    @Test
    public void deleteByIdTest() {

        when(tripDao.deleteById(anyLong())).thenReturn(true);

        boolean result = tripServiceImpl.deleteById(anyLong());

        assertTrue(result);

        verify(tripDao, times(1)).deleteById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void deleteByIdExceptionTest() {

        when(tripDao.deleteById(anyLong())).thenReturn(false);

        tripServiceImpl.deleteById(anyLong());
    }

    @Test
    public void deleteByFieldNameTest() {

        when(tripDao.deleteByFieldName(anyString())).thenReturn(true);

        boolean result = tripServiceImpl.deleteByFieldName(anyString());

        assertTrue(result);

        verify(tripDao, times(1)).deleteByFieldName(anyString());
    }

    @Test(expected = NotFoundException.class)
    public void deleteByFieldNameExceptionTest() {

        when(tripDao.deleteByFieldName(anyString())).thenReturn(false);

        tripServiceImpl.deleteByFieldName(anyString());
    }


    @Test
    public void deleteTest() {

        when(tripDao.delete(testTrip)).thenReturn(true);

        boolean result = tripServiceImpl.delete(testTrip);

        assertTrue(result);

        verify(tripDao, times(1)).delete(testTrip);
    }

    @Test(expected = NotFoundException.class)
    public void deleteExceptionTest() {

        when(tripDao.delete(testTrip)).thenReturn(false);

        tripServiceImpl.delete(testTrip);
    }
}
