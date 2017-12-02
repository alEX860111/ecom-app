package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

  private MessageService messageService;

  public SampleController(final MessageService messageService) {
    this.messageService = messageService;
  }

  @RequestMapping("/")
  @ResponseBody
  String home() {
    return messageService.getMessage();
  }

}
