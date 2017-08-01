package http;

import java.io.File;

/**
 * Created by Yi on 2017/7/27.
 */

public interface DownloadCallback {
    void success(File file);
    void fail(int errorCode,String errorMessage);
    void progress(int progress);
}
