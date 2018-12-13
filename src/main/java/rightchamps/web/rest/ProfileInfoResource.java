package rightchamps.web.rest;

import rightchamps.config.DefaultProfileUtil;

import io.github.jhipster.config.JHipsterProperties;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Resource to return information about the currently running Spring profiles.
 */
@RestController
@RequestMapping("/api")
public class ProfileInfoResource {

    private final Environment env;

    private final JHipsterProperties jHipsterProperties;

    public ProfileInfoResource(Environment env, JHipsterProperties jHipsterProperties) {
        this.env = env;
        this.jHipsterProperties = jHipsterProperties;
    }

    @GetMapping("/profile-info")
    public ProfileInfoVM getActiveProfiles() {
        String[] activeProfiles = DefaultProfileUtil.getActiveProfiles(env);
        return new ProfileInfoVM(activeProfiles);
    }


    class ProfileInfoVM {

        private String[] activeProfiles;

        ProfileInfoVM(String[] activeProfiles) {
            this.activeProfiles = activeProfiles;
        }

        public String[] getActiveProfiles() {
            return activeProfiles;
        }
    }
}
