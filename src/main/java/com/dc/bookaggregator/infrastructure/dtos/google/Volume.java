package com.dc.bookaggregator.infrastructure.dtos.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Volume(String id, VolumeInfo volumeInfo) {
   public Volume{
       Objects.requireNonNull(volumeInfo,"Volume ID must not be null");
       Objects.requireNonNull(volumeInfo,"volumeInfo must not be null");
   }
}