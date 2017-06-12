package pojo;

/**
 * Created by zhans-pc on 2017/6/12.
 */
public class ExcelChecker {
    private Integer row;
    private Integer column;
    private boolean normal=true;
    private String msg;

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public boolean isNormal() {
        return normal;
    }

    public void setNormal(boolean normal) {
        this.normal = normal;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ExcelChecker() {
    }

    public ExcelChecker(int row, int column, boolean normal, String msg) {
        this.row = row;
        this.column = column;
        this.normal = normal;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ExcelChecker{" +
                "row=" + row +
                ", column=" + column +
                ", normal=" + normal +
                ", mgs='" + msg + '\'' +
                '}';
    }
}
