package cn.zzuzl.beatbox;

/**
 * Created by zhanglei53 on 2018/1/30.
 */

public class Sound {
    private String path;
    private String name;
    private int loadId;

    public Sound(String path) {
        this.path = path;
        String[] components = path.split("/");
        String filename = components[components.length - 1];
        this.name = filename.replace(".wav", "");
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public int getLoadId() {
        return loadId;
    }

    public void setLoadId(int loadId) {
        this.loadId = loadId;
    }
}
