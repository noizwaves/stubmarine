package io.noizwaves.localemailuniverse;

import io.noizwaves.localemailuniverse.data.EmailRecord;
import io.noizwaves.localemailuniverse.data.EmailRepository;
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

    @RequestMapping("/api/emails")
    public List list() {
        return asStream(emailRepository.findAll())
                .map(EmailListItem::new)
                .collect(toList());
    }

    private static <T> Stream<T> asStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    public class EmailListItem {
        private final EmailRecord record;

        public EmailListItem(EmailRecord record) {
            this.record = record;
        }

        public int getId() {
            return record.getId();
        }

        public String getFrom() {
            return record.getFrom();
        }

        public String getTo() {
            return record.getTo();
        }

        public String getSubject() {
            return record.getSubject();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            EmailListItem that = (EmailListItem) o;

            return record != null ? record.equals(that.record) : that.record == null;
        }

        @Override
        public int hashCode() {
            return record != null ? record.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "EmailListItem{" +
                    "record=" + record +
                    '}';
        }
    }
}
