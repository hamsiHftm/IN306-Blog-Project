package ch.hftm.blog.util;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.annotation.PostConstruct;

// Warning: NOT IN USE
@Startup
@ApplicationScoped
public class ApplicationLifecycle {

    private final KeyGeneratorHelper keyGeneratorHelper;

    public ApplicationLifecycle(KeyGeneratorHelper keyGeneratorHelper) {
        this.keyGeneratorHelper = keyGeneratorHelper;
    }

    @PostConstruct
    public void onStart() throws Exception {
        keyGeneratorHelper.generateKeys();
    }
}