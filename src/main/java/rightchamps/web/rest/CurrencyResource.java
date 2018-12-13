package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import rightchamps.domain.Currency;
import rightchamps.service.CurrencyService;
import rightchamps.web.rest.errors.BadRequestAlertException;
import rightchamps.web.rest.util.HeaderUtil;
import rightchamps.service.dto.CurrencyCriteria;
import rightchamps.service.CurrencyQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Currency.
 */
@RestController
@RequestMapping("/api")
public class CurrencyResource {

    private final Logger log = LoggerFactory.getLogger(CurrencyResource.class);

    private static final String ENTITY_NAME = "Currency";

    private final CurrencyService currencyService;

    private final CurrencyQueryService currencyQueryService;

    public CurrencyResource(CurrencyService currencyService, CurrencyQueryService currencyQueryService) {
        this.currencyService = currencyService;
        this.currencyQueryService = currencyQueryService;
    }

    /**
     * POST  /currencies : Create a new currency.
     *
     * @param currency the currency to create
     * @return the ResponseEntity with status 201 (Created) and with body the new currency, or with status 400 (Bad Request) if the currency has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/currencies")
    @Timed
    public ResponseEntity<Currency> createCurrency(@RequestBody Currency currency) throws URISyntaxException {
        log.debug("REST request to save Currency : {}", currency);
        if (currency.getId() != null) {
            throw new BadRequestAlertException("A new currency cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Currency result = currencyService.save(currency);
        return ResponseEntity.created(new URI("/api/currencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /currencies : Updates an existing currency.
     *
     * @param currency the currency to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated currency,
     * or with status 400 (Bad Request) if the currency is not valid,
     * or with status 500 (Internal Server Error) if the currency couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/currencies")
    @Timed
    public ResponseEntity<Currency> updateCurrency(@RequestBody Currency currency) throws URISyntaxException {
        log.debug("REST request to update Currency : {}", currency);
        if (currency.getId() == null) {
            return createCurrency(currency);
        }
        Currency result = currencyService.save(currency);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, currency.getId().toString()))
            .body(result);
    }

    /**
     * GET  /currencies : get all the currencies.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of currencies in body
     */
    @GetMapping("/currencies")
    @Timed
    public ResponseEntity<List<Currency>> getAllCurrencies(CurrencyCriteria criteria) {
        log.debug("REST request to get Currencies by criteria: {}", criteria);
        List<Currency> entityList = currencyQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /currencies/:id : get the "id" currency.
     *
     * @param id the id of the currency to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the currency, or with status 404 (Not Found)
     */
    @GetMapping("/currencies/{id}")
    @Timed
    public ResponseEntity<Currency> getCurrency(@PathVariable Long id) {
        log.debug("REST request to get Currency : {}", id);
        Optional<Currency> currency = currencyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(currency);
    }

    /**
     * DELETE  /currencies/:id : delete the "id" currency.
     *
     * @param id the id of the currency to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/currencies/{id}")
    @Timed
    public ResponseEntity<Void> deleteCurrency(@PathVariable Long id) {
        log.debug("REST request to delete Currency : {}", id);
        currencyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
