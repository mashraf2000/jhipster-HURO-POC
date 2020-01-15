package com.huro.web.rest;

import com.huro.HuroPocApp;
import com.huro.domain.OrganizationProfile;
import com.huro.repository.OrganizationProfileRepository;
import com.huro.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.huro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.huro.domain.enumeration.Language;
/**
 * Integration tests for the {@link OrganizationProfileResource} REST controller.
 */
@SpringBootTest(classes = HuroPocApp.class)
public class OrganizationProfileResourceIT {

    private static final Language DEFAULT_LANGUAGE = Language.FRENCH;
    private static final Language UPDATED_LANGUAGE = Language.ENGLISH;

    @Autowired
    private OrganizationProfileRepository organizationProfileRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restOrganizationProfileMockMvc;

    private OrganizationProfile organizationProfile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrganizationProfileResource organizationProfileResource = new OrganizationProfileResource(organizationProfileRepository);
        this.restOrganizationProfileMockMvc = MockMvcBuilders.standaloneSetup(organizationProfileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationProfile createEntity(EntityManager em) {
        OrganizationProfile organizationProfile = new OrganizationProfile()
            .language(DEFAULT_LANGUAGE);
        return organizationProfile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationProfile createUpdatedEntity(EntityManager em) {
        OrganizationProfile organizationProfile = new OrganizationProfile()
            .language(UPDATED_LANGUAGE);
        return organizationProfile;
    }

    @BeforeEach
    public void initTest() {
        organizationProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrganizationProfile() throws Exception {
        int databaseSizeBeforeCreate = organizationProfileRepository.findAll().size();

        // Create the OrganizationProfile
        restOrganizationProfileMockMvc.perform(post("/api/organization-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationProfile)))
            .andExpect(status().isCreated());

        // Validate the OrganizationProfile in the database
        List<OrganizationProfile> organizationProfileList = organizationProfileRepository.findAll();
        assertThat(organizationProfileList).hasSize(databaseSizeBeforeCreate + 1);
        OrganizationProfile testOrganizationProfile = organizationProfileList.get(organizationProfileList.size() - 1);
        assertThat(testOrganizationProfile.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
    }

    @Test
    @Transactional
    public void createOrganizationProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = organizationProfileRepository.findAll().size();

        // Create the OrganizationProfile with an existing ID
        organizationProfile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationProfileMockMvc.perform(post("/api/organization-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationProfile)))
            .andExpect(status().isBadRequest());

        // Validate the OrganizationProfile in the database
        List<OrganizationProfile> organizationProfileList = organizationProfileRepository.findAll();
        assertThat(organizationProfileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrganizationProfiles() throws Exception {
        // Initialize the database
        organizationProfileRepository.saveAndFlush(organizationProfile);

        // Get all the organizationProfileList
        restOrganizationProfileMockMvc.perform(get("/api/organization-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organizationProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }
    
    @Test
    @Transactional
    public void getOrganizationProfile() throws Exception {
        // Initialize the database
        organizationProfileRepository.saveAndFlush(organizationProfile);

        // Get the organizationProfile
        restOrganizationProfileMockMvc.perform(get("/api/organization-profiles/{id}", organizationProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(organizationProfile.getId().intValue()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrganizationProfile() throws Exception {
        // Get the organizationProfile
        restOrganizationProfileMockMvc.perform(get("/api/organization-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrganizationProfile() throws Exception {
        // Initialize the database
        organizationProfileRepository.saveAndFlush(organizationProfile);

        int databaseSizeBeforeUpdate = organizationProfileRepository.findAll().size();

        // Update the organizationProfile
        OrganizationProfile updatedOrganizationProfile = organizationProfileRepository.findById(organizationProfile.getId()).get();
        // Disconnect from session so that the updates on updatedOrganizationProfile are not directly saved in db
        em.detach(updatedOrganizationProfile);
        updatedOrganizationProfile
            .language(UPDATED_LANGUAGE);

        restOrganizationProfileMockMvc.perform(put("/api/organization-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrganizationProfile)))
            .andExpect(status().isOk());

        // Validate the OrganizationProfile in the database
        List<OrganizationProfile> organizationProfileList = organizationProfileRepository.findAll();
        assertThat(organizationProfileList).hasSize(databaseSizeBeforeUpdate);
        OrganizationProfile testOrganizationProfile = organizationProfileList.get(organizationProfileList.size() - 1);
        assertThat(testOrganizationProfile.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingOrganizationProfile() throws Exception {
        int databaseSizeBeforeUpdate = organizationProfileRepository.findAll().size();

        // Create the OrganizationProfile

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationProfileMockMvc.perform(put("/api/organization-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationProfile)))
            .andExpect(status().isBadRequest());

        // Validate the OrganizationProfile in the database
        List<OrganizationProfile> organizationProfileList = organizationProfileRepository.findAll();
        assertThat(organizationProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrganizationProfile() throws Exception {
        // Initialize the database
        organizationProfileRepository.saveAndFlush(organizationProfile);

        int databaseSizeBeforeDelete = organizationProfileRepository.findAll().size();

        // Delete the organizationProfile
        restOrganizationProfileMockMvc.perform(delete("/api/organization-profiles/{id}", organizationProfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrganizationProfile> organizationProfileList = organizationProfileRepository.findAll();
        assertThat(organizationProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
