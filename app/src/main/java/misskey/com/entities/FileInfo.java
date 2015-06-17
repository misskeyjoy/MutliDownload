package misskey.com.entities;

import java.io.Serializable;

/**
 * 文件信息
 * Created by misskey on 15-6-13.
 */
public class FileInfo implements Serializable {
    private  int id;
    private String url;
    private String fileName;
    private int length;
    private boolean finished;
    public FileInfo(){
        super();
    }
    public FileInfo(int id,String url,String fileName,int length,boolean finished){
        this.id=id;
        this.url=url;
        this.length=length;
        this.fileName=fileName;
        this.finished=finished;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", length=" + length +
                ", finished=" + finished +
                '}';
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLength() {
        return length;
    }

}
