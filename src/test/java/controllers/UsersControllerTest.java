package controllers;

import constants.SuppressWarningsConstants;
import constants.UriConstants;
import domain.errors.runtime.DuplicateEntityFoundRuntimeException;
import domain.errors.runtime.EntityNotFoundRuntimeException;
import domain.persistence.entities.User;
import domain.persistence.repositories.UserRepository;
import domain.requests.gets.lists.RequestGetListUser;
import domain.requests.posts.RequestPostUser;
import domain.security.WordleAuthenticationManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings(SuppressWarningsConstants.ALL)
class UsersControllerTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsersController usersController;

    @Test
    void get() {
        final var userId = UUID.randomUUID();
        final var user = new User();
        user.setId(userId);
        user.setName("name");
        user.setEmail("some@email.com");

        Mockito.when(this.userRepository.findById(userId)).thenReturn(Optional.of(user));

        final var responseEntity = this.usersController.get(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user.getName(), responseEntity.getBody().name());
        assertEquals(user.getEmail(), responseEntity.getBody().email());

        Mockito.verify(this.userRepository, Mockito.times(1)).findById(userId);
    }

    @Test
    void getUserNotFound() {
        final var userId = UUID.randomUUID();

        Mockito.when(this.userRepository.findById(userId)).thenReturn(Optional.empty());

        final var entityNotFoundRuntimeException = assertThrows(EntityNotFoundRuntimeException.class, () -> this.usersController.get(userId));

        assertEquals(entityNotFoundRuntimeException.getCode(), "USER_NOT_FOUND");
        assertEquals(entityNotFoundRuntimeException.getMessage(), "La entidad User no pudo ser encontrada");

        Mockito.verify(this.userRepository, Mockito.times(1)).findById(userId);
    }

    @Test
    void list() {
        final var firstUser = new User();
        firstUser.setEmail("some@email.com");
        firstUser.setName("name");
        firstUser.setId(UUID.randomUUID());

        final var secondUser = new User();
        secondUser.setEmail("some2@email.com");
        secondUser.setName("name 2");
        secondUser.setId(UUID.randomUUID());

        final var users = new ArrayList<User>() {
            {
                add(firstUser);
                add(secondUser);
            }
        };

        final long totalElements = 2;
        final var totalPages = 1;


        final var requestGetListUser = new RequestGetListUser();
        requestGetListUser.setName("name");

        final var userPage = Mockito.mock(Page.class);
        Mockito.when(userPage.getTotalElements()).thenReturn(totalElements);
        Mockito.when(userPage.getTotalPages()).thenReturn(totalPages);
        Mockito.when(userPage.stream()).thenReturn(users.stream());

        Mockito.when(this.userRepository.findAll(Mockito.any(Specification.class), Mockito.any(PageRequest.class))).thenReturn(userPage);

        final var responseEntity =  this.usersController.list(requestGetListUser);
        final var responseGetPagedList = responseEntity.getBody();
        final var responsesGetListUser = responseGetPagedList.pageItems();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(totalElements, responseGetPagedList.totalCount());
        assertEquals(totalPages, responseGetPagedList.pageCount());

        for (var index = 0; index < users.size(); index++) {
            assertEquals(users.get(index).getEmail(), responsesGetListUser.get(index).email());
            assertEquals(users.get(index).getName(), responsesGetListUser.get(index).name());
            assertEquals(users.get(index).getId(), responsesGetListUser.get(index).id());
        }

        Mockito.verify(userPage, Mockito.times(1)).getTotalElements();
        Mockito.verify(userPage, Mockito.times(1)).getTotalPages();
        Mockito.verify(userPage, Mockito.times(1)).stream();
        Mockito.verify(this.userRepository, Mockito.times(1)).findAll(Mockito.any(Specification.class), Mockito.any(PageRequest.class));
    }

    @Test
    void listWithoutFilter() {
        final var requestGetListUser = new RequestGetListUser();

        final var firstUser = new User();
        firstUser.setEmail("some@email.com");
        firstUser.setName("name");
        firstUser.setId(UUID.randomUUID());

        final var secondUser = new User();
        secondUser.setEmail("some2@email.com");
        secondUser.setName("name 2");
        secondUser.setId(UUID.randomUUID());

        final var users = new ArrayList<User>() {
            {
                add(firstUser);
                add(secondUser);
            }
        };

        final long totalElements = 2;
        final var totalPages = 1;

        final var userPage = Mockito.mock(Page.class);
        Mockito.when(userPage.getTotalElements()).thenReturn(totalElements);
        Mockito.when(userPage.getTotalPages()).thenReturn(totalPages);
        Mockito.when(userPage.stream()).thenReturn(users.stream());

        Mockito.when(this.userRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(userPage);

        final var responseEntity =  this.usersController.list(requestGetListUser);
        final var responseGetPagedList = responseEntity.getBody();
        final var responsesGetListUser = responseGetPagedList.pageItems();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(totalElements, responseGetPagedList.totalCount());
        assertEquals(totalPages, responseGetPagedList.pageCount());

        for (var index = 0; index < users.size(); index++) {
            assertEquals(users.get(index).getEmail(), responsesGetListUser.get(index).email());
            assertEquals(users.get(index).getName(), responsesGetListUser.get(index).name());
            assertEquals(users.get(index).getId(), responsesGetListUser.get(index).id());
        }

        Mockito.verify(userPage, Mockito.times(1)).getTotalElements();
        Mockito.verify(userPage, Mockito.times(1)).getTotalPages();
        Mockito.verify(userPage, Mockito.times(1)).stream();
        Mockito.verify(this.userRepository, Mockito.times(1)).findAll(Mockito.any(PageRequest.class));
    }

    @Test
    void post() {
        final var userId = UUID.randomUUID();

        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setRequestURI(UriConstants.DELIMITER + UriConstants.Users.URL);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));

        final var requestPostUser = new RequestPostUser();
        requestPostUser.setName("name");
        requestPostUser.setEmail("some@email.com");
        requestPostUser.setPassword("@1String");

        final var password = "somereallydifficultpassword:andasalt";

        final Supplier<User> getArgumentMatcherUser = () -> Mockito.argThat((User u) -> u.getEmail().equals(requestPostUser.getEmail())
                                                                                        && u.getName().equals(requestPostUser.getName())
                                                                                        && u.getPassword().equals(password));

        Mockito.when(this.passwordEncoder.encode(requestPostUser.getPassword())).thenReturn(password);
        Mockito.when(this.userRepository.save(getArgumentMatcherUser.get())).then((iom) -> {
            final var u = iom.getArgument(0, User.class);
            u.setId(userId);
            return u;
        });

        final var responseEntity = this.usersController.post(requestPostUser);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getHeaders().get("Location"));
        assertEquals("http://localhost/users/%s".formatted(userId), responseEntity.getHeaders().get("Location").get(0));
        assertEquals(userId, responseEntity.getBody().getId());

        Mockito.verify(this.userRepository, Mockito.times(1)).findAll(Mockito.any(Specification.class));
        Mockito.verify(this.passwordEncoder, Mockito.times(1)).encode(requestPostUser.getPassword());
        Mockito.verify(this.userRepository, Mockito.times(1)).save(getArgumentMatcherUser.get());
    }

    @Test
    void postDuplicateUser() {
        final var requestPostUser = new RequestPostUser();

        Mockito.when(this.userRepository.findAll(Mockito.any(Specification.class))).thenReturn(new ArrayList() {
            {
                add(new User());
            }
        });

        assertThrows(DuplicateEntityFoundRuntimeException.class, () -> this.usersController.post(requestPostUser));

        Mockito.verify(this.userRepository, Mockito.times(1)).findAll(Mockito.any(Specification.class));
        Mockito.verify(this.passwordEncoder, Mockito.never()).encode(Mockito.any());
        Mockito.verify(this.userRepository, Mockito.never()).save(Mockito.any());
    }
}