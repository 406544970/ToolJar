package lh.myenum;

/**
 * 主键枚举
 * @author 梁昊
 * @date:2019/8/27
 */
public enum MainKeySign {
    DICTORYLINE("AA"),//主数据线性
    DICTORYTREE("AB"),//主数据树
    POWER("PO");//全权限

    private String signName;
    MainKeySign(String signName) {
        this.signName = signName;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }
}
