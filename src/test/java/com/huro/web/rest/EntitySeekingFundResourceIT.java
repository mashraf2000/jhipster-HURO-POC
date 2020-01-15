package com.huro.web.rest;

import com.huro.HuroPocApp;
import com.huro.domain.EntitySeekingFund;
import com.huro.repository.EntitySeekingFundRepository;
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
 * Integration tests for the {@link EntitySeekingFundResource} REST controller.
 */
@SpringBootTest(classes = HuroPocApp.class)
public class EntitySeekingFundResourceIT {

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
    private EntitySeekingFundRepository entitySeekingFundRepository;

    @Mock
    private EntitySeekingFundRepository entitySeekingFundRepositoryMock;

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

    private MockMvc restEntitySeekingFundMockMvc;

    private EntitySeekingFund entitySeekingFund;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntitySeekingFundResource entitySeekingFundResource = new EntitySeekingFundResource(entitySeekingFundRepository);
        this.restEntitySeekingFundMockMvc = MockMvcBuilders.standaloneSetup(entitySeekingFundResource)
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
    public static EntitySeekingFund createEntity(EntityManager em) {
        EntitySeekingFund entitySeekingFund = new EntitySeekingFund()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .telephoneNumber(DEFAULT_TELEPHONE_NUMBER)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED);
        return entitySeekingFund;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntitySeekingFund createUpdatedEntity(EntityManager em) {
        EntitySeekingFund entitySeekingFund = new EntitySeekingFund()
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .telephoneNumber(UPDATED_TELEPHONE_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);
        return entitySeekingFund;
    }

    @BeforeEach
    public void initTest() {
        entitySeekingFund = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntitySeekingFund() throws Exception {
        int databaseSizeBeforeCreate = entitySeekingFundRepository.findAll().size();

        // Create the EntitySeekingFund
        restEntitySeekingFundMockMvc.perform(post("/api/entity-seeking-funds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entitySeekingFund)))
            .andExpect(status().isCreated());

        // Validate the EntitySeekingFund in the database
        List<EntitySeekingFund> entitySeekingFundList = entitySeekingFundRepository.findAll();
        assertThat(entitySeekingFundList).hasSize(databaseSizeBeforeCreate + 1);
        EntitySeekingFund testEntitySeekingFund = entitySeekingFundList.get(entitySeekingFundList.size() - 1);
        assertThat(testEntitySeekingFund.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEntitySeekingFund.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testEntitySeekingFund.getTelephoneNumber()).isEqualTo(DEFAULT_TELEPHONE_NUMBER);
        assertThat(testEntitySeekingFund.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testEntitySeekingFund.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testEntitySeekingFund.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void createEntitySeekingFundWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entitySeekingFundRepository.findAll().size();

        // Create the EntitySeekingFund with an existing ID
        entitySeekingFund.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntitySeekingFundMockMvc.perform(post("/api/entity-seeking-funds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entitySeekingFund)))
            .andExpect(status().isBadRequest());

        // Validate the EntitySeekingFund in the database
        List<EntitySeekingFund> entitySeekingFundList = entitySeekingFundRepository.findAll();
        assertThat(entitySeekingFundList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEntitySeekingFunds() throws Exception {
        // Initialize the database
        entitySeekingFundRepository.saveAndFlush(entitySeekingFund);

        // Get all the entitySeekingFundList
        restEntitySeekingFundMockMvc.perform(get("/api/entity-seeking-funds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entitySeekingFund.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].telephoneNumber").value(hasItem(DEFAULT_TELEPHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllEntitySeekingFundsWithEagerRelationshipsIsEnabled() throws Exception {
        EntitySeekingFundResource entitySeekingFundResource = new EntitySeekingFundResource(entitySeekingFundRepositoryMock);
        when(entitySeekingFundRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restEntitySeekingFundMockMvc = MockMvcBuilders.standaloneSetup(entitySeekingFundResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEntitySeekingFundMockMvc.perform(get("/api/entity-seeking-funds?eagerload=true"))
        .andExpect(status().isOk());

        verify(entitySeekingFundRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllEntitySeekingFundsWithEagerRelationshipsIsNotEnabled() throws Exception {
        EntitySeekingFundResource entitySeekingFundResource = new EntitySeekingFundResource(entitySeekingFundRepositoryMock);
            when(entitySeekingFundRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restEntitySeekingFundMockMvc = MockMvcBuilders.standaloneSetup(entitySeekingFundResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEntitySeekingFundMockMvc.perform(get("/api/entity-seeking-funds?eagerload=true"))
        .andExpect(status().isOk());

            verify(entitySeekingFundRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getEntitySeekingFund() throws Exception {
        // Initialize the database
        entitySeekingFundRepository.saveAndFlush(entitySeekingFund);

        // Get the entitySeekingFund
        restEntitySeekingFundMockMvc.perform(get("/api/entity-seeking-funds/{id}", entitySeekingFund.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entitySeekingFund.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.telephoneNumber").value(DEFAULT_TELEPHONE_NUMBER))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEntitySeekingFund() throws Exception {
        // Get the entitySeekingFund
        restEntitySeekingFundMockMvc.perform(get("/api/entity-seeking-funds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntitySeekingFund() throws Exception {
        // Initialize the database
        entitySeekingFundRepository.saveAndFlush(entitySeekingFund);

        int databaseSizeBeforeUpdate = entitySeekingFundRepository.findAll().size();

        // Update the entitySeekingFund
        EntitySeekingFund updatedEntitySeekingFund = entitySeekingFundRepository.findById(entitySeekingFund.getId()).get();
        // Disconnect from session so that the updates on updatedEntitySeekingFund are not directly saved in db
        em.detach(updatedEntitySeekingFund);
        updatedEntitySeekingFund
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .telephoneNumber(UPDATED_TELEPHONE_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);

        restEntitySeekingFundMockMvc.perform(put("/api/entity-seeking-funds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntitySeekingFund)))
            .andExpect(status().isOk());

        // Validate the EntitySeekingFund in the database
        List<EntitySeekingFund> entitySeekingFundList = entitySeekingFundRepository.findAll();
        assertThat(entitySeekingFundList).hasSize(databaseSizeBeforeUpdate);
        EntitySeekingFund testEntitySeekingFund = entitySeekingFundList.get(entitySeekingFundList.size() - 1);
        assertThat(testEntitySeekingFund.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEntitySeekingFund.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEntitySeekingFund.getTelephoneNumber()).isEqualTo(UPDATED_TELEPHONE_NUMBER);
        assertThat(testEntitySeekingFund.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testEntitySeekingFund.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testEntitySeekingFund.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingEntitySeekingFund() throws Exception {
        int databaseSizeBeforeUpdate = entitySeekingFundRepository.findAll().size();

        // Create the EntitySeekingFund

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntitySeekingFundMockMvc.perform(put("/api/entity-seeking-funds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entitySeekingFund)))
            .andExpect(status().isBadRequest());

        // Validate the EntitySeekingFund in the database
        List<EntitySeekingFund> entitySeekingFundList = entitySeekingFundRepository.findAll();
        assertThat(entitySeekingFundList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEntitySeekingFund() throws Exception {
        // Initialize the database
        entitySeekingFundRepository.saveAndFlush(entitySeekingFund);

        int databaseSizeBeforeDelete = entitySeekingFundRepository.findAll().size();

        // Delete the entitySeekingFund
        restEntitySeekingFundMockMvc.perform(delete("/api/entity-seeking-funds/{id}", entitySeekingFund.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EntitySeekingFund> entitySeekingFundList = entitySeekingFundRepository.findAll();
        assertThat(entitySeekingFundList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
