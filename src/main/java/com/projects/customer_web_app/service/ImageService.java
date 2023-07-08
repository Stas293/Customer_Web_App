package com.projects.customer_web_app.service;

import com.projects.customer_web_app.utility.ImageConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.stream.Stream;

@SuppressWarnings("ALL")
@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {
    private final ImageConfiguration imageConfiguration;

    @SneakyThrows
    public void upload(String imagePath, String originalFilename, InputStream image) {
        Path path = Path.of(imageConfiguration.basePath(), imagePath);
        if (Files.exists(path)) {
            cleanUp(path);
        }
        path = Path.of(imageConfiguration.basePath(), imagePath, originalFilename);
        try (image) {
            Files.createDirectories(path.getParent());
            Files.write(path, image.readAllBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    @SneakyThrows
    private void cleanUp(Path path) {
        try (Stream<Path> walk = Files.walk(path)) {
            walk.sorted((p1, p2) -> -p1.compareTo(p2))
                    .forEach(this::delete);
        }
    }

    @SneakyThrows
    private void delete(Path path) {
        Files.delete(path);
    }

    public Optional<byte[]> getImage(String imagePath) {
        Path path = Path.of(imageConfiguration.basePath(), imagePath);
        return Optional.of(path)
                .filter(Files::exists)
                .map(this::readImage);
    }

    @SneakyThrows
    private byte[] readImage(Path path) {
        return Files.readAllBytes(path);
    }

    public void deleteImage(String imagePath) {
        Path path = Path.of(imageConfiguration.basePath(), imagePath);
        if (Files.exists(path)) {
            cleanUp(path);
        }
    }
}
