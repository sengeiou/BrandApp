package com.isport.brandapp.login.model;

import java.io.File;

public interface IUploadPhoto {
    void postPhoto(File file);
    void postPhotos(File... files);
}
