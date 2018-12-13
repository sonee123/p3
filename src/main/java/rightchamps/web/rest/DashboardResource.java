package rightchamps.web.rest;

import afu.org.checkerframework.checker.igj.qual.I;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rightchamps.domain.*;
import rightchamps.modal.DashboardBean;
import rightchamps.modal.LotBean;
import rightchamps.modal.VndrPriceCustom;
import rightchamps.repository.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import java.util.*;

@RestController
@RequestMapping("/api")
public class DashboardResource {
    private static final String ENTITY_NAME = "Dashboard";
    private final Logger log = LoggerFactory.getLogger(CurrencyResource.class);

    @Inject
    private RnsCatgMasterRepository rnsCatgMasterRepository;

    @Inject
    private RnsQuotationRepository rnsQuotationRepository;

    @Inject
    private RnsQuotationVariantRepository rnsQuotationVariantRepository;

    @Inject
    private AuctionVrntRepository auctionVrntRepository;

    @Inject
    private VndrPriceRepository vndrPriceRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private MessageRepository messageRepository;

    @Autowired
    private EntityManager entityManager;

    public DashboardResource() {
    }

    @PostMapping("/dashboard-messages")
    @Timed
    public ResponseEntity<DashboardBean> getDashBoardMessages(@RequestBody DashboardBean dashboardBean) {
        log.debug("REST request to get DashboardBean : {}", dashboardBean);
        List<UserAuthority> authorities = userRepository.findAllAuthorityByLogin(getCurrentUserLogin());
        if (authorities != null && authorities.get(0).getAuthorityName().equalsIgnoreCase("ROLE_VENDOR")) {
            dashboardBean.setMessageBeans(messageRepository.findAllByFromMail(getCurrentUserLogin()));
            dashboardBean.setMessageBeansTo(messageRepository.findAllByToMail(getCurrentUserLogin()));

        } else {
            Instant startDate = null;
            Instant endDate = null;
            if (dashboardBean.getMonthYear() != null) {
                String startDateString = "01-" + dashboardBean.getMonthYear();
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US);
                LocalDate date = LocalDate.parse(startDateString, dateFormat);
                startDate = Timestamp.valueOf(date.atStartOfDay()).toInstant();

                ValueRange range = date.range(ChronoField.DAY_OF_MONTH);
                Long max = range.getMaximum();
                LocalDate newDate = date.withDayOfMonth(max.intValue());
                endDate = Timestamp.valueOf(newDate.atStartOfDay()).toInstant();
            }
            dashboardBean.setMessageBeans(messageRepository.findAllByCreatedDateBetweenAndFromMail(startDate, endDate, getCurrentUserLogin()));
            dashboardBean.setMessageBeansTo(messageRepository.findAllByCreatedDateBetweenAndToMail(startDate, endDate, getCurrentUserLogin()));
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dashboardBean));
    }

    @PostMapping("/dashboard")
    @Timed
    public ResponseEntity<DashboardBean> getDashBoard(@RequestBody DashboardBean dashboardBean) {
        log.debug("REST request to get DashboardBean : {}", dashboardBean);
        List<UserAuthority> authorities = userRepository.findAllAuthorityByLogin(getCurrentUserLogin());
        if(authorities!=null && authorities.get(0).getAuthorityName().equalsIgnoreCase("ROLE_VENDOR")){
            List<RnsQuotation> quotationListTemp = rnsQuotationRepository.findByVendorId(getCurrentUserLogin());
            long totalRfq = 0;
            long openRfq = 0;
            long closedRfq = 0;
            List<String> quotations = new ArrayList<String>();
            java.util.Date date = new Date();
            for (RnsQuotation quotation : quotationListTemp) {
                if(quotations.contains(quotation.getId().toString())){}
                else{
                    ++totalRfq;
                    if(quotation.getValidity().before(date)){
                        ++closedRfq;
                    } else{
                        ++openRfq;
                    }
                    quotations.add(quotation.getId().toString());
                }
            }

            long totalRfb = 0;
            long openRfb = 0;
            long closedRfb = 0;
            List<Long> biddingList = new ArrayList<Long>();
            List<RnsQuotation> quotationList = rnsQuotationRepository.findAuctionByVendorId(getCurrentUserLogin());
            for (RnsQuotation quotation : quotationList) {
                if(biddingList.contains(quotation.getId())){}
                else{
                    if (totalRfb == 0) {
                        dashboardBean.setSelectedBid(quotation.getId());
                    }
                    ++totalRfb;
                    if (quotation.getAuctionClose() != null) {
                        ++closedRfb;
                    } else{
                        ++openRfb;
                    }
                    biddingList.add(quotation.getId());
                }
            }

            dashboardBean.setTotalRfq(totalRfq);
            dashboardBean.setOpenRfq(openRfq);
            dashboardBean.setClosedRfq(closedRfq);

            dashboardBean.setTotalRfb(totalRfb);
            dashboardBean.setOpenRfb(openRfb);
            dashboardBean.setClosedRfb(closedRfb);

            dashboardBean.setBiddingList(biddingList);

            RnsQuotationVariant rnsQuotationVariantTemp = null;

            if (dashboardBean.getSelectedBid() != null) {
                List<RnsQuotationVariant> rnsQuotationVariants = rnsQuotationVariantRepository.getRnsQuotationVariantByQuotation(dashboardBean.getSelectedBid());
                List<LotBean> lotList = new ArrayList<LotBean>();
                for (RnsQuotationVariant rnsQuotationVariant : rnsQuotationVariants) {
                    if(dashboardBean.getSelectedLot()==null){
                        rnsQuotationVariantTemp = rnsQuotationVariant;
                        dashboardBean.setSelectedLot(rnsQuotationVariant.getId());
                        dashboardBean.setRnsQuotationVariant(rnsQuotationVariant);
                    }
                    lotList.add(new LotBean(rnsQuotationVariant.getId(), rnsQuotationVariant.getTitle().replaceAll("Variant", "Lot")));
                }
                dashboardBean.setLotList(lotList);
            }

            if (dashboardBean.getSelectedBid() != null && dashboardBean.getSelectedLot() != null) {
                List<VndrPriceCustom> prices = new ArrayList<VndrPriceCustom>();
                AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(rnsQuotationVariantTemp.getId());
                List<VndrPrice> vndrPrice = vndrPriceRepository.getAllbyVariantandVendorCode(rnsQuotationVariantTemp.getId(), getCurrentUserLogin());
                Map<String, User> userMap = new HashMap<String, User>();
                for (VndrPrice price : vndrPrice) {
                    Float totalPrice = 0.0f;
                    if (price.getPriceOne() != null && auctionVrnt.getConvFactOne() != null) {
                        totalPrice = totalPrice + (price.getPriceOne() * auctionVrnt.getConvFactOne());
                    }
                    if (price.getPriceTwo() != null && auctionVrnt.getConvFactTwo() != null) {
                        totalPrice = totalPrice + (price.getPriceTwo() * auctionVrnt.getConvFactTwo());
                    }
                    if (price.getPriceThree() != null && auctionVrnt.getConvFactThree() != null) {
                        totalPrice = totalPrice + (price.getPriceThree() * auctionVrnt.getConvFactThree());
                    }
                    if (price.getPriceFour() != null && auctionVrnt.getConvFactFour() != null) {
                        totalPrice = totalPrice + (price.getPriceFour() * auctionVrnt.getConvFactFour());
                    }
                    if (price.getPriceFive() != null && auctionVrnt.getConvFactFive() != null) {
                        totalPrice = totalPrice + (price.getPriceFive() * auctionVrnt.getConvFactFive());
                    }
                    if (price.getPriceSix() != null && auctionVrnt.getConvFactSix() != null) {
                        totalPrice = totalPrice + (price.getPriceSix() * auctionVrnt.getConvFactSix());
                    }
                    if (price.getPriceSeven() != null && auctionVrnt.getConvFactSeven() != null) {
                        totalPrice = totalPrice + (price.getPriceSeven() * auctionVrnt.getConvFactSeven());
                    }
                    if (price.getPriceEight() != null && auctionVrnt.getConvFactEight() != null) {
                        totalPrice = totalPrice + (price.getPriceEight() * auctionVrnt.getConvFactEight());
                    }
                    if (price.getPriceNine() != null && auctionVrnt.getConvFactNine() != null) {
                        totalPrice = totalPrice + (price.getPriceNine() * auctionVrnt.getConvFactNine());
                    }
                    if (price.getPriceTen() != null && auctionVrnt.getConvFactTen() != null) {
                        totalPrice = totalPrice + (price.getPriceTen() * auctionVrnt.getConvFactTen());
                    }
                    User user = null;
                    if (userMap.containsKey(price.getVendorCode())) {
                        user = userMap.get(price.getVendorCode());
                    } else {
                        user = userRepository.findByLogin(price.getVendorCode());
                        userMap.put(price.getVendorCode(), user);
                    }
                    prices.add(new VndrPriceCustom(price.getId(), rnsQuotationVariantTemp.getTitle(), price.getVendorCode(), price.getCreatedOn(), totalPrice, price.isSurrogate(), user.getFirstName(), user.getLastName(), user.getVendorName()));
                }

                dashboardBean.setPricesList(prices);
            }
        }
        else {
            Instant startDate = null;
            Instant endDate = null;
            if (dashboardBean.getMonthYear() != null) {
                String startDateString = "01-" + dashboardBean.getMonthYear();
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.US);
                LocalDate date = LocalDate.parse(startDateString, dateFormat);
                startDate = Timestamp.valueOf(date.atStartOfDay()).toInstant();

                ValueRange range = date.range(ChronoField.DAY_OF_MONTH);
                Long max = range.getMaximum();
                LocalDate newDate = date.withDayOfMonth(max.intValue());
                endDate = Timestamp.valueOf(newDate.atStartOfDay()).toInstant();
            }

            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<RnsQuotation> cq = builder.createQuery(RnsQuotation.class);
            Root<RnsQuotation> root = cq.from(RnsQuotation.class);
            cq.select(root);
            Predicate predicate = builder.conjunction();

            // Filter between date
            if (startDate != null && endDate != null) {
                predicate = builder.and(predicate, builder.between(builder.function("TRUNC", java.time.Instant.class, root.get("createdOn")), startDate, endDate));
            }

            // Filter Category Code
            if (dashboardBean.getCatgCode() != null) {
                Optional<RnsCatgMaster> rnsCatgMasterOptional = rnsCatgMasterRepository.findById(dashboardBean.getCatgCode());
                predicate = builder.and(predicate, builder.equal(root.get("rnsCatgCode"), rnsCatgMasterOptional.get()));
            }

            cq.where(predicate);
            cq.orderBy(builder.desc(root.get("id")));
            List<RnsQuotation> quotationList = null;
            try {
                quotationList = entityManager.createQuery(cq.select(root)).getResultList();
            } catch (Exception e) {
            }
            if (quotationList != null && quotationList.size() > 0) {
                dashboardBean.setTotalProject(Long.parseLong(quotationList.size() + ""));
                long totalRfq = 0;
                long openRfq = 0;
                long closedRfq = 0;


                long totalRfb = 0;
                long openRfb = 0;
                long closedRfb = 0;
                List<Long> biddingList = new ArrayList<Long>();
                for (RnsQuotation rnsQuotation : quotationList) {
                    if (rnsQuotation.getRfqApplicable() != null && rnsQuotation.getRfqApplicable() == true) {
                        ++totalRfq;
                        if (rnsQuotation.getValidity()!= null && rnsQuotation.getValidity().toInstant().isAfter(Instant.now())) {
                            ++openRfq;
                        } else {
                            ++closedRfq;
                        }
                    }
                    if (rnsQuotation.getAuctionApplicable() != null && rnsQuotation.getAuctionApplicable() == true) {
                        if (totalRfb == 0) {
                            dashboardBean.setSelectedBid(rnsQuotation.getId());
                        }
                        ++totalRfb;
                        biddingList.add(rnsQuotation.getId());
                        if (rnsQuotation.getAuctionClose() != null) {
                            ++closedRfb;
                        } else {
                            ++openRfb;
                        }
                    }
                }
                dashboardBean.setTotalRfq(totalRfq);
                dashboardBean.setOpenRfq(openRfq);
                dashboardBean.setClosedRfq(closedRfq);

                dashboardBean.setTotalRfb(totalRfb);
                dashboardBean.setOpenRfb(openRfb);
                dashboardBean.setClosedRfb(closedRfb);

                dashboardBean.setBiddingList(biddingList);
                RnsQuotationVariant rnsQuotationVariantTemp = null;
                if (dashboardBean.getSelectedBid() != null) {
                    List<RnsQuotationVariant> rnsQuotationVariants = rnsQuotationVariantRepository.getRnsQuotationVariantByQuotation(dashboardBean.getSelectedBid());
                    List<LotBean> lotList = new ArrayList<LotBean>();
                    for (RnsQuotationVariant rnsQuotationVariant : rnsQuotationVariants) {
                        if(dashboardBean.getSelectedLot()==null){
                            rnsQuotationVariantTemp = rnsQuotationVariant;
                            dashboardBean.setSelectedLot(rnsQuotationVariant.getId());
                            dashboardBean.setRnsQuotationVariant(rnsQuotationVariant);
                        }
                        lotList.add(new LotBean(rnsQuotationVariant.getId(), rnsQuotationVariant.getTitle().replaceAll("Variant", "Lot")));
                    }
                    dashboardBean.setLotList(lotList);
                }

                if (dashboardBean.getSelectedBid() != null && dashboardBean.getSelectedLot() != null) {
                    List<VndrPriceCustom> prices = new ArrayList<VndrPriceCustom>();
                    AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(rnsQuotationVariantTemp.getId());
                    List<VndrPrice> vndrPrice = vndrPriceRepository.getAllbyVariant(rnsQuotationVariantTemp.getId());
                    Map<String, User> userMap = new HashMap<String, User>();
                    for (VndrPrice price : vndrPrice) {
                        Float totalPrice = 0.0f;
                        if (price.getPriceOne() != null && auctionVrnt.getConvFactOne() != null) {
                            totalPrice = totalPrice + (price.getPriceOne() * auctionVrnt.getConvFactOne());
                        }
                        if (price.getPriceTwo() != null && auctionVrnt.getConvFactTwo() != null) {
                            totalPrice = totalPrice + (price.getPriceTwo() * auctionVrnt.getConvFactTwo());
                        }
                        if (price.getPriceThree() != null && auctionVrnt.getConvFactThree() != null) {
                            totalPrice = totalPrice + (price.getPriceThree() * auctionVrnt.getConvFactThree());
                        }
                        if (price.getPriceFour() != null && auctionVrnt.getConvFactFour() != null) {
                            totalPrice = totalPrice + (price.getPriceFour() * auctionVrnt.getConvFactFour());
                        }
                        if (price.getPriceFive() != null && auctionVrnt.getConvFactFive() != null) {
                            totalPrice = totalPrice + (price.getPriceFive() * auctionVrnt.getConvFactFive());
                        }
                        if (price.getPriceSix() != null && auctionVrnt.getConvFactSix() != null) {
                            totalPrice = totalPrice + (price.getPriceSix() * auctionVrnt.getConvFactSix());
                        }
                        if (price.getPriceSeven() != null && auctionVrnt.getConvFactSeven() != null) {
                            totalPrice = totalPrice + (price.getPriceSeven() * auctionVrnt.getConvFactSeven());
                        }
                        if (price.getPriceEight() != null && auctionVrnt.getConvFactEight() != null) {
                            totalPrice = totalPrice + (price.getPriceEight() * auctionVrnt.getConvFactEight());
                        }
                        if (price.getPriceNine() != null && auctionVrnt.getConvFactNine() != null) {
                            totalPrice = totalPrice + (price.getPriceNine() * auctionVrnt.getConvFactNine());
                        }
                        if (price.getPriceTen() != null && auctionVrnt.getConvFactTen() != null) {
                            totalPrice = totalPrice + (price.getPriceTen() * auctionVrnt.getConvFactTen());
                        }
                        User user = null;
                        if (userMap.containsKey(price.getVendorCode())) {
                            user = userMap.get(price.getVendorCode());
                        } else {
                            user = userRepository.findByLogin(price.getVendorCode());
                            userMap.put(price.getVendorCode(), user);
                        }
                        prices.add(new VndrPriceCustom(price.getId(), rnsQuotationVariantTemp.getTitle(), price.getVendorCode(), price.getCreatedOn(), totalPrice, price.isSurrogate(), user.getFirstName(), user.getLastName(), user.getVendorName()));
                    }

                    dashboardBean.setPricesList(prices);
                }
            } else {
                dashboardBean.setTotalProject(0L);
                dashboardBean.setOpenRfq(0L);
                dashboardBean.setClosedRfq(0L);

                dashboardBean.setTotalRfb(0L);
                dashboardBean.setOpenRfb(0L);
                dashboardBean.setClosedRfb(0L);
            }
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dashboardBean));
    }

    @PostMapping("/dashboard/lot")
    @Timed
    public ResponseEntity<DashboardBean> getDashBoardLot(@RequestBody DashboardBean dashboardBean) throws URISyntaxException {
        List<RnsQuotationVariant> rnsQuotationVariants = rnsQuotationVariantRepository.getRnsQuotationVariantByQuotation(dashboardBean.getSelectedBid());
        List<LotBean> lotList = new ArrayList<LotBean>();
        RnsQuotationVariant rnsQuotationVariantTemp = null;
        dashboardBean.setSelectedLot(null);
        for (RnsQuotationVariant rnsQuotationVariant : rnsQuotationVariants) {
            if(dashboardBean.getSelectedLot()==null){
                dashboardBean.setSelectedLot(rnsQuotationVariant.getId());
                dashboardBean.setRnsQuotationVariant(rnsQuotationVariant);
                rnsQuotationVariantTemp = rnsQuotationVariant;
            }
            lotList.add(new LotBean(rnsQuotationVariant.getId(), rnsQuotationVariant.getTitle().replaceAll("Variant", "Lot")));
        }
        dashboardBean.setLotList(lotList);

        if (dashboardBean.getSelectedBid() != null && dashboardBean.getSelectedLot() != null) {
            List<UserAuthority> authorities = userRepository.findAllAuthorityByLogin(getCurrentUserLogin());
            if(authorities!=null && authorities.get(0).getAuthorityName().equalsIgnoreCase("ROLE_VENDOR")) {
                List<VndrPriceCustom> prices = new ArrayList<VndrPriceCustom>();
                AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(rnsQuotationVariantTemp.getId());
                List<VndrPrice> vndrPrice = vndrPriceRepository.getAllbyVariantandVendorCode(rnsQuotationVariantTemp.getId(), getCurrentUserLogin());
                Map<String, User> userMap = new HashMap<String, User>();
                for (VndrPrice price : vndrPrice) {
                    Float totalPrice = 0.0f;
                    if (price.getPriceOne() != null && auctionVrnt.getConvFactOne() != null) {
                        totalPrice = totalPrice + (price.getPriceOne() * auctionVrnt.getConvFactOne());
                    }
                    if (price.getPriceTwo() != null && auctionVrnt.getConvFactTwo() != null) {
                        totalPrice = totalPrice + (price.getPriceTwo() * auctionVrnt.getConvFactTwo());
                    }
                    if (price.getPriceThree() != null && auctionVrnt.getConvFactThree() != null) {
                        totalPrice = totalPrice + (price.getPriceThree() * auctionVrnt.getConvFactThree());
                    }
                    if (price.getPriceFour() != null && auctionVrnt.getConvFactFour() != null) {
                        totalPrice = totalPrice + (price.getPriceFour() * auctionVrnt.getConvFactFour());
                    }
                    if (price.getPriceFive() != null && auctionVrnt.getConvFactFive() != null) {
                        totalPrice = totalPrice + (price.getPriceFive() * auctionVrnt.getConvFactFive());
                    }
                    if (price.getPriceSix() != null && auctionVrnt.getConvFactSix() != null) {
                        totalPrice = totalPrice + (price.getPriceSix() * auctionVrnt.getConvFactSix());
                    }
                    if (price.getPriceSeven() != null && auctionVrnt.getConvFactSeven() != null) {
                        totalPrice = totalPrice + (price.getPriceSeven() * auctionVrnt.getConvFactSeven());
                    }
                    if (price.getPriceEight() != null && auctionVrnt.getConvFactEight() != null) {
                        totalPrice = totalPrice + (price.getPriceEight() * auctionVrnt.getConvFactEight());
                    }
                    if (price.getPriceNine() != null && auctionVrnt.getConvFactNine() != null) {
                        totalPrice = totalPrice + (price.getPriceNine() * auctionVrnt.getConvFactNine());
                    }
                    if (price.getPriceTen() != null && auctionVrnt.getConvFactTen() != null) {
                        totalPrice = totalPrice + (price.getPriceTen() * auctionVrnt.getConvFactTen());
                    }
                    User user = null;
                    if (userMap.containsKey(price.getVendorCode())) {
                        user = userMap.get(price.getVendorCode());
                    } else {
                        user = userRepository.findByLogin(price.getVendorCode());
                        userMap.put(price.getVendorCode(), user);
                    }
                    prices.add(new VndrPriceCustom(price.getId(), rnsQuotationVariantTemp.getTitle(), price.getVendorCode(), price.getCreatedOn(), totalPrice, price.isSurrogate(), user.getFirstName(), user.getLastName(), user.getVendorName()));
                }
                dashboardBean.setPricesList(prices);
            } else {
                List<VndrPriceCustom> prices = new ArrayList<VndrPriceCustom>();
                AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(rnsQuotationVariantTemp.getId());
                List<VndrPrice> vndrPrice = vndrPriceRepository.getAllbyVariant(rnsQuotationVariantTemp.getId());
                Map<String, User> userMap = new HashMap<String, User>();
                for (VndrPrice price : vndrPrice) {
                    Float totalPrice = 0.0f;
                    if (price.getPriceOne() != null && auctionVrnt.getConvFactOne() != null) {
                        totalPrice = totalPrice + (price.getPriceOne() * auctionVrnt.getConvFactOne());
                    }
                    if (price.getPriceTwo() != null && auctionVrnt.getConvFactTwo() != null) {
                        totalPrice = totalPrice + (price.getPriceTwo() * auctionVrnt.getConvFactTwo());
                    }
                    if (price.getPriceThree() != null && auctionVrnt.getConvFactThree() != null) {
                        totalPrice = totalPrice + (price.getPriceThree() * auctionVrnt.getConvFactThree());
                    }
                    if (price.getPriceFour() != null && auctionVrnt.getConvFactFour() != null) {
                        totalPrice = totalPrice + (price.getPriceFour() * auctionVrnt.getConvFactFour());
                    }
                    if (price.getPriceFive() != null && auctionVrnt.getConvFactFive() != null) {
                        totalPrice = totalPrice + (price.getPriceFive() * auctionVrnt.getConvFactFive());
                    }
                    if (price.getPriceSix() != null && auctionVrnt.getConvFactSix() != null) {
                        totalPrice = totalPrice + (price.getPriceSix() * auctionVrnt.getConvFactSix());
                    }
                    if (price.getPriceSeven() != null && auctionVrnt.getConvFactSeven() != null) {
                        totalPrice = totalPrice + (price.getPriceSeven() * auctionVrnt.getConvFactSeven());
                    }
                    if (price.getPriceEight() != null && auctionVrnt.getConvFactEight() != null) {
                        totalPrice = totalPrice + (price.getPriceEight() * auctionVrnt.getConvFactEight());
                    }
                    if (price.getPriceNine() != null && auctionVrnt.getConvFactNine() != null) {
                        totalPrice = totalPrice + (price.getPriceNine() * auctionVrnt.getConvFactNine());
                    }
                    if (price.getPriceTen() != null && auctionVrnt.getConvFactTen() != null) {
                        totalPrice = totalPrice + (price.getPriceTen() * auctionVrnt.getConvFactTen());
                    }
                    User user = null;
                    if (userMap.containsKey(price.getVendorCode())) {
                        user = userMap.get(price.getVendorCode());
                    } else {
                        user = userRepository.findByLogin(price.getVendorCode());
                        userMap.put(price.getVendorCode(), user);
                    }
                    prices.add(new VndrPriceCustom(price.getId(), rnsQuotationVariantTemp.getTitle(), price.getVendorCode(), price.getCreatedOn(), totalPrice, price.isSurrogate(), user.getFirstName(), user.getLastName(), user.getVendorName()));
                }
                dashboardBean.setPricesList(prices);
            }
        }

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dashboardBean));
    }

    @PostMapping("/dashboard/chart")
    @Timed
    public ResponseEntity<DashboardBean> getDashBoardChart(@RequestBody DashboardBean dashboardBean) throws URISyntaxException {
        List<UserAuthority> authorities = userRepository.findAllAuthorityByLogin(getCurrentUserLogin());
        if(authorities!=null && authorities.get(0).getAuthorityName().equalsIgnoreCase("ROLE_VENDOR")) {
            List<VndrPriceCustom> prices = new ArrayList<VndrPriceCustom>();
            Optional<RnsQuotationVariant> rnsQuotationVariantTemp = rnsQuotationVariantRepository.findById(dashboardBean.getSelectedLot());
            dashboardBean.setRnsQuotationVariant(rnsQuotationVariantTemp.get());
            AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(rnsQuotationVariantTemp.get().getId());
            List<VndrPrice> vndrPrice = vndrPriceRepository.getAllbyVariantandVendorCode(rnsQuotationVariantTemp.get().getId(), getCurrentUserLogin());
            Map<String, User> userMap = new HashMap<String, User>();
            for (VndrPrice price : vndrPrice) {
                Float totalPrice = 0.0f;
                if (price.getPriceOne() != null && auctionVrnt.getConvFactOne() != null) {
                    totalPrice = totalPrice + (price.getPriceOne() * auctionVrnt.getConvFactOne());
                }
                if (price.getPriceTwo() != null && auctionVrnt.getConvFactTwo() != null) {
                    totalPrice = totalPrice + (price.getPriceTwo() * auctionVrnt.getConvFactTwo());
                }
                if (price.getPriceThree() != null && auctionVrnt.getConvFactThree() != null) {
                    totalPrice = totalPrice + (price.getPriceThree() * auctionVrnt.getConvFactThree());
                }
                if (price.getPriceFour() != null && auctionVrnt.getConvFactFour() != null) {
                    totalPrice = totalPrice + (price.getPriceFour() * auctionVrnt.getConvFactFour());
                }
                if (price.getPriceFive() != null && auctionVrnt.getConvFactFive() != null) {
                    totalPrice = totalPrice + (price.getPriceFive() * auctionVrnt.getConvFactFive());
                }
                if (price.getPriceSix() != null && auctionVrnt.getConvFactSix() != null) {
                    totalPrice = totalPrice + (price.getPriceSix() * auctionVrnt.getConvFactSix());
                }
                if (price.getPriceSeven() != null && auctionVrnt.getConvFactSeven() != null) {
                    totalPrice = totalPrice + (price.getPriceSeven() * auctionVrnt.getConvFactSeven());
                }
                if (price.getPriceEight() != null && auctionVrnt.getConvFactEight() != null) {
                    totalPrice = totalPrice + (price.getPriceEight() * auctionVrnt.getConvFactEight());
                }
                if (price.getPriceNine() != null && auctionVrnt.getConvFactNine() != null) {
                    totalPrice = totalPrice + (price.getPriceNine() * auctionVrnt.getConvFactNine());
                }
                if (price.getPriceTen() != null && auctionVrnt.getConvFactTen() != null) {
                    totalPrice = totalPrice + (price.getPriceTen() * auctionVrnt.getConvFactTen());
                }
                User user = null;
                if (userMap.containsKey(price.getVendorCode())) {
                    user = userMap.get(price.getVendorCode());
                } else {
                    user = userRepository.findByLogin(price.getVendorCode());
                    userMap.put(price.getVendorCode(), user);
                }
                prices.add(new VndrPriceCustom(price.getId(), rnsQuotationVariantTemp.get().getTitle(), price.getVendorCode(), price.getCreatedOn(), totalPrice, price.isSurrogate(), user.getFirstName(), user.getLastName(), user.getVendorName()));
            }
            dashboardBean.setPricesList(prices);
        } else{
            List<VndrPriceCustom> prices = new ArrayList<VndrPriceCustom>();
            Optional<RnsQuotationVariant> rnsQuotationVariantTemp = rnsQuotationVariantRepository.findById(dashboardBean.getSelectedLot());
            dashboardBean.setRnsQuotationVariant(rnsQuotationVariantTemp.get());
            AuctionVrnt auctionVrnt = auctionVrntRepository.getAuctionVrntbyVariant(rnsQuotationVariantTemp.get().getId());
            List<VndrPrice> vndrPrice = vndrPriceRepository.getAllbyVariant(rnsQuotationVariantTemp.get().getId());
            Map<String, User> userMap = new HashMap<String, User>();
            for (VndrPrice price : vndrPrice) {
                Float totalPrice = 0.0f;
                if (price.getPriceOne() != null && auctionVrnt.getConvFactOne() != null) {
                    totalPrice = totalPrice + (price.getPriceOne() * auctionVrnt.getConvFactOne());
                }
                if (price.getPriceTwo() != null && auctionVrnt.getConvFactTwo() != null) {
                    totalPrice = totalPrice + (price.getPriceTwo() * auctionVrnt.getConvFactTwo());
                }
                if (price.getPriceThree() != null && auctionVrnt.getConvFactThree() != null) {
                    totalPrice = totalPrice + (price.getPriceThree() * auctionVrnt.getConvFactThree());
                }
                if (price.getPriceFour() != null && auctionVrnt.getConvFactFour() != null) {
                    totalPrice = totalPrice + (price.getPriceFour() * auctionVrnt.getConvFactFour());
                }
                if (price.getPriceFive() != null && auctionVrnt.getConvFactFive() != null) {
                    totalPrice = totalPrice + (price.getPriceFive() * auctionVrnt.getConvFactFive());
                }
                if (price.getPriceSix() != null && auctionVrnt.getConvFactSix() != null) {
                    totalPrice = totalPrice + (price.getPriceSix() * auctionVrnt.getConvFactSix());
                }
                if (price.getPriceSeven() != null && auctionVrnt.getConvFactSeven() != null) {
                    totalPrice = totalPrice + (price.getPriceSeven() * auctionVrnt.getConvFactSeven());
                }
                if (price.getPriceEight() != null && auctionVrnt.getConvFactEight() != null) {
                    totalPrice = totalPrice + (price.getPriceEight() * auctionVrnt.getConvFactEight());
                }
                if (price.getPriceNine() != null && auctionVrnt.getConvFactNine() != null) {
                    totalPrice = totalPrice + (price.getPriceNine() * auctionVrnt.getConvFactNine());
                }
                if (price.getPriceTen() != null && auctionVrnt.getConvFactTen() != null) {
                    totalPrice = totalPrice + (price.getPriceTen() * auctionVrnt.getConvFactTen());
                }
                User user = null;
                if (userMap.containsKey(price.getVendorCode())) {
                    user = userMap.get(price.getVendorCode());
                } else {
                    user = userRepository.findByLogin(price.getVendorCode());
                    userMap.put(price.getVendorCode(), user);
                }
                prices.add(new VndrPriceCustom(price.getId(), rnsQuotationVariantTemp.get().getTitle(), price.getVendorCode(), price.getCreatedOn(), totalPrice, price.isSurrogate(), user.getFirstName(), user.getLastName(), user.getVendorName()));
            }
            dashboardBean.setPricesList(prices);
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dashboardBean));
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    public static String getCurrentUserLogin() {
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
