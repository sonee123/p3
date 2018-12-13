package rightchamps.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to P 3.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private String company;
    private String uploadPath;
    private String serverUrl;
    private String templatePath;

    public String getCompany() { return company; }

    public void setCompany(String company) { this.company = company; }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getServerUrl() { return serverUrl; }

    public void setServerUrl(String serverUrl) { this.serverUrl = serverUrl; }

    public String getTemplatePath() { return templatePath; }

    public void setTemplatePath(String templatePath) { this.templatePath = templatePath; }
}
