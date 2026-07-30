package com.example.gridjsdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import com.aspose.gridjs.GridJsOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Configures Aspose.Cells GridJs options.
 */
@Configuration
public class GridJsConfig {

    @Bean
    public GridJsOptions gridJsOptions(
            @Value("${gridjs.cache-directory}") String cacheDirectory) {
        GridJsOptions options = new GridJsOptions();

        // Directory where temporary files (e.g., JSON cache) are stored
        try {
            Files.createDirectories(Paths.get(cacheDirectory));
        } catch (IOException e) {
            throw new IllegalStateException("Unable to create the GridJS cache directory", e);
        }
        options.setFileCacheDirectory(cacheDirectory);


        // Base route used by the GridJs controller
        options.setBaseRouteName("/GridJs");

        return options;
    }
}
