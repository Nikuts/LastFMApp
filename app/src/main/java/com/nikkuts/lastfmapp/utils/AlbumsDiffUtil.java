package com.nikkuts.lastfmapp.utils;

import android.util.Pair;

import com.nikkuts.lastfmapp.db.AlbumWithTracks;
import com.nikkuts.lastfmapp.gson.topalbums.Album;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AlbumsDiffUtil {


    public List<AlbumWithTracks> getDiffList(List<AlbumWithTracks> oldList, List<AlbumWithTracks> newList){
        return Stream.concat(oldList.stream(), newList.stream()).distinct().collect(Collectors.toList());
    }

    public int getLocalToRemoteIndex(AlbumWithTracks localAlbumWithTracks, List<Album> remoteAlbumList){
        int mapIndex = -1;
        if (!mLocalToRemoteMap.containsKey(localAlbumWithTracks)) {
            for (int i = 0; i < remoteAlbumList.size(); i++) {
                if (localAlbumWithTracks.getAlbumInfoEntity().getAlbumName().equals(remoteAlbumList.get(i).getName())){
                    mapIndex = i;
                    mLocalToRemoteMap.put(localAlbumWithTracks, mapIndex);
                }
            }
        } else {
            mapIndex = mLocalToRemoteMap.get(localAlbumWithTracks);
        }
        return mapIndex;
    }

    private Map<AlbumWithTracks, Integer> mLocalToRemoteMap = new HashMap<>();
}
