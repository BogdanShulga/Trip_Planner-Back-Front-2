package service.impl;

import dao.impl.UserDaoImpl;
import dto.PlaceDto;
import dto.UserDto;
import entity.Role;
import entity.User;
import exception.NotFoundException;
import mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    UserDaoImpl userDao;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    List<UserDto> testUserDtoList = new ArrayList<>(Arrays
            .asList(new UserDto(1L,"first1", "last1", "username1", "email1",Role.USER),
                    new UserDto(2L,"first2", "last2", "username2", "email2", Role.USER),
                    new UserDto(3L,"first3", "last3", "username3", "email3", Role.USER),
                    new UserDto(4L,"first4", "last4", "username4", "email4", Role.USER)
            ));

    List<User> testUserList = testUserDtoList.stream().map(UserMapper::getUserEntity).collect(Collectors.toList());

    List<User> emptyUserList = new ArrayList<>();

    User testUser = new User(1L, "first1", "last1", "username1", "email1", "password1", Role.USER);

    UserDto testUserDto = UserMapper.getUserDto(testUser);

    @Test
    public void getUserByIdTest() {

        when(userDao.getById(1L)).thenReturn(Optional.of(testUser));

        UserDto expectedUserDto = userServiceImpl.getById(1L);

        assertEquals(testUserDto, expectedUserDto);

        verify(userDao, times(1)).getById(1L);
    }

    @Test(expected = NotFoundException.class)
    public void getByIdExceptionTest() {

        when(userDao.getById(anyLong())).thenReturn(Optional.empty());

        userServiceImpl.getById(anyLong());
    }

    @Test
    public void getByFieldTest() {

        when(userDao.getByField("country1")).thenReturn(Optional.of(testUser));

        UserDto resultUserDto = userServiceImpl.getByField("country1");

        assertEquals(testUserDto, resultUserDto);

        verify(userDao, times(1)).getByField(anyString());
    }

    @Test(expected = NotFoundException.class)
    public void getByFieldExceptionTest() {

        when(userDao.getByField(anyString())).thenReturn(Optional.empty());

        userServiceImpl.getByField(anyString());
    }

    @Test
    public void getAllTest() {

        when(userDao.getAll()).thenReturn(testUserList);

        List<UserDto> expectedUserDtoList = userServiceImpl.getAll();

        assertEquals(testUserDtoList, expectedUserDtoList);

        verify(userDao, times(1)).getAll();
    }

    @Test(expected = NotFoundException.class)
    public void getAllExceptionTest() {

        when(userDao.getAll()).thenReturn(emptyUserList);

        userServiceImpl.getAll();
    }

    @Test
    public void getAllLimitTest() {

        when(userDao.getAllLimit()).thenReturn(testUserList);

        List<UserDto> expectedUserDtoList = userServiceImpl.getAllLimit();

        assertEquals(testUserDtoList, expectedUserDtoList);

        verify(userDao, times(1)).getAllLimit();
    }

    @Test(expected = NotFoundException.class)
    public void getAllLimitExceptionTest() {

        when(userDao.getAllLimit()).thenReturn(emptyUserList);

        userServiceImpl.getAllLimit();
    }

    @Test
    public void insertTest() {

        when(userDao.insert(testUser)).thenReturn(true);

        boolean result = userServiceImpl.insert(testUser);

        assertTrue(result);

        verify(userDao, times(1)).insert(testUser);
    }

    @Test(expected = NotFoundException.class)
    public void insertExceptionTest() {

        when(userDao.insert(testUser)).thenReturn(false);

        userServiceImpl.insert(testUser);
    }

    @Test
    public void updateByEntityTest() {

        when(userDao.updateByEntity(testUser)).thenReturn(true);

        boolean result = userServiceImpl.updateByEntity(testUser);

        assertTrue(result);

        verify(userDao, times(1)).updateByEntity(testUser);
    }

    @Test(expected = NotFoundException.class)
    public void updateByEntityExceptionTest() {

        when(userDao.updateByEntity(testUser)).thenReturn(false);

        userServiceImpl.updateByEntity(testUser);
    }

    @Test
    public void updateByFieldTest() {

        when(userDao.updateByField(anyString(), anyString())).thenReturn(true);

        boolean result = userServiceImpl.updateByField(anyString(), anyString());

        assertTrue(result);

        verify(userDao, times(1)).updateByField(anyString(), anyString());
    }

    @Test(expected = NotFoundException.class)
    public void updateByFieldExceptionTest() {

        when(userDao.updateByField(anyString(), anyString())).thenReturn(false);

        userServiceImpl.updateByField(anyString(), anyString());
    }

    @Test
    public void deleteByIdTest() {

        when(userDao.deleteById(anyLong())).thenReturn(true);

        boolean result = userServiceImpl.deleteById(anyLong());

        assertTrue(result);

        verify(userDao, times(1)).deleteById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void deleteByIdExceptionTest() {

        when(userDao.deleteById(anyLong())).thenReturn(false);

        userServiceImpl.deleteById(anyLong());
    }

    @Test
    public void deleteTest() {

        when(userDao.delete(testUser)).thenReturn(true);

        boolean result = userServiceImpl.delete(testUser);

        assertTrue(result);

        verify(userDao, times(1)).delete(testUser);
    }

    @Test(expected = NotFoundException.class)
    public void deleteExceptionTest() {

        when(userDao.delete(testUser)).thenReturn(false);

        userServiceImpl.delete(testUser);
    }

    @Test
    public void isRoleAdminTest() {

        User notAdminUser = testUser;

        boolean negativeAnswer = userServiceImpl.isRoleAdmin(notAdminUser);

        assertFalse(negativeAnswer);

        User adminUser = testUser;
        adminUser.setUserRole(Role.ADMIN);
        adminUser.setPassword("admin");

        boolean positiveAnswer = userServiceImpl.isRoleAdmin(adminUser);

        assertTrue(positiveAnswer);
    }

    @Test
    public void isUsernameUniqueTest() {

        when(userDao.getAll()).thenReturn(emptyUserList);

        boolean result = userServiceImpl.isUsernameUnique("username1");

        assertTrue(result);

        verify(userDao, times(1)).getAll();
    }

    @Test(expected = NotFoundException.class)
    public void isUsernameUniqueExceptionTest() {

        when(userDao.getAll()).thenReturn(testUserList);

        userServiceImpl.isUsernameUnique("username1");
    }
}
