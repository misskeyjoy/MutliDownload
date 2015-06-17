package misskey.com.entities;

/**
 * 线程信息
 * Created by misskey on 15-6-13.
 */
public class ThreadInfo {
    private int id;
    private String url;
    private int start;
    private int end;
    private int isFinish;
    public  ThreadInfo(){
        super();
    }

    @Override
    public String toString() {
        return "ThreadInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", isFinish=" + isFinish +
                '}';
    }

    public  ThreadInfo(int id, String url,int start,int end,int isFinish){
        this.id=id;
        this.end=end;
        this.start=start;
        this.url=url;
        this.isFinish=isFinish;
    }
    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }

    public int getEnd() {
        return end;
    }

    public int getStart() {
        return start;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
