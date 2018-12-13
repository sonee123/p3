package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.*;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import rightchamps.config.ApplicationProperties;
import rightchamps.domain.RnsCatgMaster;
import rightchamps.domain.Template;
import rightchamps.domain.User;
import rightchamps.modal.TemplateBean;
import rightchamps.repository.RnsQuotationRepository;
import rightchamps.repository.TemplateRepository;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rightchamps.repository.UserRepository;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

/**
 * REST controller for managing Template.
 */
@RestController
@RequestMapping("/api")
public class TemplateResource {

    private final Logger log = LoggerFactory.getLogger(TemplateResource.class);

    private static final String ENTITY_NAME = "Template";

    private final TemplateRepository templateRepository;

    private final UserRepository userRepository;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Inject
    private RnsQuotationRepository rnsQuotationRepository;

    public TemplateResource(TemplateRepository templateRepository,UserRepository userRepository) {
        this.templateRepository = templateRepository;
        this.userRepository = userRepository;
    }

    /**
     * POST  /templates : Create a new template.
     *
     * @param template the template to create
     * @return the ResponseEntity with status 201 (Created) and with body the new template, or with status 400 (Bad Request) if the template has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/templates")
    @Timed
    public ResponseEntity<Template> createTemplate(@RequestBody Template template) throws URISyntaxException {
        log.debug("REST request to save Template : {}", template);
        if (template.getId() != null) {
            throw new BadRequestAlertException("A new template cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Template template1 = templateRepository.findTemplateByTemplateName(template.getTemplateName().toUpperCase());
        Template result = null;
        if (template1 != null) {
            result = template1;
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, result.getId().toString(), "Template Name already exist"))
                .body(result);
        } else {
            template.setTemplateName(template.getTemplateName().toUpperCase());
            User user = userRepository.findByLogin(getCurrentUserLogin());
            template.setUser(user);
            template.setCreatedDate(Instant.now());
            result = templateRepository.save(template);
        }

        return ResponseEntity.created(new URI("/api/templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /templates : Updates an existing template.
     *
     * @param template the template to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated template,
     * or with status 400 (Bad Request) if the template is not valid,
     * or with status 500 (Internal Server Error) if the template couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/templates")
    @Timed
    public ResponseEntity<Template> updateTemplate(@RequestBody Template template) throws URISyntaxException {
        log.debug("REST request to update Template : {}", template);
        if (template.getId() == null) {
            return createTemplate(template);
        }
        User user = userRepository.findByLogin(getCurrentUserLogin());
        template.setUpdatedUser(user);
        template.setLastUpdatedDate(Instant.now());
        Template result = templateRepository.save(template);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, template.getId().toString()))
            .body(result);
    }

    /**
     * GET  /templates : get all the templates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of templates in body
     */
    @GetMapping("/templates")
    @Timed
    public List<TemplateBean> getAllTemplates() throws IllegalAccessException, InvocationTargetException {
        log.debug("REST request to get all Templates");
        List<Template> templates = templateRepository.findAll();
        List<TemplateBean> templateBeans = new ArrayList<TemplateBean>();
        for(Template template : templates){
            TemplateBean templateBean = new TemplateBean();
            BeanUtils.copyProperties(templateBean, template);
            Long quoteCount = rnsQuotationRepository.countByTemplate(templateBean.getId().toString());
            if(quoteCount>0){
                templateBean.setExist(true);
            } else{
                templateBean.setExist(false);
            }
            templateBeans.add(templateBean);
        }
        return templateBeans;
    }

    @GetMapping(value ="/templates-download/{type}")
    public @ResponseBody void getAllTemplatesForType(@PathVariable String type, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException{
        List<Template> templates = templateRepository.findAll();
        List<TemplateBean> templateBeans = new ArrayList<TemplateBean>();
        for(Template template : templates) {
            TemplateBean templateBean = new TemplateBean();
            BeanUtils.copyProperties(templateBean, template);
            templateBean.setCategoryDesc(template.getRnsCatgCode().getCatgCodeDesc()+" - "+template.getRnsCatgCode().getCatgCode());
            if(template.getUser()!=null){
                templateBean.setCreatedBy(template.getUser().getFirstName()+" "+template.getUser().getLastName());
                templateBean.setCreatedDateTimestamp(Timestamp.from(template.getCreatedDate()));
            }
            if(template.getUpdatedUser()!=null){
                templateBean.setUpdatedBy(template.getUpdatedUser().getFirstName()+" "+template.getUpdatedUser().getLastName());
                templateBean.setLastUpdatedDateTimestamp(Timestamp.from(template.getLastUpdatedDate()));
            }
            templateBeans.add(templateBean);
        }
        String path = applicationProperties.getTemplatePath()+"jasper/";
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(path+"/templateMasters.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            Map<String,Object> parameters = new HashMap<String, Object>();
            JRDataSource jrDataSource = new JRBeanCollectionDataSource(templateBeans);
            parameters.put("datasource", jrDataSource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, jrDataSource);
            if(type !=null && type.equalsIgnoreCase("CSV")) {
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=templateMasters.csv");
                JRCsvExporter exporter = new JRCsvExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                OutputStream ouputStream = response.getOutputStream();
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
                exporter.exportReport();
            } else if(type !=null && type.equalsIgnoreCase("PDF")){
                response.setContentType("application/x-pdf");
                response.setHeader("Content-Disposition","attachment; filename=templateMasters.pdf");
                final OutputStream outputStream = response.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint,outputStream);
            }
        } catch (JRException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * GET  /templates : get all the templates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of templates in body
     */
    @GetMapping("/templates/by-Catg-code/{id}")
    @Timed
    public List<Template> getAllTemplatesByCatgCode(@PathVariable Long id) {
        log.debug("REST request to get all Templates");
        return templateRepository.getTemplateByCatg(id);
    }

    /**
     * GET  /templates : get all the templates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of templates in body
     */
    @GetMapping("/templates/by-Catg-code-activated/{id}")
    @Timed
    public List<Template> getAllTemplatesByCatgCodeByActivated(@PathVariable Long id) {
        log.debug("REST request to get all Templates");
        return templateRepository.getTemplateByCatgActivated(id);
    }

    /**
     * GET  /templates/:id : get the "id" template.
     *
     * @param id the id of the template to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the template, or with status 404 (Not Found)
     */
    @GetMapping("/templates/{id}")
    @Timed
    public ResponseEntity<TemplateBean> getTemplate(@PathVariable Long id) throws InvocationTargetException, IllegalAccessException{
        log.debug("REST request to get Template : {}", id);
        Template template = templateRepository.findById(id).orElse(null);
        TemplateBean templateBean = new TemplateBean();
        BeanUtils.copyProperties(templateBean, template);
        Long quoteCount = rnsQuotationRepository.countByTemplate(templateBean.getId().toString());
        if(quoteCount>0){
            templateBean.setExist(true);
        } else{
            templateBean.setExist(false);
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(templateBean));
    }

    /**
     * GET  /templates/:id : get the "id" template.
     *
     * @param id the id of the template to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the template, or with status 404 (Not Found)
     */
    @GetMapping("/templates-copy/{id}")
    @Timed
    public ResponseEntity<TemplateBean> getTemplateCopy(@PathVariable Long id) throws InvocationTargetException, IllegalAccessException{
        log.debug("REST request to get Template : {}", id);
        Template template = templateRepository.findById(id).orElse(null);
        TemplateBean templateBean = new TemplateBean();
        BeanUtils.copyProperties(templateBean, template);
        templateBean.setId(null);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(templateBean));
    }

    /**
     * DELETE  /templates/:id : delete the "id" template.
     *
     * @param id the id of the template to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteTemplate(@PathVariable Long id) {
        log.debug("REST request to delete Template : {}", id);
        templateRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    private String getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }
        return userName;
    }
}
