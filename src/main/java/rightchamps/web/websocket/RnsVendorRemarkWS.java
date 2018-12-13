package rightchamps.web.websocket;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import rightchamps.domain.RnsQuotation;
import rightchamps.domain.RnsVendorRemark;
import rightchamps.repository.RnsQuotationRepository;
import rightchamps.service.RnsVendorRemarkService;
import rightchamps.web.rest.RnsVendorRemarkResource;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import rightchamps.web.rest.util.PaginationUtil;
import rightchamps.service.dto.RnsVendorRemarkCriteria;
import rightchamps.service.RnsVendorRemarkQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import javax.inject.Inject;


/**
 * REST controller for managing VndrPrice.
 */
@Controller
public class RnsVendorRemarkWS {

    private final Logger log = LoggerFactory.getLogger(RnsVendorRemarkResource.class);

    private static final String ENTITY_NAME = "rnsVendorRemark";

    private final RnsVendorRemarkService rnsVendorRemarkService;

    private final RnsVendorRemarkQueryService rnsVendorRemarkQueryService;

    @Inject
    private RnsQuotationRepository rnsQuotationRepository;

    public RnsVendorRemarkWS(RnsVendorRemarkService rnsVendorRemarkService, RnsVendorRemarkQueryService rnsVendorRemarkQueryService) {
        this.rnsVendorRemarkService = rnsVendorRemarkService;
        this.rnsVendorRemarkQueryService = rnsVendorRemarkQueryService;
    }

    /**
     * GET  /rns-vendor-remarks : get all the rnsVendorRemarks.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of rnsVendorRemarks in body
     */
//    @MessageMapping("/rns-vndr-remarks/{quotationId}/{vendorEmail}")
//    @SendTo("/topic/rns-vndr-remarks/{quotationId}/{vendorEmail}")
//    @Timed
//    public ResponseEntity<List<RnsVendorRemark>> getAllRnsVendorRemarks(@DestinationVariable Long quotationId, @DestinationVariable String vendorEmail) {
//        log.debug("REST request to get RnsVendorRemarks by criteria: {}", criteria);
//        RnsVendorRemarkCriteria criteria = new RnsVendorRemarkCriteria();
//        LongFilter qtIdFilter = new LongFilter();
//        qtIdFilter.setEquals(quotationId);
//        StringFilter vndrFilter = new StringFilter();
//        vndrFilter.setEquals(vendorEmail);
//        criteria.setQuotationId(qtIdFilter);
//        criteria.setVendorEmail(vndrFilter);
//        Pageable pageable = new PageRequest(0, 20, Sort.Direction.DESC, "id");
//        Page<RnsVendorRemark> page = rnsVendorRemarkQueryService.findByCriteria(criteria, pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rns-vendor-remarks");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }

    /**
     * POST  /rns-vendor-remarks : Create a new rnsVendorRemark.
     *
     * @param rnsVendorRemark the rnsVendorRemark to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rnsVendorRemark, or with status 400 (Bad Request) if the rnsVendorRemark has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @MessageMapping("/rns-vndr-remarks-ws/{quotationId}/{vendorEmail}")
    @SendTo("/topic/rns-vndr-remarks-ws/{quotationId}/{vendorEmail}")
    public ResponseEntity<RnsVendorRemark> createRnsVendorRemark(@DestinationVariable Long quotationId, @DestinationVariable String vendorEmail, @RequestBody RnsVendorRemark rnsVendorRemark) throws URISyntaxException {
        log.debug("REST request to save RnsVendorRemark : {}", rnsVendorRemark);
        System.out.println("-----------------------------------");
        if(rnsVendorRemark.getQuotation().getId()!=null) {
            RnsQuotation rnsQuotation = rnsQuotationRepository.findById(rnsVendorRemark.getQuotation().getId()).orElse(null);
            rnsVendorRemark.setQuotation(rnsQuotation);
        }
        RnsVendorRemark result = rnsVendorRemarkService.save(rnsVendorRemark);
        return ResponseEntity.created(new URI("/api/rns-vendor-remarks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

}
