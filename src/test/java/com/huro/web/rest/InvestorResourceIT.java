package com.huro.web.rest;

import com.huro.HuroPocApp;
import com.huro.domain.Investor;
import com.huro.repository.InvestorRepository;
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
 * Integration tests for the {@link InvestorResource} REST controller.
 */
@SpringBootTest(classes = HuroPocApp.class)
public class InvestorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private InvestorRepository investorRepository;

    @Mock
    private InvestorRepository investorRepositoryMock;

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

    private MockMvc restInvestorMockMvc;

    private Investor investor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvestorResource investorResource = new InvestorResource(investorRepository);
        this.restInvestorMockMvc = MockMvcBuilders.standaloneSetup(investorResource)
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
    public static Investor createEntity(EntityManager em) {
        Investor investor = new Investor()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .telephoneNumber(DEFAULT_TELEPHONE_NUMBER)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED);
        return investor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Investor createUpdatedEntity(EntityManager em) {
        Investor investor = new Investor()
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .telephoneNumber(UPDATED_TELEPHONE_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);
        return investor;
    }

    @BeforeEach
    public void initTest() {
        investor = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvestor() throws Exception {
        int databaseSizeBeforeCreate = investorRepository.findAll().size();

        // Create the Investor
        restInvestorMockMvc.perform(post("/api/investors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investor)))
            .andExpect(status().isCreated());

        // Validate the Investor in the database
        List<Investor> investorList = investorRepository.findAll();
        assertThat(investorList).hasSize(databaseSizeBeforeCreate + 1);
        Investor testInvestor = investorList.get(investorList.size() - 1);
        assertThat(testInvestor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInvestor.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testInvestor.getTelephoneNumber()).isEqualTo(DEFAULT_TELEPHONE_NUMBER);
        assertThat(testInvestor.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testInvestor.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testInvestor.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void createInvestorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = investorRepository.findAll().size();

        // Create the Investor with an existing ID
        investor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvestorMockMvc.perform(post("/api/investors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investor)))
            .andExpect(status().isBadRequest());

        // Validate the Investor in the database
        List<Investor> investorList = investorRepository.findAll();
        assertThat(investorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInvestors() throws Exception {
        // Initialize the database
        investorRepository.saveAndFlush(investor);

        // Get all the investorList
        restInvestorMockMvc.perform(get("/api/investors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(investor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].telephoneNumber").value(hasItem(DEFAULT_TELEPHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllInvestorsWithEagerRelationshipsIsEnabled() throws Exception {
        InvestorResource investorResource = new InvestorResource(investorRepositoryMock);
        when(investorRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restInvestorMockMvc = MockMvcBuilders.standaloneSetup(investorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restInvestorMockMvc.perform(get("/api/investors?eagerload=true"))
        .andExpect(status().isOk());

        verify(investorRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllInvestorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        InvestorResource investorResource = new InvestorResource(investorRepositoryMock);
            when(investorRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restInvestorMockMvc = MockMvcBuilders.standaloneSetup(investorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restInvestorMockMvc.perform(get("/api/investors?eagerload=true"))
        .andExpect(status().isOk());

            verify(investorRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getInvestor() throws Exception {
        // Initialize the database
        investorRepository.saveAndFlush(investor);

        // Get the investor
        restInvestorMockMvc.perform(get("/api/investors/{id}", investor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(investor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.telephoneNumber").value(DEFAULT_TELEPHONE_NUMBER))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvestor() throws Exception {
        // Get the investor
        restInvestorMockMvc.perform(get("/api/investors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvestor() throws Exception {
        // Initialize the database
        investorRepository.saveAndFlush(investor);

        int databaseSizeBeforeUpdate = investorRepository.findAll().size();

        // Update the investor
        Investor updatedInvestor = investorRepository.findById(investor.getId()).get();
        // Disconnect from session so that the updates on updatedInvestor are not directly saved in db
        em.detach(updatedInvestor);
        updatedInvestor
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .telephoneNumber(UPDATED_TELEPHONE_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);

        restInvestorMockMvc.perform(put("/api/investors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvestor)))
            .andExpect(status().isOk());

        // Validate the Investor in the database
        List<Investor> investorList = investorRepository.findAll();
        assertThat(investorList).hasSize(databaseSizeBeforeUpdate);
        Investor testInvestor = investorList.get(investorList.size() - 1);
        assertThat(testInvestor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInvestor.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testInvestor.getTelephoneNumber()).isEqualTo(UPDATED_TELEPHONE_NUMBER);
        assertThat(testInvestor.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testInvestor.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testInvestor.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingInvestor() throws Exception {
        int databaseSizeBeforeUpdate = investorRepository.findAll().size();

        // Create the Investor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvestorMockMvc.perform(put("/api/investors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(investor)))
            .andExpect(status().isBadRequest());

        // Validate the Investor in the database
        List<Investor> investorList = investorRepository.findAll();
        assertThat(investorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvestor() throws Exception {
        // Initialize the database
        investorRepository.saveAndFlush(investor);

        int databaseSizeBeforeDelete = investorRepository.findAll().size();

        // Delete the investor
        restInvestorMockMvc.perform(delete("/api/investors/{id}", investor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Investor> investorList = investorRepository.findAll();
        assertThat(investorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
