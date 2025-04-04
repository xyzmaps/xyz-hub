/*
 * Copyright (C) 2017-2022 HERE Europe B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 * License-Filename: LICENSE
 */
package com.here.xyz.httpconnector.util.jobs;

import com.fasterxml.jackson.annotation.*;
import com.here.xyz.models.hub.Space;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Import.class , name = "Import"),
        @JsonSubTypes.Type(value = Export.class , name = "Export")
})
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public abstract class Job {
    @JsonView({Public.class})
    public enum Type {
        Import, Export;
        public static Type of(String value) {
            if (value == null) {
                return null;
            }
            try {
                return valueOf(value.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    @JsonView({Public.class})
    public enum Status {
        waiting, queued, validating, validated, preparing, prepared, executing, executed, finalizing, finalized, aborted, failed;
        public static Status of(String value) {
            if (value == null) {
                return null;
            }
            try {
                return valueOf(value.toLowerCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    @JsonView({Public.class})
    public enum CSVFormat {
        GEOJSON,JSON_WKT,JSON_WKB;

        public static CSVFormat of(String value) {
            if (value == null) {
                return null;
            }
            try {
                return valueOf(value.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    @JsonView({Public.class})
    public enum Strategy {
        LASTWINS,SKIPEXISTING,ERROR;

        public static Strategy of(String value) {
            if (value == null) {
                return null;
            }
            try {
                return valueOf(value.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    /**
     * Beta release date: 2018-10-01T00:00Z[UTC]
     */
    @JsonIgnore
    private final long DEFAULT_TIMESTAMP = 1538352000000L;

    /**
     * The creation timestamp.
     */
    @JsonView({Public.class})
    private long createdAt = DEFAULT_TIMESTAMP;

    /**
     * The last update timestamp.
     */
    @JsonView({Public.class})
    private long updatedAt = DEFAULT_TIMESTAMP;

    /**
     * The timestamp which indicates when the execution began.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonView({Public.class})
    private Long executedAt;

    /**
     * The timestamp at which time the finalization is completed.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonView({Public.class})
    private Long finalizedAt;

    /**
     * The expiration timestamp.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonView({Public.class})
    private Long exp;

    /**
     * The job ID
     */
    @JsonView({Public.class})
    private String id;

    @JsonView({Public.class})
    protected String description;

    @JsonView({Internal.class})
    protected String targetSpaceId;

    @JsonView({Internal.class})
    protected String targetTable;

    @JsonView({Public.class})
    private String errorDescription;

    @JsonView({Public.class})
    private String errorType;

    @JsonView({Public.class})
    private Status status;

    @JsonView({Public.class})
    protected CSVFormat csvFormat;

    @JsonView({Public.class})
    protected Strategy strategy;

    @JsonView({Internal.class})
    private String targetConnector;

    @JsonView({Internal.class})
    private Long spaceVersion;

    @JsonView({Internal.class})
    private String author;

    public String getId(){
        return id;
    }

    public void setId(final String id){
        this.id = id;
    }

    public Job withId(final String id) {
        setId(id);
        return this;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public Job withErrorDescription(final String errorDescription) {
        setErrorDescription(errorDescription);
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Job withDescription(final String description) {
        setDescription(description);
        return this;
    }


    public String getTargetSpaceId() {
        return targetSpaceId;
    }

    public void setTargetSpaceId(String targetSpaceId) {
        this.targetSpaceId = targetSpaceId;
    }

    public Job withTargetSpaceId(final String targetSpaceId) {
        setTargetSpaceId(targetSpaceId);
        return this;
    }

    public String getTargetTable() {
        return targetTable;
    }

    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable;
    }

    public Job withTargetTable(final String targetTable) {
        setTargetTable(targetTable);
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Job.Status status) {
        this.status = status;
    }

    public Job withStatus(final Job.Status status) {
        setStatus(status);
        return this;
    }

    public CSVFormat getCsvFormat() {
        return csvFormat;
    }

    public void setCsvFormat(CSVFormat csv_format) {
        this.csvFormat = csv_format;
    }

    public Job withCsvFormat(CSVFormat csv_format) {
        setCsvFormat(csv_format);
        return this;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Job withCsvFormat(Strategy importStrategy) {
        setStrategy(importStrategy);
        return this;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final long createdAt) {
        this.createdAt = createdAt;
    }

    public Job withCreatedAt(final long createdAt) {
        setCreatedAt(createdAt);
        return this;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Job withUpdatedAt(final long updatedAt) {
        setUpdatedAt(updatedAt);
        return this;
    }

    public Long getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(final Long executedAt) {
        this.executedAt = executedAt;
    }

    public Job withExecutedAt(final Long startedAt) {
        setExecutedAt(startedAt);
        return this;
    }

    public Long getFinalizedAt() {
        return finalizedAt;
    }

    public void setFinalizedAt(final Long finalizedAt) {
        this.finalizedAt = finalizedAt;
    }

    public Job withFinalizedAt(final Long finalizedAt) {
        setFinalizedAt(finalizedAt);
        return this;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(final Long exp) {
        this.exp = exp;
    }

    public Job withExp(final Long exp) {
        setExp(exp);
        return this;
    }

    public String getTargetConnector() {
        return targetConnector;
    }

    public void setTargetConnector(String targetConnector) {
        this.targetConnector = targetConnector;
    }

    public Job withTargetConnecto(String targetConnector) {
        setTargetConnector(targetConnector);
        return this;
    }

    public void setErrorType(String errorType){
        this.errorType = errorType;
    }

    public String getErrorType() {
        return errorType;
    }

    public Job withErrorType(String errorType) {
        setErrorType(errorType);
        return this;
    }

    public Long getSpaceVersion() {
        return spaceVersion;
    }

    public void setSpaceVersion(final Long spaceVersion) {
        this.spaceVersion = spaceVersion;
    }

    public Job withSpaceVersion(final long spaceVersion) {
        setSpaceVersion(spaceVersion);
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Job withAuthor(String author) {
        setAuthor(author);
        return this;
    }

    public static class Public {

    }

    public static class Internal extends Space.Internal {

    }

    public Job(){ }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return Objects.equals(id, job.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
