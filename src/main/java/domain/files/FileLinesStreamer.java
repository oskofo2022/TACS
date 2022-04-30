package domain.files;

import domain.errors.constants.ErrorMessageConstants;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Stream;

@Service
public class FileLinesStreamer {
    public Stream<String> list(String filename) {
        return this.createBufferedReader(filename)
                   .lines();
    }

    private BufferedReader createBufferedReader(String filePath) {
        final var fileStream = getClass().getResourceAsStream(filePath);

        if (fileStream == null) {
            throw new RuntimeException(ErrorMessageConstants.getInvalidFilePath(filePath));
        }

        return new BufferedReader(new InputStreamReader(fileStream));
    }
}
