package domain.files;

import domain.errors.constants.ErrorMessageConstants;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Service
public class FileLinesStreamer {
    public <T> Stream<T> map(String filename, Function<String, T> mapping) {
        return this.doMap(filename, mapping);
    }

    public <T> Stream<T> map(String filename, Function<String, T> mapping, Predicate<String> filter) {
        return this.doMap(filename, mapping, ss -> ss.filter(filter));
    }

    private BufferedReader createBufferedReader(String filePath) {
        final var fileStream = getClass().getResourceAsStream(filePath);

        if (fileStream == null) {
            throw new RuntimeException(ErrorMessageConstants.getInvalidFilePath(filePath));
        }

        return new BufferedReader(new InputStreamReader(fileStream));
    }

    private <T> Stream<T> doMap(String filename, Function<String, T> mapping, Function<Stream<String>, Stream<String>> filter) {
        final var bufferedReader = this.createBufferedReader(filename);
        return filter.apply(bufferedReader.lines())
                     .map(mapping);
    }

    private <T> Stream<T> doMap(String filename, Function<String, T> mapping) {
        return this.doMap(filename, mapping, (ss) -> ss);
    }
}
