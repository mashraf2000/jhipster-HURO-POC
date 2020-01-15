package com.huro.web.rest;

import com.huro.HuroPocApp;
import com.huro.domain.Intent;
import com.huro.repository.IntentRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.huro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.huro.domain.enumeration.IntentStatus;
/**
 * Integration tests for the {@link IntentResource} REST controller.
 */
@SpringBootTest(classes = HuroPocApp.class)
public class IntentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_EXPECTED_DATE_OF_COMPLETION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPECTED_DATE_OF_COMPLETION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_FUNDING_AMOUNT_DESIRED = 1L;
    private static final Long UPDATED_FUNDING_AMOUNT_DESIRED = 2L;

    private static final IntentStatus DEFAULT_STATUS = IntentStatus.INQUIRY;
    private static final IntentStatus UPDATED_STATUS = IntentStatus.NEGOTIATE;

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private IntentRepository intentRepository;

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

    private MockMvc restIntentMockMvc;

    private Intent intent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IntentResource intentResource = new IntentResource(intentRepository);
        this.restIntentMockMvc = MockMvcBuilders.standaloneSetup(intentResource)
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
    public static Intent createEntity(EntityManager em) {
        Intent intent = new Intent()
            .name(DEFAULT_NAME)
            .expectedDateOfCompletion(DEFAULT_EXPECTED_DATE_OF_COMPLETION)
            .fundingAmountDesired(DEFAULT_FUNDING_AMOUNT_DESIRED)
            .status(DEFAULT_STATUS)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED);
        return intent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Intent createUpdatedEntity(EntityManager em) {
        Intent intent = new Intent()
            .name(UPDATED_NAME)
            .expectedDateOfCompletion(UPDATED_EXPECTED_DATE_OF_COMPLETION)
            .fundingAmountDesired(UPDATED_FUNDING_AMOUNT_DESIRED)
            .status(UPDATED_STATUS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);
        return intent;
    }

    @BeforeEach
    public void initTest() {
        intent = createEntity(em);
    }

    @Test
    @Transactional
    public void createIntent() throws Exception {
        int databaseSizeBeforeCreate = intentRepository.findAll().size();

        // Create the Intent
        restIntentMockMvc.perform(post("/api/intents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intent)))
            .andExpect(status().isCreated());

        // Validate the Intent in the database
        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeCreate + 1);
        Intent testIntent = intentList.get(intentList.size() - 1);
        assertThat(testIntent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIntent.getExpectedDateOfCompletion()).isEqualTo(DEFAULT_EXPECTED_DATE_OF_COMPLETION);
        assertThat(testIntent.getFundingAmountDesired()).isEqualTo(DEFAULT_FUNDING_AMOUNT_DESIRED);
        assertThat(testIntent.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testIntent.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testIntent.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void createIntentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = intentRepository.findAll().size();

        // Create the Intent with an existing ID
        intent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntentMockMvc.perform(post("/api/intents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intent)))
            .andExpect(status().isBadRequest());

        // Validate the Intent in the database
        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIntents() throws Exception {
        // Initialize the database
        intentRepository.saveAndFlush(intent);

        // Get all the intentList
        restIntentMockMvc.perform(get("/api/intents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].expectedDateOfCompletion").value(hasItem(DEFAULT_EXPECTED_DATE_OF_COMPLETION.toString())))
            .andExpect(jsonPath("$.[*].fundingAmountDesired").value(hasItem(DEFAULT_FUNDING_AMOUNT_DESIRED.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getIntent() throws Exception {
        // Initialize the database
        intentRepository.saveAndFlush(intent);

        // Get the intent
        restIntentMockMvc.perform(get("/api/intents/{id}", intent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(intent.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.expectedDateOfCompletion").value(DEFAULT_EXPECTED_DATE_OF_COMPLETION.toString()))
            .andExpect(jsonPath("$.fundingAmountDesired").value(DEFAULT_FUNDING_AMOUNT_DESIRED.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIntent() throws Exception {
        // Get the intent
        restIntentMockMvc.perform(get("/api/intents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIntent() throws Exception {
        // Initialize the database
        intentRepository.saveAndFlush(intent);

        int databaseSizeBeforeUpdate = intentRepository.findAll().size();

        // Update the intent
        Intent updatedIntent = intentRepository.findById(intent.getId()).get();
        // Disconnect from session so that the updates on updatedIntent are not directly saved in db
        em.detach(updatedIntent);
        updatedIntent
            .name(UPDATED_NAME)
            .expectedDateOfCompletion(UPDATED_EXPECTED_DATE_OF_COMPLETION)
            .fundingAmountDesired(UPDATED_FUNDING_AMOUNT_DESIRED)
            .status(UPDATED_STATUS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);

        restIntentMockMvc.perform(put("/api/intents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIntent)))
            .andExpect(status().isOk());

        // Validate the Intent in the database
        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeUpdate);
        Intent testIntent = intentList.get(intentList.size() - 1);
        assertThat(testIntent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIntent.getExpectedDateOfCompletion()).isEqualTo(UPDATED_EXPECTED_DATE_OF_COMPLETION);
        assertThat(testIntent.getFundingAmountDesired()).isEqualTo(UPDATED_FUNDING_AMOUNT_DESIRED);
        assertThat(testIntent.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testIntent.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testIntent.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingIntent() throws Exception {
        int databaseSizeBeforeUpdate = intentRepository.findAll().size();

        // Create the Intent

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntentMockMvc.perform(put("/api/intents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intent)))
            .andExpect(status().isBadRequest());

        // Validate the Intent in the database
        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIntent() throws Exception {
        // Initialize the database
        intentRepository.saveAndFlush(intent);

        int databaseSizeBeforeDelete = intentRepository.findAll().size();

        // Delete the intent
        restIntentMockMvc.perform(delete("/api/intents/{id}", intent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
