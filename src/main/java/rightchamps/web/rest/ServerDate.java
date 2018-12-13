package rightchamps.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rightchamps.domain.AuctionPauseDetails;
import rightchamps.modal.AuctionPauseDetailsBean;
import rightchamps.modal.DateBean;
import rightchamps.repository.AuctionPauseDetailsRepository;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

/**
 * REST controller for managing VndrPrice.
 */
@RestController
@RequestMapping("/api")
public class ServerDate {
    private final Logger log = LoggerFactory.getLogger(ServerDate.class);

    @Inject
    AuctionPauseDetailsRepository auctionPauseDetailsRepository;

    @GetMapping("/current-time")
    @Timed
    public ResponseEntity<Date> currentDateTime() throws URISyntaxException, IllegalAccessException, InvocationTargetException {
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(new Date()));
    }

    @GetMapping("/current-time-date")
    @Timed
    public ResponseEntity<DateBean> currentDateTimeFormat() throws URISyntaxException, IllegalAccessException, InvocationTargetException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ResponseEntity.ok().body(new DateBean(format.format(new Date())));
    }

    @GetMapping("/current-time/{id}")
    @Timed
    public ResponseEntity<AuctionPauseDetailsBean> currentDateTime(@PathVariable Long id) throws URISyntaxException, IllegalAccessException, InvocationTargetException {
        AuctionPauseDetails auctionPauseDetails = auctionPauseDetailsRepository.findAuctionPauseDetailsByQuotationId(id);
        AuctionPauseDetailsBean auctionPauseDetailsBean = new AuctionPauseDetailsBean();
        if(auctionPauseDetails!=null){
            BeanUtils.copyProperties(auctionPauseDetailsBean, auctionPauseDetails);
        }
        auctionPauseDetailsBean.setCurrentDate(new Date());
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auctionPauseDetailsBean));
    }
}
