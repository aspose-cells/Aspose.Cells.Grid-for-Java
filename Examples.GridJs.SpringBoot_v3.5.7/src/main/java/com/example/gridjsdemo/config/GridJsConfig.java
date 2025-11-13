package com.example.gridjsdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aspose.gridjs.GridJsOptions;

/**
 * Configures Aspose.Cells GridJs options.
 */
@Configuration
public class GridJsConfig {

    @Bean
    public GridJsOptions gridJsOptions() {
        GridJsOptions options = new GridJsOptions();

        // Directory where temporary files (e.g., JSON cache) are stored
        options.setFileCacheDirectory("D:/storage/Aspose.Cells.GridJs/");


        // Base route used by the GridJs controller
        options.setBaseRouteName("/GridJs");

        return options;
    }
}