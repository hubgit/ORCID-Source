package org.orcid.api.publicV3.server;

import static org.orcid.core.api.OrcidApiConstants.ACTIVITIES;
import static org.orcid.core.api.OrcidApiConstants.ADDRESS;
import static org.orcid.core.api.OrcidApiConstants.BIOGRAPHY;
import static org.orcid.core.api.OrcidApiConstants.BULK_WORKS;
import static org.orcid.core.api.OrcidApiConstants.CLIENT_PATH;
import static org.orcid.core.api.OrcidApiConstants.CSV_SEARCH_PATH;
import static org.orcid.core.api.OrcidApiConstants.DISTINCTION;
import static org.orcid.core.api.OrcidApiConstants.DISTINCTIONS;
import static org.orcid.core.api.OrcidApiConstants.DISTINCTION_SUMMARY;
import static org.orcid.core.api.OrcidApiConstants.EDUCATION;
import static org.orcid.core.api.OrcidApiConstants.EDUCATIONS;
import static org.orcid.core.api.OrcidApiConstants.EDUCATION_SUMMARY;
import static org.orcid.core.api.OrcidApiConstants.EMAIL;
import static org.orcid.core.api.OrcidApiConstants.EMPLOYMENT;
import static org.orcid.core.api.OrcidApiConstants.EMPLOYMENTS;
import static org.orcid.core.api.OrcidApiConstants.EMPLOYMENT_SUMMARY;
import static org.orcid.core.api.OrcidApiConstants.EXTERNAL_IDENTIFIERS;
import static org.orcid.core.api.OrcidApiConstants.FUNDING;
import static org.orcid.core.api.OrcidApiConstants.FUNDINGS;
import static org.orcid.core.api.OrcidApiConstants.FUNDING_SUMMARY;
import static org.orcid.core.api.OrcidApiConstants.INVITED_POSITION;
import static org.orcid.core.api.OrcidApiConstants.INVITED_POSITIONS;
import static org.orcid.core.api.OrcidApiConstants.INVITED_POSITION_SUMMARY;
import static org.orcid.core.api.OrcidApiConstants.JSON_LD;
import static org.orcid.core.api.OrcidApiConstants.KEYWORDS;
import static org.orcid.core.api.OrcidApiConstants.MEMBERSHIP;
import static org.orcid.core.api.OrcidApiConstants.MEMBERSHIPS;
import static org.orcid.core.api.OrcidApiConstants.MEMBERSHIP_SUMMARY;
import static org.orcid.core.api.OrcidApiConstants.ORCID_JSON;
import static org.orcid.core.api.OrcidApiConstants.ORCID_XML;
import static org.orcid.core.api.OrcidApiConstants.OTHER_NAMES;
import static org.orcid.core.api.OrcidApiConstants.PEER_REVIEW;
import static org.orcid.core.api.OrcidApiConstants.PEER_REVIEWS;
import static org.orcid.core.api.OrcidApiConstants.PEER_REVIEW_SUMMARY;
import static org.orcid.core.api.OrcidApiConstants.PERSON;
import static org.orcid.core.api.OrcidApiConstants.PERSONAL_DETAILS;
import static org.orcid.core.api.OrcidApiConstants.PUB_STATUS_PATH;
import static org.orcid.core.api.OrcidApiConstants.PUTCODE;
import static org.orcid.core.api.OrcidApiConstants.QUALIFICATION;
import static org.orcid.core.api.OrcidApiConstants.QUALIFICATIONS;
import static org.orcid.core.api.OrcidApiConstants.QUALIFICATION_SUMMARY;
import static org.orcid.core.api.OrcidApiConstants.RESEARCHER_URLS;
import static org.orcid.core.api.OrcidApiConstants.RESEARCH_RESOURCE;
import static org.orcid.core.api.OrcidApiConstants.RESEARCH_RESOURCES;
import static org.orcid.core.api.OrcidApiConstants.RESEARCH_RESOURCE_SUMMARY;
import static org.orcid.core.api.OrcidApiConstants.SEARCH_PATH;
import static org.orcid.core.api.OrcidApiConstants.SERVICE;
import static org.orcid.core.api.OrcidApiConstants.SERVICES;
import static org.orcid.core.api.OrcidApiConstants.SERVICE_SUMMARY;
import static org.orcid.core.api.OrcidApiConstants.STATUS_PATH;
import static org.orcid.core.api.OrcidApiConstants.TEXT_CSV;
import static org.orcid.core.api.OrcidApiConstants.VND_ORCID_JSON;
import static org.orcid.core.api.OrcidApiConstants.VND_ORCID_XML;
import static org.orcid.core.api.OrcidApiConstants.WORK;
import static org.orcid.core.api.OrcidApiConstants.WORKS;
import static org.orcid.core.api.OrcidApiConstants.WORK_SUMMARY;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.orcid.api.common.swagger.SwaggerUIBuilder;
import org.orcid.api.publicV3.server.delegator.PublicV3ApiServiceDelegator;
import org.orcid.core.api.OrcidApiConstants;
import org.orcid.jaxb.model.groupid_rc1.GroupIdRecord;
import org.orcid.jaxb.model.message.ScopeConstants;
import org.orcid.jaxb.model.v3.release.record.Distinction;
import org.orcid.jaxb.model.v3.release.record.Education;
import org.orcid.jaxb.model.v3.release.record.Employment;
import org.orcid.jaxb.model.v3.release.record.Funding;
import org.orcid.jaxb.model.v3.release.record.InvitedPosition;
import org.orcid.jaxb.model.v3.release.record.Membership;
import org.orcid.jaxb.model.v3.release.record.OtherName;
import org.orcid.jaxb.model.v3.release.record.PeerReview;
import org.orcid.jaxb.model.v3.release.record.PersonExternalIdentifier;
import org.orcid.jaxb.model.v3.release.record.Qualification;
import org.orcid.jaxb.model.v3.release.record.Record;
import org.orcid.jaxb.model.v3.release.record.ResearchResource;
import org.orcid.jaxb.model.v3.release.record.ResearcherUrl;
import org.orcid.jaxb.model.v3.release.record.Service;
import org.orcid.jaxb.model.v3.release.record.Work;
import org.orcid.jaxb.model.v3.release.record.WorkBulk;
import org.orcid.jaxb.model.v3.release.record.summary.ActivitiesSummary;
import org.orcid.jaxb.model.v3.release.record.summary.DistinctionSummary;
import org.orcid.jaxb.model.v3.release.record.summary.Distinctions;
import org.orcid.jaxb.model.v3.release.record.summary.EducationSummary;
import org.orcid.jaxb.model.v3.release.record.summary.Educations;
import org.orcid.jaxb.model.v3.release.record.summary.EmploymentSummary;
import org.orcid.jaxb.model.v3.release.record.summary.FundingSummary;
import org.orcid.jaxb.model.v3.release.record.summary.Fundings;
import org.orcid.jaxb.model.v3.release.record.summary.InvitedPositionSummary;
import org.orcid.jaxb.model.v3.release.record.summary.InvitedPositions;
import org.orcid.jaxb.model.v3.release.record.summary.MembershipSummary;
import org.orcid.jaxb.model.v3.release.record.summary.Memberships;
import org.orcid.jaxb.model.v3.release.record.summary.PeerReviewSummary;
import org.orcid.jaxb.model.v3.release.record.summary.PeerReviews;
import org.orcid.jaxb.model.v3.release.record.summary.QualificationSummary;
import org.orcid.jaxb.model.v3.release.record.summary.Qualifications;
import org.orcid.jaxb.model.v3.release.record.summary.ResearchResourceSummary;
import org.orcid.jaxb.model.v3.release.record.summary.ResearchResources;
import org.orcid.jaxb.model.v3.release.record.summary.ServiceSummary;
import org.orcid.jaxb.model.v3.release.record.summary.Services;
import org.orcid.jaxb.model.v3.release.record.summary.WorkSummary;
import org.orcid.jaxb.model.v3.release.record.summary.Works;
import org.springframework.beans.factory.annotation.Value;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;
import io.swagger.annotations.ExternalDocs;

/**
 * 
 * @author Angel Montenegro
 * 
 */
@Api("Development Public API v3.0")
@Path("/v3.0")
public class PublicV3ApiServiceImplV3_0 {

    protected PublicV3ApiServiceDelegator<Distinction, Education, Employment, PersonExternalIdentifier, InvitedPosition, Funding, GroupIdRecord, Membership, OtherName, PeerReview, Qualification, ResearcherUrl, Service, Work> serviceDelegator;

    @Value("${org.orcid.core.baseUri}")
    protected String baseUri;

    @Value("${org.orcid.core.pubBaseUri}")
    protected String pubBaseUri;
    
    @Context
    private HttpServletRequest httpRequest;

    public void setServiceDelegator(
            PublicV3ApiServiceDelegator<Distinction, Education, Employment, PersonExternalIdentifier, InvitedPosition, Funding, GroupIdRecord, Membership, OtherName, PeerReview, Qualification, ResearcherUrl, Service, Work> serviceDelegator) {
        this.serviceDelegator = serviceDelegator;
    }

    /**
     * Serves the Swagger UI HTML page
     * 
     * @return a 200 response containing the HTML
     */
    @GET
    @Produces(value = { MediaType.TEXT_HTML })
    @Path("/")
    @ApiOperation( nickname="viewSwaggerv3", value = "Fetch the HTML swagger UI interface", hidden = true)
    public Response viewSwagger() {
        return new SwaggerUIBuilder().buildSwaggerHTML(baseUri, pubBaseUri, false);
    }

    @GET
    @Produces(value = { MediaType.TEXT_PLAIN })
    @Path(STATUS_PATH)
    @ApiOperation( nickname="viewStatusTextv3", value = "Check the server status", response = String.class)
    public Response viewStatusText() {
        return serviceDelegator.viewStatusText();
    }
    
    @GET
    @Produces(value = { MediaType.APPLICATION_JSON })
    @Path(PUB_STATUS_PATH)
    public Response viewStatusJson() {
        httpRequest.setAttribute("isMonitoring", true);
        return serviceDelegator.viewStatus();
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(ACTIVITIES)
    @ApiOperation( nickname="viewActivitiesv3", value = "Fetch all Activities", response = ActivitiesSummary.class)
    public Response viewActivities(@PathParam("orcid") String orcid, @Context HttpServletRequest httpRequest) {
        return serviceDelegator.viewActivities(orcid);
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON,
            OrcidApiConstants.APPLICATION_CITEPROC })
    @Path(WORK + PUTCODE)
    @ApiOperation( nickname="viewWorkv3", value = "Fetch a Work", notes = "More notes about this method", response = Work.class)
    public Response viewWork(@PathParam("orcid") String orcid, @PathParam("putCode") Long putCode, @Context HttpServletRequest httpRequest) {
        if (OrcidApiConstants.APPLICATION_CITEPROC.equals(httpRequest.getHeader("Accept")))
            return serviceDelegator.viewWorkCitation(orcid, putCode);
        return serviceDelegator.viewWork(orcid, putCode);
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(WORK_SUMMARY + PUTCODE)
    @ApiOperation( nickname="viewWorkSummaryv3", value = "Fetch a Work Summary", response = WorkSummary.class)
    public Response viewWorkSummary(@PathParam("orcid") String orcid, @PathParam("putCode") Long putCode) {
        return serviceDelegator.viewWorkSummary(orcid, putCode);
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(WORKS)
    @ApiOperation( nickname="viewWorksv3", value = "Fetch all works", response = Works.class)
    public Response viewWorks(@PathParam("orcid") String orcid) {
        return serviceDelegator.viewWorks(orcid);
    }
    
    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(BULK_WORKS)
    @ApiOperation( nickname="viewSpecifiedWorksv3", value = "Fetch specified works", response = WorkBulk.class)
    public Response viewSpecifiedWorks(@PathParam("orcid") String orcid, @PathParam("putCodes") String putCodes) {
        return serviceDelegator.viewBulkWorks(orcid, putCodes);
    }
    
    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(FUNDING + PUTCODE)
    @ApiOperation( nickname="viewFundingv3", value = "Fetch a Funding", response = Funding.class)
    public Response viewFunding(@PathParam("orcid") String orcid, @PathParam("putCode") Long putCode) {
        return serviceDelegator.viewFunding(orcid, putCode);
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(FUNDING_SUMMARY + PUTCODE)
    @ApiOperation( nickname="viewFundingSummaryv3", value = "Fetch a Funding Summary", response = FundingSummary.class)
    public Response viewFundingSummary(@PathParam("orcid") String orcid, @PathParam("putCode") Long putCode) {
        return serviceDelegator.viewFundingSummary(orcid, putCode);
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(FUNDINGS)
    @ApiOperation( nickname="viewFundingsv3", value = "Fetch all fundings", response = Fundings.class)    
    public Response viewFundings(@PathParam("orcid") String orcid) {        
        return serviceDelegator.viewFundings(orcid);
    }
    
    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(EDUCATION + PUTCODE)
    @ApiOperation( nickname="viewEducationv3", value = "Fetch an Education", response = Education.class)
    public Response viewEducation(@PathParam("orcid") String orcid, @PathParam("putCode") Long putCode) {
        return serviceDelegator.viewEducation(orcid, putCode);
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(EDUCATION_SUMMARY + PUTCODE)
    @ApiOperation( nickname="viewEducationSummaryv3", value = "Fetch an Education Summary", response = EducationSummary.class)
    public Response viewEducationSummary(@PathParam("orcid") String orcid, @PathParam("putCode") Long putCode) {
        return serviceDelegator.viewEducationSummary(orcid, putCode);
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(EDUCATIONS)
    @ApiOperation( nickname="viewEducationsv3", value = "Fetch all educations", response = Educations.class)    
    public Response viewEducations(@PathParam("orcid") String orcid) {
        return serviceDelegator.viewEducations(orcid);
    }
    
    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(EMPLOYMENT + PUTCODE)
    @ApiOperation( nickname="viewEmploymentv3", value = "Fetch an Employment", notes = "Retrive a specific education representation", response = Employment.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Employment found", response = Employment.class),
            @ApiResponse(code = 404, message = "Employment not found") })
    public Response viewEmployment(@PathParam("orcid") String orcid, @PathParam("putCode") Long putCode) {
        return serviceDelegator.viewEmployment(orcid, putCode);
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(EMPLOYMENT_SUMMARY + PUTCODE)
    @ApiOperation( nickname="viewEmploymentSummaryv3", value = "Fetch an Employment Summary", response = EmploymentSummary.class)
    public Response viewEmploymentSummary(@PathParam("orcid") String orcid, @PathParam("putCode") Long putCode) {
        return serviceDelegator.viewEmploymentSummary(orcid, putCode);
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(EMPLOYMENTS)
    @ApiOperation( nickname="viewEmploymentsv3", value = "Fetch all employments", response = Fundings.class)    
    public Response viewEmployments(@PathParam("orcid") String orcid) {
        return serviceDelegator.viewEmployments(orcid);
    }
    
    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(PEER_REVIEW + PUTCODE)
    @ApiOperation( nickname="viewPeerReviewv3", value = "Fetch a Peer Review", response = PeerReview.class)
    public Response viewPeerReview(@PathParam("orcid") String orcid, @PathParam("putCode") Long putCode) {
        return serviceDelegator.viewPeerReview(orcid, putCode);
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(PEER_REVIEW_SUMMARY + PUTCODE)
    @ApiOperation( nickname="viewPeerReviewSummaryv3", value = "Fetch a Peer Review Summary", response = PeerReviewSummary.class)
    public Response viewPeerReviewSummary(@PathParam("orcid") String orcid, @PathParam("putCode") Long putCode) {
        return serviceDelegator.viewPeerReviewSummary(orcid, putCode);
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(PEER_REVIEWS)
    @ApiOperation( nickname="viewPeerReviewsv3", value = "Fetch all peer reviews", response = PeerReviews.class)    
    public Response viewPeerReviews(@PathParam("orcid") String orcid) {
        return serviceDelegator.viewPeerReviews(orcid);
    }
    
    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(RESEARCHER_URLS)
    @ApiOperation( nickname="viewResearcherUrlsv3", value = "Fetch all researcher urls for an ORCID ID")
    public Response viewResearcherUrls(@PathParam("orcid") String orcid) {
        return serviceDelegator.viewResearcherUrls(orcid);
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(RESEARCHER_URLS + PUTCODE)
    @ApiOperation( nickname="viewResearcherUrlv3", value = "Fetch one researcher url for an ORCID ID")
    public Response viewResearcherUrl(@PathParam("orcid") String orcid, @PathParam("putCode") String putCode) {
        return serviceDelegator.viewResearcherUrl(orcid, Long.valueOf(putCode));
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(EMAIL)
    @ApiOperation( nickname="viewEmailsv3", value = "Fetch all emails for an ORCID ID")
    public Response viewEmails(@PathParam("orcid") String orcid) {
        return serviceDelegator.viewEmails(orcid);
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(PERSONAL_DETAILS)
    @ApiOperation( nickname="viewPersonalDetailsv3", value = "Fetch personal details for an ORCID ID")
    public Response viewPersonalDetails(@PathParam("orcid") String orcid) {
        return serviceDelegator.viewPersonalDetails(orcid);
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(OTHER_NAMES)
    @ApiOperation( nickname="viewOtherNamesv3", value = "Fetch Other names")
    public Response viewOtherNames(@PathParam("orcid") String orcid) {
        return serviceDelegator.viewOtherNames(orcid);
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(OTHER_NAMES + PUTCODE)
    @ApiOperation( nickname="viewOtherNamev3", value = "Fetch Other name")
    public Response viewOtherName(@PathParam("orcid") String orcid, @PathParam("putCode") String putCode) {
        return serviceDelegator.viewOtherName(orcid, Long.valueOf(putCode));
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(EXTERNAL_IDENTIFIERS)
    @ApiOperation( nickname="viewExternalIdentifiersv3", value = "Fetch external identifiers", authorizations = {
            @Authorization(value = "orcid_two_legs", scopes = { @AuthorizationScope(scope = ScopeConstants.READ_PUBLIC, description = "you need this") }) })
    public Response viewExternalIdentifiers(@PathParam("orcid") String orcid) {
        return serviceDelegator.viewExternalIdentifiers(orcid);
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(EXTERNAL_IDENTIFIERS + PUTCODE)
    @ApiOperation( nickname="viewExternalIdentifierv3", value = "Fetch external identifier")
    public Response viewExternalIdentifier(@PathParam("orcid") String orcid, @PathParam("putCode") String putCode) {
        return serviceDelegator.viewExternalIdentifier(orcid, Long.valueOf(putCode));
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(KEYWORDS)
    @ApiOperation( nickname="viewKeywordsv3", value = "Fetch keywords")
    public Response viewKeywords(@PathParam("orcid") String orcid) {
        return serviceDelegator.viewKeywords(orcid);
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(KEYWORDS + PUTCODE)
    @ApiOperation( nickname="viewKeywordv3", value = "Fetch keyword")
    public Response viewKeyword(@PathParam("orcid") String orcid, @PathParam("putCode") String putCode) {
        return serviceDelegator.viewKeyword(orcid, Long.valueOf(putCode));
    }
           
    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(ADDRESS)
    @ApiOperation( nickname="viewAddressesv3", value = "Fetch all addresses")
    public Response viewAddresses(@PathParam("orcid") String orcid) {
        return serviceDelegator.viewAddresses(orcid);
    }
            
    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(ADDRESS + PUTCODE)
    @ApiOperation( nickname="viewAddressv3", value = "Fetch an address")
    public Response viewAddress(@PathParam("orcid") String orcid, @PathParam("putCode") String putCode) {
        return serviceDelegator.viewAddress(orcid, Long.valueOf(putCode));
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(BIOGRAPHY)
    @ApiOperation( nickname="viewBiographyv3", value = "Get biography details")
    public Response viewBiography(@PathParam("orcid") String orcid) {
        return serviceDelegator.viewBiography(orcid);
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(PERSON)
    @ApiOperation( nickname="viewPersonv3", value = "Fetch person details")
    public Response viewPerson(@PathParam("orcid") String orcid) {
        return serviceDelegator.viewPerson(orcid);
    }
    
    //Record 
    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON, JSON_LD })
    @Path(OrcidApiConstants.RECORD_SIMPLE)
    @ApiOperation( nickname="viewRecordv3", value = "Fetch record details", response = Record.class)
    @ExternalDocs(value = "Record XML Schema", url = "https://raw.githubusercontent.com/ORCID/ORCID-Source/master/orcid-model/src/main/resources/record_2.0/record-2.0.xsd")
    public Response viewRecord(@PathParam("orcid") String orcid) {
        return serviceDelegator.viewRecord(orcid);
    }
    
    //Record 
    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(OrcidApiConstants.RECORD_RECORD)
    @ApiOperation( nickname="viewRecordRecordv3", value = "Fetch record details2", response = Record.class, hidden=true)
    @ExternalDocs(value = "Record XML Schema", url = "https://raw.githubusercontent.com/ORCID/ORCID-Source/master/orcid-model/src/main/resources/record_2.0/record-2.0.xsd")
    public Response viewRecordRecord(@PathParam("orcid") String orcid) {
        return serviceDelegator.viewRecord(orcid);
    }
    
    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(SEARCH_PATH)
    @ApiOperation( nickname="searchByQueryv3", value = "Search records")
    @ExternalDocs(value = "Record XML Schema", url = "https://raw.githubusercontent.com/ORCID/ORCID-Source/master/orcid-model/src/main/resources/record_2.0/search-2.0.xsd")
    public Response searchByQuery(@QueryParam("q") @DefaultValue("") String query, @Context UriInfo uriInfo) {
        Map<String, List<String>> solrParams = uriInfo.getQueryParameters();
        Response jsonQueryResults = serviceDelegator.searchByQuery(solrParams);
        return jsonQueryResults;
    }
    
    @GET
    @Produces(TEXT_CSV)
    @Path(CSV_SEARCH_PATH)
    @ApiOperation( nickname="searchByQueryv3", value = "Search records")
    @ExternalDocs(value = "Record XML Schema", url = "https://raw.githubusercontent.com/ORCID/ORCID-Source/master/orcid-model/src/main/resources/record_2.0/search-2.0.xsd")
    public Response searchByQueryCSV(@QueryParam("q") @DefaultValue("") String query, @Context UriInfo uriInfo) {
        Map<String, List<String>> solrParams = uriInfo.getQueryParameters();
        Response csvQueryResults = serviceDelegator.searchByQueryCSV(solrParams);
        return csvQueryResults;
    }

    @GET
    @Path(CLIENT_PATH)
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @ApiOperation( nickname="viewClientv3", value = "Fetch client details")
    @ExternalDocs(value = "Record XML Schema", url = "https://raw.githubusercontent.com/ORCID/ORCID-Source/master/orcid-model/src/main/resources/record_2.0/client-2.0.xsd")
    public Response viewClient(@PathParam("client_id") String clientId) {
        return serviceDelegator.viewClient(clientId);
    }
    
    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(DISTINCTION + PUTCODE)
    @ApiOperation(nickname = "viewDistinctionv3", value = "Fetch an Distinction", authorizations = {
            @Authorization(value = "orcid_auth", scopes = { @AuthorizationScope(scope = ScopeConstants.READ_LIMITED, description = "you need this") }) })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Distinction.class),
            @ApiResponse(code = 404, message = "putCode not found", response = String.class),
            @ApiResponse(code = 400, message = "Invalid putCode or ORCID ID", response = String.class) })
    public Response viewDistinction(@PathParam("orcid") String orcid, @PathParam("putCode") String putCode) {
        return serviceDelegator.viewDistinction(orcid, Long.valueOf(putCode));
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(DISTINCTIONS)
    @ApiOperation(nickname = "viewDistinctionsv3", value = "Fetch all distinctions", response = Distinctions.class, authorizations = {
            @Authorization(value = "orcid_auth", scopes = { @AuthorizationScope(scope = ScopeConstants.READ_LIMITED, description = "you need this") }) })
    public Response viewDistinctions(@PathParam("orcid") String orcid) {
        return serviceDelegator.viewDistinctions(orcid);
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(DISTINCTION_SUMMARY + PUTCODE)
    @ApiOperation(nickname = "viewDistinctionSummaryv3", value = "Fetch an Distinction summary", response = DistinctionSummary.class, authorizations = {
            @Authorization(value = "orcid_auth", scopes = { @AuthorizationScope(scope = ScopeConstants.READ_LIMITED, description = "you need this") }) })
    public Response viewDistinctionSummary(@PathParam("orcid") String orcid, @PathParam("putCode") String putCode) {
        return serviceDelegator.viewDistinctionSummary(orcid, Long.valueOf(putCode));
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(INVITED_POSITION + PUTCODE)
    @ApiOperation(nickname = "viewInvitedPositionv3", value = "Fetch an InvitedPosition", authorizations = {
            @Authorization(value = "orcid_auth", scopes = { @AuthorizationScope(scope = ScopeConstants.READ_LIMITED, description = "you need this") }) })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = InvitedPosition.class),
            @ApiResponse(code = 404, message = "putCode not found", response = String.class),
            @ApiResponse(code = 400, message = "Invalid putCode or ORCID ID", response = String.class) })
    public Response viewInvitedPosition(@PathParam("orcid") String orcid, @PathParam("putCode") String putCode) {
        return serviceDelegator.viewInvitedPosition(orcid, Long.valueOf(putCode));
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(INVITED_POSITIONS)
    @ApiOperation(nickname = "viewInvitedPositionsv3", value = "Fetch all invitedPositions", response = InvitedPositions.class, authorizations = {
            @Authorization(value = "orcid_auth", scopes = { @AuthorizationScope(scope = ScopeConstants.READ_LIMITED, description = "you need this") }) })
    public Response viewInvitedPositions(@PathParam("orcid") String orcid) {
        return serviceDelegator.viewInvitedPositions(orcid);
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(INVITED_POSITION_SUMMARY + PUTCODE)
    @ApiOperation(nickname = "viewInvitedPositionSummaryv3", value = "Fetch an InvitedPosition summary", response = InvitedPositionSummary.class, authorizations = {
            @Authorization(value = "orcid_auth", scopes = { @AuthorizationScope(scope = ScopeConstants.READ_LIMITED, description = "you need this") }) })
    public Response viewInvitedPositionSummary(@PathParam("orcid") String orcid, @PathParam("putCode") String putCode) {
        return serviceDelegator.viewInvitedPositionSummary(orcid, Long.valueOf(putCode));
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(MEMBERSHIP + PUTCODE)
    @ApiOperation(nickname = "viewMembershipv3", value = "Fetch an Membership", authorizations = {
            @Authorization(value = "orcid_auth", scopes = { @AuthorizationScope(scope = ScopeConstants.READ_LIMITED, description = "you need this") }) })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Membership.class),
            @ApiResponse(code = 404, message = "putCode not found", response = String.class),
            @ApiResponse(code = 400, message = "Invalid putCode or ORCID ID", response = String.class) })
    public Response viewMembership(@PathParam("orcid") String orcid, @PathParam("putCode") String putCode) {
        return serviceDelegator.viewMembership(orcid, Long.valueOf(putCode));
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(MEMBERSHIPS)
    @ApiOperation(nickname = "viewMembershipsv3", value = "Fetch all memberships", response = Memberships.class, authorizations = {
            @Authorization(value = "orcid_auth", scopes = { @AuthorizationScope(scope = ScopeConstants.READ_LIMITED, description = "you need this") }) })
    public Response viewMemberships(@PathParam("orcid") String orcid) {
        return serviceDelegator.viewMemberships(orcid);
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(MEMBERSHIP_SUMMARY + PUTCODE)
    @ApiOperation(nickname = "viewMembershipSummaryv3", value = "Fetch an Membership summary", response = MembershipSummary.class, authorizations = {
            @Authorization(value = "orcid_auth", scopes = { @AuthorizationScope(scope = ScopeConstants.READ_LIMITED, description = "you need this") }) })
    public Response viewMembershipSummary(@PathParam("orcid") String orcid, @PathParam("putCode") String putCode) {
        return serviceDelegator.viewMembershipSummary(orcid, Long.valueOf(putCode));
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(QUALIFICATION + PUTCODE)
    @ApiOperation(nickname = "viewQualificationv3", value = "Fetch an Qualification", authorizations = {
            @Authorization(value = "orcid_auth", scopes = { @AuthorizationScope(scope = ScopeConstants.READ_LIMITED, description = "you need this") }) })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Qualification.class),
            @ApiResponse(code = 404, message = "putCode not found", response = String.class),
            @ApiResponse(code = 400, message = "Invalid putCode or ORCID ID", response = String.class) })
    public Response viewQualification(@PathParam("orcid") String orcid, @PathParam("putCode") String putCode) {
        return serviceDelegator.viewQualification(orcid, Long.valueOf(putCode));
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(QUALIFICATIONS)
    @ApiOperation(nickname = "viewQualificationsv3", value = "Fetch all qualifications", response = Qualifications.class, authorizations = {
            @Authorization(value = "orcid_auth", scopes = { @AuthorizationScope(scope = ScopeConstants.READ_LIMITED, description = "you need this") }) })
    public Response viewQualifications(@PathParam("orcid") String orcid) {
        return serviceDelegator.viewQualifications(orcid);
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(QUALIFICATION_SUMMARY + PUTCODE)
    @ApiOperation(nickname = "viewQualificationSummaryv3", value = "Fetch an Qualification summary", response = QualificationSummary.class, authorizations = {
            @Authorization(value = "orcid_auth", scopes = { @AuthorizationScope(scope = ScopeConstants.READ_LIMITED, description = "you need this") }) })
    public Response viewQualificationSummary(@PathParam("orcid") String orcid, @PathParam("putCode") String putCode) {
        return serviceDelegator.viewQualificationSummary(orcid, Long.valueOf(putCode));
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(SERVICE + PUTCODE)
    @ApiOperation(nickname = "viewServicev3", value = "Fetch an Service", authorizations = {
            @Authorization(value = "orcid_auth", scopes = { @AuthorizationScope(scope = ScopeConstants.READ_LIMITED, description = "you need this") }) })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Service.class),
            @ApiResponse(code = 404, message = "putCode not found", response = String.class),
            @ApiResponse(code = 400, message = "Invalid putCode or ORCID ID", response = String.class) })
    public Response viewService(@PathParam("orcid") String orcid, @PathParam("putCode") String putCode) {
        return serviceDelegator.viewService(orcid, Long.valueOf(putCode));
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(SERVICES)
    @ApiOperation(nickname = "viewServicesv3", value = "Fetch all services", response = Services.class, authorizations = {
            @Authorization(value = "orcid_auth", scopes = { @AuthorizationScope(scope = ScopeConstants.READ_LIMITED, description = "you need this") }) })
    public Response viewServices(@PathParam("orcid") String orcid) {
        return serviceDelegator.viewServices(orcid);
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(SERVICE_SUMMARY + PUTCODE)
    @ApiOperation(nickname = "viewServiceSummaryv3", value = "Fetch an Service summary", response = ServiceSummary.class, authorizations = {
            @Authorization(value = "orcid_auth", scopes = { @AuthorizationScope(scope = ScopeConstants.READ_LIMITED, description = "you need this") }) })
    public Response viewServiceSummary(@PathParam("orcid") String orcid, @PathParam("putCode") String putCode) {
        return serviceDelegator.viewServiceSummary(orcid, Long.valueOf(putCode));
    }
    
    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(RESEARCH_RESOURCE + PUTCODE)
    @ApiOperation(nickname = "viewResearchResourcev3", value = "Fetch a Research Resource", authorizations = {
            @Authorization(value = "orcid_auth", scopes = { @AuthorizationScope(scope = ScopeConstants.READ_LIMITED, description = "you need this") }) })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = ResearchResource.class),
            @ApiResponse(code = 404, message = "putCode not found", response = String.class),
            @ApiResponse(code = 400, message = "Invalid putCode or ORCID ID", response = String.class) })
    public Response viewResearchResource(@PathParam("orcid") String orcid, @PathParam("putCode") String putCode) {
        return serviceDelegator.viewResearchResource(orcid, Long.valueOf(putCode));
    }

    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(RESEARCH_RESOURCES)
    @ApiOperation(nickname = "viewResearchResourcesv3", value = "Fetch all Research Resources", response = ResearchResources.class, authorizations = {
            @Authorization(value = "orcid_auth", scopes = { @AuthorizationScope(scope = ScopeConstants.READ_LIMITED, description = "you need this") }) })
    public Response viewResearchResources(@PathParam("orcid") String orcid) {
        return serviceDelegator.viewResearchResources(orcid);
    }
    
    @GET
    @Produces(value = { VND_ORCID_XML, ORCID_XML, MediaType.APPLICATION_XML, VND_ORCID_JSON, ORCID_JSON, MediaType.APPLICATION_JSON })
    @Path(RESEARCH_RESOURCE_SUMMARY + PUTCODE)
    @ApiOperation(nickname = "viewResearchResourceSummaryv3", value = "Fetch a Research Resource summary", response = ResearchResourceSummary.class, authorizations = {
            @Authorization(value = "orcid_auth", scopes = { @AuthorizationScope(scope = ScopeConstants.READ_LIMITED, description = "you need this") }) })
    public Response viewResearchResourceSummary(@PathParam("orcid") String orcid, @PathParam("putCode") String putCode) {
        return serviceDelegator.viewResearchResourceSummary(orcid, Long.valueOf(putCode));
    }
    
}
