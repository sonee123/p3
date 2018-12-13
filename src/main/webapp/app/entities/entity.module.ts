import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */
import { RnsRnsRefrMasterModule } from './rns-refr-master/rns-refr-master.module';
import { RnsRnsRefrDetailsModule } from './rns-refr-details/rns-refr-details.module';
import { RnsRnsArticleMasterModule } from './rns-article-master/rns-article-master.module';
import { RnsRnsEmpMasterModule } from './rns-emp-master/rns-emp-master.module';
import { RnsRnsSectorMasterModule } from './rns-sector-master/rns-sector-master.module';
import { RnsRnsTypeMasterModule } from './rns-type-master/rns-type-master.module';
import { RnsRnsCatgMasterModule } from './rns-catg-master/rns-catg-master.module';
import { RnsRnsPayTermsMasterModule } from './rns-pay-terms-master/rns-pay-terms-master.module';
import { RnsRnsVendorMasterModule } from './rns-vendor-master/rns-vendor-master.module';
import { RnsRnsBuyerMasterModule } from './rns-buyer-master/rns-buyer-master.module';
import { RnsRnsDelPlaceMasterModule } from './rns-del-place-master/rns-del-place-master.module';
import { RnsRnsPchMasterModule } from './rns-pch-master/rns-pch-master.module';
import { RnsRnsCrmRequestMasterModule } from './rns-crm-request-master/rns-crm-request-master.module';
import { RnsRnsQuotationModule } from './rns-quotation/rns-quotation.module';
import { RnsRnsQuotationCrmRequestModule } from './rns-quotation-crm-request/rns-quotation-crm-request.module';
import { RnsRnsQuotationArticleModule } from './rns-quotation-article/rns-quotation-article.module';
import { RnsRnsQuotationVariantModule } from './rns-quotation-variant/rns-quotation-variant.module';
import { RnsRnsQuotationEventDefinationModule } from './rns-quotation-event-defination/rns-quotation-event-defination.module';
import { RnsRnsQuotationVendorsModule } from './rns-quotation-vendors/rns-quotation-vendors.module';
import { RnsRnsQuotationDealTermsModule } from './rns-quotation-deal-terms/rns-quotation-deal-terms.module';
import { RnsRnsUomMasterModule } from './rns-uom-master/rns-uom-master.module';
import { RnsRnsDelTermsMasterModule } from './rns-del-terms-master/rns-del-terms-master.module';
import { RnsRnsVendorRemarkModule } from './rns-vendor-remark/rns-vendor-remark.module';
import { RnsRnsRelationModule } from './rns-relation/rns-relation.module';
import { RnsRnsVendorRemarkCommentModule } from './rns-vendor-remark-comment/rns-vendor-remark-comment.module';
import { RnsTemplateModule } from './template/template.module';
import { RnsCurrencyModule } from './currency/currency.module';
import { RnsRnsEmpLinkMasterModule } from './rns-emp-link-master/rns-emp-link-master.module';
import { RnsRnsQuotationRemarkDetailsModule } from './rns-quotation-remark-details/rns-quotation-remark-details.module';
import { RnsRnsForwardTypeMasterModule } from './rns-forward-type-master/rns-forward-type-master.module';
import { RnsAuctionModule } from './auction/auction.module';
import { RnsAuctionVrntModule } from './auction-vrnt/auction-vrnt.module';
import { RnsVndrPriceModule } from './vndr-price/vndr-price.module';
import { RnsRnsTaxTermsMasterModule } from './rns-tax-terms-master/rns-tax-terms-master.module';
import { RnsRnsVendorFavMasterModule } from './rns-vendor-fav-master/rns-vendor-fav-master.module';
import { RnsRnsSourceTeamMasterModule } from './rns-source-team-master/rns-source-team-master.module';
import { RnsRnsSourceTeamDtlModule } from './rns-source-team-dtl/rns-source-team-dtl.module';
import { RnsRnsUpchargeMasterModule } from './rns-upcharge-master/rns-upcharge-master.module';
import { RnsRnsUpchargeDtlModule } from './rns-upcharge-dtl/rns-upcharge-dtl.module';
import { RnsRnsRfqPriceModule } from './rns-rfq-price/rns-rfq-price.module';
import { RnsFeedbackModule } from './feedback/feedback.module';
import { RnsMessageModule } from './message/message.module';
import { RnsEmailTemplateModule } from './email-template/email-template.module';
import { RnsEmailTemplateBodyModule } from './email-template-body/email-template-body.module';
import { RnsMessageBodyModule } from './message-body/message-body.module';
import { RnsAuctionVarDetailsModule } from './auction-var-details/auction-var-details.module';
import { RnsAuctionPauseDetailsModule } from './auction-pause-details/auction-pause-details.module';
import { RnsAuctionTermsMasterModule } from './auction-terms-master/auction-terms-master.module';
import { RnsAuctionTermsBodyMasterModule } from './auction-terms-body-master/';
import { RnsRnsFileUploadModule } from './rns-file-upload/rns-file-upload.module';

@NgModule({
    // prettier-ignore
    imports: [
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
        RnsRnsRefrMasterModule,
        RnsRnsRefrDetailsModule,
        RnsRnsArticleMasterModule,
        RnsRnsEmpMasterModule,
        RnsRnsSectorMasterModule,
        RnsRnsTypeMasterModule,
        RnsRnsCatgMasterModule,
        RnsRnsPayTermsMasterModule,
        RnsRnsVendorMasterModule,
        RnsRnsBuyerMasterModule,
        RnsRnsDelPlaceMasterModule,
        RnsRnsPchMasterModule,
        RnsRnsCrmRequestMasterModule,
        RnsRnsQuotationModule,
        RnsRnsQuotationCrmRequestModule,
        RnsRnsQuotationArticleModule,
        RnsRnsQuotationVariantModule,
        RnsRnsQuotationEventDefinationModule,
        RnsRnsQuotationVendorsModule,
        RnsRnsQuotationDealTermsModule,
        RnsRnsUomMasterModule,
        RnsRnsDelTermsMasterModule,
        RnsRnsVendorRemarkModule,
        RnsRnsRelationModule,
        RnsRnsVendorRemarkCommentModule,
        RnsTemplateModule,
        RnsCurrencyModule,
        RnsRnsEmpLinkMasterModule,
        RnsRnsQuotationRemarkDetailsModule,
        RnsRnsForwardTypeMasterModule,
        RnsAuctionModule,
        RnsAuctionVrntModule,
        RnsVndrPriceModule,
        RnsRnsTaxTermsMasterModule,
        RnsRnsVendorFavMasterModule,
        RnsRnsSourceTeamMasterModule,
        RnsRnsSourceTeamDtlModule,
        RnsRnsUpchargeMasterModule,
        RnsRnsUpchargeDtlModule,
        RnsRnsRfqPriceModule,
        RnsFeedbackModule,
        RnsMessageModule,
        RnsEmailTemplateModule,
        RnsEmailTemplateBodyModule,
        RnsMessageBodyModule,
        RnsAuctionVarDetailsModule,
        RnsAuctionPauseDetailsModule,
        RnsAuctionTermsMasterModule,
        RnsAuctionTermsBodyMasterModule,
        RnsRnsFileUploadModule
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class P3EntityModule {}
