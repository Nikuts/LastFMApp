package com.nikkuts.lastfmapp.utils;

import com.nikkuts.lastfmapp.db.AlbumWithTracks;
import com.nikkuts.lastfmapp.gson.topalbums.Album;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AlbumsDiffUtil {

    public static List<AlbumWithTracks> getDiffList(List<AlbumWithTracks> oldList, List<AlbumWithTracks> newList){
        return Stream.concat(oldList.stream(), newList.stream()).distinct().collect(Collectors.toList());
    }

    public static int getLocalToRemoteIndex(AlbumWithTracks localAlbumWithTracks, List<Album> remoteAlbumList){
        int mapIndex = -1;
            for (int i = 0; i < remoteAlbumList.size(); i++) {
                if (localAlbumWithTracks.getAlbumInfoEntity().getAlbumName().equals(remoteAlbumList.get(i).getName())){
                    mapIndex = i;
                }
            }
        return mapIndex;
    }

}
