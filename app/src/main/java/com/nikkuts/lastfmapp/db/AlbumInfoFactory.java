package com.nikkuts.lastfmapp.db;

import com.nikkuts.lastfmapp.db.entity.AlbumInfoEntity;
import com.nikkuts.lastfmapp.db.entity.TrackEntity;
import com.nikkuts.lastfmapp.gson.Image;
import com.nikkuts.lastfmapp.gson.albuminfo.Album;
import com.nikkuts.lastfmapp.gson.albuminfo.Track;
import com.nikkuts.lastfmapp.gson.albuminfo.Tracks;
import com.nikkuts.lastfmapp.gson.albuminfo.Wiki;

import java.util.ArrayList;
import java.util.List;

public class AlbumInfoFactory {

    public static Album createAlbumInfoFromEntities(AlbumInfoEntity albumInfoEntity, List<TrackEntity> trackEntities)
    {
        Album album = createAlbumNoTracks(albumInfoEntity);
        album.setTracks(createTracks(trackEntities));
        return album;
    }

    public static Album createAlbumInfoFromTopAlbum(com.nikkuts.lastfmapp.gson.topalbums.Album topAlbum){
        Album album = new Album();
        album.setArtist(topAlbum.getArtist().getName());
        album.setName(topAlbum.getName());

        album.setMbid(topAlbum.getMbid());
        album.setUrl(topAlbum.getUrl());

        Image largeImage = new Image();
        largeImage.setText(topAlbum.getImage().get(Image.LARGE_IMAGE_URL_INDEX).getText());
        Image extralargeImage = new Image();
        extralargeImage.setText(topAlbum.getImage().get(Image.EXTRALARGE_IMAGE_URL_INDEX).getText());

        List<Image> images = new ArrayList<>();
        for (int i = 0; i < Image.IMAGES_COUNT; i++) {
            images.add(new Image());
        }
        images.set(Image.LARGE_IMAGE_URL_INDEX, largeImage);
        images.set(Image.EXTRALARGE_IMAGE_URL_INDEX, extralargeImage);
        album.setImage(images);

        return album;
    }

    private static Album createAlbumNoTracks(AlbumInfoEntity albumInfoEntity){
        Album album = new Album();
        album.setArtist(albumInfoEntity.getArtistName());
        album.setName(albumInfoEntity.getAlbumName());

        album.setMbid(albumInfoEntity.getMbid());
        album.setUrl(albumInfoEntity.getUrl());

        Wiki wiki = new Wiki();
        wiki.setSummary(albumInfoEntity.getWiki());
        album.setWiki(wiki);

        Image largeImage = new Image();
        largeImage.setText(albumInfoEntity.getLargeImage());
        Image extralargeImage = new Image();
        extralargeImage.setText(albumInfoEntity.getExtralargeImage());

        List<Image> images = new ArrayList<>();
        for (int i = 0; i < Image.IMAGES_COUNT; i++) {
            images.add(new Image());
        }
        images.set(Image.LARGE_IMAGE_URL_INDEX, largeImage);
        images.set(Image.EXTRALARGE_IMAGE_URL_INDEX, extralargeImage);
        album.setImage(images);

        return album;
    }

    private static Tracks createTracks(List<TrackEntity> trackEntities){
        Tracks tracks = new Tracks();

        List<Track> trackList = new ArrayList<>();
        for (TrackEntity trackEntity : trackEntities) {
            Track track = new Track();
            track.setName(trackEntity.getTrackName());
            trackList.add(track);
        }

        tracks.setTrack(trackList);
        return tracks;
    }
}
