package hello;

import org.springframework.stereotype.Service;

@Service
final class MessageServiceImpl implements MessageService {

  @Override
  public String getMessage() {
    return "hello spring";
  }

}
