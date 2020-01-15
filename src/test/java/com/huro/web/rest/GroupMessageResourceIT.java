package com.huro.web.rest;

import com.huro.HuroPocApp;
import com.huro.domain.GroupMessage;
import com.huro.repository.GroupMessageRepository;
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

/**
 * Integration tests for the {@link GroupMessageResource} REST controller.
 */
@SpringBootTest(classes = HuroPocApp.class)
public class GroupMessageResourceIT {

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private GroupMessageRepository groupMessageRepository;

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

    private MockMvc restGroupMessageMockMvc;

    private GroupMessage groupMessage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GroupMessageResource groupMessageResource = new GroupMessageResource(groupMessageRepository);
        this.restGroupMessageMockMvc = MockMvcBuilders.standaloneSetup(groupMessageResource)
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
    public static GroupMessage createEntity(EntityManager em) {
        GroupMessage groupMessage = new GroupMessage()
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED);
        return groupMessage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GroupMessage createUpdatedEntity(EntityManager em) {
        GroupMessage groupMessage = new GroupMessage()
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);
        return groupMessage;
    }

    @BeforeEach
    public void initTest() {
        groupMessage = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupMessage() throws Exception {
        int databaseSizeBeforeCreate = groupMessageRepository.findAll().size();

        // Create the GroupMessage
        restGroupMessageMockMvc.perform(post("/api/group-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupMessage)))
            .andExpect(status().isCreated());

        // Validate the GroupMessage in the database
        List<GroupMessage> groupMessageList = groupMessageRepository.findAll();
        assertThat(groupMessageList).hasSize(databaseSizeBeforeCreate + 1);
        GroupMessage testGroupMessage = groupMessageList.get(groupMessageList.size() - 1);
        assertThat(testGroupMessage.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testGroupMessage.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void createGroupMessageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupMessageRepository.findAll().size();

        // Create the GroupMessage with an existing ID
        groupMessage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupMessageMockMvc.perform(post("/api/group-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupMessage)))
            .andExpect(status().isBadRequest());

        // Validate the GroupMessage in the database
        List<GroupMessage> groupMessageList = groupMessageRepository.findAll();
        assertThat(groupMessageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGroupMessages() throws Exception {
        // Initialize the database
        groupMessageRepository.saveAndFlush(groupMessage);

        // Get all the groupMessageList
        restGroupMessageMockMvc.perform(get("/api/group-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())));
    }
    
    @Test
    @Transactional
    public void getGroupMessage() throws Exception {
        // Initialize the database
        groupMessageRepository.saveAndFlush(groupMessage);

        // Get the groupMessage
        restGroupMessageMockMvc.perform(get("/api/group-messages/{id}", groupMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(groupMessage.getId().intValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGroupMessage() throws Exception {
        // Get the groupMessage
        restGroupMessageMockMvc.perform(get("/api/group-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupMessage() throws Exception {
        // Initialize the database
        groupMessageRepository.saveAndFlush(groupMessage);

        int databaseSizeBeforeUpdate = groupMessageRepository.findAll().size();

        // Update the groupMessage
        GroupMessage updatedGroupMessage = groupMessageRepository.findById(groupMessage.getId()).get();
        // Disconnect from session so that the updates on updatedGroupMessage are not directly saved in db
        em.detach(updatedGroupMessage);
        updatedGroupMessage
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED);

        restGroupMessageMockMvc.perform(put("/api/group-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGroupMessage)))
            .andExpect(status().isOk());

        // Validate the GroupMessage in the database
        List<GroupMessage> groupMessageList = groupMessageRepository.findAll();
        assertThat(groupMessageList).hasSize(databaseSizeBeforeUpdate);
        GroupMessage testGroupMessage = groupMessageList.get(groupMessageList.size() - 1);
        assertThat(testGroupMessage.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testGroupMessage.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupMessage() throws Exception {
        int databaseSizeBeforeUpdate = groupMessageRepository.findAll().size();

        // Create the GroupMessage

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupMessageMockMvc.perform(put("/api/group-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupMessage)))
            .andExpect(status().isBadRequest());

        // Validate the GroupMessage in the database
        List<GroupMessage> groupMessageList = groupMessageRepository.findAll();
        assertThat(groupMessageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupMessage() throws Exception {
        // Initialize the database
        groupMessageRepository.saveAndFlush(groupMessage);

        int databaseSizeBeforeDelete = groupMessageRepository.findAll().size();

        // Delete the groupMessage
        restGroupMessageMockMvc.perform(delete("/api/group-messages/{id}", groupMessage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GroupMessage> groupMessageList = groupMessageRepository.findAll();
        assertThat(groupMessageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
