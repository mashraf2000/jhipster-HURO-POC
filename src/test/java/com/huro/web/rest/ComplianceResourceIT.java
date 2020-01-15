package com.huro.web.rest;

import com.huro.HuroPocApp;
import com.huro.domain.Compliance;
import com.huro.repository.ComplianceRepository;
import com.huro.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.huro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ComplianceResource} REST controller.
 */
@SpringBootTest(classes = HuroPocApp.class)
public class ComplianceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ComplianceRepository complianceRepository;

    @Mock
    private ComplianceRepository complianceRepositoryMock;

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

    private MockMvc restComplianceMockMvc;

    private Compliance compliance;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComplianceResource complianceResource = new ComplianceResource(complianceRepository);
        this.restComplianceMockMvc = MockMvcBuilders.standaloneSetup(complianceResource)
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
    public static Compliance createEntity(EntityManager em) {
        Compliance compliance = new Compliance()
            .name(DEFAULT_NAME)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED);
        return compliance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Compliance createUpdatedEntity(EntityManager em) {
        Compliance compliance = new Compliance()
            .name(UPDATED_NAME)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);
        return compliance;
    }

    @BeforeEach
    public void initTest() {
        compliance = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompliance() throws Exception {
        int databaseSizeBeforeCreate = complianceRepository.findAll().size();

        // Create the Compliance
        restComplianceMockMvc.perform(post("/api/compliances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compliance)))
            .andExpect(status().isCreated());

        // Validate the Compliance in the database
        List<Compliance> complianceList = complianceRepository.findAll();
        assertThat(complianceList).hasSize(databaseSizeBeforeCreate + 1);
        Compliance testCompliance = complianceList.get(complianceList.size() - 1);
        assertThat(testCompliance.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompliance.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testCompliance.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void createComplianceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = complianceRepository.findAll().size();

        // Create the Compliance with an existing ID
        compliance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComplianceMockMvc.perform(post("/api/compliances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compliance)))
            .andExpect(status().isBadRequest());

        // Validate the Compliance in the database
        List<Compliance> complianceList = complianceRepository.findAll();
        assertThat(complianceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompliances() throws Exception {
        // Initialize the database
        complianceRepository.saveAndFlush(compliance);

        // Get all the complianceList
        restComplianceMockMvc.perform(get("/api/compliances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compliance.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCompliancesWithEagerRelationshipsIsEnabled() throws Exception {
        ComplianceResource complianceResource = new ComplianceResource(complianceRepositoryMock);
        when(complianceRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restComplianceMockMvc = MockMvcBuilders.standaloneSetup(complianceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restComplianceMockMvc.perform(get("/api/compliances?eagerload=true"))
        .andExpect(status().isOk());

        verify(complianceRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCompliancesWithEagerRelationshipsIsNotEnabled() throws Exception {
        ComplianceResource complianceResource = new ComplianceResource(complianceRepositoryMock);
            when(complianceRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restComplianceMockMvc = MockMvcBuilders.standaloneSetup(complianceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restComplianceMockMvc.perform(get("/api/compliances?eagerload=true"))
        .andExpect(status().isOk());

            verify(complianceRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCompliance() throws Exception {
        // Initialize the database
        complianceRepository.saveAndFlush(compliance);

        // Get the compliance
        restComplianceMockMvc.perform(get("/api/compliances/{id}", compliance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compliance.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompliance() throws Exception {
        // Get the compliance
        restComplianceMockMvc.perform(get("/api/compliances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompliance() throws Exception {
        // Initialize the database
        complianceRepository.saveAndFlush(compliance);

        int databaseSizeBeforeUpdate = complianceRepository.findAll().size();

        // Update the compliance
        Compliance updatedCompliance = complianceRepository.findById(compliance.getId()).get();
        // Disconnect from session so that the updates on updatedCompliance are not directly saved in db
        em.detach(updatedCompliance);
        updatedCompliance
            .name(UPDATED_NAME)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);

        restComplianceMockMvc.perform(put("/api/compliances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompliance)))
            .andExpect(status().isOk());

        // Validate the Compliance in the database
        List<Compliance> complianceList = complianceRepository.findAll();
        assertThat(complianceList).hasSize(databaseSizeBeforeUpdate);
        Compliance testCompliance = complianceList.get(complianceList.size() - 1);
        assertThat(testCompliance.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompliance.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testCompliance.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingCompliance() throws Exception {
        int databaseSizeBeforeUpdate = complianceRepository.findAll().size();

        // Create the Compliance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComplianceMockMvc.perform(put("/api/compliances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compliance)))
            .andExpect(status().isBadRequest());

        // Validate the Compliance in the database
        List<Compliance> complianceList = complianceRepository.findAll();
        assertThat(complianceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompliance() throws Exception {
        // Initialize the database
        complianceRepository.saveAndFlush(compliance);

        int databaseSizeBeforeDelete = complianceRepository.findAll().size();

        // Delete the compliance
        restComplianceMockMvc.perform(delete("/api/compliances/{id}", compliance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Compliance> complianceList = complianceRepository.findAll();
        assertThat(complianceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
