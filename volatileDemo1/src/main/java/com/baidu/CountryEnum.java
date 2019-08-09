package com.baidu;

/**
 * 枚举是jdk1.5出现的特性，他可以当做数据库来用
 * 枚举不只是kv对，他更像是数据版的一个数据库，可以是一个key,后面跟多个值
 *
 */
public enum CountryEnum {
    ONE(1,"齐"),TWO(2,"楚"),THREE(3,"燕"),FOUR(4,"赵"),FIVE(5,"魏"),SIX(6,"韩");
    private Integer retCode;
    private String retMessage;

    CountryEnum(Integer retCode, String retMessage) {
        this.retCode = retCode;
        this.retMessage = retMessage;
    }

    public Integer getRetCode() {

        return retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    //枚举天生带着对自己的一种便利方法，和ArrayList的方法一样
    public static CountryEnum foreach_CountTryEnum(int index){
        CountryEnum[] values = CountryEnum.values();
        for(CountryEnum element:values){
            if(index==element.getRetCode()){
                return element;
            }
        }
        return null;
    }
}
