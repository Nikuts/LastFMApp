package com.nikkuts.lastfmapp.db;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import com.nikkuts.lastfmapp.db.entity.AlbumInfoEntity;
import com.nikkuts.lastfmapp.db.entity.TrackEntity;

import java.util.List;

public class AlbumWithTracks {

    @Embedded
    private AlbumInfoEntity albumInfoEntity;

    @Relation(parentColumn = "id", entityColumn = "mbid")
    private List<TrackEntity> trackEntities;

    public AlbumInfoEntity getAlbumInfoEntity() {
        return albumInfoEntity;
    }

    public void setAlbumInfoEntity(AlbumInfoEntity albumInfoEntity) {
        this.albumInfoEntity = albumInfoEntity;
    }

    public void setTrackEntities(List<TrackEntity> trackEntities) {
        this.trackEntities = trackEntities;
    }

    public List<TrackEntity> getTrackEntities() {
        return trackEntities;
    }
}
