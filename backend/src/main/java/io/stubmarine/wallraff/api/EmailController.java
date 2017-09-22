package io.stubmarine.wallraff.api;

import io.stubmarine.wallraff.data.EmailRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@RestController
public class EmailController {

    private final EmailRepository emailRepository;

    public EmailController(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @RequestMapping("/api/inbox/{inboxName}/emails")
    public List list(
            @PathVariable("inboxName") String inboxName
    ) {
        return asStream(emailRepository.findByInbox(inboxName)).collect(toList());
    }

    @RequestMapping("/api/emails/{id}")
    public Object get(@PathVariable("id") int id) {
        return emailRepository.findOne(id);
    }

    private static <T> Stream<T> asStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}