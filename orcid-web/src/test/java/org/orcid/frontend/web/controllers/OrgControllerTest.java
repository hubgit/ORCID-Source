package org.orcid.frontend.web.controllers;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.orcid.core.locale.LocaleManager;
import org.orcid.core.manager.OrcidProfileManager;
import org.orcid.core.oauth.OrcidProfileUserDetails;
import org.orcid.core.security.OrcidWebRole;
import org.orcid.frontend.web.util.BaseControllerTest;
import org.orcid.pojo.OrgDisambiguated;
import org.orcid.test.OrcidJUnit4ClassRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@RunWith(OrcidJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:orcid-core-context.xml", "classpath:orcid-frontend-web-servlet.xml", "classpath:statistics-core-context.xml" })
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class OrgControllerTest extends BaseControllerTest {
    
        private static final List<String> DATA_FILES = Arrays.asList("/data/EmptyEntityData.xml", "/data/SecurityQuestionEntityData.xml",
                "/data/SourceClientDetailsEntityData.xml", "/data/ProfileEntityData.xml", "/data/ClientDetailsEntityData.xml",
                "/data/Oauth2TokenDetailsData.xml", "/data/OrgsEntityData.xml", "/data/OrgAffiliationEntityData.xml", "/data/RecordNameEntityData.xml");

        @Mock
        private HttpServletRequest servletRequest;
        
        @Resource
        protected OrcidProfileManager orcidProfileManager;
        
        @Resource
        private OrgController orgController;
        
        @Override
        protected Authentication getAuthentication() {
            orcidProfile = orcidProfileManager.retrieveOrcidProfile("4444-4444-4444-4443");
            List<OrcidWebRole> roles = Arrays.asList(OrcidWebRole.ROLE_USER);
            OrcidProfileUserDetails details = new OrcidProfileUserDetails(orcidProfile.retrieveOrcidPath(),
                    orcidProfile.getOrcidBio().getContactDetails().retrievePrimaryEmail().getValue(), null, roles);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("4444-4444-4444-4443", details.getPassword(), roles);
            auth.setDetails(details);
            return auth;
        }

        @Before
        public void initMocks() {
            
        }
        
        @BeforeClass
        public static void beforeClass() throws Exception {
            initDBUnitData(DATA_FILES);
        }

        @AfterClass
        public static void afterClass() throws Exception {
            removeDBUnitData(Lists.reverse(DATA_FILES));
        }
        
        @Test
        public void testFindBySourceTypeAndSourceId(){
            ResponseEntity<OrgDisambiguated> o = orgController.getDisambiguatedOrg("WDB", "abc456");
            
            assertEquals("abc456",o.getBody().getSourceId());
            assertEquals("WDB",o.getBody().getSourceType());
            assertEquals("London",o.getBody().getCity());
            assertEquals("An Institution",o.getBody().getValue());
            assertEquals("GB",o.getBody().getCountry());
            assertEquals(200,o.getStatusCodeValue());
            
            ResponseEntity<OrgDisambiguated> o2 = orgController.getDisambiguatedOrg("no","no");
            assertEquals(404,o2.getStatusCodeValue());

        }
}
