package net.brainified.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.brainified.MockitoExtension;
import net.brainified.db.UserDetailsDocument;

@ExtendWith(MockitoExtension.class)
class UserConverterImplTest {

  @Mock
  private PasswordEncoder passwordEncoder;

  private UserConverterImpl converter;

  @BeforeEach
  public void setUp() {
    converter = new UserConverterImpl(passwordEncoder);
  }

  @Test
  void createDocument() {
    final AddUserRequest addUserRequest = new AddUserRequest();
    addUserRequest.setUsername("username");
    addUserRequest.setPassword("password");

    when(passwordEncoder.encode("password")).thenReturn("passwordHash");

    final UserDetailsDocument document = converter.createDocument(addUserRequest);

    assertEquals("username", document.getUsername());
    assertEquals("passwordHash", document.getPasswordHash());
    assertEquals("USER", document.getRole());
    assertNull(document.getCreatedAt());
    assertNull(document.getId());
  }

  @Test
  public void createUser() {
    final UserDetailsDocument document = new UserDetailsDocument();
    document.setId("id");
    final LocalDateTime createdAt = LocalDateTime.now();
    document.setCreatedAt(createdAt);
    document.setUsername("username");
    document.setRole("USER");

    final User user = converter.createUser(document);

    assertEquals("id", user.getId());
    assertEquals(createdAt, user.getCreatedAt());
    assertEquals("username", user.getUsername());
    assertEquals("USER", user.getRole());
  }

}
