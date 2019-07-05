package lh.model;

public class FieldModel {
    /**
     * 属性名称
     */
    private String fieldName;
    /**
     * 属性类型
     */
    private String fieldType;
    /**
     * 属性值
     */
    private Object fieldValue;


    public String getFieldName() {
        return fieldName;
    }

    public FieldModel setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public String getFieldType() {
        return fieldType;
    }

    public FieldModel setFieldType(String fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public FieldModel setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
        return this;
    }
}
