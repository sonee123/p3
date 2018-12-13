package rightchamps.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(rightchamps.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(rightchamps.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(rightchamps.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
            cm.createCache(rightchamps.domain.RnsTaxTermsMaster.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsTaxTermsMaster.class.getName() + ".rnsCatgMasters", jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsVendorFavMaster.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsSourceTeamMaster.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsSourceTeamDtl.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsUpchargeMaster.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsUpchargeDtl.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsRfqPrice.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.Feedback.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.Message.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.EmailTemplate.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.EmailTemplateBody.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.MessageBody.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.AuctionVarDetails.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.AuctionPauseDetails.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.AuctionTermsMaster.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.AuctionTermsBodyMaster.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsRefrMaster.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsRefrDetails.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsArticleMaster.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsEmpMaster.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsSectorMaster.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsTypeMaster.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsCatgMaster.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsPayTermsMaster.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsVendorMaster.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsBuyerMaster.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsDelPlaceMaster.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsPchMaster.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsCrmRequestMaster.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsQuotation.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsQuotationCrmRequest.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsQuotationArticle.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsQuotationVariant.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsQuotationEventDefination.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsQuotationVendors.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsQuotationDealTerms.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsQuotation.class.getName() + ".variants", jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsQuotationVariant.class.getName() + ".vendors", jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsRefrDetails.class.getName() + ".rnsRefrMasters", jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsVendorMaster.class.getName() + ".vendos", jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsVendorMaster.class.getName() + ".vendors", jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsQuotation.class.getName() + ".quotationVendors", jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsQuotationVariant.class.getName() + ".quotations", jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsUomMaster.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsDelTermsMaster.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsVendorRemark.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsCatgMaster.class.getName() + ".users", jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsRelation.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsVendorRemarkComment.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.Template.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsPchMaster.class.getName() + ".users", jcacheConfiguration);
            cm.createCache(rightchamps.domain.Currency.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsEmpLinkMaster.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsQuotationRemarkDetails.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsForwardTypeMaster.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.Auction.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.AuctionVrnt.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.VndrPrice.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsFileUpload.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.RnsCatgMasterUser.class.getName(), jcacheConfiguration);
            cm.createCache(rightchamps.domain.VndrPriceDelete.class.getName(), jcacheConfiguration);
        };
    }
}
