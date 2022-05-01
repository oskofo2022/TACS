package domain.files;

import domain.errors.runtime.FileNotFoundRuntimeException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Stream;

@Service
public class FileLinesStreamer {
    public Stream<String> list(String filePath) {
        return this.createBufferedReader(filePath)
                   .lines();
    }

    private BufferedReader createBufferedReader(String filePath) {
        final var fileStream = getClass().getResourceAsStream(filePath);

        if (fileStream == null) {
            throw new FileNotFoundRuntimeException(filePath);
        }

        return new BufferedReader(new InputStreamReader(fileStream));
    }
}
