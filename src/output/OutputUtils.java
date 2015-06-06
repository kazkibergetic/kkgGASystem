package output;

import java.io.File;

/**
 * OutputUtils.
 *
 * @author Max
 */
public final class OutputUtils {

    private OutputUtils() {
    }

    public static void mkdirsForPath(String path) {
        File targetFile = new File(path);
        File parent = targetFile.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }
    }
}
