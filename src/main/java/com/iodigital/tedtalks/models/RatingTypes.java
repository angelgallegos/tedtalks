package com.iodigital.tedtalks.models;

import com.iodigital.tedtalks.service.IRatingService;
import com.iodigital.tedtalks.service.SpeakerAverageRatingService;
import com.iodigital.tedtalks.service.SpeakerWilsonRatingService;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RatingTypes {
    AVG("average") {
        @Override
        public Class<? extends IRatingService> getImplementation() {
            return SpeakerAverageRatingService.class;
        }
    },
    WILSON("wilson") {
        @Override
        public Class<? extends IRatingService> getImplementation() {
            return SpeakerWilsonRatingService.class;
        }
    };

    public abstract Class<?> getImplementation();

    public final String type;

    public static RatingTypes findByType(String type){
        for(RatingTypes v : values()){
            if( v.getType().equals(type)){
                return v;
            }
        }
        return null;
    }
}
