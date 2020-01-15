package service.impl;

import dao.impl.RateDaoImpl;
import entity.Rate;
import exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RateServiceImplTest {

    @Mock
    RateDaoImpl rateDao;

    @InjectMocks
    RateServiceImpl rateServiceImpl;

    List<Rate> testRateList = List.of(new Rate(1L, 3, 1L, 33L),
            new Rate(2L, 5, 2L, 55L),
            new Rate(3L, 7, 3L, 55L));

    Rate testRate = testRateList.get(0);

    List<Rate> emptyRateList = new ArrayList<>();

    @Test
    public void getByFieldNameTest() {

        when(rateDao.getByFieldName(anyLong())).thenReturn(testRateList);

        List<Rate> resultRateList = rateServiceImpl.getByFieldName(anyLong());

        assertFalse(resultRateList.isEmpty());

        verify(rateDao, times(1)).getByFieldName(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void getByFieldNameExceptionTest() {

        when(rateDao.getByFieldName(anyLong())).thenReturn(emptyRateList);

        rateServiceImpl.getByFieldName(anyLong());
    }

    @Test
    public void getAllTest() {

        when(rateDao.getAll()).thenReturn(testRateList);

        List<Rate> resultRateList = rateServiceImpl.getAll();

        assertFalse(resultRateList.isEmpty());

        verify(rateDao, times(1)).getAll();
    }

    @Test(expected = NotFoundException.class)
    public void getAllExceptionTest() {

        when(rateDao.getAll()).thenReturn(emptyRateList);

        rateServiceImpl.getAll();
    }

    @Test
    public void insertTest() {

        when(rateDao.insert(testRate)).thenReturn(true);

        boolean result = rateServiceImpl.insert(testRate);

        assertTrue(result);

        verify(rateDao, times(1)).insert(testRate);
    }

    @Test(expected = NotFoundException.class)
    public void insertExceptionTest() {

        when(rateDao.insert(testRate)).thenReturn(false);

        rateServiceImpl.insert(testRate);
    }

    @Test
    public void updateByEntityTest() {

        when(rateDao.updateByEntity(testRate)).thenReturn(true);

        boolean result = rateServiceImpl.updateByEntity(testRate);

        assertTrue(result);

        verify(rateDao, times(1)).updateByEntity(testRate);
    }

    @Test(expected = NotFoundException.class)
    public void updateByEntityExceptionTest() {

        when(rateDao.updateByEntity(testRate)).thenReturn(false);

        rateServiceImpl.updateByEntity(testRate);
    }

    @Test
    public void deleteByIdTest() {

        when(rateDao.deleteById(anyLong())).thenReturn(true);

        boolean result = rateServiceImpl.deleteById(anyLong());

        assertTrue(result);

        verify(rateDao, times(1)).deleteById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void deleteByIdExceptionTest() {

        when(rateDao.deleteById(anyLong())).thenReturn(false);

        rateServiceImpl.deleteById(anyLong());
    }

    @Test
    public void getRateValueTest() {

        long rateCount = (5 + 7) / 2;
        long placeId = 55;
        List<Rate> rateListWithPlaceId55 = List.of(testRateList.get(1),
                testRateList.get(2));

        when(rateDao.getByFieldName(placeId)).thenReturn(rateListWithPlaceId55);

        long result = rateServiceImpl.getRateValue(placeId);

        assertEquals(rateCount, result);

        verify(rateDao, times(1)).getByFieldName(placeId);
    }
}
