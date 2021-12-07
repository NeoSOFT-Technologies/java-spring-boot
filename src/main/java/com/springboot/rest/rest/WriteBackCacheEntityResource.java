package com.springboot.rest.rest;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rest.domain.dto.WriteBackCacheEntityDTO;
import com.springboot.rest.infrastructure.entity.SampleEntity;
import com.springboot.rest.rest.errors.BadRequestAlertException;
import com.springboot.rest.usecase.sampleentity.CreateWriteThroughCacheEntity;
import com.springboot.rest.usecase.sampleentity.DeleteWriteThroughCacheEntity;
import com.springboot.rest.usecase.sampleentity.ReadWriteThroughCacheEntity;
import com.springboot.rest.usecase.sampleentity.UpdateWriteThroughCacheEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link SampleEntity}.
 */
// @Component
@RestController
@RequestMapping("/api")
public class WriteBackCacheEntityResource {

    private final Logger log = LoggerFactory.getLogger(WriteBackCacheEntityResource.class);

    private static final String ENTITY_NAME = "a";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    //private final SampleEntityServicePort sampleEntityServicePort;

    private final CreateWriteThroughCacheEntity createSampleEntity;
    private final ReadWriteThroughCacheEntity readSampleEntity;
    private final UpdateWriteThroughCacheEntity updateSampleEntity;
    private final DeleteWriteThroughCacheEntity deleteSampleEntity;
    

    public WriteBackCacheEntityResource(CreateWriteThroughCacheEntity createSampleEntity, ReadWriteThroughCacheEntity readSampleEntity, UpdateWriteThroughCacheEntity updateSampleEntity, DeleteWriteThroughCacheEntity deleteSampleEntity) {
        //this.sampleEntityServicePort = sampleEntityServicePort;
        this.createSampleEntity=createSampleEntity;
        this.readSampleEntity=readSampleEntity;
        this.updateSampleEntity=updateSampleEntity;
        this.deleteSampleEntity=deleteSampleEntity;
    }
    
    /*
    // testing with mapper: initialize Mapper Class
	private final SampleEntityMapper sampleEntityMapper;

    public SampleEntityResource(SampleEntityServicePort sampleEntityServicePort, SampleEntityMapper sampleEntityMapper) {
        this.sampleEntityServicePort = sampleEntityServicePort;
        this.sampleEntityMapper = sampleEntityMapper;
    }
    */

    /**
     * {@code POST  /sample-entity} : Create a new a.
     *
     * @parama the a to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new a, or with status {@code 400 (Bad Request)} if the a has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sample-entity")
    @Operation(summary = "/sample-entity", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<WriteBackCacheEntityDTO> createSampleEntity(@RequestBody WriteBackCacheEntityDTO sampleEntityDTO) throws URISyntaxException {
        log.debug("REST request to save A : {}", sampleEntityDTO);
        if (sampleEntityDTO.getId() != null) {
            throw new BadRequestAlertException("A new a cannot already have an ID", ENTITY_NAME, "idexists");
        }

        SampleEntity sampleEntity = createSampleEntity.save(sampleEntityDTO);
        WriteBackCacheEntityDTO sampleEntityDTOResponse = new WriteBackCacheEntityDTO(sampleEntity);
	
        return null;
       
   
      
    }

    /**
     * {@code PUT  /sample-entity/:id} : Updates an existing a.
     *
     * @param id the id of the a to save.
     * @parama the a to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated a,
     * or with status {@code 400 (Bad Request)} if the a is not valid,
     * or with status {@code 500 (Internal Server Error)} if the a couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sample-entity/{id}")
    @Operation(summary = "/sample-entity", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<WriteBackCacheEntityDTO> updateSampleEntity(@PathVariable(value = "id", required = false) final Long id, @RequestBody WriteBackCacheEntityDTO sampleEntityDTO)
            throws URISyntaxException {
        log.debug("REST request to update A : {}, {}", id, sampleEntityDTO);

        SampleEntity sampleEntity = updateSampleEntity.update(id, sampleEntityDTO);
        WriteBackCacheEntityDTO sampleEntityDTOResponse = new WriteBackCacheEntityDTO(sampleEntity);

        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sampleEntityDTOResponse.getId().toString()))
                .body(sampleEntityDTOResponse);
    }

    /**
     * {@code PATCH  /sample-entity/:id} : Partial updates given fields of an existing a, field will ignore if it is null
     *
     * @parama the a to update.
     * @param id the id of the a to save.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated a,
     * or with status {@code 400 (Bad Request)} if the a is not valid,
     * or with status {@code 404 (Not Found)} if the a is not found,
     * or with status {@code 500 (Internal Server Error)} if the a couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sample-entity/{id}", consumes = "application/merge-patch+json")
    @Operation(summary = "/sample-entity", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<SampleEntity> partialUpdateSampleEntity(@PathVariable(value = "id", required = false) final Long id, @RequestBody WriteBackCacheEntityDTO sampleEntityDTO)
            throws URISyntaxException {
        log.debug("REST request to partial update A partially : {}, {}", id, sampleEntityDTO);

        Optional<SampleEntity> result = updateSampleEntity.patch(id, sampleEntityDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sampleEntityDTO.getId().toString())
        );

    }

    /**
     * {@code GET  /sample-entity} : get all the aS.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aS in body.
     */
    @GetMapping("/sample-entity")
    @Operation(summary = "/sample-entity", security = @SecurityRequirement(name = "bearerAuth"))
    public List<WriteBackCacheEntityDTO> getAllSampleEntity() {
        log.debug("REST request to get all SampleEntity");

        List<WriteBackCacheEntityDTO> sampleEntityDTOS = readSampleEntity.findAll()
                .stream()
                .map(a -> new WriteBackCacheEntityDTO(a))
                .collect(Collectors.toList());

        return sampleEntityDTOS;
    }

    /**
     * {@code GET  /sample-entity/:id} : get the "id" a.
     *
     * @param id the id of the a to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the a, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sample-entity/{id}")
    @Operation(summary = "/sample-entitys", security = @SecurityRequirement(name = "bearerAuth"))
    public  Optional<SampleEntity> getA(@PathVariable Long id) {
        log.debug("REST request to get A : {}", id);

        Optional<SampleEntity> a = readSampleEntity.findById(id);
		return a;

 
       
    }

    /**
     * {@code DELETE  /sample-entity/:id} : delete the "id" a.
     *
     * @param id the id of the a to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sample-entity/{id}")
    @Operation(summary = "/sample-entity", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Boolean> deleteA(@PathVariable Long id) {
        log.debug("REST request to delete A : {}", id);
       deleteSampleEntity.deleteById(id);
        
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}