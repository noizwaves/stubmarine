package io.noizwaves.localemailuniverse.smtp;

import io.noizwaves.localemailuniverse.data.EmailRecord;
import io.noizwaves.localemailuniverse.data.EmailRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EmailRepositoryMessageListenerTest {

    @Mock
    private EmailRepository emailRepository;

    @InjectMocks
    private EmailRepositoryMessageListener listener;

    @Test
    public void testDeliver() throws Exception {
        String emailMessage = "Received: from 235.1.168.192.in-addr.arpa (localhost [0:0:0:0:0:0:0:1])\r\n" +
                "        by hostname\r\n" +
                "        with SMTP (SubEthaSMTP 3.1.7) id J6AZJWEC\r\n" +
                "        for sender@example.com;\r\n" +
                "        Sun, 13 Aug 2017 11:09:48 -0600 (MDT)\r\n" +
                "Content-Type: text/plain; charset=\"us-ascii\"\r\n" +
                "MIME-Version: 1.0\r\n" +
                "Content-Transfer-Encoding: 7bit\r\n" +
                "Subject: Test Subject\r\n" +
                "From: Sender <sender@example.com>\r\n" +
                "To: Recipient <recipient@example.com>\r\n" +
                "\r\n" +
                "Hello World!\r\n";

        listener.deliver("foo", "bar", asciiInputStream(emailMessage));

        verify(emailRepository).save(new EmailRecord(
                null,
                "Sender <sender@example.com>",
                "Recipient <recipient@example.com>",
                "Test Subject",
                "Hello World!\r\n"
        ));
    }

    private static InputStream asciiInputStream(String content) {
        return new ByteArrayInputStream(content.getBytes(StandardCharsets.US_ASCII));
    }
}